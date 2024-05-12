package org.example.labthree.dataAccessLayer.dao;

import io.micrometer.common.lang.NonNullApi;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerBase;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
@NonNullApi
public interface OwnerDao extends JpaRepository<OwnerBase, UUID> {

    List<OwnerBase> findAll();

    List<OwnerBase> findAll(Sort sort);

    List<OwnerBase> save(Iterable<? extends OwnerBase> entities);

    void flush();
    java.util.Optional<OwnerBase> findById(UUID id );
    OwnerBase findByName(String name);

    OwnerBase saveAndFlush(OwnerBase entity);

    void deleteInBatch(Iterable<OwnerBase> entities);
    List<OwnerBase> findOwnerByName(String name);
    List<OwnerBase> findByDateOfBirth(LocalDate dateOfBirth);
}
