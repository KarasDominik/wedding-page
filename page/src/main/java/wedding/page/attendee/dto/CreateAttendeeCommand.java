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
        validLength(names, "Names");
        song = song.filter(StringUtils::isNotBlank);
        dietaryRequirements = dietaryRequirements.filter(StringUtils::isNotBlank);
        message = message.filter(StringUtils::isNotBlank);
        alcoholPreferences = alcoholPreferences == null ? Set.of() : alcoholPreferences;
        song.ifPresent(s -> validLength(s, "Song"));
        dietaryRequirements.ifPresent(s -> validLength(s, "Dietary requirements"));
        message.ifPresent(this::maxLength);
    }

    private void maxLength(String value) {
        if (value.length() > 250) {
            throw new InvalidFieldException("Message" + " must not be longer than " + 250 + " characters");
        }
    }

    private void notBlank(String names) {
        if (names == null || names.isBlank()) {
            throw new InvalidFieldException("Names must not be blank");
        }
    }

    private void validLength(String value, String fieldName) {
        if (value.length() < 4 || value.length() > 80) {
            throw new InvalidFieldException(String.format("%s must be between %d and %d characters", fieldName, 4, 80));
        }
    }
}
