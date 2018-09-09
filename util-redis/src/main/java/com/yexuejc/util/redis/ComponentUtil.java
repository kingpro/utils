package com.yexuejc.util.redis;

import com.yexuejc.base.util.JsonUtil;
import com.yexuejc.util.redis.autoconfigur.AliPushFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 跨层通用处理component
 *
 * @PackageName: com.mcworle.ecentm.api.user.util
 * @Description:
 * @author: maxf
 * @date: 2018/2/12 14:52
 */
@Component
public class ComponentUtil {
    @Autowired
    AliPushFacade aliPushFacade;

    /**
     * 推送消息给android
     *
     * @param targetValue 目标
     * @param title       标题
     * @param toView      跳转页面
     * @param bizId       业务id
     * @param body        内容
     * @param mobile      用户
     * @param name        推送名称
     * @param type        推送类型
     * @return
     */
    public boolean pushMessageToAndroid(String targetValue, String title, String toView, String bizId, String body,
                                        String mobile, String name, String type) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("body", body);
        map.put("time", ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        map.put("toView", toView);
        map.put("name", name);
        map.put("pushType", "me");
        map.put("type", type);
        map.put("mobile", mobile);
        map.put("bizId", bizId);
        System.out.println(JsonUtil.obj2Json(map));
        boolean b = false;
        try {
            b = aliPushFacade.pushMessageToAndroid(
                    targetValue,
                    title,
                    JsonUtil.obj2Json(map)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 推送消息给IOS
     *
     * @param targetValue 目标
     * @param title       标题
     * @param toView      跳转页面
     * @param bizId       业务id
     * @param body        内容
     * @param mobile      用户
     * @param name        推送名称
     * @param type        推送类型
     * @return
     */
    public boolean pushMessageToIOS(String targetValue, String title, String toView, String bizId, String body,
                                    String mobile, String name, String type) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("body", body);
        map.put("time", ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        map.put("toView", toView);
        map.put("name", name);
        map.put("type", type);
        map.put("pushType", "me");
        map.put("mobile", mobile);
        map.put("bizId", bizId);
        System.out.println(JsonUtil.obj2Json(map));
        boolean b = false;
        try {
            b = aliPushFacade.sendIOS(
                    targetValue,
                    title,
                    JsonUtil.obj2Json(map)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }



}
