package org.example.labthree.dataAccessLayer.entities.cat;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerBase;
import org.example.labthree.dataAccessLayer.models.CatColors;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CatDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    private String name;
    private LocalDate dateOfBirth;
    private String species;
    private CatColors color;
    private UUID ownerId;
    private List<UUID> friends;
}
