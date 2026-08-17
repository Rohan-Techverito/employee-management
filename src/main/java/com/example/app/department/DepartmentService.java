package com.example.app.department;

import com.example.app.exception.DepartmentNotFoundException;
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
public class DepartmentService {

    private final DepartmentRepository repository;
    public Page<DepartmentResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(DepartmentResponse::from);
    }
    public DepartmentResponse findById(UUID id) {
        Department entity = repository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
        return DepartmentResponse.from(entity);
    }
    public DepartmentResponse create(DepartmentCreateRequest request) {
        Department entity = DepartmentMapper.toEntity(request);
        
        
        DepartmentResponse response = DepartmentResponse.from(repository.save(entity));
        return response;
    }
    public DepartmentResponse update(UUID id, DepartmentUpdateRequest request) {
        Department entity = repository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
        DepartmentMapper.updateEntity(entity, request);
        
        
        DepartmentResponse response = DepartmentResponse.from(repository.save(entity));
        return response;
    }
    public void delete(UUID id) {
        Department entity = repository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
        repository.delete(entity);
    }

}
