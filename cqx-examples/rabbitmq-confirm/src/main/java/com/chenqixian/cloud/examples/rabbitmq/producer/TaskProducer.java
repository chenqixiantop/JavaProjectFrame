package com.chenqixian.cloud.examples.rabbitmq.producer;

import com.chenqixian.cloud.examples.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @author 53486
 */
public class TaskProducer {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {

        try(Channel channel= RabbitMqUtils.getChannel();) {
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String message = scanner.next();
                channel.basicPublish("", QUEUE_NAME,null, message.getBytes());
                System.out.println("发送消息完成:" + message);
            }
        }
    }
}
