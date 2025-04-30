package wedding.page.attendee.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import wedding.page.attendee.AttendeeService;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AttendeeController.class)
class AttendeeControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttendeeService service;

    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @MethodSource(value = "invalidRequests")
    void shouldThrowWhenRequestIsInvalid(String request) throws Exception {
        mockMvc.perform(post("/api/v1/attendee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());

        verify(service, never()).create(any());
    }

    public static Stream<Arguments> invalidRequests() {
        return Stream.of(
                // names blank
                Arguments.of(
                        """
                        {
                            "names": "  ",
                            "song": "Darude - Sandstorm",
                            "dietaryRequirements": null,
                            "alcoholPreferences": ["BEER", "WINE"],
                            "message": "Looking forward to have fun!"
                        }
                        """
                ),
                // names null
                Arguments.of(
                        """
                        {
                            "song": "Darude - Sandstorm",
                            "dietaryRequirements": null,
                            "alcoholPreferences": ["BEER", "WINE"],
                            "message": "Looking forward to have fun!"
                        }
                        """
                ),
                // names too long
                Arguments.of(
                        """
                        {
                            "names": "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTest",
                            "song": "Darude - Sandstorm",
                            "dietaryRequirements": null,
                            "alcoholPreferences": ["BEER", "WINE"],
                            "message": "Looking forward to have fun!"
                        }
                        """
                ),
                // song too long
                Arguments.of(
                        """
                        {
                            "names": "TestContent",
                            "song": "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTest",
                            "dietaryRequirements": null,
                            "alcoholPreferences": ["BEER", "WINE"],
                            "message": "Looking forward to have fun!"
                        }
                        """
                ),
                // requirements too long
                Arguments.of(
                        """
                        {
                            "names": "TestContent",
                            "song": "Test",
                            "dietaryRequirements": "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTest",
                            "alcoholPreferences": ["BEER", "WINE"],
                            "message": "Looking forward to have fun!"
                        }
                        """
                ),
                // message too long
                Arguments.of(
                        """
                        {
                            "names": "TestContent",
                            "song": "Test",
                            "dietaryRequirements": "Test",
                            "alcoholPreferences": ["BEER", "WINE"],
                            "message": "TestContentTestContentTestContentTestContentTestContentTestContentTestContentTestContent
                            TestContentTestContentTestContentTestContentTestContentTestContentTestContentTestContentTestContent
                            TestContentTestContentTestContentTestContentTestContentTestContentTestContentTestContentTestContent
                            TestContentTestContentTestContentTestContentTestContentTestContentTestContentTestContentTestContent"
                        }
                        """
                )
        );
    }
}