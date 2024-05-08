package org.example.labthree.dataAccessLayer.dao;

import org.example.labthree.dataAccessLayer.entities.role.RoleBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleDao extends JpaRepository<RoleBase, UUID> {
    Optional<RoleBase> findByName(String name);
}
