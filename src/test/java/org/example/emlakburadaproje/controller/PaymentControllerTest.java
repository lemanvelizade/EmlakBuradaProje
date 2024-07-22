package org.example.emlakburadaproje.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.emlakburadaproje.model.Payment;
import org.example.emlakburadaproje.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    void processPayment_successfully() throws Exception {
        // Given
        Long userId = 1L;
        Double amount = 100.0;
        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setAmount(amount);
        payment.setStatus("SUCCESS");
        payment.setTransactionDate(LocalDateTime.now());

        when(paymentService.processPayment(anyLong(), anyDouble())).thenReturn(payment);

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/payments/process")
                .param("userId", userId.toString())
                .param("amount", amount.toString())
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk());
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Payment responsePayment = objectMapper.readValue(responseBody, Payment.class);

        assertEquals(payment.getUserId(), responsePayment.getUserId());
        assertEquals(payment.getAmount(), responsePayment.getAmount());
        assertEquals(payment.getStatus(), responsePayment.getStatus());
    }
}