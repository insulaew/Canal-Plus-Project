package com.meetingplanner.test.unit.integration;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.meetingplanner.Application;
import com.meetingplanner.dto.MeetingDto;
import com.meetingplanner.model.FreeTool;
import com.meetingplanner.model.Meeting;
import com.meetingplanner.model.Room;
import com.meetingplanner.model.User;
import com.meetingplanner.validator.MeetingValidator;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/*Classe de test du Controller de l'entité MeetingValidator*/
@Transactional
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes= {Application.class})
public class MeetingValidatorTest {

    /*JSON d'un DTO Meeting*/
    public String json1 = "    {\n" +
            "        \"id\": 6,\n" +
            "        \"type\": \"RC\",\n" +
            "        \"userDto\": null,\n" +
            "        \"startHour\": 9,\n" +
            "        \"endHour\": 10,\n" +
            "        \"numberOfPersons\": 7,\n" +
            "        \"isReserved\": false,\n" +
            "        \"roomDto\": null,\n" +
            "        \"freeToolDtos\": [\n" +
            "            {\n" +
            "                \"freeToolId\": 14,\n" +
            "                \"type\": \"Tableau\",\n" +
            "                \"meetingsIds\": [\n" +
            "                    8\n" +
            "                ]\n" +
            "            }\n" +
            "        ]\n" +
            "    }";

    String json2 = " {\n" +
            "        \"id\": 2,\n" +
            "        \"type\": \"VC\",\n" +
            "        \"userDto\": {\n" +
            "            \"id\": 1,\n" +
            "            \"firstName\": \"Nicolas\",\n" +
            "            \"lastName\": \"Sivignon\",\n" +
            "            \"email\": \"media.svd@outlook.fr\",\n" +
            "            \"password\": \"sudoku13\",\n" +
            "            \"meetingsIds\": [\n" +
            "                \n" +
            "            ]\n" +
            "        },\n" +
            "        \"startHour\": 9,\n" +
            "        \"endHour\": 10,\n" +
            "        \"numberOfPersons\": 6,\n" +
            "        \"isReserved\": true,\n" +
            "        \"roomDto\": {\n" +
            "            \"id\": \"E3003\",\n" +
            "            \"capacity\": 9,\n" +
            "            \"capacity70\": 5,\n" +
            "            \"roomToolDtos\": [\n" +
            "                {\n" +
            "                    \"roomToolId\": 10,\n" +
            "                    \"type\": \"Ecran\",\n" +
            "                    \"roomId\": \"E3003\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"roomToolId\": 11,\n" +
            "                    \"type\": \"Pieuvre\",\n" +
            "                    \"roomId\": \"E3003\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"meetingsIds\": [\n" +
            "                \n" +
            "            ]\n" +
            "        },\n" +
            "        \"freeToolDtos\": [\n" +
            "            \n" +
            "        ]\n" +
            "    }";

    public ObjectMapper mapper = new ObjectMapper();

    /*Je teste bien qu'on rentre dans la condition où on teste qu'un Meeting non réservé
    * ne peut pas avoir d'outils libres, d'utilisateur ou de salle affectés*/
    @Test
    public void testIsValid1() throws Exception {
        MeetingDto meetingDto = mapper.readValue(json1, MeetingDto.class);
        Meeting meeting = meetingDto.toMeeting();
        Set<FreeTool> freeTools = meetingDto.getFreeToolDtos()
                .stream()
                .map(freeToolDto -> freeToolDto.toFreeTool())
                .collect(Collectors.toSet());
        meeting.setFreeTools(freeTools);
        Exception exception = assertThrows(Exception.class,
                () -> {
                    MeetingValidator.isValid(meeting);
                });
        assertEquals(exception.getMessage(),"Une réunion qui n'est pas réservée n'a ni utilisateur, ni outils libres ni salle affectés !");
    }

    /*Je teste bien qu'on rentre dans la condition où on teste qu'un Meeting soit dans une salle
    * dont la capacité à 70% est supérieure ou égale au nombre de personnes conviées*/
    @Test
    public void testIsValid2() throws Exception {
        MeetingDto meetingDto = mapper.readValue(json2, MeetingDto.class);
        Meeting meeting = meetingDto.toMeeting();
        User user = meetingDto.getUserDto().toUser();
        Room room = meetingDto.getRoomDto().toRoom();
        room.setCapacity70(5);
        meeting.setUser(user);
        meeting.setRoom(room);
        Exception exception = assertThrows(Exception.class,
                () -> {
                    MeetingValidator.isValid(meeting);
                });
        assertEquals(exception.getMessage(),"Le nombre de personnes conviées est supérieur à la capacité de la salle à 70% !");
    }

}
