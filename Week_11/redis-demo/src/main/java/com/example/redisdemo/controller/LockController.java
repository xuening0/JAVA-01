package com.example.redisdemo.controller;

import com.example.redisdemo.annotation.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class LockController {

    @Autowired
    private RedisTemplate<Long, Long> redisTemplate;

    @RedisLock()
    @PostMapping("/findById")
    public void lockTest(Long id){
        System.out.println("/findById, id= " + id);
    }

    @RedisLock()
    @PostMapping("/releaseSkuById")
    public void releaseSku(Long id){
        Long decrement = redisTemplate.opsForValue().decrement(id);
        System.out.println("库存"+ id + "剩余"+ decrement + "件");
    }

}










