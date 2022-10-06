package com.newland.rocketmqdemo.basic.producer.dlx;

import com.newland.rocketmqdemo.utils.RocketUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * Author: leell
 * Date: 2022/10/6 00:14:37
 */
public class Producer {
    private static final String CONSUMER_GROUP1 = "producer_grp_01";
    private static final String TopicTest = "topic_grp";

    public static void main(String[] args) throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(CONSUMER_GROUP1, RocketUtils.getAclRPCHook());
        defaultMQProducer.setNamesrvAddr(RocketUtils.NAMESRVADDR);
//        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(0);
        //设置同步重试次数
        defaultMQProducer.setRetryTimesWhenSendFailed(0);
        defaultMQProducer.start();
        Message message = new Message(TopicTest, "TAG1", "orderId+" + System.currentTimeMillis(), ("订单消息：" + System.currentTimeMillis()).getBytes());
        message.setDeliverTimeMs(1000L);
        SendResult result = defaultMQProducer.send(message);
        System.out.println(result.getSendStatus());
        defaultMQProducer.shutdown();
    }
}
