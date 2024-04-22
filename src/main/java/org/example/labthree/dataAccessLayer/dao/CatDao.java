package org.example.labthree.dataAccessLayer.dao;

import io.micrometer.common.lang.NonNullApi;
import org.example.labthree.dataAccessLayer.entities.cat.CatBase;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
@NonNullApi
public interface CatDao extends JpaRepository<CatBase, UUID> {

    List<CatBase> findAll();

    List<CatBase> findAll(Sort sort);

    List<CatBase> save(Iterable<? extends CatBase> entities);

    void flush();

    CatBase saveAndFlush(CatBase entity);
    //List<CatBase> findCatBaseByOwnerId(UUID id);

    void deleteInBatch(Iterable<CatBase> entities);
}