package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.model.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventDrivenController {

    @Autowired
    private StreamBridge streamBridge;

    @Autowired
    private SessionRepository sessionRepository;

    @PostMapping
    public Session create(@RequestBody Session session) {
        Session savedSession = sessionRepository.saveAndFlush(session);
        // A Cloud Stream producer that will send the session payload to a Rabbitmq topic -- NB: No more boiler plate needed all is handled by the underlying SpringBoot Cloud Stream lib
        streamBridge.send("sessions_publisher_channel", savedSession);
        return savedSession;
    }
}
