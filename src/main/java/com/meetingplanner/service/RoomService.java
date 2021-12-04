package com.meetingplanner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetingplanner.model.Room;
import com.meetingplanner.repository.RoomRepository;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getRoomsWithEnoughCapacity(int meetingNumberOfPersons) {
        List<Room > rooms = this.roomRepository.findRoomsWithEnoughCapacity(6);
        return roomRepository.findRoomsWithEnoughCapacity(meetingNumberOfPersons);
    }

}