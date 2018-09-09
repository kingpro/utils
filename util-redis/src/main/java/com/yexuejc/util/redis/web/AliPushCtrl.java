package com.yexuejc.util.redis.web;

import com.yexuejc.base.http.Resps;
import com.yexuejc.util.redis.ComponentUtil;
import com.yexuejc.util.redis.constant.ToViewConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 阿里推送
 *
 * @PackageName: com.yexuejc.util.redis.web
 * @Description:
 * @author: maxf
 * @date: 2018/7/5 15:57
 */
@RestController
@RequestMapping("/alipush")
public class AliPushCtrl {
    @Autowired
    ComponentUtil componentUtil;

    @RequestMapping("/put/{type}/{id}")
    public Resps putPush(@PathVariable String type, @PathVariable String id) throws Exception {
        boolean b = false;
        switch (type) {
            case "1":
                b = componentUtil.pushMessageToAndroid(
                        id,
                        "1实名认证",
                        ToViewConst.TO_CONSUMER_REAL,
                        "",
                        "【e分钱】亲爱的" + "卢宗平同学" + "您的认证信息审核通过，您可以获得参与百分百中奖的万元红包抢抢抢。详见APP首页活动规则",
                        "13512345678",
                        "",
                        ""
                );
                break;
            case "2":
                b = componentUtil.pushMessageToAndroid(
                        id,
                        "2注册成功",
                        ToViewConst.TO_CIRCLE_FAMILY,
                        "",
                        "【e分钱】恭喜您成功注册e分钱，您可以获得参与百分百中奖的万元红包抢抢抢。详见APP首页活动规则",
                        "13512345678",
                        "",
                        ""
                );
            case "3":
                b = componentUtil.pushMessageToAndroid(
                        id,
                        "3旗下用户注册提醒",
                        ToViewConst.TO_CIRCLE_FAMILY,
                        "45696ded735847b887f85718b5586aa6",
                        "【e分钱】恭喜您旗下用户注册为您贡献" + 10.00 +
                                "元奖励，帮助用户学习使用e分钱，持续收益更简单",
                        "13512345678",
                        "推荐奖励",
                        "a"
                );
                break;
            case "4":
                b = componentUtil.pushMessageToAndroid(
                        id,
                        "4刷卡分润",
                        ToViewConst.TO_CASHOUT_PROFIT,
                        "8b5da0cee88e4145804150e121c97845",
                        "【e分钱】恭喜您旗下用户为您贡献刷卡分润" + 10.00 +
                                "元，高级别带来高收益，e分钱持续分红就这么简单",
                        "13512345678",
                        "刷卡分润",
                        "c"
                );
                break;
            case "5":
                b = componentUtil.pushMessageToAndroid(
                        id,
                        "5消费分润",
                        ToViewConst.TO_CONSUME_PROFIT,
                        "71b4771d776e4021b53e44a0022b3f71",
                        "【e分钱】恭喜您旗下用户为您贡献消费分润" + 10.00 +
                                "元，高级别带来高收益，e分钱持续分红就这么简单",
                        "13512345678",
                        "消费红包",
                        "e"
                );
                break;
            case "6":
                b = componentUtil.pushMessageToAndroid(
                        id,
                        "6升级佣金",
                        ToViewConst.TO_LEVELUP_PROFIT,
                        "da678ce4bce64f54bea5985f2344896f",
                        "【e分钱】恭喜您旗下用户为您贡献晋升奖" + 10.00 +
                                "元，高级别带来高收益，e分钱持续分红就这么简单",
                        "13512345678",
                        "晋升奖励",
                        "b"
                );
                break;
            case "7":
                String title = "7保险分佣";
                String body = "【e分钱】恭喜您旗下用户为您贡献保险分佣" + 10.00 +
                        "元，高级别带来高收益，e分钱持续分红就这么简单";
                b = componentUtil.pushMessageToAndroid(id,
                        title,
                        ToViewConst.TO_ZHONHAN_PROFIT,
                        "da678ce4bce64f54bea5985f2344896f",
                        body,
                        "13512345678",
                        "保险分佣",
                        "j"
                );
                break;
            case "8":
                title = "8保险佣金";
                body = "【e分钱】恭喜您得到保险佣金" + 11.00 +
                        "元，高级别带来高收益，e分钱持续分红就这么简单";
                b = componentUtil.pushMessageToAndroid(id,
                        title,
                        ToViewConst.TO_ZHONHAN_PROFIT,
                        "da678ce4bce64f54bea5985f2344896f",
                        body,
                        "13512345678",
                        "保险佣金",
                        "k"
                );
                break;
            default:
                break;
        }
        if (!b) {
            return Resps.fail("推送失败");
        }
        return Resps.success("推送成功");
    }

