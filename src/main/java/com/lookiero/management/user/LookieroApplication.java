package com.lookiero.management.user;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(title = "Lookiero API", version = "1.0", description = "Users Information"))
public class LookieroApplication {

  public static void main(String[] args) {
    SpringApplication.run(LookieroApplication.class, args);
  }
}
