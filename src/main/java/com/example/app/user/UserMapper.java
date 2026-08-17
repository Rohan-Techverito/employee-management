package com.example.app.user;

public final class UserMapper {

    private UserMapper() {}

    public static User toEntity(UserCreateRequest request) {
        User entity = new User();
        entity.setEmail(request.email());
        entity.setPasswordHash(request.passwordHash());
        entity.setRole(request.role());
        return entity;
    }

    public static void updateEntity(User entity, UserUpdateRequest request) {
        entity.setEmail(request.email());
        entity.setPasswordHash(request.passwordHash());
        entity.setRole(request.role());
    }
}
