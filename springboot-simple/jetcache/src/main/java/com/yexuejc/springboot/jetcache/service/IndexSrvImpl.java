package com.yexuejc.springboot.jetcache.service;

import com.yexuejc.base.util.StrUtil;
import com.yexuejc.springboot.jetcache.UserDO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author maxf
 * @version 1.0
 * @ClassName IndexSrvImpl
 * @Description
 * @date 2018/12/3 16:09
 */
@Service
public class IndexSrvImpl implements IndexSrv {
    @Override
    public UserDO addUser(String name) {
        UserDO userDO = new UserDO(StrUtil.genUUID(), name);
        System.out.println("新增-------" + name);
        return userDO;
    }

    @Override
    public UserDO addUser(UserDO user) {
        System.out.println("新增2-------" + user);
        return user;
    }

    @Override
    public UserDO getUser(String name) {
        System.out.println("获取-------" + name);
        return null;
    }

    @Override
    public UserDO updUser(UserDO user) {
        System.out.println("更新-------" + user);
        return null;
    }

    @Override
    public Long delUser(UserDO user) {
        System.out.println("删除-------" + user);
        return null;
    }

    @Override
    public UserDO loadUser(String id) {
        System.out.println("load user: " + id);
        UserDO user = new UserDO();
        user.setId(id);
        user.setName("user" + id);
        return user;
    }

    @Override
    public void delUser(String id) {
        System.out.println("delete user: " + id);
    }

    @Override
    public Map getConsumer(String username) {
        System.out.println("没从缓存里面取 ： username = [" + username + "]");
        long l = System.currentTimeMillis();
        System.out.println(l);
        Map<String, Object> m = new HashMap<String, Object>(16);
        return m;
    }

    @Override
    public void loginConsumer(String username, Map<String, Object> m) {
        
    }
}
