package com.example.app.exception;
import java.util.UUID;

public class EmployeeNotFoundException extends ResourceNotFoundException {

    public EmployeeNotFoundException(UUID id) {
        super("Employee not found: " + id);
    }
}
