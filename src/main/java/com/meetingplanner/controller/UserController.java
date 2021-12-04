package com.meetingplanner.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import com.meetingplanner.model.Meeting;
import com.meetingplanner.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.meetingplanner.dto.UserDto;
import com.meetingplanner.mapper.UserMapper;
import com.meetingplanner.model.User;
import com.meetingplanner.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MeetingService meetingService;

    @GetMapping(value="/Utilisateurs")
    public ResponseEntity<Set<UserDto>> listeUtilisateurs() {
        try {
            List<User> _users = this.userService.getUsers();
            Set<User> _users_ = new HashSet<>(_users);
            Set<UserDto> userDtos = new HashSet<>();
            _users_.forEach(x -> userDtos.add(UserMapper.UserEntityDtoMapper(x)));
            return new ResponseEntity<>(userDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value="/Utilisateur")
    public ResponseEntity<UserDto> postUtilisateur(@Valid @RequestBody UserDto userDto) {
        Set<Long> meetingsIds = userDto.getMeetingsIds();
        List<Long> _meetingsIds = new ArrayList<>(meetingsIds);
        List<Meeting> meetings = this.meetingService.getMeetingsByIds(_meetingsIds);
        Set<Meeting> _meetings = new HashSet<>(meetings);
        User userSaved = userDto.toUser();
        userSaved.setMeetings(_meetings);
        try {
            UserDto userDtoFinal = UserMapper.UserEntityDtoMapper(this.userService.saveUser(userSaved));
            return new ResponseEntity<>(userDtoFinal, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
