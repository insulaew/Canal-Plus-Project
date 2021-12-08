package com.meetingplanner.test.unit.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.meetingplanner.Application;
import com.meetingplanner.model.*;
import com.meetingplanner.repository.FreeToolRepository;
import com.meetingplanner.repository.MeetingRepository;
import com.meetingplanner.repository.RoomRepository;
import com.meetingplanner.repository.UserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*Classe de test du Repository FreeTool*/
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes= {Application.class})
public class FreeToolRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private FreeToolRepository freeToolRepository;

    @Test
    public void testFindFreeToolById() {
        assertEquals(this.freeToolRepository.findById(1L).orElseThrow().getType().toString(), "Pieuvre");
    }

    @Test
    public void testFindFreeTools() {
        assertEquals(this.freeToolRepository.findAll().size(), 15);
    }

    /*Ce test permet de vérifier que des outils libres affectés à une réservation à 9 heures ne peuvent
    pas être réservés de nouveau à 9 heures. De base il y a 4 webcams, 4 pieuvres et 5 écrans libres.
    La réunion 2 est une réunion de type VC qui nécessite 1 webcam, 1 écran et 1 pieuvre.
     */
    @Test
    public void testFindFreeToolsByTypeCompatibleForMeeting() {
        User nicolas = this.userRepository.findById(1l).orElseThrow();
        Room room2004 = this.roomRepository.findByRoomCode("E2004").orElseThrow();
        Set<Long> freeToolsIds = new HashSet<>();
        freeToolsIds.add(3L);
        freeToolsIds.add(11L);
        freeToolsIds.add(7L);
        List<FreeTool> _freeTools= this.freeToolRepository.findAllById(freeToolsIds);
        Set<FreeTool> freeToolsIds_ = new HashSet<>(_freeTools);
        Meeting meeting2 = this.meetingRepository.findById(2L).orElseThrow();
        meeting2.setRoom(room2004);
        meeting2.setFreeTools(freeToolsIds_);
        meeting2.setUser(nicolas);
        meeting2.setReserved(true);
        this.meetingRepository.save(meeting2);
        assertEquals(this.freeToolRepository.findFreeToolsByTypeCompatibleForMeeting("Pieuvre", 9).size(), 3);
        assertEquals(this.freeToolRepository.findFreeToolsByTypeCompatibleForMeeting("Ecran", 9).size(), 4);
        assertEquals(this.freeToolRepository.findFreeToolsByTypeCompatibleForMeeting("Webcam", 9).size(), 3);
    }

    @Test
    public void testFindFreeToolsByType() {
        assertEquals(this.freeToolRepository.findFreeToolsByType("Webcam").size(), 4);
    }

    @Test
    public void testUpdateFreeTool() {
        FreeTool freeTool = this.freeToolRepository.getById(1L);
        freeTool.setType(ToolType.Ecran);
        this.freeToolRepository.save(freeTool);
        assertNotEquals(ToolType.Pieuvre, this.freeToolRepository.getById(1L).getType());
    }

}
