package com.meetingplanner.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.meetingplanner.dto.UserDto;
import com.meetingplanner.model.User;

/*Classe de traitement Entité/DTO User*/
public class UserMapper {

    /*Permet de convertir une entité User en DTO User*/
    public static UserDto UserEntityDtoMapper (User user) {

        Set<Long> meetingsIds = new HashSet<>();

        if (user.getMeetings() != null) {
            meetingsIds = user.getMeetings()
                    .stream()
                    .map(meeting -> meeting.getId())
                    .collect(Collectors.toSet());
        }

        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                new HashSet<Long>());
    }

}
