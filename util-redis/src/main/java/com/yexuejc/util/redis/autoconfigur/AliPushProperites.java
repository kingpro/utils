package com.yexuejc.util.redis.autoconfigur;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里推送
 *
 * @PackageName: com.mcworle.ecentm.base.autoconfigure
 * @Description:
 * @author: maxf
 * @date: 2018/2/11 11:43
 */
@ConfigurationProperties(prefix = "yexuejc.alipush")
public class AliPushProperites {
    private Long appKey = 24800674L;
    private String regionId = "cn-hangzhou";
    private String accessKeyId = "LTAIkZbUQP6dLYKp";
    private String secret = "X1KFWCpyFUf62mLCzao7SWHAWNkGe5";

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setAppKey(Long appKey) {
        this.appKey = appKey;
    }

    public Long getAppKey() {
        return appKey;
    }
}
