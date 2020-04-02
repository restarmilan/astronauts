package com.rmilan.astronauts.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Astronaut {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private LocalDate birthDate;

    private LocalDate deathDate;

    private String nationality;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "Astronaut_Mission",
            joinColumns = {@JoinColumn(name = "astronaut_id")},
            inverseJoinColumns = {@JoinColumn(name = "mission_id")}
    )
    @Singular
    @JsonManagedReference(value="anyName")
    private Set<Mission> missions;

    //to avoid Jackson infinite recursion!!!

}