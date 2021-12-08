package com.meetingplanner.validator;

import com.meetingplanner.model.FreeTool;
import com.meetingplanner.model.Meeting;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/*Classe de validation d'une entité Meeting lors de sa sauvegarde en base de données*/
public class MeetingValidator {

    /*Méthode qui vérifie la validité fonctionnelle de la réunion sauvegardée en base de données*/
    public static boolean isValid(Meeting meeting) {
        AtomicBoolean result = new AtomicBoolean(true);

        if (meeting.isReserved() == true ) {

            if (meeting.getRoom().getCapacity70() < meeting.getNumberOfPersons()) {
                result.set(false);
            }

            if(meeting.getRoom().getMeetings() != null){
                Set<Meeting> roomMeetings = meeting.getRoom().getMeetings();
                roomMeetings.forEach((x) -> {
                    if (x.getStartHour() == meeting.getStartHour() || (x.getStartHour() - 1) == meeting.getStartHour() || (x.getStartHour() + 1) == meeting.getStartHour()) {
                        result.set(false);
                    }
                });
            }

            if (meeting.getFreeTools() != null) {
                Set<FreeTool> meetingFreeTools = meeting.getFreeTools();
                meetingFreeTools.forEach((x) -> {
                    x.getMeetings().forEach((y) -> {
                        if (y.getStartHour() == meeting.getStartHour()) {
                            result.set(false);
                        }
                    });
                });
            }

        } else if (meeting.getRoom() != null || meeting.getUser() != null || meeting.getFreeTools() != null) {

            result.set(false);
        }

        return result.get();
    }
}
