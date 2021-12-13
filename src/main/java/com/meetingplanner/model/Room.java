package com.meetingplanner.model;

import java.util.Set;

import javax.persistence.*;

/*Classe de l'entité Room*/
@Entity
@Table(name = "ROOM")
public class Room {

    @Id
    @Column(name = "salle_id", nullable = false)
    private String id;

    @Column(name = "capacity", nullable = false, updatable = false)
    private int capacity;

    @Column(name = "capacity70")
    private Integer capacity70;

    @OneToMany(mappedBy = "room")
    private Set<RoomTool> roomTools;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<Meeting> meetings;

    public Room(String id, int capacity, int capacity70) {
        this.id = id;
        this.capacity = capacity;
        this.capacity70 = capacity70;
    }

    public Room(String id, int capacity, int capacity70, Set<RoomTool> roomTools, Set<Meeting> meetings) {
        this.id = id;
        this.capacity = capacity;
        this.capacity70 = capacity70;
        this.roomTools = roomTools;
        this.meetings = meetings;
    }

    public Room() {
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

    public Integer getCapacity70() {
        return capacity70;
    }

    public void setCapacity70(Integer capacity70) {
        this.capacity70 = capacity70;
    }

    public Set<RoomTool> getRoomTools() {
        return roomTools;
    }

    public void setRoomTools(Set<RoomTool> roomTools) {
        this.roomTools = roomTools;
    }

    public Set<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(Set<Meeting> meetings) {
        this.meetings = meetings;
    }

}
