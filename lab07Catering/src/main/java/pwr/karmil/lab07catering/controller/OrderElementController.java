package pwr.karmil.lab07catering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pwr.karmil.lab07catering.database.OrderElement;
import pwr.karmil.lab07catering.database.OrderElementRepository;

import java.util.List;

@RestController
@RequestMapping("/api/order-elements")
public class OrderElementController {

    @Autowired
    private OrderElementRepository orderElementRepository;

    @GetMapping
    public List<OrderElement> getAllOrderElements() {
        return orderElementRepository.findAll();
    }

}
