package com.yexuejc.util.redis.autoconfigur;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.push.model.v20160801.*;
import com.aliyuncs.utils.ParameterHelper;
import com.yexuejc.base.util.JsonUtil;
import com.yexuejc.base.util.StrUtil;
import com.yexuejc.util.base.util.LogUtil;

import java.util.Date;
import java.util.Map;

/**
 * 阿里推送
 *
 * @PackageName: com.mcworle.ecentm.base.autoconfigure
 * @Description:
 * @author: maxf
 * @date: 2018/2/11 11:37
 */
public class AliPushFacade {

    public final AliPushProperites properites;
    private final DefaultAcsClient client;

    public AliPushFacade(AliPushProperites properites) {
        this.properites = properites;
        IClientProfile profile = DefaultProfile.getProfile(properites.getRegionId(), properites.getAccessKeyId(),
                properites.getSecret());
        this.client = new DefaultAcsClient(profile);
    }


    /**
     * 推送消息给android
     * <p>
     * 参见文档 https://help.aliyun.com/document_detail/48085.html
     *
     * @param targetValue 设备id
     * @param title       标题
     * @param body        内容
     * @return
     * @throws Exception
     */
    public boolean pushMessageToAndroid(String targetValue, String title, String body) throws
            Exception {
        return sendAndroid(targetValue, title, body, "MESSAGE");
    }

    /**
     * 推送通知给android
     * <p>
     * 参见文档 https://help.aliyun.com/document_detail/48087.html
     *
     * @param targetValue 设备id
     * @param title       标题
     * @param body        内容
     * @return
     * @throws Exception
     */
    public boolean pushNoticeToAndroid(String targetValue, String title, String body) throws Exception {
        return sendAndroid(targetValue, title, body, "NOTICE");
    }

    /**
     * @param targetValue 推送类型
     * @param title       设备id
     * @param body        标题
     * @param pushType    推送类型
     * @return
     * @throws ClientException
     */
    public boolean sendAndroid(String targetValue, String title, String body, String pushType) throws ClientException {
        boolean b = queryDeviceInfoOnline(targetValue);
        LogUtil.bizLogger.debug("设备:{}是否在线：{}", targetValue, b);
        PushRequest pushRequest = new PushRequest();
        //安全性比较高的内容建议使用HTTPS
        pushRequest.setProtocol(ProtocolType.HTTPS);
        pushRequest.setMethod(MethodType.POST);
        // 推送目标
        pushRequest.setAppKey(properites.getAppKey());
        pushRequest.setTarget("DEVICE");//按设备推送
        pushRequest.setTargetValue(targetValue);//设备id
        pushRequest.setDeviceType("ANDROID");//Android端
        pushRequest.setPushType(pushType);//消息
        // 推送配置
        pushRequest.setTitle(title);
        pushRequest.setBody(body);

        String expireTime = ParameterHelper.getISO8601Time(new Date(System.currentTimeMillis() + 12 * 3600 * 1000)); // 12小时后消息失效, 不会再发送
        pushRequest.setExpireTime(expireTime);
        pushRequest.setStoreOffline(true); // 离线消息是否保存,若保存, 在推送时候，用户即使不在线，下一次上线则会收到

//        pushRequest.setAndroidNotifyType("BOTH");
//
//        pushRequest.setAndroidOpenType("URL");
//        pushRequest.setAndroidOpenUrl("http://www.aliyun.com");
//
//        pushRequest.setAndroidOpenType("ACTIVITY");
//        pushRequest.setAndroidActivity("com.mcworle.ecentm.consumer.core.main.MainActivity");
//        pushRequest.setAndroidExtParameters("{\"k1\":\"android\",\"k2\":\"v2\"}");


        PushResponse pushResponse = client.getAcsResponse(pushRequest);
        if (pushResponse != null && StrUtil.isNotEmpty(pushResponse.getRequestId())) {
            LogUtil.bizLogger.debug("推送成功：{}", targetValue, JsonUtil.obj2Json(pushResponse));
            return true;
        }
        return false;
    }

