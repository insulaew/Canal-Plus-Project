package com.meetingplanner.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.meetingplanner.dto.RoomToolDto;
import com.meetingplanner.mapper.RoomToolMapper;
import com.meetingplanner.model.RoomTool;
import com.meetingplanner.service.RoomToolService;

/*Classe du controller de l'entité RoomTool*/
@RestController
@CrossOrigin
@RequestMapping("/api")
public class RoomToolController {

    @Autowired
    private RoomToolService roomToolService;

    /*Permet de récupérer tous les équipements d'une salle en base de données*/
    @GetMapping(value="/EquipementsSalle")
    public ResponseEntity<Set<RoomToolDto>>listeEquipementsSalle() {
        try {
            Set<RoomTool> roomTools = roomToolService.getRoomTools()
                    .stream()
                    .map(roomTool -> new RoomTool(roomTool.getRoomToolId(), roomTool.getType(), roomTool.getRoom()))
                    .collect(Collectors.toSet());
            Set<RoomToolDto> roomToolDtos = roomTools
                    .stream()
                    .map(roomTool -> RoomToolMapper.RoomToolEntityDtoMapper(roomTool))
                    .collect(Collectors.toSet());
            return new ResponseEntity<>(roomToolDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*Permet de récupérer un équipement d'une salle par id en base de données*/
    @GetMapping(value="/EquipementSalle", params="id")
    public ResponseEntity<RoomToolDto>EquipementSalleById(@RequestParam Long id) {
        try {
            RoomTool roomTool = roomToolService.getRoomToolById(id).orElseThrow();
            return new ResponseEntity<>(RoomToolMapper.RoomToolEntityDtoMapper(roomTool), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*Permet de récupérer tous les équipements d'une salle par ids en base de données*/
    @GetMapping(value="/EquipementsSalle", params="ids")
    public ResponseEntity<Set<RoomToolDto>>listeEquipementsSalleByIds(@RequestParam List<Long> ids) {
        try {
            Set<RoomTool> roomToolsByIds = roomToolService.getRoomToolsByIds(ids)
                    .stream()
                    .map(roomTool -> new RoomTool(roomTool.getRoomToolId(), roomTool.getType(), roomTool.getRoom()))
                    .collect(Collectors.toSet());
            Set<RoomToolDto> roomToolsByIdsDtos = roomToolsByIds
                    .stream()
                    .map(roomTool -> RoomToolMapper.RoomToolEntityDtoMapper(roomTool))
                    .collect(Collectors.toSet());
            return new ResponseEntity<>(roomToolsByIdsDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
