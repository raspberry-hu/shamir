package com.share.shamir.biz;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@ImportResource("classpath:spring.xml")
@SpringBootApplication(exclude = {FreeMarkerAutoConfiguration.class, DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class})
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MainApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}

