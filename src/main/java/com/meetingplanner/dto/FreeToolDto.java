package com.meetingplanner.dto;

import com.meetingplanner.model.FreeTool;
import com.meetingplanner.model.ToolType;

import java.util.Set;

public class FreeToolDto {

    private long freeToolId;

    private String type;

    private Set<Long> meetingsIds;

    public FreeToolDto(long freeToolId, String type) {
        this.freeToolId = freeToolId;
        this.type = type;
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

    public FreeTool toFreeTool() {
        return new FreeTool(
               this.freeToolId,
                ToolType.valueOf(this.type)
        );
    }

}
