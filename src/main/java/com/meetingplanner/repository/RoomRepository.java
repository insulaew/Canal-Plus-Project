package com.meetingplanner.repository;

import com.meetingplanner.model.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query(value = "select * " +
            "from room r " +
            "where " +
            "r.capacity70 >= :meetingNumberOfPersons " +
            "and r.capacity70 <= 14.8*log(:meetingNumberOfPersons)",
    nativeQuery = true)
    List<Room> findRoomsWithEnoughCapacity(int meetingNumberOfPersons);
}
