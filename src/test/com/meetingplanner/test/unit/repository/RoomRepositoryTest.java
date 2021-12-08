package com.meetingplanner.test.unit.repository;

import static org.junit.Assert.assertEquals;

import com.meetingplanner.Application;
import com.meetingplanner.repository.RoomRepository;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/*Classe de test du Repository Room*/
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes= {Application.class})
public class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Test
    public void testFindByRoomCode() {
        assertEquals((int) this.roomRepository.findByRoomCode("E1001").orElseThrow().getCapacity70(), 16);
    }

    /*Ce test permet de trouver une salle pour un meeting à 8h avec 16 personnes conviées.
    * Il n'y a qu'une salle compatible.*/
    @Test
    public void testfindRoomsCompatibleForMeeting() {
        assertEquals(this.roomRepository.findRoomsCompatibleForMeetingEmergency(16,8).size(), 1);
    }

}
