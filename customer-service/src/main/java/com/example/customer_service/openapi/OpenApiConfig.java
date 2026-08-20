package com.example.customer_service.openapi;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Customer Service API",
                version = "1.0",
                description = "Customer management APIs"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8222",
                        description = "API Gateway"
                )
        }
)
public class OpenApiConfig {
}
