package org.example.labthree.serviceLayer.owner;


import lombok.RequiredArgsConstructor;
import org.example.labthree.dataAccessLayer.dao.CatDao;
import org.example.labthree.dataAccessLayer.dao.OwnerDao;
import org.example.labthree.dataAccessLayer.entities.cat.CatDto;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerBase;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerDto;
import org.example.labthree.dataAccessLayer.mappers.CatMapper;
import org.example.labthree.dataAccessLayer.mappers.OwnerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerServiceImplementation  implements OwnerService {
    private final OwnerDao ownerRepository;
    private final OwnerMapper ownerMapper;
    private final CatMapper catMapper;
    private final CatDao catRepository;
    @Override
            //@Transactional
    public OwnerDto findOwner(UUID id){
        var owner = ownerRepository.getReferenceById(id);
        return ownerMapper.convertToDto(owner);
    }

    @Override
    //@Transactional
    public void saveOwner(OwnerDto owner){
        var ownerBase = ownerMapper.convertToBase(owner);
        ownerRepository.save(ownerBase);
    }

    @Override
    //@Transactional
    public Boolean deleteOwner(UUID id){
        if (ownerRepository.existsById(id)){
            ownerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    //@Transactional
    public Boolean updateOwner(OwnerDto owner, UUID id){
        var ownerBase = ownerMapper.convertToBase(owner);
        if (ownerRepository.existsById(id)){
            ownerBase.setId(id);
            ownerRepository.save(ownerBase);
            return true;
        }
        return false;
    }

    @Override
    //@Transactional
    public List<OwnerDto> findAllOwners(){
        return ownerRepository.findAll().stream().map(ownerMapper::convertToDto).collect(Collectors.toList());


    }

    @Override
    //@Transactional
    public CatDto findCatById(UUID id){
        var cat = catRepository.getReferenceById(id);
        return catMapper.convertToDto(cat);
    }
}
