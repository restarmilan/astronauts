package com.rmilan.astronauts.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "Astronaut")
@Table(name = "astronaut")
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
    @JoinTable(name = "astronaut_mission",
            joinColumns = @JoinColumn(name = "astronaut_id"),
            inverseJoinColumns = @JoinColumn(name = "mission_id")
    )

    @Singular
    private Set<Mission> missions = new HashSet<>();

    public void addMission(Mission mission) {
        missions.add(mission);
        mission.getAstronauts().add(this);
    }

    public void removeMission(Mission mission) {
        missions.remove(mission);
        mission.getAstronauts().remove(this);
    }
}
