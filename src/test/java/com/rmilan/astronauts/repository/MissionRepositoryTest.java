package com.rmilan.astronauts.repository;

import com.rmilan.astronauts.entity.Astronaut;
import com.rmilan.astronauts.entity.Mission;
import com.rmilan.astronauts.entity.MissionResult;
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
public class MissionRepositoryTest {

    @Autowired
    MissionRepository missionRepository;

    @Autowired
    AstronautRepository astronautRepository;

    @Test
    public void saveAMission() {
        Mission vostok5 = Mission.builder()
                .missionName("Vostok-5")
                .startedAt(LocalDate.of(1963, 6, 14))
                .finishedAt(LocalDate.of(1963, 6, 19))
                .result(MissionResult.SUCCESSFULL)
                .country("Soviet Union")
                .build();

        missionRepository.save(vostok5);
        List<Mission> missions = missionRepository.findAll();
        assertThat(missions)
                .hasSize(1)
                .containsOnly(vostok5);
    }

    /**
     * mission_name column is NOT_NULL, test case checks that save an item without missionName into DB throws a
     * DataIntegrityViolationException.
     */
    @Test(expected = DataIntegrityViolationException.class)
    public void checkNotNullColumn() {
        Mission vostok5 = Mission.builder()
                .startedAt(LocalDate.of(1963, 6, 14))
                .finishedAt(LocalDate.of(1963, 6, 19))
                .result(MissionResult.SUCCESSFULL)
                .country("Soviet Union")
                .build();

        missionRepository.save(vostok5);
    }

    /**
     * mission_name column is unique, test case checks that save two items with the same missionName into DB throws a
     * DataIntegrityViolationException.
     */
    @Test(expected = DataIntegrityViolationException.class)
    public void checkUniqueColumn() {
        Mission vostok5 = Mission.builder()
                .missionName("Vostok-5")
                .startedAt(LocalDate.of(1963, 6, 14))
                .finishedAt(LocalDate.of(1963, 6, 19))
                .result(MissionResult.SUCCESSFULL)
                .country("Soviet Union")
                .build();

        missionRepository.save(vostok5);

        Mission vostok6 = Mission.builder()
                .missionName("Vostok-5")
                .startedAt(LocalDate.of(1963, 6, 14))
                .finishedAt(LocalDate.of(1964, 6, 19))
                .result(MissionResult.FAILED)
                .country("Soviet Union")
                .build();

        missionRepository.saveAndFlush(vostok6);
    }

    @Test
    public void missionIsPersistedWithAstronaut() {

        Mission vostok1 = Mission.builder()
                .missionName("Vostok-1")
                .startedAt(LocalDate.of(1961, 3, 12))
                .finishedAt(LocalDate.of(1961, 3, 12))
                .result(MissionResult.SUCCESSFULL)
                .country("Soviet Union")
                .build();

        Mission vostok2 = Mission.builder()
                .missionName("Vostok-2")
                .startedAt(LocalDate.of(1961, 3, 12))
                .finishedAt(LocalDate.of(1961, 3, 12))
                .result(MissionResult.SUCCESSFULL)
                .country("Soviet Union")
                .build();

        Astronaut gagarin = Astronaut.builder()
                .name("Yuri Gagarin")
                .nationality("soviet")
                .birthDate(LocalDate.of(1934, 3, 9))
                .deathDate(LocalDate.of(1968, 3, 27))
                .mission(vostok1)
                .mission(vostok2)
                .build();

        astronautRepository.save(gagarin);
        List<Mission> missions = missionRepository.findAll();
        assertThat(missions)
                .hasSize(2)
                .allMatch(mission -> mission.getId() > 0L);
    }

    @Test
    public void findMissionByCountry() {
        Mission vostok1 = Mission.builder()
                .missionName("Vostok-1")
                .startedAt(LocalDate.of(1961, 3, 12))
                .finishedAt(LocalDate.of(1961, 3, 12))
                .result(MissionResult.SUCCESSFULL)
                .country("Soviet Union")
                .build();

        Mission sojuzT2 = Mission.builder()
                .missionName("Sojuz T-2")
                .country("Soviet Union")
                .build();

        Mission apollo8 = Mission.builder()
                .missionName("Apollo-8")
                .country("USA")
                .build();

        missionRepository.saveAll(Lists.newArrayList(vostok1, apollo8, sojuzT2));
        List<Mission> missions = missionRepository.findMissionByCountryContains("Soviet");

        assertThat(missions)
                .hasSize(2)
                .containsExactlyInAnyOrder(vostok1, sojuzT2);
    }

