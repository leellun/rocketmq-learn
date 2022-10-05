package com.newland.rocketmqdemo.utils;

import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.remoting.RPCHook;

/**
 * Author: leell
 * Date: 2022/10/5 20:42:19
 */
public class RocketUtils {
    public static final String NAMESRVADDR="192.168.66.70:8876;192.168.66.70:8878;192.168.66.70:8879";
    // acl用户名和密码
    public static final String ACL_ACCESS_KEY = "RocketMQ";
    public static final String ACL_SECRET_KEY = "12345678";
    /**
     * 将账号密码 在发送消息的时候携带过去
     *
     * @return
     */
    public static RPCHook getAclRPCHook() {
        return new AclClientRPCHook(new SessionCredentials(ACL_ACCESS_KEY, ACL_SECRET_KEY));
    }
}
