package com.example.app.controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckController {

    private final JdbcTemplate jdbcTemplate;
    private final StringRedisTemplate redisTemplate;

    public CheckController(
            JdbcTemplate jdbcTemplate,
            StringRedisTemplate redisTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/api/health")
    public String health() {
        return "OK";
    }

    @GetMapping("/api/db")
    public String db() {
        return jdbcTemplate.queryForObject(
                "SELECT current_database()",
                String.class
        );
    }

    @GetMapping("/api/redis")
    public String redis() {
        redisTemplate.opsForValue().set("connection-test", "OK");
        return redisTemplate.opsForValue().get("connection-test");
    }
}
