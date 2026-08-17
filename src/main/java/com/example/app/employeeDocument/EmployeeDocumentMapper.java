package com.example.app.employeeDocument;

public final class EmployeeDocumentMapper {

    private EmployeeDocumentMapper() {}

    public static EmployeeDocument toEntity(EmployeeDocumentCreateRequest request) {
        EmployeeDocument entity = new EmployeeDocument();
        entity.setEmployeeId(request.employeeId());
        entity.setOriginalFilename(request.originalFilename());
        entity.setMimeType(request.mimeType());
        entity.setStoragePath(request.storagePath());
        entity.setSizeBytes(request.sizeBytes());
        return entity;
    }

    public static void updateEntity(EmployeeDocument entity, EmployeeDocumentUpdateRequest request) {
        entity.setEmployeeId(request.employeeId());
        entity.setOriginalFilename(request.originalFilename());
        entity.setMimeType(request.mimeType());
        entity.setStoragePath(request.storagePath());
        entity.setSizeBytes(request.sizeBytes());
    }
}
