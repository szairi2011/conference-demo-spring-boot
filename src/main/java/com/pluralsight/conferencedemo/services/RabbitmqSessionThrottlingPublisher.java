package com.pluralsight.conferencedemo.services;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.pluralsight.conferencedemo.model.Session;
import com.pluralsight.conferencedemo.model.Session.SessionStatus;

import lombok.extern.log4j.Log4j;

/* 
 * As part of Spring Supplier interface below supplier bean
 *  will publish a new Session every one second it's a Spring default.
 * Spring triggers it automatically every second, so it’s a great tool for testing and developing streams
 */

@Service

@Log4j
public class RabbitmqSessionThrottlingPublisher {

  @Bean
  public Supplier<Session> publishSession() {
    return () -> {
      Session session = new Session.SessionBuilder().build();
      session.setSessionStatus(SessionStatus.PUBLISHED);
      log.info("Publishing session: " + session);
      return session;
    };
  }
  
}
