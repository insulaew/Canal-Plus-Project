package com.meetingplanner.repository;

import com.meetingplanner.model.FreeTool;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface FreeToolRepository extends JpaRepository<FreeTool, Long> {

}
