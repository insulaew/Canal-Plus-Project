package com.meetingplanner.repository;

import com.meetingplanner.model.Meeting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/*Classe du repository de l'entité Meeting*/
@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    /*Requête SQL native personnalisée permettant de récupérer les réunions non réservées
    * en base de données*/
    @Query(value = "SELECT m.* FROM meeting m WHERE m.is_reserved = false", nativeQuery = true)
    List<Meeting> findNotReservedMeetings();

    /*Requête SQL native personnalisée permettant de récupérer les réunions réservées
     * en base de données*/
    @Query(value = "SELECT m.* FROM meeting m WHERE m.is_reserved = true", nativeQuery = true)
    List<Meeting> findReservedMeetings();

}
