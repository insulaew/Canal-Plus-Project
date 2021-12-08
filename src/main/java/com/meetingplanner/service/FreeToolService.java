package com.meetingplanner.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetingplanner.model.FreeTool;
import com.meetingplanner.repository.FreeToolRepository;

/*Classe du service de l'entité FreeTool*/
@Service
public class FreeToolService {

    @Autowired
    private FreeToolRepository freeToolRepository;

    public List<FreeTool> getFreeTools() {
        return freeToolRepository.findAll();
    }

    public Optional<FreeTool> getFreeToolById(Long id) {
        return freeToolRepository.findById(id);
    }

    public List<FreeTool> getFreeToolsByTypeCompatibleForMeeting(String type, int meetingStartHour) { return freeToolRepository.findFreeToolsByTypeCompatibleForMeeting(type, meetingStartHour); }

}
