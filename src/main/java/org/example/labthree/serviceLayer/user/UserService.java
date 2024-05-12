package org.example.labthree.serviceLayer.user;

import org.example.labthree.dataAccessLayer.entities.user.UserBase;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    /*UserBase register(UserBase user);
    List<UserBase> getAll();
    Optional<UserBase> findByUserName(String userName);
    UserBase findById(UUID id);
    void delete(UUID id);*/

    boolean isCurrentUserEquals(String username);
    void addUser(UserBase user);
    void updateUser(UserBase user);
    void deleteUser(UserBase user);
    UserBase getUserById(UUID userId);
    UserBase getUserByUsername(String username);
    //public UserBase getUserByOwnerId(UUID ownerId);
}

