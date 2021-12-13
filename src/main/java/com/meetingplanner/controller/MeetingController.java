package com.meetingplanner.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.meetingplanner.model.*;
import com.meetingplanner.validator.MeetingValidator;
import com.meetingplanner.dto.MeetingDto;
import com.meetingplanner.mapper.MeetingMapper;
import com.meetingplanner.service.MeetingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/*Classe du controller de l'entité Meeting*/
@RestController
@CrossOrigin
@RequestMapping("/api")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @GetMapping(value="/Reunions")
    public ResponseEntity<Set<MeetingDto>>listeReunions() {
        try {
            Set<Meeting> meetings = meetingService.getMeetings()
                    .stream()
                    .map(meeting -> new Meeting(meeting.getId(), meeting.getType(), meeting.getStartHour(), meeting.getNumberOfPersons(), meeting.isReserved(), meeting.getRoom(), meeting.getUser(), meeting.getFreeTools()))
                    .collect(Collectors.toSet());
            Set<MeetingDto> meetingsDtos = meetings
                    .stream()
                    .map(meeting -> MeetingMapper.MeetingEntityDtoMapper(meeting))
                    .collect(Collectors.toSet());
            return new ResponseEntity<>(meetingsDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/ReunionsByIds", params="ids")
    public ResponseEntity<Set<MeetingDto>>listeReunionsByIds(@RequestParam List<Long> ids) {
        try {
            Set<Meeting> meetingsByIds = meetingService.getMeetingsByIds(ids)
                    .stream()
                    .map(meeting -> new Meeting(meeting.getId(), meeting.getType(), meeting.getStartHour(), meeting.getNumberOfPersons(), meeting.isReserved(), meeting.getRoom(), meeting.getUser(), meeting.getFreeTools()))
                    .collect(Collectors.toSet());
            Set<MeetingDto> meetingsByIdsDtos = meetingsByIds
                    .stream()
                    .map(meeting -> MeetingMapper.MeetingEntityDtoMapper(meeting))
                    .collect(Collectors.toSet());
            return new ResponseEntity<>(meetingsByIdsDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/ReunionsNonReservees")
    public ResponseEntity<Set<MeetingDto>>listeReunionsNonReservees() {
        try {
            Set<Meeting> meetings = meetingService.getNotReservedMeetings()
                    .stream()
                    .map(meeting -> new Meeting(meeting.getId(), meeting.getType(), meeting.getStartHour(), meeting.getNumberOfPersons(), meeting.isReserved(), meeting.getRoom(), meeting.getUser(), meeting.getFreeTools()))
                    .collect(Collectors.toSet());
            Set<MeetingDto> meetingsDtos = meetings
                    .stream()
                    .map(meeting -> MeetingMapper.MeetingEntityDtoMapper(meeting))
                    .collect(Collectors.toSet());
            return new ResponseEntity<>(meetingsDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/ReunionsReservees")
    public ResponseEntity<Set<MeetingDto>>listeReunionsReservees() {
        try {
            Set<Meeting> meetings = meetingService.getReservedMeetings()
                    .stream()
                    .map(meeting -> new Meeting(meeting.getId(), meeting.getType(), meeting.getStartHour(), meeting.getNumberOfPersons(), meeting.isReserved(), meeting.getRoom(), meeting.getUser(), meeting.getFreeTools()))
                    .collect(Collectors.toSet());
            Set<MeetingDto> meetingsDtos = meetings
                    .stream()
                    .map(meeting -> MeetingMapper.MeetingEntityDtoMapper(meeting))
                    .collect(Collectors.toSet());
            return new ResponseEntity<>(meetingsDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "Reunion")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MeetingDto> postReunion(@Valid @RequestBody MeetingDto meetingDto) {
        /*Le DTO du meeting arrivant du front-end est transformé en entité*/
        Meeting meeting = meetingDto.toMeeting();

        /*Si le DTO Meeting a un UserDTO. */
        if (meetingDto.getUserDto() != null) {
            /*Le DTO de l'user du DTO meeting est transformé en entité User*/
            User user = meetingDto.getUserDto().toUser();
            /*On affecte l'User au meeting*/
            meeting.setUser(user);
        }

        Room room = meetingDto.getRoomDto().toRoom();
        /*Si le DTO Meeting a une RoomDTO, on le transforme en entité Room,
        * on lui affecte ses RoomTools et on l'affecte au Meeting. */
        if(meetingDto.getRoomDto() != null) {
            Set<Long> meetingsIds = meetingDto.getRoomDto().getMeetingsIds();
            if(meetingsIds != null) {
                Set<Meeting> roomMeetings = this.meetingService.getMeetingsByIds(new ArrayList<>(meetingsIds))
                        .stream()
                        .map(meeting1 -> new Meeting(meeting1.getId(), meeting1.getType(), meeting1.getStartHour(), meeting1.getNumberOfPersons(), meeting1.isReserved()))
                        .collect(Collectors.toSet());
                room.setMeetings(roomMeetings);
            }
            if (meetingDto.getRoomDto().getRoomToolDtos() != null) {
                Set<RoomTool> roomTools = meetingDto.getRoomDto().getRoomToolDtos()
                        .stream()
                        .map(roomToolDto -> new RoomTool(roomToolDto.getRoomToolId(), ToolType.valueOf(roomToolDto.getType()), room))
                        .collect(Collectors.toSet());
                room.setRoomTools(roomTools);
            }
            meeting.setRoom(room);
        }

        /*Si le DTO Meeting a des FreeTools DTOs, on les transforme en entité FreeTool, on leur affecte leur Meetings
         * et on les affecte au Meeting. */
        if (meetingDto.getFreeToolDtos() != null) {
            Set<FreeTool> freeTools = meetingDto.getFreeToolDtos()
                    .stream()
                    .map(freeToolDto -> new FreeTool(freeToolDto.getFreeToolId(), ToolType.valueOf(freeToolDto.getType()), new HashSet<>(this.meetingService.getMeetingsByIds(new ArrayList<>(freeToolDto.getMeetingsIds())))))
                    .collect(Collectors.toSet());
            meeting.setFreeTools(freeTools);
        }

        try {
            MeetingValidator.isValid(meeting);
            Meeting savedMeeting = this.meetingService.saveMeeting(meeting);
            return new ResponseEntity<>(MeetingMapper.MeetingEntityDtoMapper(savedMeeting), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "Reunion")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MeetingDto> cancelReunion(@RequestBody MeetingDto meetingDto) {
        Meeting meeting = meetingDto.toMeeting();
        meeting.setReserved(false);
        try {
            MeetingValidator.isValid(meeting);
            return  new ResponseEntity<>(MeetingMapper.MeetingEntityDtoMapper(this.meetingService.saveMeeting(meeting)), HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
