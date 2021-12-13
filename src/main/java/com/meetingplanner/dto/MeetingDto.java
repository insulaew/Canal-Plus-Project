package com.meetingplanner.dto;

import com.meetingplanner.model.Meeting;
import com.meetingplanner.model.MeetingType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.Set;

/*Classe du DTO de l'entité Meeting*/
public class MeetingDto {

    private long id;

    @NotNull(message="Vous devez indiquer un type")
    @NotEmpty(message="Le type ne peut pas être vide")
    private String type;

    private UserDto userDto;

    @NotNull(message="Vous devez indiquer une heure de départ")
    private int startHour;

    @NotNull(message="Vous devez indiquer une heure de fin")
    private int endHour;

    @NotNull(message="Vous devez indiquer un nombre de personnes")
    private int numberOfPersons;

    @NotNull(message="Vous devez indiquer un statut de réservation")
    private boolean isReserved;

    private RoomDto roomDto;

    private Set<FreeToolDto> freeToolDtos;

    public MeetingDto(long id, String type, UserDto userDto, int startHour, int endHour, int numberOfPersons, boolean isReserved, RoomDto roomDto, Set<FreeToolDto> freeToolDtos) {
        this.id = id;
        this.type = type;
        this.userDto = userDto;
        this.startHour = startHour;
        this.endHour = endHour;
        this.numberOfPersons = numberOfPersons;
        this.isReserved = isReserved;
        this.roomDto = roomDto;
        this.freeToolDtos = freeToolDtos;
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

    public boolean getIsReserved() {
        return isReserved;
    }

    public void setIsReserved(boolean isReserved) {
        this.isReserved = isReserved;
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

    /*Permet de convertir un DTO Meeting en entité Meeting*/
    public Meeting toMeeting() {
        return new Meeting(
                this.id,
                MeetingType.valueOf(this.type),
                this.startHour,
                this.numberOfPersons,
                this.isReserved
        );
    }

}
