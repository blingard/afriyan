package org.ligot.afriyan.config;

import jakarta.annotation.PostConstruct;
import kong.unirest.Unirest;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UnirestConfig {
    @PostConstruct
    public void init() {
        Unirest.config().reset();

        Unirest.config()
                .connectTimeout(60000)
                .socketTimeout(60000);
    }
}
