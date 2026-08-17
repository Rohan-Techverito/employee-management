package com.example.app.department;

public final class DepartmentMapper {

    private DepartmentMapper() {}

    public static Department toEntity(DepartmentCreateRequest request) {
        Department entity = new Department();
        entity.setName(request.name());
        return entity;
    }

    public static void updateEntity(Department entity, DepartmentUpdateRequest request) {
        entity.setName(request.name());
    }
}
