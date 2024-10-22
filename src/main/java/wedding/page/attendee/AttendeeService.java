package wedding.page.attendee;

import wedding.page.attendee.dto.CreateAttendeeCommand;

import java.util.UUID;

public interface AttendeeService {

    UUID create(CreateAttendeeCommand command);
}
