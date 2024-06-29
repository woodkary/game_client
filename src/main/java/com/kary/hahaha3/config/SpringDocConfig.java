package com.kary.hahaha3.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author:123
 */
@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI getOpenAPI(){
        return new OpenAPI().info(new Info()
                .title("kary")
                .description("controllers")
                .version("1.0")
        );
    }
}
