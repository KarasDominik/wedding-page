package wedding.page.attendee.internal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wedding.page.attendee.dto.AlcoholType;
import wedding.page.attendee.dto.CreateAttendeeCommand;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attendee {

    public static Attendee create(CreateAttendeeCommand command) {
        return Attendee.builder()
                .id(UUID.randomUUID())
                .names(command.names())
                .song(command.song().orElse(null))
                .dietaryRequirements(command.dietaryRequirements().orElse(null))
                .alcoholPreferences(command.alcoholPreferences().stream()
                        .map(AlcoholType::name)
                        .reduce((a, b) -> a + ", " + b).orElse(null))
                .message(command.message().orElse(null))
                .build();
    }

    @Id
    private UUID id;

    private String names;
    private String song;
    private String dietaryRequirements;
    private String alcoholPreferences;
    private String message;
}
