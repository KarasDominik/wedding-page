package wedding.page.attendee.dto;

import io.micrometer.common.util.StringUtils;
import lombok.Builder;
import wedding.page.config.InvalidFieldException;

import java.util.Optional;
import java.util.Set;

@Builder
public record CreateAttendeeCommand(String names, Optional<String> song, Optional<String> dietaryRequirements,
                                    Set<AlcoholType> alcoholPreferences, Optional<String> message) {

    public CreateAttendeeCommand {
        notBlank(names);
        maxLength(names, "Names");
        song = song.filter(StringUtils::isNotBlank);
        dietaryRequirements = dietaryRequirements.filter(StringUtils::isNotBlank);
        message = message.filter(StringUtils::isNotBlank);
        alcoholPreferences = alcoholPreferences == null ? Set.of() : alcoholPreferences;
        song.ifPresent(s -> maxLength(s, "Song"));
        dietaryRequirements.ifPresent(s -> maxLength(s, "Dietary requirements"));
        message.ifPresent(m -> maxLength(m, "Message"));
    }

    private void maxLength(String value, String field) {
        if (value.length() > 300) {
            throw new InvalidFieldException(field + " must not be longer than " + 300 + " characters");
        }
    }

    private void notBlank(String names) {
        if (names == null || names.isBlank()) {
            throw new InvalidFieldException("Names must not be blank");
        }
    }
}
