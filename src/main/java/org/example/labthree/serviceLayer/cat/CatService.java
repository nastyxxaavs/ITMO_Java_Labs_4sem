package org.example.labthree.serviceLayer.cat;

import org.example.labthree.dataAccessLayer.entities.cat.CatDto;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerDto;

import java.util.List;
import java.util.UUID;

public interface CatService {

    public CatDto findCat(UUID id);
    public void saveCat(CatDto cat);

    public Boolean update(CatDto cat, UUID id);

    public Boolean delete(UUID id);
    public CatDto findFriendById(UUID id);
    public OwnerDto findOwnerById(UUID id);

    public List<CatDto> findAll();
}

