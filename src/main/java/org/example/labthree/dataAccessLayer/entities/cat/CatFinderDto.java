package org.example.labthree.dataAccessLayer.entities.cat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.labthree.dataAccessLayer.models.CatColors;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class CatFinderDto {
        String name;
        LocalDate dateOfBirth;
        String species;
        CatColors color;
}
