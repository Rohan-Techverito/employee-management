package com.example.app.employeeDocument;

import com.example.app.exception.EmployeeDocumentNotFoundException;
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
public class EmployeeDocumentService {

    private final EmployeeDocumentRepository repository;
    public Page<EmployeeDocumentResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(EmployeeDocumentResponse::from);
    }
    public EmployeeDocumentResponse findById(UUID id) {
        EmployeeDocument entity = repository.findById(id)
                .orElseThrow(() -> new EmployeeDocumentNotFoundException(id));
        return EmployeeDocumentResponse.from(entity);
    }
    public EmployeeDocumentResponse create(EmployeeDocumentCreateRequest request) {
        EmployeeDocument entity = EmployeeDocumentMapper.toEntity(request);
        
        
        
        
        
        
        EmployeeDocumentResponse response = EmployeeDocumentResponse.from(repository.save(entity));
        return response;
    }
    public EmployeeDocumentResponse update(UUID id, EmployeeDocumentUpdateRequest request) {
        EmployeeDocument entity = repository.findById(id)
                .orElseThrow(() -> new EmployeeDocumentNotFoundException(id));
        EmployeeDocumentMapper.updateEntity(entity, request);
        
        
        
        
        
        
        EmployeeDocumentResponse response = EmployeeDocumentResponse.from(repository.save(entity));
        return response;
    }
    public void delete(UUID id) {
        EmployeeDocument entity = repository.findById(id)
                .orElseThrow(() -> new EmployeeDocumentNotFoundException(id));
        repository.delete(entity);
    }

}
