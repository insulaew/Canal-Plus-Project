package com.meetingplanner.mapper;

import com.meetingplanner.dto.RoomToolDto;
import com.meetingplanner.model.RoomTool;

public class RoomToolMapper {

    public static RoomToolDto RoomToolEntityDtoMapper (RoomTool roomTool) {
        return new RoomToolDto(
                roomTool.getRoomToolId(),
                roomTool.getType().toString(),
                roomTool.getRoom().getId()
        );
    }

}
