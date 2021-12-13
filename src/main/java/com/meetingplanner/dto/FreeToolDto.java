package com.meetingplanner.dto;

import com.meetingplanner.model.FreeTool;
import com.meetingplanner.model.ToolType;

import java.util.Set;

/*Classe du DTO de l'entité FreeTool*/
public class FreeToolDto {

    private long freeToolId;

    private String type;

    private Set<Long> meetingsIds;

    public FreeToolDto(long freeToolId, String type, Set<Long> meetingsIds) {
        this.freeToolId = freeToolId;
        this.type = type;
        this.meetingsIds = meetingsIds;
    }

    public FreeToolDto() { }

    public long getFreeToolId() {
        return freeToolId;
    }

    public void setFreeToolId(long freeToolId) {
        this.freeToolId = freeToolId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Long> getMeetingsIds() {
        return meetingsIds;
    }

    public void setMeetingsIds(Set<Long> meetingsIds) {
        this.meetingsIds = meetingsIds;
    }

    /*Permet de convertir un DTO FreeTool en entité FreeTool*/
    public FreeTool toFreeTool() {
        return new FreeTool(
               this.freeToolId,
                ToolType.valueOf(this.type)
        );
    }

}
