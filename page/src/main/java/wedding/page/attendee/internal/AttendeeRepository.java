package wedding.page.attendee.internal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttendeeRepository extends JpaRepository<Attendee, UUID> {}
