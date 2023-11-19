package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.model.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/v1/sessions")
public class SessionsController {
    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping
    public List<Session> findAll() { // Jackson serializer will create a json object and returns it in the http
                                     // response payload
        return sessionRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Optional<Session> findSessionById(@PathVariable Long id) {
        return sessionRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // This will return 201 code instead of the default 200 http code
    public Session create(@RequestBody Session session) {

        return sessionRepository.saveAndFlush(session);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(code = HttpStatus.GONE)
    public void delete(@PathVariable Long id) {
        // Need to cascade the delete to the session dependencies from other tables,
        // otherwise we keep orphaned records
        sessionRepository.deleteById(id);
    }

    // There two types of update methods or HTTP verbs; PUT and PATCH
    // PUT expects all attributes to be provided in the request payload, where those
    // missing attributes will be added by the Jackson session serializer with the
    // values null; and this can cause a problem
    // PATCH only expects a subset of attributes to be provided and will only update
    // the provided ones and keep the rest intact
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Session update(@PathVariable Long id, @RequestBody Session session) {
        Session existingSession = sessionRepository.getOne(id);
        BeanUtils.copyProperties(session, existingSession, "id");
        return sessionRepository.saveAndFlush(existingSession);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PATCH)
    public Session partiallyUpdate(@PathVariable Long id, @RequestBody Session session) {
        Optional<Session> existingSession = sessionRepository.findById(id);
        Session actual = existingSession.get();
        // BeanUtils.copyProperties(session, actual, "id");
        BeanUtils.copyProperties(session, actual, getNullPropertyNames(session));
        return sessionRepository.saveAndFlush(actual);
    }

    // Utility function for partial updates
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            if (src.isReadableProperty((pd.getName()))) {
                Object srcValue = src.getPropertyValue(pd.getName());
                if (srcValue == null)
                    emptyNames.add(pd.getName());
            }

        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
