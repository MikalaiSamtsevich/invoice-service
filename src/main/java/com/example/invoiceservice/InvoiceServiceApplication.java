package com.example.invoiceservice;

import com.example.invoiceservice.order.Order;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RegisterReflectionForBinding({Order.class})
public class InvoiceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvoiceServiceApplication.class, args);
    }

    @Bean
    public RecordMessageConverter converter(){
        return new JsonMessageConverter();
    }

    @Bean
    RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.rootUri("http://localhost:8085").build();
    }

    @Bean
    public NewTopic topic(){
        return TopicBuilder.name("public.order.orders.v1")
                .partitions(1)
                .replicas(1)
                .build();
    }
}