/*
 * Created by Wonuk Hwang on 2023/02/12
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by infra Team <wonuk_hwang@bigin.io>, 2023/02/12
 */
package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.messagequeue.KafkaProducer;
import com.example.orderservice.messagequeue.OrderProducer;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create on 2023/02/12. create by IntelliJ IDEA.
 *
 * <p> </p>
 * <p> {@link } and {@link } </p> *
 *
 * @author wonukHwang
 * @version 1.0
 * @see
 * @since (ex : 5 + 5)
 */
@RestController
@RequestMapping("/order-service")
@Slf4j
public class OrderController {
  private Environment env;
  private OrderService orderService;
  private KafkaProducer kafkaProducer;

  private OrderProducer orderProducer;

  @Autowired
  public OrderController(Environment env, OrderService orderService, KafkaProducer kafkaProducer, OrderProducer orderProducer) {
    this.env = env;
    this.orderService = orderService;
    this.kafkaProducer = kafkaProducer;
    this.orderProducer = orderProducer;
  }

  @GetMapping("/health_check")
  public String status() {
    return String.format("It's Working in order Service on PORT %s", env.getProperty("local.server.port"));
  }

  // http://127.0.0.1:0/order-service/{user_id}/orders
  @PostMapping("/{userId}/orders")
  public ResponseEntity<ResponseOrder> createOrder(
      @PathVariable("userId") String userId,
      @RequestBody RequestOrder orderDetails
  ) {
    log.info("Before add orders data");
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    OrderDto orderDto = mapper.map(orderDetails, OrderDto.class);
    orderDto.setUserId(userId);

    /* JPA */
    OrderDto createdOrder = orderService.createOrder(orderDto);
    ResponseOrder responseOrder = mapper.map(createdOrder, ResponseOrder.class);

    /* Kafka */
//    orderDto.setOrderId(UUID.randomUUID().toString());
//    orderDto.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());

    /* Send this order to the kafka */
    kafkaProducer.send("example-category-topic", orderDto);
//    orderProducer.send("orders", orderDto);

//    ResponseOrder responseOrder = mapper.map(orderDto, ResponseOrder.class);

    log.info("After added orders data");
    return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
  }

  @GetMapping("/{userId}/orders")
  public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId) {
    log.info("Before retrieve orders data");
    Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);

    List<ResponseOrder> result = new ArrayList<>();
    orderList.forEach(v -> {
      result.add(new ModelMapper().map(v, ResponseOrder.class));
    });

    log.info("Add retrieved orders data");

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
