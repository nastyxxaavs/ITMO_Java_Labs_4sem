package org.example.labthree.dataAccessLayer.entities.owner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class OwnerFinderDto {
    String name;
    LocalDate dateOfBirth;
}