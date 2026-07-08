package pwr.karmil.lab07catering.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.karmil.lab07catering.database.Order;
import pwr.karmil.lab07catering.database.OrderRepository;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Strona Administratora", description = "Operacje: akceptacja płatności i symulacja czasu")
public class AdminController {

    @Autowired private OrderRepository orderRepository;

    @Operation(summary = "Akceptuj płatność za zamówienie",
            description = "Zmienia status zamówienia z 'OCZEKIWANIE' na 'ZAPLACONE'")
    @PutMapping("/orders/{id}/accept-payment")
    public ResponseEntity<Order> acceptPayment(@PathVariable Integer id) {
        return orderRepository.findById(id).map(order -> {
            if ("OCZEKIWANIE".equals(order.getState())) {
                order.setState("ZAPLACONE");
                return ResponseEntity.ok(orderRepository.save(order));
            }
            return ResponseEntity.badRequest().<Order>build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Symulacja czasu dostawy",
            description = "Zmienia status wszystkich opłaconych na 'DOSTARCZONE', jeśli ustawiona data jest odpowiednia..")
    @PostMapping("/set-time")
    public ResponseEntity<String> setTime(@RequestParam String date) {
        Instant setDate = Instant.parse(date);
        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {
            if ("ZAPLACONE".equals(order.getState()) && order.getDate().isBefore(setDate)) {
                order.setState("DOSTARCZONE");
                orderRepository.save(order);
            }
        }
        return ResponseEntity.ok("Symulacja czasu udana");
    }
}
