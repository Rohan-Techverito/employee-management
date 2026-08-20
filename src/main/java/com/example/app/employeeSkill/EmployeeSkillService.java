package com.example.app.employeeSkill;

import com.example.app.exception.EmployeeSkillNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@Service
public class EmployeeSkillService {

    private final EmployeeSkillRepository repository;
    public Page<EmployeeSkillResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(EmployeeSkillResponse::from);
    }
    public EmployeeSkillResponse findById(UUID id) {
        EmployeeSkill entity = repository.findById(id)
                .orElseThrow(() -> new EmployeeSkillNotFoundException(id));
        return EmployeeSkillResponse.from(entity);
    }
    public EmployeeSkillResponse create(EmployeeSkillCreateRequest request) {
        EmployeeSkill entity = EmployeeSkillMapper.toEntity(request);
        
        
        
        
        EmployeeSkillResponse response = EmployeeSkillResponse.from(repository.save(entity));
        return response;
    }
    public EmployeeSkillResponse update(UUID id, EmployeeSkillUpdateRequest request) {
        EmployeeSkill entity = repository.findById(id)
                .orElseThrow(() -> new EmployeeSkillNotFoundException(id));
        EmployeeSkillMapper.updateEntity(entity, request);
        
        
        
        
        EmployeeSkillResponse response = EmployeeSkillResponse.from(repository.save(entity));
        return response;
    }
    public void delete(UUID id) {
        EmployeeSkill entity = repository.findById(id)
                .orElseThrow(() -> new EmployeeSkillNotFoundException(id));
        repository.delete(entity);
    }

}
