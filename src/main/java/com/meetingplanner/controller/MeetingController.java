package com.meetingplanner.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.meetingplanner.dto.MeetingDto;
import com.meetingplanner.mapper.MeetingMapper;
import com.meetingplanner.model.Meeting;
import com.meetingplanner.service.MeetingService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @GetMapping(value="/Reunions")
    public ResponseEntity<Set<MeetingDto>>listeReunions() {
        try {
            List<Meeting> _meetings = meetingService.getMeetings();
            Set<Meeting> _meetings_ = new HashSet<>(_meetings);
            Set<MeetingDto> meetingsDtos = new HashSet<>();
            _meetings_.forEach(x -> meetingsDtos.add(MeetingMapper.MeetingEntityDtoMapper(x)));
            return new ResponseEntity<>(meetingsDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/ReunionsByIds", params="ids")
    public ResponseEntity<Set<MeetingDto>>listeReunionsByIds(@RequestParam List<Long> ids) {
        try {
            List<Meeting> _meetingsByIds = meetingService.getMeetingsByIds(ids);
            Set<Meeting> _meetingsByIds_ = new HashSet<>(_meetingsByIds);
            Set<MeetingDto> meetingsByIdsDtos = new HashSet<>();
            _meetingsByIds_.forEach(x -> meetingsByIdsDtos.add(MeetingMapper.MeetingEntityDtoMapper(x)));
            return new ResponseEntity<>(meetingsByIdsDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/ReunionsNonReservees")
    public ResponseEntity<Set<MeetingDto>>listeReunionsNonReservees() {
        try {
            List<Meeting> _meetings = meetingService.getNotReservedMeetings();
            Set<Meeting> _meetings_ = new HashSet<>(_meetings);
            Set<MeetingDto> meetingsDtos = new HashSet<>();
            _meetings_.forEach(x -> meetingsDtos.add(MeetingMapper.MeetingEntityDtoMapper(x)));
            return new ResponseEntity<>(meetingsDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/ReunionsReservees")
    public ResponseEntity<Set<MeetingDto>>listeReunionsReservees() {
        try {
            List<Meeting> _meetings = meetingService.getReservedMeetings();
            Set<Meeting> _meetings_ = new HashSet<>(_meetings);
            Set<MeetingDto> meetingsDtos = new HashSet<>();
            _meetings_.forEach(x -> meetingsDtos.add(MeetingMapper.MeetingEntityDtoMapper(x)));
            return new ResponseEntity<>(meetingsDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
