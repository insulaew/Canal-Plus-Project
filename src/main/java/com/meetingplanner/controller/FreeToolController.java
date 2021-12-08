package com.meetingplanner.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            List<FreeTool> _freeTools = freeToolService.getFreeTools();
            Set<FreeTool> _freeTools_ = new HashSet<>(_freeTools);
            Set<FreeToolDto> freeToolDtos = new HashSet<>();
            _freeTools_.forEach(x -> freeToolDtos.add(FreeToolMapper.FreeToolEntityDtoMapper(x)));
            return new ResponseEntity<>(freeToolDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /*Permet de récupérer un outil libre par son id en base de données*/
    @GetMapping(value="/EquipementLibre/")
    public ResponseEntity<FreeToolDto>equipementById(@RequestParam Long id) {
        try {
            FreeTool _freeToolById = freeToolService.getFreeToolById(id).orElseThrow();
            return new ResponseEntity<>(FreeToolMapper.FreeToolEntityDtoMapper(_freeToolById), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /*Permet de récupérer tous les outils libres par type disponibles pour une réunion en base de données*/
    @GetMapping(value="/EquipementsLibresByType")
    public ResponseEntity<Set<FreeToolDto>>listeEquipementsLibresByTypeCompatibleForMeeting(@RequestParam String type, int meetingStartHour) {
        try {
            List<FreeTool> _freeToolsByTypeCompatibleForMeeting = freeToolService.getFreeToolsByTypeCompatibleForMeeting(type, meetingStartHour);
            Set<FreeTool> _freeToolsByTypeCompatibleForMeeting_ = new HashSet<>(_freeToolsByTypeCompatibleForMeeting);
            Set<FreeToolDto> freeToolsByTypeCompatibleForMeetingDtos = new HashSet<>();
            _freeToolsByTypeCompatibleForMeeting_.forEach(x -> freeToolsByTypeCompatibleForMeetingDtos.add(FreeToolMapper.FreeToolEntityDtoMapper(x)));
            System.out.println(freeToolsByTypeCompatibleForMeetingDtos);
            return new ResponseEntity<>(freeToolsByTypeCompatibleForMeetingDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
