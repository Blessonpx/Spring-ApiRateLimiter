package com.bpx.ApiRateLimiter.Controller;

import java.time.Duration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@RestController
public class ApiController {
	
	private final Bucket bucket;
	
	public ApiController(){
        Bandwidth limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
            .addLimit(limit)
            .build();
    }
	
	@GetMapping("/api/resource")
    public String getResource() {
        // Your resource logic here
		if (bucket.tryConsume(1)) {
        return "Resource Data";
		}
		return "TOO Many Requests, please try again";
    }
} 
