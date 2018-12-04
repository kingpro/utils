package com.yexuejc.springboot.jetcache.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.yexuejc.springboot.jetcache.UserDO;

import java.util.Map;

/**
 * @author maxf
 * @version 1.0
 * @ClassName IndexSrv
 * @Description
 * @date 2018/12/3 16:09
 */
public interface IndexSrv {
    @Cached(name = "user-", key = "#name")
    UserDO addUser(String name);

    @Cached(name = "user-", key = "#user.name")
    UserDO addUser(UserDO user);

    @Cached(name = "user-", key = "#name")
    UserDO getUser(String name);

    @CacheUpdate(name = "user-", key = "#user.name", value = "#user")
    UserDO updUser(UserDO user);

    @CacheInvalidate(name = "user-", key = "#user.name")
    Long delUser(UserDO user);


    @Cached(name = "loadUser")
    UserDO loadUser(String id);


    @CacheInvalidate(name = "loadUser")
    void delUser(String id);


    /***
     * 缓存的是返回值
     * @param username
     * @return
     */
    @Cached(name = "consumer-session-", key = "#username")
    Map getConsumer(String username);


    /**
     * 用#m更新consumer-session-#username
     *
     * @param username
     * @param m
     */
    @CacheUpdate(name = "consumer-session-", key = "#username", value = "#m")
    void loginConsumer(String username, Map<String, Object> m);
}
