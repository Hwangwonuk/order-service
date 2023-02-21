/*
 * Created by Wonuk Hwang on 2023/02/21
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by infra Team <wonuk_hwang@bigin.io>, 2023/02/21
 */
package com.example.orderservice.messagequeue;

import com.example.orderservice.dto.Field;
import com.example.orderservice.dto.KafkaOrderDto;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.Payload;
import com.example.orderservice.dto.Schema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * create on 2023/02/21. create by IntelliJ IDEA.
 *
 * <p> </p>
 * <p> {@link } and {@link } </p> *
 *
 * @author wonukHwang
 * @version 1.0
 * @see
 * @since (ex : 5 + 5)
 */
@Service
@Slf4j
public class OrderProducer {

  private KafkaTemplate<String, String> kafkaTemplate;

  List<Field> fileds = Arrays.asList(new Field("string", true, "order_id"),
      new Field("string", true, "user_id"),
      new Field("string", true, "product_id"),
      new Field("int32", true, "qty"),
      new Field("int32", true, "unit_price"),
      new Field("int32", true, "total_price"));

  Schema schema = Schema.builder()
      .type("struct")
      .fields(fileds)
      .optional(false)
      .name("orders")
      .build();

  @Autowired
  public OrderProducer(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public OrderDto send(String topic, OrderDto orderDto) {
    Payload payload = Payload.builder()
        .order_id(orderDto.getOrderId())
        .user_id(orderDto.getUserId())
        .product_id(orderDto.getProductId())
        .qty(orderDto.getQty())
        .unit_price(orderDto.getUnitPrice())
        .total_price(orderDto.getTotalPrice())
        .build();

    KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema, payload);

    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = "";
    try {
      jsonInString = mapper.writeValueAsString(kafkaOrderDto);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
    }

    kafkaTemplate.send(topic, jsonInString);
    log.info("Order Producer sent data from the Order microservice: " + kafkaOrderDto);

    return orderDto;
  }
}
