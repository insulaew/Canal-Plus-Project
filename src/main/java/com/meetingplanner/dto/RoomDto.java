package com.meetingplanner.dto;

import java.util.Set;

public class RoomDto {

    private String id;

    private int capacity;

    private int capacity70;

    private Set<RoomToolDto> roomToolDtos;

    private Set<Long> meetingsIds;

    public RoomDto(String id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        this.capacity70 = (int) (capacity*0.7);
    }

    public RoomDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity70() {
        return capacity70;
    }

    public void setCapacity70(int capacity70) {
        this.capacity70 = capacity70;
    }

    public Set<RoomToolDto> getRoomToolDtos() {
        return roomToolDtos;
    }

    public void setRoomToolDtos(Set<RoomToolDto> roomToolDtos) {
        this.roomToolDtos = roomToolDtos;
    }

    public Set<Long> getMeetingsIds() {
        return meetingsIds;
    }

    public void setMeetingsIds(Set<Long> meetingsIds) {
        this.meetingsIds = meetingsIds;
    }

}