    /**
     * @param targetValue 推送类型
     * @param title       设备id
     * @param body        标题
     * @return
     * @throws ClientException
     */
    public boolean sendIOS(String targetValue, String title, String body) throws ClientException {
        boolean b = queryDeviceInfoOnline(targetValue);
        LogUtil.bizLogger.debug("设备:{}是否在线：{}", targetValue, b);
        PushMessageToiOSRequest request = new PushMessageToiOSRequest();
        request.setAppKey(properites.getAppKey());
        request.setBody(body);
        request.setTarget("DEVICE");
        request.setTargetValue(targetValue);
        request.setTitle(title);
        PushMessageToiOSResponse pushResponse = client.getAcsResponse(request);
        if (pushResponse != null && StrUtil.isNotEmpty(pushResponse.getRequestId())) {
            LogUtil.bizLogger.debug("推送成功：{}", targetValue, JsonUtil.obj2Json(pushResponse));
            return true;
        }
        return false;
    }


    /**
     * 查询设备状态是否在线
     *
     * @param deviceid
     * @return {@link com.aliyuncs.push.model.v20160801.QueryDeviceInfoResponse.DeviceInfo DeviceInfo}
     * @throws ClientException
     */
    public Boolean queryDeviceInfoOnline(String deviceid) throws ClientException {
        QueryDeviceInfoResponse.DeviceInfo deviceInfo = queryDeviceInfo(deviceid);
        if (deviceInfo != null) {
            return deviceInfo.getOnline();
        }
        return false;

    }

    /**
     * 查询设备状态
     *
     * @param deviceid
     * @return
     * @throws ClientException
     */
    public QueryDeviceInfoResponse.DeviceInfo queryDeviceInfo(String deviceid) throws ClientException {
        QueryDeviceInfoRequest queryDeviceInfoRequest = new QueryDeviceInfoRequest();
        queryDeviceInfoRequest.setAppKey(properites.getAppKey());
        queryDeviceInfoRequest.setDeviceId(deviceid);
        QueryDeviceInfoResponse response = client.getAcsResponse(queryDeviceInfoRequest);
        if (response != null && StrUtil.isNotEmpty(response.getDeviceInfo())) {
            return response.getDeviceInfo();
        }
        return null;

    }

    /**
     * 高级推送
     *
     * @param targetValue 目标
     * @param title       标题
     * @param body        内容
     * @return
     * @throws ClientException
     */
    public boolean push(String targetValue, String title, String body) throws ClientException {
        return send("DEVICE",
                targetValue,
                "ALL",
                "NOTICE",
                title,
                body,
                "APPLICATION",
                "DEFAULT",
                "",
                "",
                "",
                "",
                "",
                50,
                2,
                null,
                null
        );
    }


