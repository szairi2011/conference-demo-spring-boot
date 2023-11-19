package com.pluralsight.conferencedemo.services;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.pluralsight.conferencedemo.model.Session;
import com.pluralsight.conferencedemo.model.Session.SessionStatus;

import lombok.extern.log4j.Log4j;

@Log4j

@Service
public class RabbitmqSessionProcessor {
  
  @Bean
  public Function<Session, Session> processSession() {
    return (Session session) -> {
      String sessionName = session.getSession_name();
      session.setSession_name(sessionName.toUpperCase());
      session.setSessionStatus(SessionStatus.PROCESSED);
      log.info("Processed session name to uppercase: " + session);
      return session;
    };
  }
}
