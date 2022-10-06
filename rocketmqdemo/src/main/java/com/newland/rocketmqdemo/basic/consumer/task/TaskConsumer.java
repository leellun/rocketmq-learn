package com.newland.rocketmqdemo.basic.consumer.task;

import com.newland.rocketmqdemo.utils.RocketUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 定时/延迟消息
 * Author: leell
 * Date: 2022/10/6 00:42:22
 */
public class TaskConsumer {
    private static final String CONSUMER_GROUP1 = "group_task";
    private static final String TopicTest = "topic_task";
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer(CONSUMER_GROUP1, RocketUtils.getAclRPCHook(),new AllocateMessageQueueAveragely());
        consumer.setNamesrvAddr(RocketUtils.NAMESRVADDR);
        consumer.subscribe(TopicTest,"*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}
