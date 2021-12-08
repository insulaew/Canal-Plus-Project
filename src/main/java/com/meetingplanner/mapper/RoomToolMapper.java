package com.meetingplanner.mapper;

import com.meetingplanner.dto.RoomToolDto;
import com.meetingplanner.model.RoomTool;

/*Classe de traitement Entité/DTO RoomTool*/
public class RoomToolMapper {

    /*Permet de convertir une entité RoomTool en DTO RoomTool*/
    public static RoomToolDto RoomToolEntityDtoMapper (RoomTool roomTool) {
        return new RoomToolDto(
                roomTool.getRoomToolId(),
                roomTool.getType().toString(),
                roomTool.getRoom().getId()
        );
    }

}
