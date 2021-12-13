package com.meetingplanner.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.meetingplanner.dto.RoomDto;
import com.meetingplanner.dto.RoomToolDto;
import com.meetingplanner.model.Room;

/*Classe de traitement Entité/DTO Room*/
public class RoomMapper {

    /*Permet de convertir une entité Room en DTO Room*/
    public static RoomDto RoomEntityDtoMapper (Room room) {
        Set<RoomToolDto> roomToolDtos = new HashSet<RoomToolDto>();
        Set<Long> meetingsIds = new HashSet<>();

        if (room.getMeetings() != null) {
            meetingsIds = room.getMeetings()
                    .stream()
                    .map(meeting -> meeting.getId())
                    .collect(Collectors.toSet());
        }

        if (room.getRoomTools() != null) {
            roomToolDtos = room.getRoomTools()
                    .stream()
                    .map(roomTool -> RoomToolMapper.RoomToolEntityDtoMapper(roomTool))
                    .collect(Collectors.toSet());
        }

        return new RoomDto(
                room.getId(),
                room.getCapacity(),
                room.getCapacity70(),
                roomToolDtos,
                meetingsIds
        );

    }

}
