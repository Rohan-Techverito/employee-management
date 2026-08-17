package com.example.app.employee;

public final class EmployeeMapper {

    private EmployeeMapper() {}

    public static Employee toEntity(EmployeeCreateRequest request) {
        Employee entity = new Employee();
        entity.setFirstName(request.firstName());
        entity.setLastName(request.lastName());
        entity.setEmail(request.email());
        entity.setJobTitle(request.jobTitle());
        entity.setHireDate(request.hireDate());
        entity.setStatus(request.status());
        return entity;
    }

    public static void updateEntity(Employee entity, EmployeeUpdateRequest request) {
        entity.setFirstName(request.firstName());
        entity.setLastName(request.lastName());
        entity.setEmail(request.email());
        entity.setJobTitle(request.jobTitle());
        entity.setHireDate(request.hireDate());
        entity.setStatus(request.status());
    }
}