    /**
     * @param target                         推送目标:  DEVICE:推送给设备;  ACCOUNT:推送给指定帐号, TAG:推送给自定义标签; ALIAS: 按别名推送;  ALL: 全推
     * @param targetValue                    根据Target来设定，如Target=DEVICE, 则对应的值为 设备id1,设备id2. 多个值使用逗号分隔.
     *                                       (帐号与设备有一次最多100个的限制)
     * @param deviceType                     设备类型deviceType, iOS设备: "iOS"; Android设备: "ANDROID"; 全部: "ALL", 这是默认值.
     * @param pushType                       MESSAGE:表示消息(默认), NOTICE:表示通知
     * @param title                          消息的标题
     * @param body                           消息的内容
     * @param androidOpenType                点击通知后动作 'APPLICATION': 打开应用 'ACTIVITY': 打开应用AndroidActivity 'URL': 打开URL
     *                                       'NONE': 无跳转
     * @param androidNotifyType              通知的提醒方式 ‘VIBRATE': 振动  'SOUND': 声音 'DEFAULT': 声音和振动 'NONE':不做处理，用户自定义
     * @param androidOpenUrl                 点击打开url
     * @param androidActivity                Android收到推送后打开对应的url,仅当`AndroidOpenType="URL"`有效
     * @param androidPopupActivity           设置该参数后启动辅助弹窗功能,此处指定通知点击后跳转的Activity（辅助弹窗的前提条件：1. 集成第三方辅助通道；2.
     *                                       StoreOffline参数设为true）
     * @param androidPopupTitle              设置辅助弹窗通知的标题
     * @param androidPopupBody               设置辅助弹窗通知的内容
     * @param androidNotificationBarType     Android自定义通知栏样式，取值：1-100
     * @param androidNotificationBarPriority Android通知在通知栏展示时排列位置的优先级 -2 -1 0 1 2
     * @param androidExtParameters           设定通知的扩展属性。(注意 : 该参数要以 json map的格式传入,否则会解析出错)
     * @param pushDate                       用于定时发送。不设置缺省是立即发送。时间格式按照ISO8601标准表示，并需要使用UTC时间，格式为`YYYY-MM-DDThh:mm:ssZ`。
     */
    public boolean send(String target, String targetValue, String deviceType, String pushType,
                        String title, String body, String androidOpenType, String androidNotifyType,
                        String androidOpenUrl, String androidActivity, String androidPopupActivity,
                        String androidPopupTitle, String androidPopupBody, int androidNotificationBarType,
                        int androidNotificationBarPriority, Map<String, Object> androidExtParameters,
                        Date pushDate) throws ClientException {
        PushRequest pushRequest = new PushRequest();
        // 推送目标
        pushRequest.setAppKey(properites.getAppKey());
        pushRequest.setTarget(target);
        pushRequest.setTargetValue(targetValue);
        pushRequest.setDeviceType(deviceType);
        // 推送配置
        pushRequest.setPushType(pushType);
        pushRequest.setTitle(title);
        pushRequest.setBody(body);

        // 推送配置: iOS
//        pushRequest.setIOSBadge(5); // iOS应用图标右上角角标
//        pushRequest.setIOSMusic("default"); // iOS通知声音
//        pushRequest.setIOSApnsEnv("PRODUCT");//iOS的通知是通过APNs中心来发送的，需要填写对应的环境信息。'DEV': 表示开发环境 'PRODUCT': 表示生产环境
//        pushRequest.setIOSRemind(true); //
//        // 消息推送时设备不在线（既与移动推送的服务端的长连接通道不通），则这条推送会做为通知，通过苹果的APNs通道送达一次。注意：**离线消息转通知仅适用于`生产环境`**
//        pushRequest.setIOSRemindBody("PushRequest summary"); // iOS消息转通知时使用的iOS通知内容，仅当iOSApnsEnv=`PRODUCT` &&
//        // iOSRemind为true时有效
//        pushRequest.setIOSExtParameters(""); //通知的扩展属性(注意 : 该参数要以json map的格式传入,否则会解析出错)

        // 推送配置: Android
        pushRequest.setAndroidOpenType(androidOpenType);
        pushRequest.setAndroidNotifyType(androidNotifyType);
        pushRequest.setAndroidOpenUrl(androidOpenUrl);
        /// Android通知声音
        pushRequest.setAndroidMusic("default");
        pushRequest.setAndroidActivity(androidActivity);
//        pushRequest.setAndroidPopupActivity(androidPopupActivity);
//        pushRequest.setAndroidPopupTitle(androidPopupTitle);
//        pushRequest.setAndroidPopupBody(androidPopupBody);
        pushRequest.setAndroidNotificationBarType(androidNotificationBarType);
        pushRequest.setAndroidNotificationBarPriority(androidNotificationBarPriority);
        pushRequest.setAndroidExtParameters(JsonUtil.obj2Json(androidExtParameters));
        // 推送控制
        final String pushTime = ParameterHelper.getISO8601Time(pushDate);
        pushRequest.setPushTime(pushTime); // 延后推送。可选，如果不设置表示立即推送
        pushRequest.setStoreOffline(true); // 离线消息是否保存,若保存, 在推送时候，用户即使不在线，下一次上线则会收到
        final String expireTime = ParameterHelper.getISO8601Time(new Date(System.currentTimeMillis() + 12 * 3600 *
                1000)); // 12小时后消息失效, 不会再发送
        pushRequest.setExpireTime(expireTime);
        // 短信融合通知
//        pushRequest.setSmsTemplateName("SMS_1234567"); // 设置短信模板名
//        pushRequest.setSmsSignName("测试"); //设置短信签名
//        pushRequest.setSmsParams("name=Bob&code=123"); // 短信模板变量
//        pushRequest.setSmsSendPolicy(0); // 补发短信的策略，0 表示当设备未收到推送时补发
//        pushRequest.setSmsDelaySecs(120); // 两分钟未收到触发短信
        PushResponse pushResponse = client.getAcsResponse(pushRequest);
        if (pushResponse != null && StrUtil.isNotEmpty(pushResponse.getRequestId())) {
            return true;
        }
        return false;
    }


}
