package org.example.emlakburadaproje.controller;

import lombok.RequiredArgsConstructor;

import org.example.emlakburadaproje.model.Payment;
import org.example.emlakburadaproje.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {


    private final PaymentService paymentService;

    @PostMapping("/process")
    public Payment processPayment(@RequestParam Long userId, @RequestParam Double amount) {
        return paymentService.processPayment(userId, amount);
    }
}