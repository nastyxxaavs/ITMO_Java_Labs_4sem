package org.example.labthree.dataAccessLayer.mappers;

import lombok.RequiredArgsConstructor;
import org.example.labthree.dataAccessLayer.dao.CatDao;
import org.example.labthree.dataAccessLayer.dao.OwnerDao;
import org.example.labthree.dataAccessLayer.entities.cat.CatBase;
import org.example.labthree.dataAccessLayer.entities.cat.CatDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CatMapper {

    private final OwnerDao ownersRepo ;
    private final CatDao catsRepo ;

    public CatDto convertToDto(CatBase cat){
        return new CatDto(cat.getId(), cat.getName(), cat.getDateOfBirth(),cat.getSpecies(), cat.getColor(), cat.getOwnerId().getId(), cat.getCatsFriends().stream().map(CatBase::getId).toList());
        //return modelMapper.map(cat, CatDto.class);
    }
    public CatBase convertToBase(CatDto cat){
        return new CatBase(cat.getId(), cat.getName(), cat.getDateOfBirth(), cat.getSpecies(), cat.getColor(),ownersRepo.findById(cat.getOwnerId()).orElse(null), cat.getFriends().stream().map(x -> catsRepo.findById(x).orElse(null)).toList());
        //return new CatBase(cat.getId(), cat.getName(), cat.getDateOfBirth(),cat.getSpecies(), cat.getColor(), cat.getOwnerId(), cat.getCatsFriends().stream().map(CatBase::getId).toList());
        //return modelMapper.map(cat, CatBase.class);
    }
}
