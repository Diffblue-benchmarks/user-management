package io.pockid.backend.usermanagement.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class UserUpdatedEvent {

    private final String eventType = "UserUpdated";
    private final String eventId;
    private final String timeStamp;
    private final String uid;
}
