package com.yexuejc.util.redis.autoconfigur;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aliyuncs.push.model.v20160801.PushMessageToAndroidRequest;

/**
 * 阿里推送
 */
@Configuration
@ConditionalOnClass({PushMessageToAndroidRequest.class})
@EnableConfigurationProperties(AliPushProperites.class)
public class AliPushAutoConfiguration {
    private final AliPushProperites properites;

    public AliPushAutoConfiguration(AliPushProperites properites) {
        this.properites = properites;
    }

    @Bean
    @ConditionalOnMissingBean
    public AliPushFacade aliPushFacade() {
        return new AliPushFacade(properites);
    }


}
