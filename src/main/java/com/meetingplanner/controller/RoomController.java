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

import com.meetingplanner.dto.RoomDto;
import com.meetingplanner.mapper.RoomMapper;
import com.meetingplanner.model.Room;
import com.meetingplanner.service.RoomService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping(value="/Salles")
    public ResponseEntity<Set<RoomDto>>listeSalles() {
        try {
            List<Room> _rooms = roomService.getRooms();
            Set<Room> _rooms_ = new HashSet<Room>(_rooms);
            Set<RoomDto> roomDtos = new HashSet<RoomDto>();
            _rooms_.forEach(x -> roomDtos.add(RoomMapper.RoomEntityDtoMapper(x)));
            return new ResponseEntity<>(roomDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
