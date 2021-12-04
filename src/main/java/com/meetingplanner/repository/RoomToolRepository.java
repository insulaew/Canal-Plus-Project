package com.meetingplanner.repository;

import com.meetingplanner.model.RoomTool;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomToolRepository extends JpaRepository<RoomTool, Long> {

    @Query(value = "select * from room_tool as r where r.type = :type",
    nativeQuery = true)
    List<RoomTool> findRoomToolsByType(String type);

    @Query(value = "select * from room_tool as r where r.salle_id = :id",
    nativeQuery = true)
    List<RoomTool> findRoomToolsByRoom(String id);

}
