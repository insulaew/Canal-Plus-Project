package com.meetingplanner.validator;

import com.meetingplanner.model.FreeTool;
import com.meetingplanner.model.Meeting;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/*Classe de validation d'une entité Meeting lors de sa sauvegarde en base de données*/
public class MeetingValidator {

    /*Méthode qui vérifie la validité fonctionnelle de la réunion sauvegardée en base de données*/
    public static void isValid(Meeting meeting) throws Exception {

        if (meeting.isReserved() == true ) {

            if (meeting.getRoom().getCapacity70() < meeting.getNumberOfPersons()) {
                throw new Exception("Le nombre de personnes conviées est supérieur à la capacité de la salle à 70% !");
            }

            if(meeting.getRoom().getMeetings() != null){
                Set<Meeting> roomMeetings = meeting.getRoom().getMeetings();
                AtomicBoolean isThrowable = new AtomicBoolean();
                isThrowable.set(false);
                roomMeetings.forEach((x) -> {
                    if (x.getStartHour() == meeting.getStartHour() || (x.getStartHour() - 1) == meeting.getStartHour() || (x.getStartHour() + 1) == meeting.getStartHour()) {
                        isThrowable .set(true);
                    }
                });
                if (isThrowable.get()) {
                    throw new Exception("La réunion ne peut pas être réservée dans une salle déjà prise 1h avant ou 1h après car chaque salle doit être nettoyée pendant 1h avant chaque réunion !");
                }
            }

            if (meeting.getFreeTools() != null) {
                Set<FreeTool> meetingFreeTools = meeting.getFreeTools();
                AtomicBoolean isThrowable = new AtomicBoolean();
                isThrowable.set(false);
                meetingFreeTools.forEach((x) -> {
                    x.getMeetings().forEach((y) -> {
                        if (y.getStartHour() == meeting.getStartHour()) {
                            isThrowable .set(true);
                        }
                    });
                });
                if (isThrowable.get()) {
                    throw new Exception("Cet outil libre est déjà affecté à une réunion à cet horaire !");
                }
            }

        } else if (meeting.getRoom() != null || meeting.getUser() != null || meeting.getFreeTools() != null) {
            throw new Exception("Une réunion qui n'est pas réservée n'a ni utilisateur, ni outils libres ni salle affectés !");
        }

    }

}
