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
 * 普通消息消费者
 * Author: leell
 * Date: 2022/10/6 00:19:43
 */
public class Consumer {
    private static final String CONSUMER_GROUP1 = "consumer_grp_01";
    private static final String TopicTest = "topic_grp";

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP1, RocketUtils.getAclRPCHook(), new AllocateMessageQueueAveragely());
        consumer.setNamesrvAddr(RocketUtils.NAMESRVADDR);
//        consumer.setMaxReconsumeTimes(1);
        consumer.subscribe(TopicTest, "*");
        /**
         * 推送消息 提高消费处理能力
         * 1 提高消费并行度
         * 2 以批量方式进行 消费
         * 3 检测延时情况,跳过非重要消息
         */
        //消费限流 只针对推送来设置,拉取消息自己控制
        // 1 提高消费并行度
//        consumer.setConsumeThreadMax(10);
//        consumer.setConsumeThreadMin(1);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
        consumer.start();
    }
}
