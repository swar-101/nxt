package com.nxt.payment_service.service;

import com.nxt.payment_service.client.PaymentGatewayClient;
import com.nxt.payment_service.strategy.PaymentGatewayStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentGatewayStrategy paymentGatewayStrategy;

    @Autowired
    public PaymentServiceImpl(PaymentGatewayStrategy paymentGatewayStrategy) {
        this.paymentGatewayStrategy = paymentGatewayStrategy;
    }

    @Override
    public String getPaymentLink(Long amount, String orderId, String phoneNumber, String name) {
        PaymentGatewayClient paymentGateway = paymentGatewayStrategy.getOptimalPaymentGateway();
        return paymentGateway.getPaymentLink(amount, orderId, phoneNumber, name);
    }
}