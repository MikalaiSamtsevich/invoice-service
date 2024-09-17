package com.example.invoiceservice.invoice;

import com.example.invoiceservice.order.Order;
import com.example.invoiceservice.order.OrderServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
@Slf4j
public class InvoiceController {

    private final OrderServiceClient orderServiceClient;

    @SneakyThrows
    @GetMapping(value = "/{id}/{sleep}")
    public Invoice getInvoice(@PathVariable Long id, @PathVariable Long sleep) {
        Order order = this.orderServiceClient.getOrder(id, sleep);
        log.info("Invoice saved!");
        return new Invoice(id, order.orderDate(), order.totalAmount());
    }

    @ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<String> handleConflict(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}

