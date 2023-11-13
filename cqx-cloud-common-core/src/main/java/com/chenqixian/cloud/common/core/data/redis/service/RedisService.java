package com.chenqixian.cloud.common.core.data.redis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationUtils;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author 53486
 */
public class RedisService {

    private static final Logger log = LoggerFactory.getLogger(RedisService.class);
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public RedisService() {
    }

    public Boolean expire(String key, Long time) {
        try {
            if (time > 0L) {
                this.redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }

            return true;
        } catch (Exception var4) {
            log.error("Exception: {}", var4.getMessage());
            return false;
        }
    }

    public void setExpire(String key, Object value, long time, TimeUnit timeUnit) {
        this.redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    public void setExpire(String key, Object value, final long time, final TimeUnit timeUnit, RedisSerializer<Object> valueSerializer) {
        final byte[] rawKey = this.rawKey(key);
        final byte[] rawValue = this.rawValue(value, valueSerializer);
        this.redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                this.potentiallyUsePsetEx(connection);
                return null;
            }

            public void potentiallyUsePsetEx(RedisConnection connection) {
                if (!TimeUnit.MILLISECONDS.equals(timeUnit) || !this.failsafeInvokePsetEx(connection)) {
                    connection.setEx(rawKey, TimeoutUtils.toSeconds(time, timeUnit), rawValue);
                }

            }

            private boolean failsafeInvokePsetEx(RedisConnection connection) {
                boolean failed = false;

                try {
                    connection.pSetEx(rawKey, time, rawValue);
                } catch (UnsupportedOperationException var4) {
                    failed = true;
                }

                return !failed;
            }
        }, true);
    }

    public Long getExpire(String key) {
        return this.redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public Boolean hasKey(String key) {
        try {
            return this.redisTemplate.hasKey(key);
        } catch (Exception var3) {
            log.error("Exception: {}", var3.getMessage());
            return false;
        }
    }

    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                this.redisTemplate.delete(key[0]);
            } else {
                this.redisTemplate.delete(Arrays.asList(key));
            }
        }

    }

    public Object get(String key) {
        return key == null ? null : this.redisTemplate.opsForValue().get(key);
    }

    public Object get(String key, RedisSerializer<Object> valueSerializer) {
        byte[] rawKey = this.rawKey(key);
        return this.redisTemplate.execute((connection) -> {
            return this.deserializeValue(connection.get(rawKey), valueSerializer);
        }, true);
    }

    public Boolean set(String key, Object value) {
        try {
            this.redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception var4) {
            log.error("Exception: {}", var4.getMessage());
            return false;
        }
    }

    public Boolean set(String key, Object value, Long time) {
        try {
            if (time > 0L) {
                this.redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                this.set(key, value);
            }

            return true;
        } catch (Exception var5) {
            log.error("Exception: {}", var5.getMessage());
            return false;
        }
    }

    public Boolean set(String key, Object value, Duration timeout) {
        try {
            Assert.notNull(timeout, "Timeout must not be null!");
            if (TimeoutUtils.hasMillis(timeout)) {
                this.redisTemplate.opsForValue().set(key, value, timeout.toMillis(), TimeUnit.MILLISECONDS);
            } else {
                this.redisTemplate.opsForValue().set(key, value, timeout.getSeconds(), TimeUnit.SECONDS);
            }

            return true;
        } catch (Exception var5) {
            log.error("Exception: {}", var5.getMessage());
            return false;
        }
    }

    public Long incr(String key, Long delta) {
        if (delta < 0L) {
            throw new RuntimeException("递增因子必须大于0");
        } else {
            return this.redisTemplate.opsForValue().increment(key, delta);
        }
    }

    public Long decr(String key, Long delta) {
        if (delta < 0L) {
            throw new RuntimeException("递减因子必须大于0");
        } else {
            return this.redisTemplate.opsForValue().increment(key, -delta);
        }
    }

    public Object hget(String key, String item) {
        return this.redisTemplate.opsForHash().get(key, item);
    }

    public Map<Object, Object> hmget(String key) {
        return this.redisTemplate.opsForHash().entries(key);
    }

    public Boolean hmset(String key, Map<String, Object> map) {
        try {
            this.redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception var4) {
            log.error("Exception: {}", var4.getMessage());
            return false;
        }
    }

    public Boolean hmset(String key, Map<String, Object> map, Long time) {
        try {
            this.redisTemplate.opsForHash().putAll(key, map);
            if (time > 0L) {
                this.expire(key, time);
            }

            return true;
        } catch (Exception var5) {
            log.error("Exception: {}", var5.getMessage());
            return false;
        }
    }

    public Boolean hset(String key, String item, Object value) {
        try {
            this.redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception var5) {
            log.error("Exception: {}", var5.getMessage());
            return false;
        }
    }

    public Boolean hset(String key, String item, Object value, Long time) {
        try {
            this.redisTemplate.opsForHash().put(key, item, value);
            if (time > 0L) {
                this.expire(key, time);
            }

            return true;
        } catch (Exception var6) {
            log.error("Exception: {}", var6.getMessage());
            return false;
        }
    }

    public void hdel(String key, Object... item) {
        this.redisTemplate.opsForHash().delete(key, item);
    }

    public Boolean hHasKey(String key, String item) {
        return this.redisTemplate.opsForHash().hasKey(key, item);
    }

    public Double hincr(String key, String item, Double by) {
        return this.redisTemplate.opsForHash().increment(key, item, by);
    }

    public Double hdecr(String key, String item, Double by) {
        return this.redisTemplate.opsForHash().increment(key, item, -by);
    }

    public Set<Object> sGet(String key) {
        try {
            return this.redisTemplate.opsForSet().members(key);
        } catch (Exception var3) {
            log.error("Exception: {}", var3.getMessage());
            return null;
        }
    }

    public Boolean sHasKey(String key, Object value) {
        try {
            return this.redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception var4) {
            log.error("Exception: {}", var4.getMessage());
            return false;
        }
    }

    public Long sSet(String key, Object... values) {
        try {
            return this.redisTemplate.opsForSet().add(key, values);
        } catch (Exception var4) {
            log.error("Exception: {}", var4.getMessage());
            return 0L;
        }
    }

    public Long sSetAndTime(String key, Long time, Object... values) {
        try {
            Long count = this.redisTemplate.opsForSet().add(key, values);
            if (time > 0L) {
                this.expire(key, time);
            }

            return count;
        } catch (Exception var5) {
            log.error("Exception: {}", var5.getMessage());
            return 0L;
        }
    }

    public Long sGetSetSize(String key) {
        try {
            return this.redisTemplate.opsForSet().size(key);
        } catch (Exception var3) {
            log.error("Exception: {}", var3.getMessage());
            return 0L;
        }
    }

    public Long setRemove(String key, Object... values) {
        try {
            return this.redisTemplate.opsForSet().remove(key, values);
        } catch (Exception var4) {
            log.error("Exception: {}", var4.getMessage());
            return 0L;
        }
    }

    public List<Object> lGet(String key, Long start, Long end) {
        try {
            return this.redisTemplate.opsForList().range(key, start, end);
        } catch (Exception var5) {
            log.error("Exception: {}", var5.getMessage());
            return null;
        }
    }

    public Long lGetListSize(String key) {
        try {
            return this.redisTemplate.opsForList().size(key);
        } catch (Exception var3) {
            log.error("Exception: {}", var3.getMessage());
            return 0L;
        }
    }

    public Object lGetIndex(String key, Long index) {
        try {
            return this.redisTemplate.opsForList().index(key, index);
        } catch (Exception var4) {
            log.error("Exception: {}", var4.getMessage());
            return null;
        }
    }

    public Boolean lSet(String key, Object value) {
        try {
            this.redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception var4) {
            log.error("Exception: {}", var4.getMessage());
            return false;
        }
    }

    public Boolean lSet(String key, Object value, Long time) {
        try {
            this.redisTemplate.opsForList().rightPush(key, value);
            if (time > 0L) {
                this.expire(key, time);
            }

            return true;
        } catch (Exception var5) {
            log.error("Exception: {}", var5.getMessage());
            return false;
        }
    }

    public Boolean lSet(String key, List<Object> value) {
        try {
            this.redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception var4) {
            log.error("Exception: {}", var4.getMessage());
            return false;
        }
    }

    public Boolean lSet(String key, List<Object> value, Long time) {
        try {
            this.redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0L) {
                this.expire(key, time);
            }

            return true;
        } catch (Exception var5) {
            log.error("Exception: {}", var5.getMessage());
            return false;
        }
    }

    public Boolean lUpdateIndex(String key, Long index, Object value) {
        try {
            this.redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception var5) {
            log.error("Exception: {}", var5.getMessage());
            return false;
        }
    }

    public Long lRemove(String key, Long count, Object value) {
        try {
            return this.redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception var5) {
            log.error("Exception: {}", var5.getMessage());
            return 0L;
        }
    }

    public List<Object> getList(String key, int start, int end, RedisSerializer<Object> valueSerializer) {
        byte[] rawKey = this.rawKey(key);
        return (List)this.redisTemplate.execute((connection) -> {
            return this.deserializeValues(connection.lRange(rawKey, (long)start, (long)end), valueSerializer);
        }, true);
    }

    private byte[] rawKey(Object key) {
        Assert.notNull(key, "non null key required");
        if (key instanceof byte[]) {
            return (byte[])key;
        } else {
            RedisSerializer<Object> redisSerializer = (RedisSerializer<Object>) this.redisTemplate.getKeySerializer();
            return redisSerializer.serialize(key);
        }
    }

    private byte[] rawValue(Object value, RedisSerializer valueSerializer) {
        return value instanceof byte[] ? (byte[])value : valueSerializer.serialize(value);
    }

    private List deserializeValues(List<byte[]> rawValues, RedisSerializer<Object> valueSerializer) {
        return valueSerializer == null ? rawValues : SerializationUtils.deserialize(rawValues, valueSerializer);
    }

    private Object deserializeValue(byte[] value, RedisSerializer<Object> valueSerializer) {
        return valueSerializer == null ? value : valueSerializer.deserialize(value);
    }

    public Set<String> keys(String key) {
        return this.redisTemplate.keys(key);
    }
}
