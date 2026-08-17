package com.example.app.user;

import com.example.app.exception.UserNotFoundException;
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
public class UserService {

    private final UserRepository repository;
    public Page<UserResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(UserResponse::from);
    }
    public UserResponse findById(UUID id) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return UserResponse.from(entity);
    }
    public UserResponse create(UserCreateRequest request) {
        User entity = UserMapper.toEntity(request);
        
        
        
        
        UserResponse response = UserResponse.from(repository.save(entity));
        return response;
    }
    public UserResponse update(UUID id, UserUpdateRequest request) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        UserMapper.updateEntity(entity, request);
        
        
        
        
        UserResponse response = UserResponse.from(repository.save(entity));
        return response;
    }
    public void delete(UUID id) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        repository.delete(entity);
    }

}
