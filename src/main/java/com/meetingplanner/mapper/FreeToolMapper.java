package com.meetingplanner.mapper;

import java.util.HashSet;
import java.util.Set;

import com.meetingplanner.dto.FreeToolDto;
import com.meetingplanner.model.FreeTool;
import com.meetingplanner.model.Meeting;

/*Classe de traitement Entité/DTO FreeTool*/
public class FreeToolMapper {

    /*Permet de convertir une entité FreeTool en DTO FreeTool*/
    public static FreeToolDto FreeToolEntityDtoMapper (FreeTool freeTool) {

        FreeToolDto freeToolDto = new FreeToolDto(
                freeTool.getFreeToolId(),
                freeTool.getType().toString()
        );

        Set<Meeting> meetings = freeTool.getMeetings();
        Set<Long> meetingsIds = new HashSet<>();

        if (meetings != null) {

            meetings.forEach(x -> meetingsIds.add(x.getId()));

        }

        freeToolDto.setMeetingsIds(meetingsIds);

        return freeToolDto;
    }

}
