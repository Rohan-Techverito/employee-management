package com.example.app.timeOffRequest;

public final class TimeOffRequestMapper {

    private TimeOffRequestMapper() {}

    public static TimeOffRequest toEntity(TimeOffRequestCreateRequest request) {
        TimeOffRequest entity = new TimeOffRequest();
        entity.setEmployeeId(request.employeeId());
        entity.setManagerId(request.managerId());
        entity.setStatus(request.status());
        return entity;
    }

    public static void updateEntity(TimeOffRequest entity, TimeOffRequestUpdateRequest request) {
        entity.setEmployeeId(request.employeeId());
        entity.setManagerId(request.managerId());
        entity.setStatus(request.status());
    }
}
