package com.meetingplanner.repository;

import com.meetingplanner.model.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*Classe du repository de l'entité Room*/
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    /*Requête SQL native personnalisée permettant de requêter les salles dont la capacité à 70% est supérieure
     * à un nombre de personnes donné et qui sont disponibles
     * pour une réunion à une heure précise mais peuvent potentiellement être réservés pour d'autres réunions
     * avec une contrainte d'1h de nettoyage avant chaque réunion dans la salle.
     * Variante avec la capacité à 70% qui doit être inférieure à 15*LOG(nombre de personnes)*/
    @Query(value = "SELECT RM.* FROM ROOM RM \n" +
            "WHERE RM.SALLE_ID IN \n" +
            "(SELECT R.SALLE_ID FROM ROOM R\n" +
            "LEFT JOIN MEETING MT\n" +
            "ON MT.SALLE_ID = R.SALLE_ID\n" +
            "WHERE (R.CAPACITY70 >= :meetingNumberOfPersons AND R.CAPACITY70 <= 15*LOG(:meetingNumberOfPersons))\n" +
            "AND (MT.START_HOUR NOT IN (:meetingStartHour - 1, :meetingStartHour, :meetingStartHour + 1) OR MT.SALLE_ID IS NULL)\n" +
            "EXCEPT (SELECT M.SALLE_ID FROM \n" +
            "(SELECT * FROM MEETING MT WHERE MT.START_HOUR NOT IN (:meetingStartHour - 1, :meetingStartHour, :meetingStartHour + 1) and MT.SALLE_ID IS NOT NULL) AS M\n" +
            "JOIN (SELECT * FROM MEETING MT WHERE MT.START_HOUR IN (:meetingStartHour - 1, :meetingStartHour, :meetingStartHour + 1) and MT.SALLE_ID IS NOT NULL) MT\n" +
            "ON M.SALLE_ID = MT.SALLE_ID))",
    nativeQuery = true)
    List<Room> findRoomsCompatibleForMeeting(int meetingNumberOfPersons, int meetingStartHour);

    /*Permet de récupérer une salle en fonction de son Id String*/
    @Query(value = "select rm.* from room rm where rm.salle_id = :roomCode",
    nativeQuery = true)
    Optional<Room> findByRoomCode(String roomCode);

}
