package com.example.Token.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        return new JedisPool(poolConfig, "192.168.156.252", 6379);
    }

    @Bean
    public Jedis jedis(JedisPool jedisPool) {
        return jedisPool.getResource();
    }
}
