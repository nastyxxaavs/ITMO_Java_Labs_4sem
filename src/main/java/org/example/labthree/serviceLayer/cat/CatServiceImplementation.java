package org.example.labthree.serviceLayer.cat;


import lombok.RequiredArgsConstructor;
import org.example.labthree.dataAccessLayer.dao.CatDao;
import org.example.labthree.dataAccessLayer.dao.OwnerDao;
import org.example.labthree.dataAccessLayer.entities.cat.CatDto;
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
public class CatServiceImplementation implements CatService {
    private final CatDao catRepository;
    private final OwnerMapper ownerMapper;
    private final CatMapper catMapper;
    private final OwnerDao ownerRepository;
    @Override
    //@Transactional
    public CatDto findCat(UUID id) {
        var cat = catRepository.getReferenceById(id);
        return catMapper.convertToDto(cat);
    }
    @Override
    //@Transactional
    public void saveCat(CatDto cat){
        var catBase = catMapper.convertToBase(cat);
        catRepository.save(catBase);
    }

    @Override
    //@Transactional
    public Boolean update(CatDto cat, UUID id){
        var catBase = catMapper.convertToBase(cat);
        if (catRepository.existsById(id)){
            catBase.setId(id);
            catRepository.save(catBase);
            return true;
        }
        return false;
    }

    @Override
    //@org.springframework.transaction.annotation.Transactional
    public Boolean delete(UUID id){
        if (catRepository.existsById(id)){
            catRepository.deleteById(id);
            return true;
        }
        return false;
    }
    @Override
    //@Transactional
    public CatDto findFriendById(UUID id){
        var cat = catRepository.getReferenceById(id);
        return catMapper.convertToDto(cat);
    }
    @Override
    //@Transactional
    public OwnerDto findOwnerById(UUID id){
        var owner = ownerRepository.getReferenceById(id);
        return ownerMapper.convertToDto(owner);
    }

    @Override
    //@Transactional
    public List<CatDto> findAll(){
        return catRepository.findAll().stream().map(catMapper::convertToDto).collect(Collectors.toList());
    }
}

