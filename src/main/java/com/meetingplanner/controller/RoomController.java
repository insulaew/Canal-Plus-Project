package com.meetingplanner.controller;

import java.util.Set;
import java.util.stream.Collectors;

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
            Set<Room> rooms = roomService.getRooms()
                    .stream()
                    .map(room -> new Room(room.getId(), room.getCapacity(), room.getCapacity70(), room.getRoomTools(), room.getMeetings()))
                    .collect(Collectors.toSet());
            Set<RoomDto> roomDtos = rooms
                    .stream()
                    .map(room -> RoomMapper.RoomEntityDtoMapper(room))
                    .collect(Collectors.toSet());
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
            Set<Room> roomsCompatiblesForMeeting = roomService.getRoomsCompatibleForMeeting(numberOfPersons, meetingStartHour)
                    .stream()
                    .map(room -> new Room(room.getId(), room.getCapacity(), room.getCapacity70(), room.getRoomTools(), room.getMeetings()))
                    .collect(Collectors.toSet());
            Set<RoomDto> roomsCompatiblesForMeetingDtos = roomsCompatiblesForMeeting
                    .stream()
                    .map(room -> RoomMapper.RoomEntityDtoMapper(room))
                    .collect(Collectors.toSet());
            return new ResponseEntity<>(roomsCompatiblesForMeetingDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
