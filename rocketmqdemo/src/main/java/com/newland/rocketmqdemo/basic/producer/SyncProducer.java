package com.newland.rocketmqdemo.basic.producer;

import com.newland.rocketmqdemo.utils.RocketUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * Producer端发送同步消息
 * Author: leell
 * Date: 2022/8/20 11:26:19
 */
public class SyncProducer {
    private static final String CONSUMER_GROUP1="please_rename_unique_group_name";
    private static final String TopicTest="TopicTest";
    public static void main(String[] args) throws Exception {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer(CONSUMER_GROUP1, RocketUtils.getAclRPCHook());
        // 设置NameServer的地址
        producer.setNamesrvAddr(RocketUtils.NAMESRVADDR);
        // 启动Producer实例
        producer.start();
        for (int i = 0; i < 100; i++) {
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message(TopicTest,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // 发送消息到一个Broker
            SendResult sendResult = producer.send(msg);
            // 通过sendResult返回消息是否成功送达
            System.out.printf("%s%n", sendResult);
        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }
}
