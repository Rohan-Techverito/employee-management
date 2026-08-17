package com.example.app.exception;
import java.util.UUID;

public class DepartmentNotFoundException extends ResourceNotFoundException {

    public DepartmentNotFoundException(UUID id) {
        super("Department not found: " + id);
    }
}
