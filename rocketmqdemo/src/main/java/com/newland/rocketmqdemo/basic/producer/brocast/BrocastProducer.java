package com.newland.rocketmqdemo.basic.producer.brocast;

import com.newland.rocketmqdemo.utils.RocketUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * 广播消息
 * Author: leell
 * Date: 2022/10/5 23:52:15
 */
public class BrocastProducer {
    private static final String CONSUMER_GROUP1 = "group_brocast";
    private static final String TopicTest = "topic_brocast";

    public static void main(String[] args) throws Exception {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer(CONSUMER_GROUP1, RocketUtils.getAclRPCHook());
        // 设置NameServer的地址
        producer.setNamesrvAddr(RocketUtils.NAMESRVADDR);
        producer.start();
        for (int i = 0; i < 10; i++) {
            // 加个时间前缀
            String body = System.currentTimeMillis() + " Hello RocketMQ " + i;
            Message msg = new Message(TopicTest, "TAG1", "KEY" + i, body.getBytes());
            producer.send(msg);
        }
    }
}
