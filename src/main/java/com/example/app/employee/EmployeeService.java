package com.example.app.employee;

import com.example.app.exception.EmployeeNotFoundException;
import com.example.app.exception.DepartmentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.example.app.department.Department;
import com.example.app.department.DepartmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository repository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeStrategyFactory strategyFactory;
    @Transactional(readOnly = true)
    public Page<EmployeeResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(EmployeeResponse::from);
    }
    @Transactional(readOnly = true)
    public EmployeeResponse findById(UUID id) {
        Employee entity = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return EmployeeResponse.from(entity);
    }
    @Transactional
    public EmployeeResponse create(EmployeeCreateRequest request) {
        Employee entity = EmployeeMapper.toEntity(request);
        
        
        
        
        
        
        
        
        if (request.department() != null) {
            entity.setDepartment(departmentRepository.findById(request.department())
                    .orElseThrow(() -> new DepartmentNotFoundException(request.department())));
        }
        
        strategyFactory.resolve(entity.getStatus()).execute(entity);
        EmployeeResponse response = EmployeeResponse.from(repository.save(entity));
        return response;
    }
    @Transactional
    public EmployeeResponse update(UUID id, EmployeeUpdateRequest request) {
        Employee entity = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        EmployeeMapper.updateEntity(entity, request);
        
        
        
        
        
        
        
        
        if (request.department() != null) {
            entity.setDepartment(departmentRepository.findById(request.department())
                    .orElseThrow(() -> new DepartmentNotFoundException(request.department())));
        }
        
        EmployeeResponse response = EmployeeResponse.from(repository.save(entity));
        return response;
    }
    @Transactional
    public void delete(UUID id) {
        Employee entity = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        repository.delete(entity);
    }

}
