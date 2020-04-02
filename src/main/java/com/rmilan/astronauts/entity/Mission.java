package com.rmilan.astronauts.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Mission {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String missionName;

    private String country;

    private LocalDate startedAt;

    private LocalDate finishedAt;

    @Enumerated(EnumType.STRING)
    private MissionResult result;

    @ManyToMany(mappedBy = "missions")
    @Singular
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Set<Astronaut>astronauts;
}
