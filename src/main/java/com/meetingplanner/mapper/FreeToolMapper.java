package com.meetingplanner.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.meetingplanner.dto.FreeToolDto;
import com.meetingplanner.model.FreeTool;

/*Classe de traitement Entité/DTO FreeTool*/
public class FreeToolMapper {

    /*Permet de convertir une entité FreeTool en DTO FreeTool*/
    public static FreeToolDto FreeToolEntityDtoMapper (FreeTool freeTool) {
        Set<Long> meetingsIds = new HashSet<>();
        if (freeTool.getMeetings() != null) {
            meetingsIds = freeTool.getMeetings()
                    .stream()
                    .map(meeting -> meeting.getId())
                    .collect(Collectors.toSet());
        }
        return new FreeToolDto(
                freeTool.getFreeToolId(),
                freeTool.getType().toString(),
                meetingsIds);

    }

}
