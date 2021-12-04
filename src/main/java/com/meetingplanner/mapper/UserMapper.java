package com.meetingplanner.mapper;

import java.util.HashSet;
import java.util.Set;

import com.meetingplanner.dto.UserDto;
import com.meetingplanner.model.Meeting;
import com.meetingplanner.model.User;

public class UserMapper {

    public static UserDto UserEntityDtoMapper (User user) {

        UserDto userDto = new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword()
        );

        Set<Long> meetingsIds = new HashSet<>();
        Set<Meeting> meetings = user.getMeetings();
        if (meetings != null) {
            meetings.forEach(x -> meetingsIds.add(x.getId()));
            userDto.setMeetingsIds(meetingsIds);
        }

        return userDto;
    }

}
