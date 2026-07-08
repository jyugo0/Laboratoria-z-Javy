package pwr.karmil.lab07catering.controller;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.karmil.lab07catering.database.*;
import pwr.karmil.lab07catering.dto.ElementsSelection;
import pwr.karmil.lab07catering.dto.OrderRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private MenuRepository menuRepository;
    @Autowired private OrderElementRepository orderElementRepository;

    @Operation(summary = "Złóż zamówienie podając nazwy", description = "Automatycznie oblicza koszt i przypisuje elementy.")
    @PostMapping("/place-order")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Nie ma takiego użytkownika"));

        Order order = new Order();
        order.setUser(user);
        order.setState("NIEOPLACONE");
        order.setDate(request.getDeliveryDate());

        BigDecimal total = BigDecimal.ZERO;
        List<OrderElement> elements = new ArrayList<>();

        for (ElementsSelection item : request.getItems()) {
            Menu menu = menuRepository.findByName(item.getMenuName())
                    .orElseThrow(() -> new RuntimeException("Nie ma pozycji: " + item.getMenuName()));

            BigDecimal cost = BigDecimal.valueOf(menu.getPrice()).multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(cost);

            OrderElement element = new OrderElement();
            element.setItem(menu);
            element.setHowMany(item.getQuantity());
            element.setOrder(order);
            elements.add(element);
        }

        order.setCost(total);
        Order savedOrder = orderRepository.save(order);
        orderElementRepository.saveAll(elements);

        return ResponseEntity.ok(savedOrder);
    }

    @Operation(summary = "Opłać zamówienie", description = "Zmienia stan zamówienia z NIEOPLACONE na OCZEKIWANIE.")
    @PutMapping("/{id}/pay")
    public ResponseEntity<?> payForOrder(@PathVariable Integer id) {
        return orderRepository.findById(id).map(order -> {
            if (!"NIEOPLACONE".equalsIgnoreCase(order.getState())) {
                return ResponseEntity.badRequest()
                        .body("Można opłacić tylko zamówienia NIEOPLACONE. Obecny stan: " + order.getState());
            }

            order.setState("OCZEKIWANIE");
            orderRepository.save(order);
            return ResponseEntity.ok("Zamówienie nr " + id + " zostało opłacone i oczekuje na akceptację.");
        }).orElse(ResponseEntity.notFound().build());
    }
}