package org.example.labthree.dataAccessLayer.entities.role;

import jakarta.persistence.*;
import lombok.Data;
import org.example.labthree.dataAccessLayer.entities.user.UserBase;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Data
public class RoleBase{
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Column(name="name")
    private String name;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<UserBase> users;
}
