package org.example.labthree.dataAccessLayer.mappers;

import lombok.RequiredArgsConstructor;
import org.example.labthree.dataAccessLayer.dao.CatDao;
import org.example.labthree.dataAccessLayer.dao.OwnerDao;
import org.example.labthree.dataAccessLayer.entities.cat.CatBase;
import org.example.labthree.dataAccessLayer.entities.cat.CatDto;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerBase;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CatMapper {
    //private final ModelMapper modelMapper;
    /*public CatMapper(){
        this.modelMapper = new ModelMapper();
    }*/

    private final OwnerDao ownersRepo ;
    private final CatDao catsRepo ;

    public CatDto convertToDto(CatBase cat){
        return new CatDto(cat.getId(), cat.getName(), cat.getDateOfBirth(),cat.getSpecies(), cat.getColor(), cat.getOwnerId().getId(), cat.getCatsFriends().stream().map(CatBase::getId).toList());
        //return modelMapper.map(cat, CatDto.class);
    }
    public CatBase convertToBase(CatDto cat){
        return new CatBase(cat.getId(), cat.getName(), cat.getDateOfBirth(), cat.getSpecies(), cat.getColor(),ownersRepo.findById(cat.getOwnerId()).get(), cat.getFriends().stream().map(x -> catsRepo.findById(x).get()).toList());
        //return new CatBase(cat.getId(), cat.getName(), cat.getDateOfBirth(),cat.getSpecies(), cat.getColor(), cat.getOwnerId(), cat.getCatsFriends().stream().map(CatBase::getId).toList());
        //return modelMapper.map(cat, CatBase.class);
    }
}
