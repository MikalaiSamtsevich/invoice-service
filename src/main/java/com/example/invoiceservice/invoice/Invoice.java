package com.example.invoiceservice.invoice;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record Invoice(Long id, ZonedDateTime orderDate, BigDecimal totalAmount) {
}

