package com.chenqixian.cloud.examples.rabbitmq.producer;

import com.chenqixian.cloud.examples.rabbitmq.util.RabbitMqUtils;
import com.chenqixian.cloud.examples.rabbitmq.util.SleepUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.Scanner;

/**
 * @author 53486
 */
public class TaskProducerAck {
    private static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] arg) throws Exception {
        try (Channel channel = RabbitMqUtils.getChannel()) {
            channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入信息：");
            while (sc.hasNext()) {
                String message = sc.nextLine();
                channel.basicPublish("", TASK_QUEUE_NAME, null, message.getBytes("UTF-8"));
                System.out.println("生产者发出消息" + message);
            }
        }
    }
}
