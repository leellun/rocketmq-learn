package com.newland.rocketmqdemo.basic.producer;

import com.newland.rocketmqdemo.utils.RocketUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 单向发送消息
 * Author: leell
 * Date: 2022/8/20 11:27:19
 */
public class OnewayProducer {
    private static final String CONSUMER_GROUP1 = "please_rename_unique_group_name";
    private static final String TopicTest = "TopicTest";

    public static void main(String[] args) throws Exception {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer(CONSUMER_GROUP1, RocketUtils.getAclRPCHook());
        // 设置NameServer的地址
        producer.setNamesrvAddr(RocketUtils.NAMESRVADDR);
        // 启动Producer实例
        producer.start();
        for (int i = 0; i < 1; i++) {
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message(TopicTest, "TagA",
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // 发送单向消息，没有任何返回结果
            producer.sendOneway(msg);

        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }
}
