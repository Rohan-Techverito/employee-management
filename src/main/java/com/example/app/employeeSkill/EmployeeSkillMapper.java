package com.example.app.employeeSkill;

public final class EmployeeSkillMapper {

    private EmployeeSkillMapper() {}

    public static EmployeeSkill toEntity(EmployeeSkillCreateRequest request) {
        EmployeeSkill entity = new EmployeeSkill();
        entity.setEmployeeId(request.employeeId());
        entity.setSkillName(request.skillName());
        entity.setProficiencyLevel(request.proficiencyLevel());
        return entity;
    }

    public static void updateEntity(EmployeeSkill entity, EmployeeSkillUpdateRequest request) {
        entity.setEmployeeId(request.employeeId());
        entity.setSkillName(request.skillName());
        entity.setProficiencyLevel(request.proficiencyLevel());
    }
}
