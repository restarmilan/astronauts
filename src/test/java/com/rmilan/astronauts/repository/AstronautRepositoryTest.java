package com.rmilan.astronauts.repository;

import com.rmilan.astronauts.entity.Astronaut;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class AstronautRepositoryTest {

    @Autowired
    private AstronautRepository astronautRepository;

    @Test
    public void saveAnAstronaut() {
        Astronaut astronaut = Astronaut.builder()
                .name("Valentina Tereshkova")
                .birthDate(LocalDate.of(1937, 3, 6))
                .nationality("soviet")
                .build();

        astronautRepository.save(astronaut);
        List<Astronaut> allAstronauts = astronautRepository.findAll();
        assertThat(allAstronauts).hasSize(1);
    }


    /**
     * name column is NOT_NULL, test case checks that save an item without name into DB throws a
     * DataIntegrityViolationException.
     */
    @Test(expected = DataIntegrityViolationException.class)
    public void checkNotNullColumn() {
        Astronaut astronaut = Astronaut.builder()
                .birthDate(LocalDate.of(1937, 3, 6))
                .nationality("soviet")
                .build();

        astronautRepository.saveAndFlush(astronaut);
    }

    /**
     * name column is unique, test case checks that save two items with the same name into DB throws a
     * DataIntegrityViolationException.
     */
    @Test(expected = DataIntegrityViolationException.class)
    public void checkUniqueColumn() {
        Astronaut astronaut = Astronaut.builder()
                .name("Valentina Tereshkova")
                .birthDate(LocalDate.of(1937, 3, 6))
                .nationality("soviet")
                .build();

        astronautRepository.save(astronaut);

        Astronaut astronaut2 = Astronaut.builder()
                .name("Valentina Tereshkova")
                .birthDate(LocalDate.of(1936, 3, 6))
                .nationality("soviet")
                .build();

        astronautRepository.saveAndFlush(astronaut2);
    }

    @Test
    public void findAstronautByExactName() {
        Astronaut tereshkova = Astronaut.builder()
                .name("Valentina Tereshkova")
                .birthDate(LocalDate.of(1937, 3, 6))
                .nationality("soviet")
                .build();

        Astronaut gagarin = Astronaut.builder()
                .name("Yuri Gagarin")
                .nationality("soviet")
                .birthDate(LocalDate.of(1934, 3, 9))
                .deathDate(LocalDate.of(1968, 3, 27))
                .build();

        astronautRepository.saveAll(Lists.newArrayList(tereshkova, gagarin));

        Astronaut result = astronautRepository.findByName("Valentina Tereshkova");
        assertThat(result.getName().equals(tereshkova.getName()));
    }

    @Test
    public void findAstronautByNameContains() {

        Astronaut astronaut1 = Astronaut.builder()
                .name("Yuri Glazkov")
                .birthDate(LocalDate.of(1939, 10, 2))
                .deathDate(LocalDate.of(2008, 12, 9))
                .nationality("soviet")
                .build();

        Astronaut astronaut2 = Astronaut.builder()
                .name("Yuri Gagarin")
                .nationality("soviet")
                .birthDate(LocalDate.of(1934, 3, 9))
                .deathDate(LocalDate.of(1968, 3, 27))
                .build();

        astronautRepository.saveAll(Lists.newArrayList(astronaut1, astronaut2));

        List<Astronaut> results = astronautRepository.findByNameContainsIgnoreCase("Yuri");
        //results.forEach(System.out::println);
        assertThat(results)
                .hasSize(2)
                .containsExactlyInAnyOrder(astronaut1, astronaut2);
    }

    @Test
    public void findAstronautByNationality() {

        Astronaut tereshkova = Astronaut.builder()
                .name("Valentina Tereshkova")
                .birthDate(LocalDate.of(1937, 3, 6))
                .nationality("soviet")
                .build();

        Astronaut gagarin = Astronaut.builder()
                .name("Yuri Gagarin")
                .nationality("soviet")
                .birthDate(LocalDate.of(1934, 3, 9))
                .deathDate(LocalDate.of(1968, 3, 27))
                .build();

        Astronaut aldrin = Astronaut.builder()
                .name("Buzz Aldrin")
                .nationality("USA")
                .birthDate(LocalDate.of(1930, 1, 20))
                .build();

        astronautRepository.saveAll(Lists.newArrayList(tereshkova, gagarin, aldrin));

        List<Astronaut> results =astronautRepository.findByNationality("USA");

                assertThat(results)
                .hasSize(1)
                .containsOnly(aldrin);
    }
}
