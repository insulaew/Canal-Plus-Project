package com.meetingplanner.controller;

import java.util.Set;
import java.util.stream.Collectors;

import com.meetingplanner.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.meetingplanner.dto.FreeToolDto;
import com.meetingplanner.mapper.FreeToolMapper;
import com.meetingplanner.model.FreeTool;
import com.meetingplanner.service.FreeToolService;

/*Classe du controller de l'entité FreeTool*/
@RestController
@CrossOrigin
@RequestMapping("/api")
public class FreeToolController {

    @Autowired
    private FreeToolService freeToolService;

    @Autowired
    private MeetingService meetingService;

    /*Permet de récupérer tous les outils libres en base de données*/
    @GetMapping(value="/EquipementsLibres")
    public ResponseEntity<Set<FreeToolDto>>listeEquipementsLibres() {
        try {
            Set<FreeTool> freeTools = freeToolService.getFreeTools()
                    .stream()
                    .map(freeTool -> new FreeTool(freeTool.getFreeToolId(), freeTool.getType(), freeTool.getMeetings()))
                    .collect(Collectors.toSet());
            Set<FreeToolDto> freeToolDtos = freeTools
                    .stream()
                    .map(freeTool -> FreeToolMapper.FreeToolEntityDtoMapper(freeTool))
                    .collect(Collectors.toSet());
            return new ResponseEntity<>(freeToolDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*Permet de récupérer un outil libre par son id en base de données*/
    @GetMapping(value="/EquipementLibre/")
    public ResponseEntity<FreeToolDto>equipementById(@RequestParam Long id) {
        try {
            FreeTool freeToolById = freeToolService.getFreeToolById(id).orElseThrow();
            return new ResponseEntity<>(FreeToolMapper.FreeToolEntityDtoMapper(freeToolById), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*Permet de récupérer tous les outils libres par type disponibles pour une réunion en base de données*/
    @GetMapping(value="/EquipementsLibresByType")
    public ResponseEntity<Set<FreeToolDto>>listeEquipementsLibresByTypeCompatibleForMeeting(@RequestParam String type, int meetingStartHour) {
        try {
            Set<FreeTool> freeToolsByTypeCompatibleForMeeting = freeToolService.getFreeToolsByTypeCompatibleForMeeting(type, meetingStartHour)
                    .stream()
                    .map(freeTool -> new FreeTool(freeTool.getFreeToolId(), freeTool.getType(), freeTool.getMeetings()))
                    .collect(Collectors.toSet());
            Set<FreeToolDto> freeToolsByTypeCompatibleForMeetingDtos = freeToolsByTypeCompatibleForMeeting
                    .stream()
                    .map(freeTool -> FreeToolMapper.FreeToolEntityDtoMapper(freeTool))
                    .collect(Collectors.toSet());
            return new ResponseEntity<>(freeToolsByTypeCompatibleForMeetingDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
