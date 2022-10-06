package com.newland.rocketmqdemo.basic.producer.transaction;

import com.newland.rocketmqdemo.utils.RocketUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * 事务消息
 * Author: leell
 * Date: 2022/10/6 00:50:56
 */
public class TransactionProducer {
    private static final String CONSUMER_GROUP1 = "group_transaction";
    private static final String TopicTest = "topic_transaction";
    public static void main(String[] args) throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(CONSUMER_GROUP1, RocketUtils.getAclRPCHook());
        defaultMQProducer.setNamesrvAddr(RocketUtils.NAMESRVADDR);
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(0);
        defaultMQProducer.start();
        Message message = new Message(TopicTest, "TAG1", "orderId+" + System.currentTimeMillis(), ("订单消息：" + System.currentTimeMillis()).getBytes());
        defaultMQProducer.send(message);
        defaultMQProducer.shutdown();
    }
    //演示demo，模拟订单表查询服务，用来确认订单事务是否提交成功。
    private static boolean checkOrderById(String orderId) {
        return true;
    }

    //演示demo，模拟本地事务的执行结果。
    private static boolean doLocalTransaction() {
        return true;
    }
}
