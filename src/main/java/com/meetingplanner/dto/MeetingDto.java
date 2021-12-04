package com.meetingplanner.dto;

import java.util.Set;

public class MeetingDto {

    private long id;

    private String type;

    private UserDto userDto;

    private int startHour;

    private int endHour;

    private int numberOfPersons;

    private boolean isReserved;

    private RoomDto roomDto;

    private Set<FreeToolDto> freeToolDtos;

    public MeetingDto(long id, String type, int startHour, int endHour, int numberOfPersons, boolean isReserved) {
        this.id = id;
        this.type = type;
        this.startHour = startHour;
        this.endHour = endHour;
        this.numberOfPersons = numberOfPersons;
        this.isReserved = isReserved;
    }

    public MeetingDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public RoomDto getRoomDto() {
        return roomDto;
    }

    public void setRoomDto(RoomDto roomDto) {
        this.roomDto = roomDto;
    }

    public Set<FreeToolDto> getFreeToolDtos() {
        return freeToolDtos;
    }

    public void setFreeToolDtos(Set<FreeToolDto> freeToolDtos) {
        this.freeToolDtos = freeToolDtos;
    }

}
