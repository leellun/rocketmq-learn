package com.newland.rocketmqdemo.basic.consumer.brocast;

import com.newland.rocketmqdemo.utils.RocketUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * Author: leell
 * Date: 2022/10/5 23:58:53
 */
public class BrocastConsumer2 {
    private static final String CONSUMER_GROUP1 = "group_brocast";
    private static final String TopicTest = "topic_brocast";

    public static void main(String[] args) throws Exception {
        // 实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP1, RocketUtils.getAclRPCHook(),new AllocateMessageQueueAveragely());

        // 设置NameServer的地址
        consumer.setNamesrvAddr(RocketUtils.NAMESRVADDR);
        //设置   广播模式
        consumer.setMessageModel(MessageModel.BROADCASTING);
        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
        consumer.subscribe(TopicTest, "*");
        // 注册回调实现类来处理从broker拉取回来的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者实例
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
