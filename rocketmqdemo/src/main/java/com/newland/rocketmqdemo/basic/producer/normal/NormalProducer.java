package com.newland.rocketmqdemo.basic.producer.normal;

import com.newland.rocketmqdemo.utils.RocketUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * Author: leell
 * Date: 2022/10/6 00:14:37
 */
public class NormalProducer {
    private static final String CONSUMER_GROUP1 = "group_task";
    private static final String TopicTest = "topic_task";

    public static void main(String[] args) throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(CONSUMER_GROUP1, RocketUtils.getAclRPCHook());
        defaultMQProducer.setNamesrvAddr(RocketUtils.NAMESRVADDR);
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(0);
        defaultMQProducer.start();
        defaultMQProducer.send(new Message(TopicTest, "TAG1", "orderId+" + System.currentTimeMillis(), ("订单消息：" + System.currentTimeMillis()).getBytes()));
        defaultMQProducer.shutdown();
    }
}
