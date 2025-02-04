create table attendee (
    id uuid primary key,
    names text not null,
    song text,
    dietary_requirements text,
    alcohol_preferences text,
    message text
);