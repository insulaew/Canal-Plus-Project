package com.meetingplanner.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*Classe de l'entité Meeting*/
@Entity
@Table(name = "MEETING")
public class Meeting {

    @Id
    @Column(name = "reunion_id", nullable = false)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "Type", nullable = false, updatable = false)
    private MeetingType type;

    @ManyToOne
    @JoinColumn(name="salle_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name="utilisateur_id")
    private User user;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "meeting_free_tool",
            joinColumns = { @JoinColumn(name = "reunion_id") },
            inverseJoinColumns = { @JoinColumn(name = "outil_libre_id") }
    )
    Set<FreeTool> freeTools;

    @Column(name = "start_hour", nullable = false, updatable = false)
    private int startHour;

    @Column(name = "end_hour", nullable = false, updatable = false)
    private int endHour;

    @Column(name = "number_of_persons", nullable = false, updatable = false)
    private int numberOfPersons;

    @Column(name = "is_reserved", nullable = false)
    private boolean isReserved;

    public Meeting(long id, MeetingType type, int startHour, int numberOfPersons, boolean isReserved) {
        this.id = id;
        this.type = type;
        this.startHour = startHour;
        this.endHour = startHour + 1;
        this.numberOfPersons = numberOfPersons;
        this.isReserved = isReserved;
    }

    public Meeting() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MeetingType getType() {
        return type;
    }

    public void setType(MeetingType type) {
        this.type = type;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<FreeTool> getFreeTools() {
        return freeTools;
    }

    public void setFreeTools(Set<FreeTool> freeTools) {
        this.freeTools = freeTools;
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

}
