package com.example.admin.config.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

/**
 * @program workspace
 * @description 封装了 Redis 缓存中 token 的增删查操作
 * @author 贲玉柱
 * @create 2023/3/21 16:14
 **/
@Service
public class RedisTokenService {

    // Redis 中保存 token 的键名前缀
    private static final String REDIS_KEY_PREFIX = "jwt_token:";

    // token 的默认有效期（秒）
    private static final long TOKEN_TTL_SECONDS = 15*60;

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 构造方法，传入一个 StringRedisTemplate 对象
     * @param stringRedisTemplate Redis 操作模板对象
     */
    public RedisTokenService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 将 token 添加到 Redis 缓存中，并指定过期时间
     * @param token token 字符串
     */
    public void addToken(String token) {
        String key = REDIS_KEY_PREFIX + token;
        // 获取当前时间
        Instant now = Instant.now();
        // 计算过期时间
        Instant expirationInstant = now.plusSeconds(TOKEN_TTL_SECONDS);
        stringRedisTemplate.opsForValue().set(key, "1", Duration.between(now, expirationInstant) );
    }

    /**
     * 判断给定的 token 是否存在于 Redis 缓存中
     *
     * @param token token 字符串
     * @return true 表示存在，false 表示不存在
     */
    public Boolean isTokenExists(String token) {
        String key = REDIS_KEY_PREFIX + token;
        // 调用 get() 方法获取值，并将结果转换为 Boolean 类型
        return Optional.ofNullable(stringRedisTemplate.opsForValue().get(key))
                .map(value -> "1".equals(value.trim()))
                .orElse(false);
    }

    /**
     * 将给定的 token 从 Redis 缓存中删除
     * @param token token 字符串
     */
    public void removeToken(String token) {
        String key = REDIS_KEY_PREFIX + token;
        stringRedisTemplate.delete(key);
    }
}
