package com.chenqixian.cloud.examples.rabbitmq.customer;


import com.chenqixian.cloud.examples.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

/**
 * @author 53486
 */
public class WorkCustomer {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        DeliverCallback deliverCallback =  (consumerTag, delivery) -> {
            String receivedMessage = new String(delivery.getBody());
            System.out.println("接收到消息:"+receivedMessage);
        };

        CancelCallback cancelCallback = (consumerTag -> {
           System.out.println(consumerTag + "消费者取消消费接口回调逻辑");
        });

        System.out.println("C2消费者启动等待消费");
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
