package com.share.shamir.dal.source;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DataSourceConfig {
    @Configuration
    @Profile("development")
    protected static class DevConfig {
        @Bean(name = "userDataSource",initMethod = "init", destroyMethod = "close")
        @ConfigurationProperties("ds.user-development")
        public DruidDataSource boaDataSource() {
            return new DruidDataSource();
        }
    }
}
