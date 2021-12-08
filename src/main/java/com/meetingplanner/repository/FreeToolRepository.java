package com.meetingplanner.repository;

import com.meetingplanner.model.FreeTool;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*Classe du repository de l'entité FreeTool*/
@Repository
public interface FreeToolRepository extends JpaRepository<FreeTool, Long> {

    @Query(value = "select ft.* from free_tool ft " +
            "where ft.type = :type",
    nativeQuery = true)
    List<FreeTool> findFreeToolsByType(String type);

    /*Requête SQL native personnalisée permettant de requêter les outils libres par type qui sont disponibles
    * pour une réunion à une heure précise mais peuvent potentiellement être réservés pour d'autres réunions
    * à une heure différente.*/
    @Query(value = "select ft.* from free_tool ft where ft.outil_libre_id in\n" +
            "((select distinct ft.outil_libre_id from free_tool ft\n" +
            "left join meeting_free_tool mft on ft.outil_libre_id = mft.outil_libre_id\n" +
            "left join meeting mt on mt.reunion_id = mft.reunion_id\n" +
            "where (ft.type = :type)\n" +
            "and (mt.start_hour != :meetingStartHour or mt.start_hour is null))\n" +
            "except\n" +
            "(select distinct mft.outil_libre_id from \n" +
            "(select * from meeting_free_tool mft\n" +
            "left join meeting mt\n" +
            "on mft.reunion_id = mt.reunion_id\n" +
            "where (mt.start_hour != :meetingStartHour)) mft\n" +
            "join (select * from meeting_free_tool mft\n" +
            "left join meeting mt\n" +
            "on mft.reunion_id = mt.reunion_id\n" +
            "where (mt.start_hour = :meetingStartHour)) mf_t\n" +
            "on mft.outil_libre_id = mf_t.outil_libre_id))",
    nativeQuery = true)
    List<FreeTool> findFreeToolsByTypeCompatibleForMeeting(String type, int meetingStartHour);

}