    @RequestMapping("/put/ios/{type}/{id}")
    public Resps putIOSPush(@PathVariable String type, @PathVariable String id) throws Exception {
        boolean b = false;
        switch (type) {
            case "1":
                b = componentUtil.pushMessageToIOS(
                        id,
                        "1实名认证",
                        ToViewConst.TO_CONSUMER_REAL,
                        "",
                        "【e分钱】亲爱的" + "卢宗平同学" + "您的认证信息审核通过，您可以获得参与百分百中奖的万元红包抢抢抢。详见APP首页活动规则",
                        "13512345678",
                        "",
                        ""
                );
                break;
            case "2":
                b = componentUtil.pushMessageToIOS(
                        id,
                        "2注册成功",
                        ToViewConst.TO_CIRCLE_FAMILY,
                        "",
                        "【e分钱】恭喜您成功注册e分钱，您可以获得参与百分百中奖的万元红包抢抢抢。详见APP首页活动规则",
                        "13512345678",
                        "",
                        ""
                );
            case "3":
                b = componentUtil.pushMessageToIOS(
                        id,
                        "3旗下用户注册提醒",
                        ToViewConst.TO_CIRCLE_FAMILY,
                        "45696ded735847b887f85718b5586aa6",
                        "【e分钱】恭喜您旗下用户注册为您贡献" + 10.00 +
                                "元奖励，帮助用户学习使用e分钱，持续收益更简单",
                        "13512345678",
                        "推荐奖励",
                        "a"
                );
                break;
            case "4":
                b = componentUtil.pushMessageToIOS(
                        id,
                        "4刷卡分润",
                        ToViewConst.TO_CASHOUT_PROFIT,
                        "8b5da0cee88e4145804150e121c97845",
                        "【e分钱】恭喜您旗下用户为您贡献刷卡分润" + 10.00 +
                                "元，高级别带来高收益，e分钱持续分红就这么简单",
                        "13512345678",
                        "刷卡分润",
                        "c"
                );
                break;
            case "5":
                b = componentUtil.pushMessageToIOS(
                        id,
                        "5消费分润",
                        ToViewConst.TO_CONSUME_PROFIT,
                        "71b4771d776e4021b53e44a0022b3f71",
                        "【e分钱】恭喜您旗下用户为您贡献消费分润" + 10.00 +
                                "元，高级别带来高收益，e分钱持续分红就这么简单",
                        "13512345678",
                        "消费红包",
                        "e"
                );
                break;
            case "6":
                b = componentUtil.pushMessageToIOS(
                        id,
                        "6升级佣金",
                        ToViewConst.TO_LEVELUP_PROFIT,
                        "da678ce4bce64f54bea5985f2344896f",
                        "【e分钱】恭喜您旗下用户为您贡献晋升奖" + 10.00 +
                                "元，高级别带来高收益，e分钱持续分红就这么简单",
                        "13512345678",
                        "晋升奖励",
                        "b"
                );
                break;
            case "7":
                String title = "7保险分佣";
                String body = "【e分钱】恭喜您旗下用户为您贡献保险分佣" + 10.00 +
                        "元，高级别带来高收益，e分钱持续分红就这么简单";
                b = componentUtil.pushMessageToIOS(id,
                        title,
                        ToViewConst.TO_ZHONHAN_PROFIT,
                        "da678ce4bce64f54bea5985f2344896f",
                        body,
                        "13512345678",
                        "保险分佣",
                        "j"
                );
                break;
            case "8":
                title = "8保险佣金";
                body = "【e分钱】恭喜您得到保险佣金" + 11.00 +
                        "元，高级别带来高收益，e分钱持续分红就这么简单";
                b = componentUtil.pushMessageToIOS(id,
                        title,
                        ToViewConst.TO_ZHONHAN_PROFIT,
                        "da678ce4bce64f54bea5985f2344896f",
                        body,
                        "13512345678",
                        "保险佣金",
                        "k"
                );
                break;
            default:
                break;
        }
        if (!b) {
            return Resps.fail("推送失败");
        }
        return Resps.success("推送成功");
    }

}
