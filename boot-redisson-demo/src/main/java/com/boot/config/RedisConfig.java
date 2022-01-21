package com.boot.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisConfig
 *
 * @author lgn
 * @since 2022/1/19 18:25
 */

@Configuration
public class RedisConfig {

    /**
     * 将 RedissonClient 加入容器
     */
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();

        // 设置 Redisson 编解码器
        config.setCodec(new JsonJacksonCodec());
        // 单个 Redis 实例
        config.useSingleServer()
                .setAddress("redis://localhost:6379");

        // 主从复制模式
        // config.useReplicatedServers()

        // 哨兵模式
        // config.useSentinelServers().addSentinelAddress("", "", "");

        /**
         * 多个 Redis 集群
         * config.useClusterServers()
         *         .addNodeAddress("redis://slave1:6369", "redis://slave2:6379")
         *         .addNodeAddress("redis://master:6389");
         */

        // 创建 redisson 实例，并返回，注入到容器中
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

    /**
     * 将 RedisTemplate 加入容器
     * @return RedisTemplate<String, Object>
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory());

        // 使用 StringRedisSerializer 对 redis 的 key 进行序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        // 使用 Jackson2JsonRedisSerializer 替换默认的 JdkSerializationRedisSerializer 来序列化和反序列化 redis 的 value 值
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        return template;
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        LettuceConnectionFactory factory = new LettuceConnectionFactory();
        return factory;
    }

}
