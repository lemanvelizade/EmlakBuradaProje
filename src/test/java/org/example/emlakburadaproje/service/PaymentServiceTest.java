package org.example.emlakburadaproje.service;

import org.example.emlakburadaproje.model.Payment;
import org.example.emlakburadaproje.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    public void processPayment_successfully() {
        // Given
        Long userId = 1L;
        Double amount = 100.0;

        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setAmount(amount);
        payment.setStatus("SUCCESS");
        payment.setTransactionDate(LocalDateTime.now());

        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        // When
        Payment result = paymentService.processPayment(userId, amount);

        // Then
        assertEquals(userId, result.getUserId());
        assertEquals(amount, result.getAmount());
        assertEquals("SUCCESS", result.getStatus());
        verify(paymentRepository).save(any(Payment.class));
    }
}