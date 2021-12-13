package com.meetingplanner.test.unit.repository;

import com.meetingplanner.Application;
import com.meetingplanner.model.FreeTool;
import com.meetingplanner.model.Meeting;
import com.meetingplanner.model.Room;
import com.meetingplanner.model.User;
import com.meetingplanner.repository.FreeToolRepository;
import com.meetingplanner.repository.MeetingRepository;
import com.meetingplanner.repository.RoomRepository;
import com.meetingplanner.repository.UserRepository;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/*Classe de test du Repository Meeting*/
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes= {Application.class})
public class MeetingRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FreeToolRepository freeToolRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    /*On teste la fonctionnalité de sauvegarde d'une réunion en base de données*/
    @Test
    public void testSaveMeetingAndReservedNotReserved() {
        Meeting meeting2 = this.createMeetingForTest();
        this.meetingRepository.save(meeting2);
        Meeting savedMeeting2 = this.meetingRepository.findById(2L).orElseThrow();
        /*On vérifie que la réunion est bien réservée, qu'on lui a affecté l'utilisateur Nicolas Sivignon
        * dont le mail est media.svd@outlook.fr, qu'elle a bien 3 outils libres, etc.*/
        assertEquals(savedMeeting2.isReserved(), true);
        assertEquals(savedMeeting2.getUser().getEmail(), "media.svd@outlook.fr");
        assertEquals(savedMeeting2.getFreeTools().size(), 3);
        assertEquals((int) savedMeeting2.getRoom().getCapacity70(), 6);
        assertEquals(this.meetingRepository.findReservedMeetings().size(), 1);
        assertEquals(this.meetingRepository.findNotReservedMeetings().size(), 19);
    }

    /*Méthode permettant de renvoyer un meeting personnalisé pour les tests.*/
    public Meeting createMeetingForTest() {
        User nicolas = this.userRepository.findById(1l).orElseThrow();
        Room room2004 = this.roomRepository.findByRoomCode("E2004").orElseThrow();
        /*La réunion 2 est de type VC et nécessite 1 webcam, 1 pieuvre et 1 écran.*/
        Set<Long> freeToolsIds = new HashSet<>();
        freeToolsIds.add(3L);
        freeToolsIds.add(11L);
        freeToolsIds.add(7L);
        Set<FreeTool> freeTools = this.freeToolRepository.findAllById(freeToolsIds)
                .stream()
                .map(freeTool -> new FreeTool(freeTool.getFreeToolId(), freeTool.getType(), freeTool.getMeetings()))
                .collect(Collectors.toSet());
        Meeting meeting2 = this.meetingRepository.findById(2L).orElseThrow();
        meeting2.setRoom(room2004);
        meeting2.setFreeTools(freeTools);
        meeting2.setUser(nicolas);
        meeting2.setReserved(true);

        return meeting2;
    }

}
