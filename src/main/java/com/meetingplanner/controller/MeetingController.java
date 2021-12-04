package com.meetingplanner.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
