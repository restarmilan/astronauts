package com.rmilan.astronauts.repository;

import com.rmilan.astronauts.entity.Astronaut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AstronautRepository extends JpaRepository<Astronaut, Long> {

    Astronaut findByName(String name);

    List<Astronaut> findByNameContainsIgnoreCase(String name);

    List<Astronaut> findByNationality(String nationality);
}
