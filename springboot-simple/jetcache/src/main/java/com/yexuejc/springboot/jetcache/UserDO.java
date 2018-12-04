package com.yexuejc.springboot.jetcache;

import com.yexuejc.base.util.JsonUtil;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author maxf
 * @version 1.0
 * @ClassName UserDO
 * @Description
 * @date 2018/12/3 15:16
 */
@Component("userDO")
public class UserDO implements Serializable {
    private static final long serialVersionUID = 1154537672887501952L;
    private String id;
    private String name;

    public UserDO() {
    }

    public UserDO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return JsonUtil.obj2Json(this);
    }

    public String getId() {
        return id;
    }

    public UserDO setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserDO setName(String name) {
        this.name = name;
        return this;
    }
}
