package com.rmilan.astronauts.repository;

import com.rmilan.astronauts.entity.Mission;
import com.rmilan.astronauts.entity.MissionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface MissionRepository extends JpaRepository<Mission, Long> {

    List<Mission> findMissionByCountryContains(String country);

    List<Mission> findMissionByMissionNameContains(String missionName);

    List<Mission> findMissionByResult(MissionResult result);

    @Query("SELECT m.missionName FROM Mission m where m.startedAt BETWEEN :from and :to")
    List<String> findAllMissionNamesAtGivenTimeRange(@Param("from")LocalDate from, @Param("to")LocalDate to);
}

