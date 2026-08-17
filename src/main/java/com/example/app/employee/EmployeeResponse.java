package com.example.app.employee;
import java.util.UUID;
import java.time.Instant;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import com.example.app.department.Department;

public record EmployeeResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String jobTitle,
        LocalDate hireDate,
        String status,
        String phone,
        Instant createdAt,
        Instant updatedAt,
        DepartmentSummary department
) {
    public record DepartmentSummary(UUID id, String name, Instant createdAt, Instant updatedAt) {}

    public static EmployeeResponse from(Employee entity) {
        return new EmployeeResponse(
entity.getId(),
entity.getFirstName(),
entity.getLastName(),
entity.getEmail(),
entity.getJobTitle(),
entity.getHireDate(),
entity.getStatus(),
entity.getPhone(),
entity.getCreatedAt(),
entity.getUpdatedAt(),
entity.getDepartment() != null
                        ? new DepartmentSummary(entity.getDepartment().getId(), entity.getDepartment().getName(), entity.getDepartment().getCreatedAt(), entity.getDepartment().getUpdatedAt())
                        : null
        );
    }
}
