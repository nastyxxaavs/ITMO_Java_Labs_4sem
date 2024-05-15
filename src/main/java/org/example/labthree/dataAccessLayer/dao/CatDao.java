package org.example.labthree.dataAccessLayer.dao;

import io.micrometer.common.lang.NonNullApi;
import org.example.labthree.dataAccessLayer.entities.cat.CatBase;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerBase;
import org.example.labthree.dataAccessLayer.models.CatColors;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Repository
@NonNullApi
public interface CatDao extends JpaRepository<CatBase, UUID> {

    List<CatBase> findAll();

    List<CatBase> findAll(Sort sort);
    //CatBase findByName(String name);

    List<CatBase> save(Iterable<? extends CatBase> entities);

    void flush();

    CatBase saveAndFlush(CatBase entity);
    java.util.Optional<CatBase> findById(UUID id );

    void deleteInBatch(Iterable<CatBase> entities);
    List<CatBase> findByColor(CatColors color);
    List<CatBase> findByName(String name);
    List<CatBase> findBySpecies(String species);
    List<CatBase> findByDateOfBirth(LocalDate birthday);
    Boolean existsCatBaseByOwnerId(OwnerBase owner);

}