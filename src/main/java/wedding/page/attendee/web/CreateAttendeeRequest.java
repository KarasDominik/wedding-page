package wedding.page.attendee.web;

import wedding.page.attendee.dto.AlcoholType;
import wedding.page.attendee.dto.CreateAttendeeCommand;

import java.util.Optional;
import java.util.Set;

record CreateAttendeeRequest(String names, Optional<String> song, Optional<String> dietaryRequirements,
                             Set<AlcoholType> alcoholPreferences, Optional<String> message) {

    public CreateAttendeeCommand toCommand() {
        return CreateAttendeeCommand.builder()
                .names(names)
                .song(song)
                .dietaryRequirements(dietaryRequirements)
                .alcoholPreferences(alcoholPreferences)
                .message(message)
                .build();
    }
}
