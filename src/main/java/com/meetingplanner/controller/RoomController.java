package com.meetingplanner.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.meetingplanner.dto.RoomDto;
import com.meetingplanner.mapper.RoomMapper;
import com.meetingplanner.model.Room;
import com.meetingplanner.service.RoomService;

/*Classe du controller de l'entité Room*/
@RestController
@CrossOrigin
@RequestMapping("/api")
public class RoomController {

    @Autowired
    private RoomService roomService;

    /*Permet de récupérer toutes les salles en base de données*/
    @GetMapping(value="/Salles")
    public ResponseEntity<Set<RoomDto>>listeSalles() {
        try {
            List<Room> _rooms = roomService.getRooms();
            Set<Room> _rooms_ = new HashSet<>(_rooms);
            Set<RoomDto> roomDtos = new HashSet<>();
            _rooms_.forEach(x -> roomDtos.add(RoomMapper.RoomEntityDtoMapper(x)));
            return new ResponseEntity<>(roomDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*Permet de récupérer toutes les salles qui ont une capacité à 70% > à un nombre de personnes
     et qui sont disponibles pour un meeting donné avec contrainte de nettoyage 1h avant en base de données. Variante logarithme.*/
    @GetMapping(value="/SallesCompatiblesPourMeeting")
    public ResponseEntity<Set<RoomDto>>listeSallesCompatiblesForMeeting(@RequestParam int numberOfPersons, int meetingStartHour) {
        try {
            List<Room> _roomsCompatiblesForMeeting = roomService.getRoomsCompatibleForMeeting(numberOfPersons, meetingStartHour);
            Set<Room> _roomsCompatiblesForMeeting_ = new HashSet<>(_roomsCompatiblesForMeeting);
            Set<RoomDto> roomsCompatiblesForMeetingDtos = new HashSet<>();
            _roomsCompatiblesForMeeting_.forEach(x -> roomsCompatiblesForMeetingDtos.add(RoomMapper.RoomEntityDtoMapper(x)));
            return new ResponseEntity<>(roomsCompatiblesForMeetingDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*Permet de récupérer toutes les salles qui ont une capacité à 70% > à un nombre de personnes
 et qui sont disponibles pour un meeting donné avec contrainte de nettoyage 1h avant en base de données*/
    @GetMapping(value="/SallesCompatiblesPourMeetingEmergency")
    public ResponseEntity<Set<RoomDto>>listeSallesCompatiblesForMeetingEmergency(@RequestParam int numberOfPersons, int meetingStartHour) {
        try {
            List<Room> _roomsCompatiblesForMeeting = roomService.getRoomsCompatibleForMeetingEmergency(numberOfPersons, meetingStartHour);
            Set<Room> _roomsCompatiblesForMeeting_ = new HashSet<>(_roomsCompatiblesForMeeting);
            Set<RoomDto> roomsCompatiblesForMeetingDtos = new HashSet<>();
            _roomsCompatiblesForMeeting_.forEach(x -> roomsCompatiblesForMeetingDtos.add(RoomMapper.RoomEntityDtoMapper(x)));
            return new ResponseEntity<>(roomsCompatiblesForMeetingDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
