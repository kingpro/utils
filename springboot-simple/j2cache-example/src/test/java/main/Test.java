package main;

import com.yexuejc.base.util.JsonUtil;
import com.yexuejc.base.util.SysUtil;
import net.oschina.j2cache.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maxf
 * @version 1.0
 * @ClassName Test
 * @Description
 * @date 2018/12/3 11:15
 */
public class Test {
    public static void main(String[] args) throws IOException {
//        testCache();
//        testCache2redis();
        CacheChannel cache = J2Cache.getChannel();
        Map<String, Object> m = new HashMap<String, Object>(16);
        m.put("username", "18202837563");
        m.put("umengToken", "068fee4e8e32473a9b9b82f5844c3c6b");
        m.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJmMGY0YjA0YzllNWY0ZTdkYWNiNzliZmVhMDVjYmExMSIsImlhdCI6MTU0MzgwOTY2MCwiaXNzIjoibWN3b3JsZS5jb20iLCJzdWIiOiJleUoxYzJWeWJtRnRaU0k2SWpFNE1qQXlPRE0zTlRZeklpd2lZMjl1YzNWdFpYSkpaQ0k2SWpGallURTRaamMzWkRNMVlUUmhPVEZoXG5aREU1WVdReE9EZzNPR0l3TjJGbElpd2libWxqYTI1aGJXVWlPaUlpTENKcGJXRm5aU0k2SWlKOSJ9.ZnH9OYpeP9D4Xxg6YySklGYumcoONJo8HbGe3SBoKTSp0ewHNKi-QBHF1-f3Zbw1nmCYZkIqLna7nllxeVj68A");
        List<String> roels = new ArrayList<>();
        roels.add("ROLE_CONSUMER");
        roels.add("ROLE_CONSUMER_ACTIVATED");
        roels.add("ROLE_CONSUMER_CASHOUT");
        roels.add("ROLE_CONSUMER_REAL");
        roels.add("ROLE_CONSUMER_STANDARDS");
        m.put("roles", roels);
        m.put("id", "1ca18f77d35a4a91ad19ad18878b07ae");
        m.put("code", "001004001004");
        m.put("app", "ANDROID");
        cache.set("consumer-session" + "-" + "18202837563", m);
        Map<String, CacheObject> map = cache.get("consumer-session" + "-" + "18202837563", m.keySet());
        System.out.println(map);
        System.out.println(map.get("username"));
        roels = (List<String>) map.get("roles").getValue();
        System.out.println(JsonUtil.obj2Json(roels));
        System.out.println(map.get("roles"));
    }

    /**
     * 键入test.key1 后立即修改redis的test.key1值，20s后取出的为一级缓存值
     *
     * @throws IOException
     */
    private static void testCache2redis() throws IOException {
        J2CacheConfig config = J2CacheConfig.initFromConfig("/j2cache.properties");
        //填充 config 变量所需的配置信息
        J2CacheBuilder builder = J2CacheBuilder.init(config);
        CacheChannel channel = builder.getChannel();
        channel.set("test", "key1", "我是测试");
        System.out.println("键入test.key1");
        SysUtil.threadRun(() -> {
            System.out.println("读取test.key1....");
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(channel.get("test", "key1"));
            //进行缓存的操作
            channel.close();
        });
    }

    private static void testCache() {
        CacheChannel cache = J2Cache.getChannel();

        //缓存操作
//        cache.set("default", "1", "Hello J2Cache");
//        System.out.println(cache.get("default", "1"));
//        cache.evict("default", "1");
        System.out.println(cache.get("default", "1"));

        cache.close();
    }
}
