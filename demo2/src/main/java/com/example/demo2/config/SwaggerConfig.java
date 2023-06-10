package com.example.demo2.config;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("我的SpringBoot演示项目API接口")
                .description("所有控制器类的描述和测试")
                .version("1.0"));
    }
}

