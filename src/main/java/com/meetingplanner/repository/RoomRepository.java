package com.meetingplanner.repository;

import com.meetingplanner.model.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query(value = "select r.* from room r " +
            "left join meeting mt " +
            "on (r.salle_id = mt.salle_id) " +
            "where " +
            "(r.capacity70 >= :meetingNumberOfPersons and r.capacity70 <= (15*log(:meetingNumberOfPersons))) " +
            "and " +
            "(mt.start_hour != :meetingStartHour and mt.start_hour != (:meetingStartHour - 1) or mt.start_hour IS null)",
    nativeQuery = true)
    List<Room> findRoomsCompatibleForMeeting(int meetingNumberOfPersons, int meetingStartHour);

}
