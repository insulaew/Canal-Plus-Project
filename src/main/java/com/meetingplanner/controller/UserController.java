package com.meetingplanner.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import com.meetingplanner.model.Meeting;
import com.meetingplanner.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @PostMapping(value="/Utilisateur")
    public ResponseEntity<UserDto> postUtilisateur(@Valid @RequestBody UserDto userDto) {
        try {
            List<Meeting> meetingsIds = this.meetingService.getMeetingsByIds((List<Long>)userDto.getMeetingsIds());
            Set<Meeting> _meetingsIds = new HashSet<>(meetingsIds);
            User userSaved = userDto.toUser();
            userSaved.setMeetings(_meetingsIds);
            UserDto userDtoFinal = UserMapper.UserEntityDtoMapper(userSaved);
            return new ResponseEntity<>(userDtoFinal, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
