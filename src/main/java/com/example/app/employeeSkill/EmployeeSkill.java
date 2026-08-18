package com.example.app.employeeSkill;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Objects;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employee_skills")
@EntityListeners(AuditingEntityListener.class)
public class EmployeeSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private UUID employeeId;

    @Column(nullable = false)
    private String skillName;

    @Column(nullable = false)
    private String proficiencyLevel;

    @CreatedDate
    @Column(nullable = true, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = true)
    private Instant updatedAt;


    // ─── Tell-Don't-Ask state predicates ──────────────────────────────────────

    public boolean isBeginner() {
        return "beginner".equals(proficiencyLevel);
    }

    public boolean isIntermediate() {
        return "intermediate".equals(proficiencyLevel);
    }

    public boolean isExpert() {
        return "expert".equals(proficiencyLevel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeSkill other)) return false;
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
