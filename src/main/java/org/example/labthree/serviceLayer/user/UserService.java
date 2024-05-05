package org.example.labthree.serviceLayer.user;

import org.example.labthree.dataAccessLayer.entities.user.UserBase;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserBase register(UserBase user);
    List<UserBase> getAll();
    UserBase findByUserName(String userName);
    UserBase findById(UUID id);
    void delete(UUID id);
}

