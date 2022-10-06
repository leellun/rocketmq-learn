package com.newland.rocketmqdemo.basic.consumer.dlx;

import com.newland.rocketmqdemo.utils.RocketUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 死信队列
 * Author: leell
 * Date: 2022/10/6 00:19:43
 */
public class DlxConsumer {
    private static final String CONSUMER_GROUP1 = "consumer_grp_02";

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer(CONSUMER_GROUP1,RocketUtils.getAclRPCHook(),new AllocateMessageQueueAveragely());
        consumer.setNamesrvAddr(RocketUtils.NAMESRVADDR);
        // 指定死信队列主题：%DLQ%+消费组ID
        consumer.subscribe("%DLQ%consumer_grp_01","*");
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