    @Test
    public void findMissionByName() {
        Mission vostok5 = Mission.builder()
                .missionName("Vostok-5")
                .startedAt(LocalDate.of(1963, 6, 14))
                .finishedAt(LocalDate.of(1963, 6, 19))
                .result(MissionResult.SUCCESSFULL)
                .country("Soviet Union")
                .build();

        Mission vostok1 = Mission.builder()
                .missionName("Vostok-1")
                .startedAt(LocalDate.of(1961, 3, 12))
                .finishedAt(LocalDate.of(1961, 3, 12))
                .result(MissionResult.SUCCESSFULL)
                .country("Soviet Union")
                .build();

        Mission sojuzT2 = Mission.builder()
                .missionName("Sojuz T-2")
                .country("Soviet Union")
                .build();

        Mission apollo8 = Mission.builder()
                .missionName("Apollo-8")
                .country("USA")
                .build();

        missionRepository.saveAll(Lists.newArrayList(vostok1, vostok5, sojuzT2, apollo8));
        List<Mission> missions = missionRepository.findMissionByMissionNameContains("Vostok");

        assertThat(missions)
                .hasSize(2)
                .containsExactlyInAnyOrder(vostok1, vostok5);
    }

    @Test
    public void findMissionByResult() {
        Mission vostok5 = Mission.builder()
                .missionName("Vostok-5")
                .startedAt(LocalDate.of(1963, 6, 14))
                .finishedAt(LocalDate.of(1963, 6, 19))
                .result(MissionResult.SUCCESSFULL)
                .country("Soviet Union")
                .build();

        Mission vostok1 = Mission.builder()
                .missionName("Vostok-1")
                .startedAt(LocalDate.of(1961, 3, 12))
                .finishedAt(LocalDate.of(1961, 3, 12))
                .result(MissionResult.SUCCESSFULL)
                .country("Soviet Union")
                .build();

        Mission sojuzT2 = Mission.builder()
                .missionName("Sojuz T-2")
                .country("Soviet Union")
                .result(MissionResult.SUCCESSFULL)
                .build();

        Mission apollo8 = Mission.builder()
                .missionName("Apollo-8")
                .country("USA")
                .result(MissionResult.SUCCESSFULL)
                .build();

        missionRepository.saveAll(Lists.newArrayList(vostok1, vostok5, sojuzT2, apollo8));

        List<Mission> missionByResult = missionRepository.findMissionByResult(MissionResult.FAILED);
        assertThat(missionByResult)
                .hasSize(0);

        List<Mission> successfulMissions = missionRepository.findMissionByResult(MissionResult.SUCCESSFULL);
        assertThat(successfulMissions)
                .hasSize(4);
    }

    @Test
    public void findMissionBetweenGivenTimeFrame() {
        Mission vostok5 = Mission.builder()
                .missionName("Vostok-5")
                .startedAt(LocalDate.of(1963, 6, 14))
                .finishedAt(LocalDate.of(1963, 6, 19))
                .result(MissionResult.SUCCESSFULL)
                .country("Soviet Union")
                .build();

        Mission vostok1 = Mission.builder()
                .missionName("Vostok-1")
                .startedAt(LocalDate.of(1961, 3, 12))
                .finishedAt(LocalDate.of(1961, 3, 12))
                .result(MissionResult.SUCCESSFULL)
                .country("Soviet Union")
                .build();

        Mission sojuzT2 = Mission.builder()
                .missionName("Sojuz T-2")
                .country("Soviet Union")
                .result(MissionResult.SUCCESSFULL)
                .startedAt(LocalDate.of(1980, 6, 5))
                .finishedAt(LocalDate.of(1980, 6, 9))
                .build();

        Mission apollo8 = Mission.builder()
                .missionName("Apollo-8")
                .country("USA")
                .result(MissionResult.SUCCESSFULL)
                .startedAt(LocalDate.of(1968, 12, 21))
                .finishedAt(LocalDate.of(1968, 12, 27))
                .build();

        missionRepository.saveAll(Lists.newArrayList(vostok1, vostok5, sojuzT2, apollo8));
        List<String> allMissionsAtGivenTimeRange = missionRepository.findAllMissionNamesAtGivenTimeRange(LocalDate.of(1960, 1, 1),
                LocalDate.of(1969, 12, 31));

        assertThat(allMissionsAtGivenTimeRange)
                .hasSize(3)
                .containsExactlyInAnyOrder("Vostok-1", "Vostok-5", "Apollo-8");


    }
}