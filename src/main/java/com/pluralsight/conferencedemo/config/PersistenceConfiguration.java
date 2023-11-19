package com.pluralsight.conferencedemo.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // We tell Spring that this is a configuration class so that Spring can use it
public class PersistenceConfiguration {

//  @Bean // Tell Spring Boot that the reurned object is a Spring datasource bean to wire it with the bean factory
//  public DataSource configureDataSource() {
//    DataSourceBuilder builder = DataSourceBuilder.create();
//    builder.url("jdbc:mysql://localhost:3306/conference_demo?createDatabaseIfNotExist=true&autoReconnect=true&useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true");
//    return builder.build();
//  }
}
