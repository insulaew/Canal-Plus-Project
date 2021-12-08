package com.meetingplanner.mapper;

import java.util.HashSet;
import java.util.Set;

import com.meetingplanner.dto.RoomDto;
import com.meetingplanner.dto.RoomToolDto;
import com.meetingplanner.model.Meeting;
import com.meetingplanner.model.Room;
import com.meetingplanner.model.RoomTool;

/*Classe de traitement Entité/DTO Room*/
public class RoomMapper {

    /*Permet de convertir une entité Room en DTO Room*/
    public static RoomDto RoomEntityDtoMapper (Room room) {
        RoomDto roomDto = new RoomDto(
                room.getId(),
                room.getCapacity()
        );

        Set<Long> meetingsIds = new HashSet<>();
        Set<Meeting> meetings = room.getMeetings();

        if (meetings != null) {

            meetings.forEach(x -> meetingsIds.add(x.getId()));

        }

        roomDto.setMeetingsIds(meetingsIds);

        Set<RoomToolDto> roomToolDtos = new HashSet<>();
        Set<RoomTool> roomTools = room.getRoomTools();

        if (roomTools != null) {

            roomTools.forEach(x -> roomToolDtos.add(RoomToolMapper.RoomToolEntityDtoMapper(x)));

        }

        roomDto.setRoomToolDtos(roomToolDtos);

        return roomDto;
    }

}
