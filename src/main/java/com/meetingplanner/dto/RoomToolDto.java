package com.meetingplanner.dto;

public class RoomToolDto {

    private long roomToolId;

    private String type;

    private String roomId;

    public RoomToolDto(long roomToolId, String type, String roomId) {
        this.roomToolId = roomToolId;
        this.type = type;
        this.roomId = roomId;
    }

    public RoomToolDto() {
    }

    public long getRoomToolId() {
        return roomToolId;
    }

    public void setRoomToolId(long roomToolId) {
        this.roomToolId = roomToolId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

}
