package com.example.redisdemo.config;

import com.example.redisdemo.annotation.RedisLock;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Configuration
@Aspect
public class RedisLockAop {

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(redisLock)")
    public void point(RedisLock redisLock){
    }

    @Around(value = "point(redisLock)")
    public void strongAfter(RedisLock redisLock, ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Object arg = args[0];
        UUID uuid = UUID.randomUUID();

        joinLock(redisLock, arg,uuid);
        joinPoint.proceed(args);
        unLock(arg,uuid);
    }

    private void unLock(Object arg, UUID uuid) {
        ValueOperations ops = redisTemplate.opsForValue();
        String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                "    return redis.call(\"del\",KEYS[1])\n" +
                "else\n" +
                "    return 0\n" +
                "end";
        Long result = (Long) redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Collections.singletonList(arg), uuid);
        if (1L == result) {
            System.out.println("Redis unLock key : " + arg + ", value : " + uuid);
        }

    }

    private void joinLock(RedisLock redisLock, Object arg, UUID uuid) {
        ValueOperations ops = redisTemplate.opsForValue();
        Boolean aBoolean = ops.setIfAbsent(arg, uuid, redisLock.expireTime(), TimeUnit.MILLISECONDS);
        if (!aBoolean){
            throw new RuntimeException("并发异常, 稍后再试");
        }
    }


}
