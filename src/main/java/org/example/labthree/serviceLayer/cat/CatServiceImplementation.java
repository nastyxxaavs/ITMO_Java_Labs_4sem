package org.example.labthree.serviceLayer.cat;


import lombok.RequiredArgsConstructor;
import org.example.labthree.dataAccessLayer.dao.CatDao;
import org.example.labthree.dataAccessLayer.dao.OwnerDao;
import org.example.labthree.dataAccessLayer.entities.cat.CatBase;
import org.example.labthree.dataAccessLayer.entities.cat.CatDto;
import org.example.labthree.dataAccessLayer.entities.cat.CatFinderDto;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerDto;
import org.example.labthree.dataAccessLayer.mappers.CatMapper;
import org.example.labthree.dataAccessLayer.mappers.OwnerMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CatDto findCat(UUID id) {
        var cat = catRepository.getReferenceById(id);
        return catMapper.convertToDto(cat);
    }
    @Override
    public void saveCat(CatDto cat){
        var catBase = catMapper.convertToBase(cat);
        catRepository.save(catBase);
    }

    @Override
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
    public Boolean delete(UUID id){
        if (catRepository.existsById(id)){
            catRepository.deleteById(id);
            return true;
        }
        return false;
    }
    @Override
    public CatDto findFriendById(UUID id){
        var cat = catRepository.getReferenceById(id);
        return catMapper.convertToDto(cat);
    }
    @Override
    public OwnerDto findOwnerById(UUID id){
        var owner = ownerRepository.getReferenceById(id);
        return ownerMapper.convertToDto(owner);
    }

    @Override
    public List<CatDto> findAll(){
        return catRepository.findAll().stream().map(catMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<CatDto> findCatsByParam(CatFinderDto param) {
        List<CatBase> cats = null;
        if (param.getName() != null) {
            cats = catRepository.findByName(param.getName());
        }
        else if (param.getSpecies() != null) {
            cats = catRepository.findBySpecies(param.getSpecies());
        }
        else if (param.getDateOfBirth() != null) {
            cats = catRepository.findByDateOfBirth(param.getDateOfBirth());
        }
        else if (param.getColor() != null) {
            cats = catRepository.findByColor(param.getColor());
        }
        return cats.stream().map(catMapper::convertToDto).collect(Collectors.toList());
    }
}

