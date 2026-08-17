package com.example.app.exception;
import java.util.UUID;

public class TimeOffRequestNotFoundException extends ResourceNotFoundException {

    public TimeOffRequestNotFoundException(UUID id) {
        super("TimeOffRequest not found: " + id);
    }
}
