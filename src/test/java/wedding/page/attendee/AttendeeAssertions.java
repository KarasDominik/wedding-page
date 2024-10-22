package wedding.page.attendee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wedding.page.attendee.internal.AttendeeRepository;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Component
@RequiredArgsConstructor
public class AttendeeAssertions {

    private final AttendeeRepository attendees;

    public void assertAttendeeExists(UUID id, Map<String, String> expected) {
        var actual = attendees.findById(id).orElseThrow();
        assertThat(actual.getNames()).isEqualTo(expected.get("names"));
        assertThat(actual.getSong()).isEqualTo(expected.get("song"));
        assertThat(actual.getDietaryRequirements()).isEqualTo(expected.get("dietaryRequirements"));
        assertThat(Set.of(actual.getAlcoholPreferences().split(", ")))
                .isEqualTo(Set.of(expected.get("alcoholPreferences").split(", ")));
        assertThat(actual.getMessage()).isEqualTo(expected.get("message"));
    }
}
