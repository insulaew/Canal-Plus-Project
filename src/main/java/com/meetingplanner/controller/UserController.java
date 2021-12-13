package com.meetingplanner.controller;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.meetingplanner.service.MeetingService;
import com.meetingplanner.dto.UserDto;
import com.meetingplanner.mapper.UserMapper;
import com.meetingplanner.model.User;
import com.meetingplanner.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/*Classe du controller de l'entité User*/
@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MeetingService meetingService;

    /*Permet de récupérer tous les utilisateurs en base de données*/
    @GetMapping(value="/Utilisateurs")
    public ResponseEntity<Set<UserDto>> listeUtilisateurs() {
        try {
            Set<User> users = this.userService.getUsers()
                    .stream()
                    .map(user -> new User(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getMeetings()))
                    .collect(Collectors.toSet());
            Set<UserDto> userDtos = users
                    .stream()
                    .map(user -> UserMapper.UserEntityDtoMapper(user))
                    .collect(Collectors.toSet());
            return new ResponseEntity<>(userDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*Permet de récupérer un utilisateur grâce à son email en base de données*/
    @GetMapping(value="/UtilisateurEmail")
    public ResponseEntity<UserDto> UtilisateurByEmail(@RequestParam String email) {
        try {
            User user = this.userService.getUserByEmail(email);
            return new ResponseEntity<>(UserMapper.UserEntityDtoMapper(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*Permet de sauvegarder un utilisateur en base de données*/
    @Transactional
    @PostMapping(value="/Utilisateur")
    public ResponseEntity<UserDto> postUtilisateur(@Valid @RequestBody UserDto userDto) {
        try {
            User user = this.userService.saveUser(userDto.toUser());
            return new ResponseEntity<>(UserMapper.UserEntityDtoMapper(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
