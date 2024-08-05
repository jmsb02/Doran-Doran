//package com.dorandoran.backend.session.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.concurrent.TimeUnit;
//
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class RedisService {
//    private final RedisTemplate<String, Object> redisTemplate;
//
//    public void setValues(String key,String value){
//        redisTemplate.opsForValue().set(key,value);
//    }
//
//    //만료시간 설정 -> 자동 삭제
//    public void setValuesWithTimeout(String key,String value,long timeout){
//        redisTemplate.opsForValue().set(key,value,timeout, TimeUnit.MINUTES);
//    }
//
//    public String getValues(String key){
//       Object value = redisTemplate.opsForValue().get(key);
//       return value != null ? value.toString() : null;
//    }
//
//    public void deleteValues(String key){
//        redisTemplate.delete(key);
//    }
//}
