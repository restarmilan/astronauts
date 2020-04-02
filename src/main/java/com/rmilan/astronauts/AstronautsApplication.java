package com.rmilan.astronauts;

import com.rmilan.astronauts.entity.Astronaut;
import com.rmilan.astronauts.entity.Mission;
import com.rmilan.astronauts.entity.MissionResult;
import com.rmilan.astronauts.repository.AstronautRepository;
import com.rmilan.astronauts.repository.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.*;

@SpringBootApplication
public class AstronautsApplication {

    @Autowired
    private AstronautRepository astronautRepository;

    public static void main(String[] args) {
        SpringApplication.run(AstronautsApplication.class, args);
    }

//    @Bean
//    @Profile("production")
//    public CommandLineRunner init() {
//        return args -> {
//
//            Mission vostok1 = Mission.builder()
//                    .missionName("Vostok-1")
//                    .startedAt(LocalDate.of(1961, 3, 12))
//                    .finishedAt(LocalDate.of(1961, 3, 12))
//                    .result(MissionResult.SUCCESSFULL)
//                    .country("Soviet Union")
//                    .build();
//
//            Mission vostok5 = Mission.builder()
//                    .missionName("Vostok-5")
//                    .startedAt(LocalDate.of(1963, 6, 14))
//                    .finishedAt(LocalDate.of(1963, 6, 19))
//                    .result(MissionResult.SUCCESSFULL)
//                    .country("Soviet Union")
//                    .build();
//
//            Mission soyuz22 = Mission.builder()
//                    .missionName("Sojuz-22")
//                    .startedAt(LocalDate.of(1976, 9, 15))
//                    .finishedAt(LocalDate.of(1976, 9, 23))
//                    .result(MissionResult.SUCCESSFULL)
//                    .country("Soviet Union")
//                    .build();
//
//            Astronaut gagarin = Astronaut.builder()
//                    .name("Yuri Gagarin")
//                    .nationality("soviet")"1934, 3, 9))
//                    .deathDate(LocalDate.of(1968, 3, 27))
//                    .mission(vostok1)
//                    .build();
//
//            Astronaut bykovsky = Astronaut.builder()
//                    .name("Valery Bykovsky")
//                    .nationality("soviet")
//                    .birthDate(LocalDate.of(1934, 8, 2))
//                    .deathDate(LocalDate.of(2019, 3, 27))
//                    .mission(vostok5)
//                    .mission(soyuz22)
//                    .build();
//
//            Astronaut aksyonov = Astronaut.builder()
//                    .name("Vladimir Aksyonov")
//                    .nationality("soviet")
//                    .birthDate(LocalDate.of(1935, 2, 1))
//                    .mission(soyuz22)
//                    .build();
//
//            astronautRepository.saveAll(Arrays.asList(gagarin, bykovsky, aksyonov));
//        };
//    }

}
