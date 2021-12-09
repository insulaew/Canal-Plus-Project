package com.meetingplanner.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.meetingplanner.model.*;
import com.meetingplanner.validator.MeetingValidator;
import com.meetingplanner.dto.FreeToolDto;
import com.meetingplanner.dto.MeetingDto;
import com.meetingplanner.mapper.MeetingMapper;
import com.meetingplanner.service.MeetingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping(value = "Reunion")
    public ResponseEntity<MeetingDto> postReunion(@Valid @RequestBody MeetingDto meetingDto) {
        /*Le DTO du meeting arrivant du front-end est transformé en entité*/
        Meeting meeting = meetingDto.toMeeting();

        if (meetingDto.getUserDto() != null) {
            /*Le DTO de l'user du DTO meeting est transformé en entité User*/
            User user = meetingDto.getUserDto().toUser();
            /*On récupère les Ids Meetings du DTO User*/

            if (meetingDto.getUserDto().getMeetingsIds().size() > 0) {
                Set<Long> _userMeetingsIds = meetingDto.getUserDto().getMeetingsIds();
                List<Long> _userMeetingsIds_ = new ArrayList<>(_userMeetingsIds);
                /*On récupère les Meetings en base de données grâce aux Ids Meeting*/
                List<Meeting> _userMeetings = this.meetingService.getMeetingsByIds(_userMeetingsIds_);
                if (_userMeetings.size() > 0) {
                    Set<Meeting> _userMeetings_ = new HashSet<>(_userMeetings);
                    /*On set les entités des Meetings de l'entité User*/
                    user.setMeetings(_userMeetings_);
                }
            }

            /*On set l'entité User de l'entité Meeting*/
            meeting.setUser(user);
        }


        if (meetingDto.getRoomDto() != null) {

            /*Le DTO Room du DTO Meeting est transformé en entité Room*/
            Room room = meetingDto.getRoomDto().toRoom();

            if (meetingDto.getRoomDto().getMeetingsIds().size() > 0) {
                /*On récupères les Ids Meetings du DTO Room du DTO Meeting*/
                Set<Long> _roomMeetingsIds = meetingDto.getRoomDto().getMeetingsIds();
                List<Long> _roomMeetingsIds_ = new ArrayList<>(_roomMeetingsIds);
                /*On récupère les Meetings en base de données grâce aux Ids Meeting*/
                List<Meeting> _roomMeetings = this.meetingService.getMeetingsByIds(_roomMeetingsIds_);
                if (_roomMeetings.size() > 0) {
                    Set<Meeting> _roomMeetings_ = new HashSet<>(_roomMeetings);
                    /*On set les entités Meetings de l'entité Room*/
                    room.setMeetings(_roomMeetings_);
                    if (meetingDto.getRoomDto().getRoomToolDtos().size() > 0) {
                        Set<RoomTool> roomTools = new HashSet<>();
                        /*On transforme en entité RoomTool tous les DTO RoomTool du DTO Room*/
                        meetingDto.getRoomDto().getRoomToolDtos().forEach(x -> {
                            roomTools.add(x.toRoomTool());
                        });
                        /*On set l'entité Room pour toutes les entités RoomTool*/
                        roomTools.forEach(x -> x.setRoom(room));
                        /*On set les entités RoomTool de l'entité Room*/
                        room.setRoomTools(roomTools);
                    }
                }
            }

            /*On set l'entité Room dans l'entité Meeting*/
            meeting.setRoom(room);
        }

        if (meetingDto.getFreeToolDtos().size() > 0) {
            /*On récupère tous les DTOs FreeTool du DTO Meeting*/
            Set<FreeToolDto> freeToolsDtos = meetingDto.getFreeToolDtos();
            Set<FreeTool> freeTools = new HashSet<>();
            freeToolsDtos.forEach(x ->{
                FreeTool freeTool = x.toFreeTool();
                /*Pour chaque DTO FreeTool, on va chercher en base de données les Meetings grâce aux IDs Meeting des DTO FreeTool
                * et on les ajoute aux entités FreeTool*/
                freeTool.setMeetings(new HashSet<>(this.meetingService.getMeetingsByIds(new ArrayList<>(x.getMeetingsIds()))));
                freeTools.add(freeTool);
            });
            /*On set les entités FreeTool de l'entité Meeting*/
            meeting.setFreeTools(freeTools);
        }

        try {

            MeetingValidator.isValid(meeting);
            this.meetingService.saveMeeting(meeting);
            return new ResponseEntity<>(MeetingMapper.MeetingEntityDtoMapper(meeting), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "Reunion")
    public ResponseEntity<MeetingDto> updateReunion(@RequestBody MeetingDto meetingDto) {
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
