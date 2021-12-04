package com.meetingplanner.repository;

import com.meetingplanner.model.RoomTool;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomToolRepository extends JpaRepository<RoomTool, Long> {

}
