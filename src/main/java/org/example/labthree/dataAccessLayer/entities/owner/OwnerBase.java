package org.example.labthree.dataAccessLayer.entities.owner;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.labthree.dataAccessLayer.entities.cat.CatBase;
import org.example.labthree.dataAccessLayer.entities.user.UserBase;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "owners")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OwnerBase {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "ownerId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CatBase> cats;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private UserBase user;


    public OwnerBase(String name, LocalDate date) {
        this.name = name;
        this.dateOfBirth = date;
        cats = new ArrayList<>();
    }


    public void addCat(CatBase cat){
        cat.setOwnerId(this);
        cats.add(cat);
    }
}
