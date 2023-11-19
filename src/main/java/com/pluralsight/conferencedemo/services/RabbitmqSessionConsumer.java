package com.pluralsight.conferencedemo.services;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.pluralsight.conferencedemo.model.Session;
import com.pluralsight.conferencedemo.model.Session.SessionStatus;

import lombok.extern.log4j.Log4j;

@Service

@Log4j
public class RabbitmqSessionConsumer {

  @Bean
  public Consumer<Session> onProcessedSession() {
    return (session) -> {
      // System.out.println("Consumer reached ...");
      session.setSessionStatus(SessionStatus.SUBSCRIBED);
      log.info("Consumed the processed session: " + session);
    };
  }
}
