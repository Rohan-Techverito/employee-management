package com.example.app.timeOffRequest;

import com.example.app.exception.TimeOffRequestNotFoundException;
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
public class TimeOffRequestService {

    private final TimeOffRequestRepository repository;
    private final TimeOffRequestStrategyFactory strategyFactory;
    public Page<TimeOffRequestResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(TimeOffRequestResponse::from);
    }
    public TimeOffRequestResponse findById(UUID id) {
        TimeOffRequest entity = repository.findById(id)
                .orElseThrow(() -> new TimeOffRequestNotFoundException(id));
        return TimeOffRequestResponse.from(entity);
    }
    public TimeOffRequestResponse create(TimeOffRequestCreateRequest request) {
        TimeOffRequest entity = TimeOffRequestMapper.toEntity(request);
        
        
        
        
        strategyFactory.resolve(entity.getStatus()).execute(entity);
        TimeOffRequestResponse response = TimeOffRequestResponse.from(repository.save(entity));
        return response;
    }
    public TimeOffRequestResponse update(UUID id, TimeOffRequestUpdateRequest request) {
        TimeOffRequest entity = repository.findById(id)
                .orElseThrow(() -> new TimeOffRequestNotFoundException(id));
        TimeOffRequestMapper.updateEntity(entity, request);
        
        
        
        
        TimeOffRequestResponse response = TimeOffRequestResponse.from(repository.save(entity));
        return response;
    }
    public void delete(UUID id) {
        TimeOffRequest entity = repository.findById(id)
                .orElseThrow(() -> new TimeOffRequestNotFoundException(id));
        repository.delete(entity);
    }

}
