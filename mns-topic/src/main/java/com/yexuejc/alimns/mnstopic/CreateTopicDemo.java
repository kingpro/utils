package com.yexuejc.alimns.mnstopic;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.TopicMeta;

/**
 * @author: maxf
 * @date: 2018/3/19 11:53
 */
public class CreateTopicDemo {
    public static void main(String[] args) {
        CloudAccount account = new CloudAccount(
                "LTAInXldov6WcXlj",
                "UUlRRu67VaH4kmMGixC40C0cB8gYvV",
                "http://1950210790512434.mns.cn-hangzhou.aliyuncs.com");
        MNSClient client = account.getMNSClient(); // 在程序中，CloudAccount以及MNSClient单例实现即可，多线程安全
        String topicName = "TestTopic";
        TopicMeta meta = new TopicMeta();
        meta.setTopicName(topicName);
        try {
            CloudTopic topic = client.createTopic(meta);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("create topic error, " + e.getMessage());
        }
        client.close();
    }
}