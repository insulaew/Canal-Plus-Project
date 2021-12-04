package com.meetingplanner.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "FREE_TOOL")
public class FreeTool {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "outil_libre_id", nullable = false)
    private long freeToolId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, updatable = false)
    private ToolType type;

    @ManyToMany(mappedBy = "freeTools")
    private Set<Meeting> meetings;

    public FreeTool(long freeToolId, ToolType type) {
        this.freeToolId = freeToolId;
        this.type = type;
    }

    public FreeTool() {
    }

    public long getFreeToolId() {
        return freeToolId;
    }

    public void setFreeToolId(long freeToolId) {
        this.freeToolId = freeToolId;
    }

    public ToolType getType() {
        return type;
    }

    public void setType(ToolType type) {
        this.type = type;
    }

    public Set<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(Set<Meeting> meetings) {
        this.meetings = meetings;
    }

}
