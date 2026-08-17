package com.example.app.exception;
import java.util.UUID;

public class EmployeeDocumentNotFoundException extends ResourceNotFoundException {

    public EmployeeDocumentNotFoundException(UUID id) {
        super("EmployeeDocument not found: " + id);
    }
}
