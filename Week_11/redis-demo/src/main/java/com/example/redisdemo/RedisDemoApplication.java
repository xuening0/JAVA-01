package com.example.redisdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
@EnableAspectJAutoProxy
public class RedisDemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
    }

    @Autowired
    private RedisTemplate<Long, Long> redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        redisTemplate.opsForValue().setIfAbsent(222L, 10000L); //设置库存
    }
}
