package org.example.labthree.serviceLayer.owner;

import org.example.labthree.dataAccessLayer.entities.cat.CatDto;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerDto;

import java.util.List;
import java.util.UUID;

public interface OwnerService {
    public OwnerDto findOwner(UUID id);

    public void saveOwner(OwnerDto owner);

    public Boolean deleteOwner(UUID id); //OwnerBase owner

    public Boolean updateOwner(OwnerDto owner, UUID id);

    public List<OwnerDto> findAllOwners();

    public CatDto findCatById(UUID id);

}
