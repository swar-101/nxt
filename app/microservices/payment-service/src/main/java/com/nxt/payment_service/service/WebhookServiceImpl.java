package com.nxt.payment_service.service;

import org.springframework.stereotype.Service;

@Service

public class WebhookServiceImpl implements WebhookService {
    @Override
    public boolean verifyRazorpaySignature(String payload, String actualSignature) {
        return false;
    }

    @Override
    public boolean verifyStripeSignature(String payload, String sigHeader) {
        // TODO: Use Stripe SDK for verification
        // Placeholder fallback logic
        return sigHeader != null && sigHeader.contains("t=") && sigHeader.contains("v1=");
    }

    @Override
    public void processStripeEvent(String payload) {
        System.out.println("✅ Stripe Payload: " + payload);
        // TODO: parse event, update DB
    }

    @Override
    public void processRazorpayEvent(String payload) {
        System.out.println("✅ Razorpay Payload: " + payload);
        // TODO: parse event, update DB
    }
}
