package com.yexuejc.alimns.mnstopic;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.SubscriptionMeta;
import com.aliyun.mns.sample.HttpEndpoint;

/**
 * @author: maxf
 * @date: 2018/3/19 11:56
 */
public class SubscribeDemo {
    public static void main(String[] args) {
        CloudAccount account = new CloudAccount(
                "LTAInXldov6WcXlj",
                "UUlRRu67VaH4kmMGixC40C0cB8gYvV",
                "http://1950210790512434.mns.cn-hangzhou.aliyuncs.com");
        MNSClient client = account.getMNSClient(); // 在程序中，CloudAccount以及MNSClient单例实现即可，多线程安全
        CloudTopic topic = client.getTopicRef("TestTopic");
        try {
            SubscriptionMeta subMeta = new SubscriptionMeta();
            subMeta.setSubscriptionName("TestSub");
            subMeta.setEndpoint(HttpEndpoint.GenEndpointLocal());
            subMeta.setNotifyContentFormat(SubscriptionMeta.NotifyContentFormat.XML);
            //subMeta.setFilterTag("filterTag"); //设置订阅的filterTag
            String subUrl = topic.subscribe(subMeta);
            System.out.println("subscription url: " + subUrl);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("subscribe/unsubribe error");
        }
        client.close();
    }
}