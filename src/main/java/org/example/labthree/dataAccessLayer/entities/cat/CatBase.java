package org.example.labthree.dataAccessLayer.entities.cat;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerBase;
import org.example.labthree.dataAccessLayer.models.CatColors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "cats")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CatBase {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "species")
    private String species;
    @Enumerated(EnumType.STRING)
    @Convert(converter = ConverterOfColor.class)
    @Column(name = "color")
    private CatColors color;

    //@OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private OwnerBase ownerId;


    //@OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(targetEntity = CatBase.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "cats_friends",
            joinColumns = { @JoinColumn(name = "cat_id")},
            inverseJoinColumns = { @JoinColumn(name = "second_cat_id")}
    )
    private List<CatBase> catsFriends;


    public CatBase(String name, LocalDate date, String species, CatColors color) {
        this.name = name;
        this.species = species;
        this.dateOfBirth = date;
        this.color = color;
        catsFriends = new ArrayList<>();
    }

    public void addFriends(CatBase friend){
        catsFriends.add(friend);
    }

}
