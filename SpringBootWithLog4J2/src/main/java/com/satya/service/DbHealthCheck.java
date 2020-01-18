package com.satya.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Component;

@Component
public class DbHealthCheck implements HealthIndicator {
	
    @Autowired
    JdbcTemplate template;
    
    @Override
    public Health health() {
        int errorCode = check(); // perform some specific health check
        if (errorCode != 1) {
            return Health.down().withDetail("Error Code", 500).build();
        }
        return Health.up().build();
    }

    public int check(){
        List<Object> results = template.query("select 1 from dual",
                new SingleColumnRowMapper<>());
        return results.size();
    }
}
