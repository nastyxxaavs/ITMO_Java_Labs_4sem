package org.example.labthree.serviceLayer.owner;


import lombok.RequiredArgsConstructor;
import org.example.labthree.dataAccessLayer.dao.CatDao;
import org.example.labthree.dataAccessLayer.dao.OwnerDao;
import org.example.labthree.dataAccessLayer.dao.UserDao;
import org.example.labthree.dataAccessLayer.entities.cat.CatDto;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerBase;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerDto;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerFinderDto;
import org.example.labthree.dataAccessLayer.entities.user.UserBase;
import org.example.labthree.dataAccessLayer.mappers.CatMapper;
import org.example.labthree.dataAccessLayer.mappers.OwnerMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final UserDao userRepository;
    @Override
    public OwnerDto findOwner(UUID id){
        var owner = ownerRepository.getReferenceById(id);
        return ownerMapper.convertToDto(owner);
    }

    @Override
    public OwnerDto findOwnerByName(String name){
        var owner = ownerRepository.findByUsername(name);
        return ownerMapper.convertToDto(owner);
    }

    @Override
    public void saveOwner(OwnerDto owner){
        var ownerBase = ownerMapper.convertToBase(owner);
        ownerRepository.save(ownerBase);
    }

    @Override
    public Boolean deleteOwner(UUID id){
        if (ownerRepository.existsById(id)){
            ownerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
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
    public List<OwnerDto> findAllOwners(){
        return ownerRepository.findAll().stream().map(ownerMapper::convertToDto).collect(Collectors.toList());


    }

    @Override
    public CatDto findCatById(UUID id){
        var cat = catRepository.getReferenceById(id);
        return catMapper.convertToDto(cat);
    }

    @Override
    public List<OwnerDto> findOwnersByParam(OwnerFinderDto param) {
        List<OwnerBase> owners = null;
        if (param.getName() != null) {
            owners = ownerRepository.findByName(param.getName());
        }
        else if (param.getDateOfBirth() != null) {
            owners = ownerRepository.findByDateOfBirth(param.getDateOfBirth());
        }
        return owners.stream().map(ownerMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public OwnerBase addOrUpdateOwnerWithDtoByUsername(OwnerDto ownerDto, String userName){
        UserBase user = userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("UserEntity with current username does not exists"));

        var ownerBase = ownerMapper.convertToBase(ownerDto);
        ownerBase.setName(user.getUserName());
        ownerBase.setId(user.getId());
        user.setOwner(ownerBase);

        ownerRepository.save(ownerBase);
        userRepository.save(user);

        return ownerBase;
    }
    @Override
    public OwnerDto getOwnerDtoByUsername(String userName){
        return findOwnerByName(userName);
    }
}
