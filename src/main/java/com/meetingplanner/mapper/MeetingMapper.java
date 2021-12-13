package com.meetingplanner.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.meetingplanner.dto.FreeToolDto;
import com.meetingplanner.dto.MeetingDto;
import com.meetingplanner.dto.RoomDto;
import com.meetingplanner.dto.UserDto;
import com.meetingplanner.model.FreeTool;
import com.meetingplanner.model.Meeting;
import com.meetingplanner.model.Room;
import com.meetingplanner.model.User;

/*Classe de traitement Entité/DTO Meeting*/
public class MeetingMapper {

    /*Permet de convertir une entité Meeting en DTO Meeting*/
    public static MeetingDto MeetingEntityDtoMapper (Meeting meeting) {

        Set<FreeTool> freeTools = meeting.getFreeTools();
        Set<FreeToolDto> freeToolDtos = new HashSet<>();
        if (freeTools != null) {
            freeToolDtos = freeTools
                    .stream()
                    .map(freeTool -> FreeToolMapper.FreeToolEntityDtoMapper(freeTool))
                    .collect(Collectors.toSet());
        }

        MeetingDto meetingDto = new MeetingDto(
                meeting.getId(),
                meeting.getType().toString(),
                null,
                meeting.getStartHour(),
                meeting.getEndHour(),
                meeting.getNumberOfPersons(),
                meeting.isReserved(),
                null,
                freeToolDtos
        );

        User user = meeting.getUser();
        if (user != null) {
            UserDto userDto = UserMapper.UserEntityDtoMapper(user);
            meetingDto.setUserDto(userDto);
        }

        Room room = meeting.getRoom();
        if (room != null) {
            RoomDto roomDto = RoomMapper.RoomEntityDtoMapper(room);
            meetingDto.setRoomDto(roomDto);
        }

        return meetingDto;
    }

}
