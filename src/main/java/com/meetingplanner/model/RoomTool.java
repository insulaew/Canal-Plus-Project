package com.meetingplanner.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*Classe de l'entité RoomTool*/
@Entity
@Table(name = "ROOM_TOOL")
public class RoomTool {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "outil_salle_id", nullable = false)
    private long roomToolId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, updatable = false)
    private ToolType type;

    @ManyToOne
    @JoinColumn(name="salle_id", nullable=false, updatable = false)
    private Room room;

    public RoomTool(long roomToolId, ToolType type) {
        this.roomToolId = roomToolId;
        this.type = type;
    }

    public RoomTool() {
    }

    public long getRoomToolId() {
        return roomToolId;
    }

    public void setRoomToolId(long roomToolId) {
        this.roomToolId = roomToolId;
    }

    public ToolType getType() {
        return type;
    }

    public void setType(ToolType type) {
        this.type = type;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

}
