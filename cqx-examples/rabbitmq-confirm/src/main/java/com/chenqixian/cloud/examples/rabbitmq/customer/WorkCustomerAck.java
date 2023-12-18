package com.chenqixian.cloud.examples.rabbitmq.customer;

import com.chenqixian.cloud.examples.rabbitmq.util.RabbitMqUtils;
import com.chenqixian.cloud.examples.rabbitmq.util.SleepUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.Scanner;

/**
 * @author 53486
 */
public class WorkCustomerAck {

    private static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] arg) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);

        System.out.println("C1等待接收消息");
        DeliverCallback deliverCallback = ((consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            SleepUtils.sleep(10);
            System.out.println("接收消息"+ message);
            // false 只应答接收到的哪个传递的消息
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        });
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, consumerTag -> {
            System.out.println(consumerTag + "消费者取消消费接口回调逻辑");
        });
    }
}
