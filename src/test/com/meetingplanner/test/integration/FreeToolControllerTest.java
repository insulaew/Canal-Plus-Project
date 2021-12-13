package com.meetingplanner.test.unit.integration;

import com.meetingplanner.Application;
import com.meetingplanner.model.FreeTool;
import com.meetingplanner.model.Meeting;
import com.meetingplanner.model.Room;
import com.meetingplanner.model.User;
import com.meetingplanner.repository.FreeToolRepository;
import com.meetingplanner.repository.MeetingRepository;
import com.meetingplanner.repository.RoomRepository;
import com.meetingplanner.repository.UserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*Classe de test du Controller de l'entité FreeTool*/
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes= {Application.class})
public class FreeToolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FreeToolRepository freeToolRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    /*Il y a 15 équipements libres au total en base de données.*/
    @Test
    public void testListeEquipementsLibres() throws Exception {
        this.mockMvc.perform(get("/api/EquipementsLibres"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(equalTo(15))));
    }

    @Test
    public void testListeEquipementsLibresByTypeCompatibleForMeeting() throws Exception {
        Meeting meeting2 = this.createMeetingForTest();
        this.meetingRepository.save(meeting2);
        /*La réunion 2 est à 9h et on y a affecté une Pieuvre libre, on s'attend donc
        * à 3 pieuvres disponibles pour une autre réunion à 9h*/
        this.mockMvc.perform(get("/api/EquipementsLibresByType")
                        .param("type", "Pieuvre")
                        .param("meetingStartHour", "9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(equalTo(3))));
    }

    /*Crée une réservation personnalisée pour le test*/
    public Meeting createMeetingForTest() {
        /*L'utilisateur qui réalise la sauvegarde est le numéro 1 en base de données*/
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
        /*On affect la salle, les outils libres et l'utilisateur au Meeting*/
        meeting2.setRoom(room2004);
        meeting2.setFreeTools(freeTools);
        meeting2.setUser(nicolas);
        meeting2.setReserved(true);

        return meeting2;
    }

}
