package com.example.app.employeeSkill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, UUID> {
}
