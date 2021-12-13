package com.meetingplanner.test.unit.integration;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.meetingplanner.Application;
import com.meetingplanner.dto.MeetingDto;
import com.meetingplanner.mapper.MeetingMapper;
import com.meetingplanner.model.FreeTool;
import com.meetingplanner.model.Meeting;
import com.meetingplanner.model.Room;
import com.meetingplanner.model.User;
import com.meetingplanner.repository.FreeToolRepository;
import com.meetingplanner.repository.MeetingRepository;
import com.meetingplanner.repository.RoomRepository;
import com.meetingplanner.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.util.LinkedMultiValueMap;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/*Classe de test du Controller de l'entité Meeting*/
@Transactional
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes= {Application.class})
public class MeetingControllerTest {

    @Autowired
    private WebApplicationContext context;

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

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    /*Il y a 20 réunions au total en base de données.*/
    @Test
    public void testListeReunions() throws Exception {

        this.mockMvc.perform(get("/api/Reunions"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(equalTo(20))));
    }

    /*Ce test ne marche que si on lance tous les tests en même temps
    * car une autre réunion est réservée par les autres tests.*/
    @Test
    public void testListeReunionsReservees() throws Exception {
        this.mockMvc.perform(get("/api/ReunionsReservees"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(equalTo(1))));
    }

    @Test
    public void testListesReunionsByIds() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("ids", "2");
        params.add("ids", "3");
        params.add("ids", "4");
        this.mockMvc.perform(get("/api/ReunionsByIds")
                .params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(equalTo(3))));
    }

    @Test
    @WithMockUser("media.svd@outlook.fr")
    public void testPostReunion() throws Exception {

        /*On crée une réservation pour la réunion 3 à l'aide de la fonction createMeetingForTest*/
        Meeting meeting3 = this.createMeetingForTest();
        ObjectMapper mapper = new ObjectMapper();

        /*On poste le JSON d'un DTO Meeting*/
        this.mockMvc.perform(post("/api/Reunion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(MeetingMapper.MeetingEntityDtoMapper(meeting3))))
                .andExpect(status().isCreated());
    }

    /*Teste la fonctionnalité update Meeting utilisée côté front pour annuler une réservation*/
    @Test
    @WithMockUser("media.svd@outlook.fr")
    public void testUpdateReunion() throws Exception {
        Meeting meeting3 = this.createMeetingForTest();
        ObjectMapper mapper = new ObjectMapper();
        MeetingDto meeting3Dto = MeetingMapper.MeetingEntityDtoMapper(meeting3);
        /*On poste le JSON d'un DTO Meeting*/
        this.mockMvc.perform(post("/api/Reunion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(meeting3Dto)))
                .andExpect(status().isCreated());

        MeetingDto updatedMeeting2Dto = new MeetingDto(meeting3Dto.getId(), meeting3Dto.getType(), null, meeting3Dto.getStartHour(), meeting3Dto.getEndHour(), meeting3Dto.getNumberOfPersons(), false, null, new HashSet<>());
        /*On poste le JSON d'un updated DTO Meeting*/
        this.mockMvc.perform(put("/api/Reunion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedMeeting2Dto)))
                .andExpect(status().isOk());

        /*On vérifie que la réunion 3 n'est plus réservée*/
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("ids", "3");

        this.mockMvc.perform(get("/api/ReunionsByIds")
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isReserved", is(false)));
    }

    /*Crée une réservation personnalisée pour le test*/
    public Meeting createMeetingForTest() {
        /*L'utilisateur qui réalise la sauvegarde est le numéro 2 en base de données*/
        User daniele = this.userRepository.findById(2l).orElseThrow();
        Room room2003 = this.roomRepository.findByRoomCode("E2003").orElseThrow();
        /*La réunion 3 est de type RC et nécessite 1 tableau, 1 pieuvre et 1 écran.*/
        Set<Long> freeToolsIds = new HashSet<>();
        freeToolsIds.add(1L);
        freeToolsIds.add(15L);
        freeToolsIds.add(9L);
        Set<FreeTool> freeTools = this.freeToolRepository.findAllById(freeToolsIds)
                .stream()
                .map(freeTool -> new FreeTool(freeTool.getFreeToolId(), freeTool.getType(), freeTool.getMeetings()))
                .collect(Collectors.toSet());
        Meeting meeting3 = this.meetingRepository.findById(3L).orElseThrow();
        /*On affect la salle, les outils libres et l'utilisateur au Meeting*/
        meeting3.setRoom(room2003);
        meeting3.setFreeTools(freeTools);
        meeting3.setUser(daniele);
        meeting3.setReserved(true);

        return meeting3;
    }

}
