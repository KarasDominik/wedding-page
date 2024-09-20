package wedding.page.attendee.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wedding.page.attendee.AttendeeService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendee")
class AttendeeController {

    private final AttendeeService service;

    @PostMapping
    @ResponseStatus(CREATED)
    CreateAttendeeResponse create(@RequestBody CreateAttendeeRequest request) {
        return new CreateAttendeeResponse(service.create(request.toCommand()));
    }
}
