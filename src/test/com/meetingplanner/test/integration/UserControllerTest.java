package com.meetingplanner.test.unit.integration;


import com.meetingplanner.Application;
import com.meetingplanner.dto.UserDto;
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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*Classe de test du Controller de l'entité User*/
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes= {Application.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    /*Teste une requête Http Get de tous les Users en base de données*/
    @Test
    public void testListeUtilisateurs() throws Exception {
        this.mockMvc.perform(get("/api/Utilisateurs"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(equalTo(3))));
    }

    /*Teste une requête Http Post User qui ne marche pas...*/
    @Test
    public void testPostUtilisateur() throws Exception{
        UserDto laetitia = new UserDto(4L, "Laetitia", "Dautrey", "laetitia.dautrey@alten.com", "alten123");

        this.mockMvc.perform(post("/api/Utilisateur", laetitia))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Laetitia"));
    }

}
