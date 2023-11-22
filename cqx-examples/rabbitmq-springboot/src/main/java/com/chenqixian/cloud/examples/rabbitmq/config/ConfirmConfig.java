package com.chenqixian.cloud.examples.rabbitmq.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Configuration;

/**
 * @author 53486
 */
@Configuration
public class ConfirmConfig {

    public static final String CONFIRM_QUEUE_NAME = "confirm.queue";

    public static final String CONFIRM_EXCHANGE_NAME = "confirm.exchange";
}
