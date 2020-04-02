package com.rmilan.astronauts.controller;

import com.rmilan.astronauts.entity.Astronaut;
import com.rmilan.astronauts.repository.AstronautRepository;
import com.rmilan.astronauts.repository.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AstronautController {

    @Autowired
    AstronautRepository astronautRepository;

    @Autowired
    MissionRepository missionRepository;

    @GetMapping("/astronauts")
    public List<Astronaut> getAllAstronauts() {
        return astronautRepository.findAll();
    }

    @GetMapping("astronauts/{id}")
    public ResponseEntity<Astronaut> getAstronautById(@PathVariable("id") long id) throws Exception {
        Astronaut astronaut = astronautRepository.findById(id)
                .orElseThrow(() -> new Exception("Astronaut " + id + " not found"));
        return ResponseEntity.ok().body(astronaut);
    }

    @GetMapping("astronauts/name/{name}")
    public List<Astronaut> getAstronautByName(@PathVariable("name") String name) {
        return astronautRepository.findByNameContainsIgnoreCase(name);
    }

    @PostMapping(value = "/astronauts", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Astronaut createAstronaut(@Valid @RequestBody Astronaut astronaut) {
        return astronautRepository.save(astronaut);
    }
}