package wedding.page.attendee.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wedding.page.attendee.AttendeeService;
import wedding.page.attendee.dto.CreateAttendeeCommand;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
class AttendeeServiceImpl implements AttendeeService {

    private final AttendeeRepository attendees;

    @Override
    public UUID create(CreateAttendeeCommand command) {
        log.info("Creating attendee with names: {}", command.names());
        return attendees.save(Attendee.create(command)).getId();
    }
}
