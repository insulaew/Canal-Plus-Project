package com.meetingplanner.repository;

import com.meetingplanner.model.FreeTool;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface FreeToolRepository extends JpaRepository<FreeTool, Long> {

    @Query(value = "select * from free_tool as f where f.type = :type",
    nativeQuery = true)
    List<FreeTool> findFreeToolsByType(String type);

    @Query(value ="select ft.* \n" +
            "from free_tool ft,\n" +
            "meeting_free_tool mft,\n" +
            "meeting mt\n" +
            "WHERE\n" +
            "ft.outil_libre_id = mft.outil_libre_id\n" +
            "AND mt.reunion_id = mft.reunion_id\n" +
            "AND mt.reunion_id = 1",
    nativeQuery = true)
    List<FreeTool> findFreeToolByMeeting(Long meetingId);

}
