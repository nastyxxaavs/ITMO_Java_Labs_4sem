package org.example.labthree.dataAccessLayer.dao;

import org.example.labthree.dataAccessLayer.entities.owner.OwnerBase;
import org.example.labthree.dataAccessLayer.entities.user.UserBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserDao extends JpaRepository<UserBase, UUID> {
    Optional<UserBase> findByUsername(String name);
    //UserBase findByUsername(String name);
    Boolean existsByUsername(String userName);
}
