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

import lombok.AllArgsConstructor;
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

/**
 * Kafka를 사용하기위한 Field class
 */
@Data
@AllArgsConstructor
public class Field {
  private String type;
  private boolean optional;
  private String field;
}
