package com.yexuejc.springboot.jetcache.web;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.yexuejc.base.http.Resps;
import com.yexuejc.base.util.StrUtil;
import com.yexuejc.springboot.jetcache.UserDO;
import com.yexuejc.springboot.jetcache.service.IndexSrv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author maxf
 * @version 1.0
 * @ClassName IndexCtrl
 * @Description
 * @date 2018/12/3 15:15
 */
@RestController
public class IndexCtrl {
    @Autowired
    IndexSrv indexSrv;
    /**
     * https://github.com/alibaba/jetcache/wiki/CreateCache_CN
     */
    @CreateCache(name = "user-", expire = 100, timeUnit = TimeUnit.MINUTES, cacheType = CacheType.BOTH)
    private Cache<String, UserDO> userCache;

    @RequestMapping("/a")
    public Object addCache() {
        //@Cached(name = "user-", key = "#name")
        long l = System.currentTimeMillis();
        UserDO userDO = new UserDO("1", "張三");
        userCache.put(userDO.getName(), userDO);
        return Resps.success("添加缓存成功").setSucc(l);
    }

    @RequestMapping("/g/{l}")
    public Object getCache(@PathVariable String l) {
        UserDO userDO = userCache.get(l);
        return Resps.success("获取缓存成功").setSucc(userDO);
    }


    @RequestMapping("/p/{l}")
    public Object putCache(@PathVariable Integer l) {
        UserDO id = null;
        if (1 == l) {
            id = indexSrv.addUser("张三123456");
        } else {
            id = indexSrv.addUser(new UserDO("48916149844", "王麻子"));
        }
        return Resps.success("添加缓存成功").setSucc(id);
    }

    @RequestMapping("/s/{l}/{name}")
    public Object selectCache(@PathVariable Integer l, @PathVariable String name) {
        UserDO user = null;
        if (1 == l) {
            user = indexSrv.getUser(name);
        } else {
            user = indexSrv.updUser(new UserDO(StrUtil.genUUID(), name));
        }
        return Resps.success("添加缓存成功").setSucc(user);
    }

    @RequestMapping("/d/{name}")
    public Object selectCache(@PathVariable String name) {
        Long aLong = indexSrv.delUser(new UserDO(StrUtil.genUUID(), name));
        return Resps.success("添加缓存成功").setSucc(aLong);
    }

    @RequestMapping("/c/{id}")
    public Object c(@PathVariable String id) {
        UserDO userDO = indexSrv.loadUser(id);
        return Resps.success("添加缓存成功").setSucc(userDO);
    }

    @RequestMapping("/cd/{id}")
    public Object cd(@PathVariable String id) {
        indexSrv.delUser(id);
        return Resps.success("删除缓存成功");
    }

    @RequestMapping("/con/{username}")
    public Object con(@PathVariable String username) {
        return indexSrv.getConsumer(username);
    }


    @RequestMapping("/login/{username}")
    public Resps login(@PathVariable String username) {
        System.out.println("更新登录信息 ： username = [" + username + "]");
        long l = System.currentTimeMillis();
        System.out.println(l);
        Map<String, Object> m = new HashMap<String, Object>(16);
        m.put("username", username);
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
        m.put("logTime", l);
        indexSrv.loginConsumer(username, m);
        return Resps.success("更新成功").setSucc(l);
    }
}
