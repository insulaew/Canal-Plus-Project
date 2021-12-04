package com.meetingplanner.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.meetingplanner.dto.RoomToolDto;
import com.meetingplanner.mapper.RoomToolMapper;
import com.meetingplanner.model.RoomTool;
import com.meetingplanner.service.RoomToolService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class RoomToolController {

    @Autowired
    private RoomToolService roomToolService;

    @GetMapping(value="/EquipementsSalle")
    public ResponseEntity<Set<RoomToolDto>>listeEquipementsSalle() {
        try {
            List<RoomTool> _roomTools = roomToolService.getRoomTools();
            Set<RoomTool> _roomTools_ = new HashSet<RoomTool>(_roomTools);
            Set<RoomToolDto> roomToolDtos = new HashSet<RoomToolDto>();
            _roomTools_.forEach(x -> roomToolDtos.add(RoomToolMapper.RoomToolEntityDtoMapper(x)));
            return new ResponseEntity<>(roomToolDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/EquipementsSalle", params="id")
    public ResponseEntity<RoomToolDto>EquipementsSalleById(@RequestParam Long id) {
        try {
            RoomTool _roomTool = roomToolService.getRoomToolById(id).orElseThrow();
            return new ResponseEntity<>(RoomToolMapper.RoomToolEntityDtoMapper(_roomTool), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/EquipementsSalle", params="ids")
    public ResponseEntity<Set<RoomToolDto>>listeEquipementsSalleByIds(@RequestParam List<Long> ids) {
        try {
            List<RoomTool> _roomToolsByIds = roomToolService.getRoomToolsByIds(ids);
            Set<RoomTool> _roomToolsByIds_ = new HashSet<RoomTool>(_roomToolsByIds);
            Set<RoomToolDto> roomToolsByIdsDtos = new HashSet<RoomToolDto>();
            _roomToolsByIds_.forEach(x -> roomToolsByIdsDtos.add(RoomToolMapper.RoomToolEntityDtoMapper(x)));
            return new ResponseEntity<>(roomToolsByIdsDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
