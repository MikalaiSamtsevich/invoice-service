package com.example.invoiceservice.order;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OrderServiceClient {

    private final RestTemplate restTemplate;

    public Order getOrder(Long id, Long sleep) {
        return this.restTemplate
                .getForObject("/orders/" + id + "/" + sleep, Order.class);
    }

}
