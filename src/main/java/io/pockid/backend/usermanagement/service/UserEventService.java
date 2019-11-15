package io.pockid.backend.usermanagement.service;

import io.pockid.backend.usermanagement.event.UserCreatedEvent;
import io.pockid.backend.usermanagement.event.UserDeletedEvent;
import io.pockid.backend.usermanagement.event.UserUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


@Service
@Slf4j
class UserEventService {

    private final String userTopic;

    private final PubSubTemplate pubSubTemplate;

    @Autowired
    public UserEventService(@Value("${user.events.topic}") String userTopic, PubSubTemplate pubSubTemplate) {
        this.userTopic = userTopic;
        this.pubSubTemplate = pubSubTemplate;
    }

    boolean userCreated(String uid) {
        var userCreatedEvent = new UserCreatedEvent(UUID.randomUUID().toString(), LocalDateTime.now().toString(), uid);
        return publishEvent(userCreatedEvent);
    }

    boolean userUpdated(String uid) {
        var userUpdatedEvent = new UserUpdatedEvent(UUID.randomUUID().toString(), LocalDateTime.now().toString(), uid);
        return publishEvent(userUpdatedEvent);
    }

    boolean userDeleted(String uid) {
        var userDeletedEvent = new UserDeletedEvent(UUID.randomUUID().toString(), LocalDateTime.now().toString(), uid);
        return publishEvent(userDeletedEvent);
    }

    private boolean publishEvent(Object event) {
        try {
            pubSubTemplate.publish(userTopic, event).get();
            return true;
        } catch (InterruptedException | ExecutionException e) {
            log.error("Exception while publishing event", e);
            return false;
        }
    }
}
