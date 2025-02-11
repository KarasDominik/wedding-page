package wedding.page.attendee.web;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import wedding.page.attendee.AttendeeAssertions;
import wedding.page.attendee.config.DockerizedDbInitializer;

import java.util.HashMap;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {DockerizedDbInitializer.class})
public class AttendeeControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AttendeeAssertions assertions;

    @Test
    void shouldCreateAttendee() throws Exception {
        String attendeeJson = """
        {
            "names": "John Doe",
            "song": "Darude - Sandstorm",
            "dietaryRequirements": null,
            "alcoholPreferences": ["BEER", "WINE"],
            "message": "Looking forward to have fun!"
        }
    """;

        var result = mockMvc.perform(post("/api/v1/attendee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(attendeeJson))
                .andExpect(status().isCreated())
                .andReturn();

        var responseContent = result.getResponse().getContentAsString();
        var id = UUID.fromString(JsonPath.read(responseContent, "$.id"));

        var expected = new HashMap<String, String>();
        expected.put("names", "John Doe");
        expected.put("song", "Darude - Sandstorm");
        expected.put("dietaryRequirements", null);
        expected.put("alcoholPreferences", "BEER, WINE");
        expected.put("message", "Looking forward to have fun!");

        assertions.assertAttendeeExists(id, expected);
    }
}
