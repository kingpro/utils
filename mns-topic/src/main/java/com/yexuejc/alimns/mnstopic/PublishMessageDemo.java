package com.yexuejc.alimns.mnstopic;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Base64TopicMessage;
import com.aliyun.mns.model.TopicMessage;

/**
 * @author: maxf
 * @date: 2018/3/19 11:58
 */
public class PublishMessageDemo {
    public static void main(String[] args) {
        CloudAccount account = new CloudAccount(
                "LTAInXldov6WcXlj",
                "UUlRRu67VaH4kmMGixC40C0cB8gYvV",
                "http://1950210790512434.mns.cn-hangzhou.aliyuncs.com");
        MNSClient client = account.getMNSClient(); // 在程序中，CloudAccount以及MNSClient单例实现即可，多线程安全
        CloudTopic topic = client.getTopicRef("TestTopic");
        try {
            TopicMessage msg = new Base64TopicMessage(); //可以使用TopicMessage结构，选择不进行Base64加密
            msg.setMessageBody("hello world!");
            //msg.setMessageTag("filterTag"); //设置该条发布消息的filterTag
            msg = topic.publishMessage(msg);
            System.out.println(msg.getMessageId());
            System.out.println(msg.getMessageBodyMD5());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("subscribe error");
        }
        client.close();
    }
}