package com.cambyze.finance_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Spring boot application class to deploy in tomcat
 */
@SpringBootApplication
public class FinanceApiApplication extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(FinanceApiApplication.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(FinanceApiApplication.class, args);
  }

}
