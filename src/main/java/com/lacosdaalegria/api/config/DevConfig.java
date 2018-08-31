package com.lacosdaalegria.api.config;

import com.lacosdaalegria.api.service.dev.DevDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DevDBService devDBService;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        devDBService.instantiateDevDatabase();

        return true;
    }
}
