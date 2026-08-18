package com.example.app.exception;
import java.util.UUID;

public class EmployeeSkillNotFoundException extends ResourceNotFoundException {

    public EmployeeSkillNotFoundException(UUID id) {
        super("EmployeeSkill not found: " + id);
    }
}
