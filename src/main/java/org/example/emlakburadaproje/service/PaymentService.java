package org.example.emlakburadaproje.service;

import lombok.RequiredArgsConstructor;

import org.example.emlakburadaproje.model.Payment;
import org.example.emlakburadaproje.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {


    private final PaymentRepository paymentRepository;

    public Payment processPayment(Long userId, Double amount) {
        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setAmount(amount);
        payment.setStatus("SUCCESS");
        payment.setTransactionDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }
}