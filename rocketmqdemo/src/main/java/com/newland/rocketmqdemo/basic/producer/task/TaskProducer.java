package com.newland.rocketmqdemo.basic.producer.task;

import com.newland.rocketmqdemo.utils.RocketUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * 定时/延迟消息
 * Author: leell
 * Date: 2022/10/6 00:42:53
 */
public class TaskProducer {
    private static final String CONSUMER_GROUP1 = "group_task";
    private static final String TopicTest = "topic_task";

    public static void main(String[] args) throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(CONSUMER_GROUP1, RocketUtils.getAclRPCHook());
        defaultMQProducer.setNamesrvAddr(RocketUtils.NAMESRVADDR);
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(0);
        //以下示例表示：延迟时间为10分钟之后的Unix时间戳。
        Long deliverTimeStamp = System.currentTimeMillis() + 10L * 60 * 1000;
        defaultMQProducer.start();
        Message message = new Message(TopicTest, "TAG1", "orderId+" + System.currentTimeMillis(), ("订单消息：" + System.currentTimeMillis()).getBytes());
//        message.setDelayTimeLevel(3);
//        message.setDelayTimeMs(1 * 1000);
        message.setDelayTimeMs(20 * 1000);
        SendResult result = defaultMQProducer.send(message);
        System.out.println(result.getSendStatus());
        defaultMQProducer.shutdown();
    }
}
