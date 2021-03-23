package com.Ecommmerce.payment;


import java.math.BigDecimal;

public interface PaymentService {
    public boolean pay(PaymentCard card, BigDecimal amount);
}