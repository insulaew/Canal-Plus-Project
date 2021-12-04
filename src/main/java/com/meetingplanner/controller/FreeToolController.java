package com.meetingplanner.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.meetingplanner.dto.FreeToolDto;
import com.meetingplanner.mapper.FreeToolMapper;
import com.meetingplanner.model.FreeTool;
import com.meetingplanner.service.FreeToolService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class FreeToolController {

    @Autowired
    private FreeToolService freeToolService;

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

}
