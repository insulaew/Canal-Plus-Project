package com.meetingplanner.test.unit.integration;

import com.meetingplanner.Application;
import com.meetingplanner.repository.RoomToolRepository;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*Classe de test du Controller de l'entité RoomTool*/
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes= {Application.class})
public class RoomToolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RoomToolRepository roomToolRepository;

    /*Il y a 11 équipements salle en base de données au total.*/
    @Test
    public void testListeEquipementsSalle() throws Exception {
        this.mockMvc.perform(get("/api/EquipementsSalle"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(equalTo(11))));
    }

    /*Le 1er équipement salle en base de données est un écran.*/
    @Test
    public void testEquipementSalleById() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        /*Le premier outil salle en base de données est un écran de la salle 'E1002'*/
        params.add("id", "1");
        this.mockMvc.perform(get("/api/EquipementSalle")
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type", is("Ecran")));
    }

    @Test
    public void testEquipementsSalleByIds() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        /*Le deuxième outil salle en base de données est une pieuvre de la salle 'E1003'*/
        params.add("ids", "1");
        params.add("ids", "2");
        this.mockMvc.perform(get("/api/EquipementsSalle")
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[?(@.type == \"Pieuvre\")]").exists())
                .andExpect(jsonPath("$.[?(@.type == \"Ecran\")]").exists())
                .andExpect(jsonPath("$", hasSize(equalTo(2))));
    }

}