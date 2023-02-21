/*
 * Created by Wonuk Hwang on 2023/02/21
 * As part of Bigin
 *
 * Copyright (C) Bigin (https://bigin.io/main) - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by infra Team <wonuk_hwang@bigin.io>, 2023/02/21
 */
package com.example.orderservice.dto;

import lombok.Builder;
import lombok.Data;

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
@Data
@Builder
public class Payload {
  private String order_id;
  private String user_id;
  private String product_id;
  private int qty;
  private int unit_price;
  private int total_price;
}
