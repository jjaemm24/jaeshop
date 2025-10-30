package com.jaeshop.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public Map<String, Object> healthCheck() {

        Map<String, Object> result = new HashMap<>();
        result.put("status", "UP");
        result.put("message", "JaeShop API is running");
        result.put("timestamp", System.currentTimeMillis());
        return result;
    }
}
