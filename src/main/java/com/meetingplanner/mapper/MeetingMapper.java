package com.meetingplanner.mapper;

import java.util.HashSet;
import java.util.Set;

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

        MeetingDto meetingDto = new MeetingDto(
                meeting.getId(),
                meeting.getType().toString(),
                meeting.getStartHour(),
                meeting.getEndHour(),
                meeting.getNumberOfPersons(),
                meeting.isReserved()
        );

        Set<FreeToolDto> freeToolDtos = new HashSet<>();
        Set<FreeTool> freeTools = meeting.getFreeTools();

        if (freeTools != null) {

            freeTools.forEach(x -> freeToolDtos.add(FreeToolMapper.FreeToolEntityDtoMapper(x)));

        }

        meetingDto.setFreeToolDtos(freeToolDtos);

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
