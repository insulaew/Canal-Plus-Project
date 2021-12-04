package com.meetingplanner.repository;

import com.meetingplanner.model.Meeting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query("SELECT m FROM Meeting m WHERE m.isReserved = false")
    List<Meeting> findNotReservedMeetings();

    @Query("SELECT m FROM Meeting m WHERE m.isReserved = true")
    List<Meeting> findReservedMeetings();

}
