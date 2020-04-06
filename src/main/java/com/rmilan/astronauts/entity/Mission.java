package com.rmilan.astronauts.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "Mission")
@Table(name = "mission")
public class Mission {

    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
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
    private Set<Astronaut> astronauts = new HashSet<>();
}
