package com.meetingplanner.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetingplanner.model.RoomTool;
import com.meetingplanner.repository.RoomToolRepository;

@Service
public class RoomToolService {

    @Autowired
    private RoomToolRepository roomToolRepository;

    public List<RoomTool> getRoomTools() {
        return roomToolRepository.findAll();
    }

    public Optional<RoomTool> getRoomToolById(Long id) {
        return roomToolRepository.findById(id);
    }

    public List<RoomTool> getRoomToolsByIds(List<Long> ids) {
        return roomToolRepository.findAllById(ids);
    }

}
