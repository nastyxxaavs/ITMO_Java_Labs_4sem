package org.example.labthree.dataAccessLayer.dao;

import org.example.labthree.dataAccessLayer.entities.role.RoleBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleDao extends JpaRepository<RoleBase, UUID> {
    RoleBase findByName(String name);
}
