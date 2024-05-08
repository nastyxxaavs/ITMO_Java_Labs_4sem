package org.example.labthree.dataAccessLayer.dao;

import org.example.labthree.dataAccessLayer.entities.user.UserBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserDao extends JpaRepository<UserBase, UUID> {
    Optional<UserBase> findByUserName(String name);
    Boolean existsByUserName(String userName);
}
