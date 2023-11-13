package com.chenqixian.cloud.common.core.data.redis.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 53486
 */
@ConfigurationProperties("chenqixian.cloud.redis")
public class RedisCloudRedisProperties {
    public static final String PREFIX = "chenqixian.cloud.redis";

    private Boolean enable = true;

    public Boolean getEnable() {
        return this.enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
