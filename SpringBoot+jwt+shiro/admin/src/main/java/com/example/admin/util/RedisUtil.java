package com.example.admin.util;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author 贲玉柱
 * @program workspace
 * @description 封装了 Redis 缓存中 的增删查操作
 * @create 2023/3/21 16:14
 **/
@Service
public class RedisUtil {

    /**
     * RedisTemplate 对象，用于与 Redis 进行数据交互
     */
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * StringRedisTemplate 对象，用于与 Redis 进行数据交互
     */
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 构造方法，传入一个 StringRedisTemplate 对象
     *
     * @param stringRedisTemplate Redis 操作模板对象
     * @param redisTemplate       Redis 操作模板对象
     */
    public RedisUtil(StringRedisTemplate stringRedisTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.redisTemplate = redisTemplate;
    }

    // Redis 中保存 token 的键名前缀
    @Value("${spring.redis.token-prefix}")
    private String REDIS_KEY_TOKEN_PREFIX;

    // token 的默认有效期（秒）
    @Value("${spring.redis.expire}")
    private long TOKEN_TTL_SECONDS;

    /*  设置储存 TOKEN 信息  **/

    /**
     * 将 token 添加到 Redis 缓存中，并指定过期时间
     *
     * @param token token 字符串
     */
    public void addToken(String token) {
        String key = REDIS_KEY_TOKEN_PREFIX + token;
        // 获取当前时间
        Instant now = Instant.now();
        // 计算过期时间
        Instant expirationInstant = now.plusSeconds(TOKEN_TTL_SECONDS);
        stringRedisTemplate.opsForValue().set(key, "1", Duration.between(now, expirationInstant));
    }

    /**
     * 判断给定的 token 是否存在于 Redis 缓存中
     *
     * @param token token 字符串
     * @return true 表示存在，false 表示不存在
     */
    public Boolean isTokenExists(String token) {
        String key = REDIS_KEY_TOKEN_PREFIX + token;
        // 调用 get() 方法获取值，并将结果转换为 Boolean 类型
        return Optional.ofNullable(stringRedisTemplate.opsForValue().get(key))
                .map(value -> "1".equals(value.trim()))
                .orElse(false);
    }

    /**
     * 将给定的 token 从 Redis 缓存中删除
     *
     * @param token token 字符串
     */
    public void removeToken(String token) {
        String key = REDIS_KEY_TOKEN_PREFIX + token;
        stringRedisTemplate.delete(key);
    }

    /*  设置储存用户信息  **/

    /**
     * 设置缓存值，并设置过期时间
     *
     * @param key   缓存键
     * @param value 缓存值
     */
    public <K, V> void addData(K key, V value) {
        String redisKey = JSON.toJSONString(key);
        redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(value));
        // 设置过期时间
        redisTemplate.expire(redisKey, TOKEN_TTL_SECONDS, TimeUnit.SECONDS);
    }

    /**
     * 获取缓存值
     *
     * @param key   缓存键
     * @param clazz 缓存值类型
     * @return 缓存值
     */
    public <K, V> V getData(K key, Class<V> clazz) {
        String redisKey = JSON.toJSONString(key);
        String jsonValue = (String) redisTemplate.opsForValue().get(redisKey);
        if (jsonValue == null) {
            return null;
        }
        return JSON.parseObject(jsonValue, clazz);
    }

    /**
     * 删除缓存键
     *
     * @param key 缓存键
     */
    public <K> void removeAdmin(K key) {
        String redisKey = JSON.toJSONString(key);
        redisTemplate.delete(redisKey);
    }

    /**
     * 判断缓存键是否存在
     *
     * @param key 缓存键
     * @return true：存在；false：不存在
     */
    public <K> boolean exists(K key) {
        String redisKey = JSON.toJSONString(key);
        return redisTemplate.type(redisKey) != null;
    }
}
