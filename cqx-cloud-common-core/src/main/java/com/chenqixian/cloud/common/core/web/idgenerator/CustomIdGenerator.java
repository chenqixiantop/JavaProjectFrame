package com.chenqixian.cloud.common.core.web.idgenerator;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomIdGenerator implements IdentifierGenerator {
    private static final Logger log = LoggerFactory.getLogger(CustomIdGenerator.class);

    public CustomIdGenerator() {
    }

    @Override
    public Long nextId(Object entity) {
        return IdUtil.getSnowflake(1L, 1L).nextId();
    }

    @Override
    public String nextUUID(Object entity) {
        return UUID.fastUUID().toString().toUpperCase();
    }
}
