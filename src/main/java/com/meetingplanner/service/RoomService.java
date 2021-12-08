package com.meetingplanner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetingplanner.model.Room;
import com.meetingplanner.repository.RoomRepository;

/*Classe du service de l'entité Room*/
@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getRoomsCompatibleForMeeting(int meetingNumberOfPersons, int meetingStartHour) {
        return roomRepository.findRoomsCompatibleForMeeting(meetingNumberOfPersons, meetingStartHour);
    }

    public List<Room> getRoomsCompatibleForMeetingEmergency(int meetingNumberOfPersons, int meetingStartHour) {
        return roomRepository.findRoomsCompatibleForMeetingEmergency(meetingNumberOfPersons, meetingStartHour);
    }

}
