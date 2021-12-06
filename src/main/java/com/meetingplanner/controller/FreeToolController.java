package com.meetingplanner.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.meetingplanner.model.Meeting;
import com.meetingplanner.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.meetingplanner.dto.FreeToolDto;
import com.meetingplanner.mapper.FreeToolMapper;
import com.meetingplanner.model.FreeTool;
import com.meetingplanner.service.FreeToolService;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class FreeToolController {

    @Autowired
    private FreeToolService freeToolService;

    @Autowired
    private MeetingService meetingService;

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

    @GetMapping(value="/EquipementLibre/")
    public ResponseEntity<FreeToolDto>equipementById(@RequestParam Long id) {
        try {
            FreeTool _freeToolById = freeToolService.getFreeToolById(id).orElseThrow();
            return new ResponseEntity<>(FreeToolMapper.FreeToolEntityDtoMapper(_freeToolById), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/EquipementsLibresByType")
    public ResponseEntity<Set<FreeToolDto>>listeEquipementsLibresByTypeCompatibleForMeeting(@RequestParam String type, int meetingStartHour) {
        try {
            List<FreeTool> _freeToolsByType = freeToolService.getFreeToolsByType(type, meetingStartHour);
            _freeToolsByType.stream().filter(x -> !x.getMeetings().isEmpty());
            Set<FreeTool> _freeToolsByType_ = new HashSet<>(_freeToolsByType);
            Set<FreeToolDto> freeToolsByTypeDtos = new HashSet<>();
            _freeToolsByType_.forEach(x -> freeToolsByTypeDtos.add(FreeToolMapper.FreeToolEntityDtoMapper(x)));
            return new ResponseEntity<>(freeToolsByTypeDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/EquipementsLibres", params="ids")
    public ResponseEntity<Set<FreeToolDto>>listeEquipementsLibresByIds(@RequestParam List<Long> ids) {
        try {
            List<FreeTool> _freeToolsByIds = freeToolService.getFreeToolsByIds(ids);
            Set<FreeTool> _freeToolsByIds_ = new HashSet<>(_freeToolsByIds);
            Set<FreeToolDto> freeToolDtosByIds = new HashSet<>();
            _freeToolsByIds_.forEach(x -> freeToolDtosByIds.add(FreeToolMapper.FreeToolEntityDtoMapper(x)));
            return new ResponseEntity<>(freeToolDtosByIds, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/Equipement")
    public ResponseEntity<FreeToolDto> postOutilLibre(@Valid @RequestBody FreeToolDto freeToolDto) {
        FreeTool freeTool = freeToolDto.toFreeTool();
        Set<Long> meetingsIds =  freeToolDto.getMeetingsIds();
        List<Long> _meetingsIds = new ArrayList<>(meetingsIds);
        try {
            List<Meeting> meetings = this.meetingService.getMeetingsByIds(_meetingsIds);
            Set<Meeting> _meetings = new HashSet<>(meetings);
            freeTool.setMeetings(_meetings);
            return new ResponseEntity<>(FreeToolMapper.FreeToolEntityDtoMapper((this.freeToolService.saveFreeTool(freeTool))), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
