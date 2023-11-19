package com.pluralsight.conferencedemo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

@AllArgsConstructor

@Entity(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long id;
    private String session_name;
    private String session_description;
    private Integer session_length;

    /* 
        Unmanaged JPA property only used as a covenient attribute to evaluate 
        Spring cloud stream publish | process | subscribe new features based on 
        functional programming paradigm
    */
    @Transient
    @Setter // Only create the setter to amend the status on the fly
    private SessionStatus sessionStatus;

    @ManyToMany
    @JoinTable(name = "session_speakers", joinColumns = @JoinColumn(name = "session_id"), inverseJoinColumns = @JoinColumn(name = "speaker_id"))
    private List<Speaker> speakers;

    public Session() {
    }

    public List<Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<Speaker> speakers) {
        this.speakers = speakers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSession_name() {
        return session_name;
    }

    public void setSession_name(String session_name) {
        this.session_name = session_name;
    }

    public String getSession_description() {
        return session_description;
    }

    public void setSession_description(String session_description) {
        this.session_description = session_description;
    }

    public Integer getSession_length() {
        return session_length;
    }

    public void setSession_length(Integer session_length) {
        this.session_length = session_length;
    }

    public static class SessionBuilder {
        private Long id = Long.parseLong(RandomStringUtils.randomNumeric(2));
        private String name = RandomStringUtils.randomAlphanumeric(10);
        private String description = RandomStringUtils.randomAlphanumeric(20);;
        private Integer length = Integer.parseInt(RandomStringUtils.randomNumeric(2));

        public SessionBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public SessionBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public SessionBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public SessionBuilder withLength(Integer length) {
            this.length = length;
            return this;
        }

        public Session build() {
            return new Session(this.id, this.name,
                    this.description, this.length, null, null);
        }
    }

    public enum SessionStatus {
        PUBLISHED, PROCESSED, SUBSCRIBED
    }

    @Override
    public String toString() {
        return "Session{" +
                "id='" + id + '\'' +
                ", sessionName='" + session_name + '\'' +
                ", description='" + session_description + '\'' +
                ", sessionStatus=" + sessionStatus +
                '}';
    }
}
