package com.meetingplanner.test.unit.integration;


import com.fasterxml.jackson.databind.ObjectMapper;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;

import static org.hamcrest.Matchers.*;
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

    @Test
    public void testUtilisateurByEmail() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        this.mockMvc.perform(get("/api/UtilisateurEmail")
                .param("email", "media.svd@outlook.fr"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Nicolas")));

    }

    /*Teste une requête Http Post User.*/
    @Test
    public void testPostUtilisateur() throws Exception{
        UserDto laetitia = new UserDto(4L, "Laetitia", "Dautrey", "laetitia.dautrey@alten.com", "alten123", new HashSet<Long>());

        ObjectMapper mapper = new ObjectMapper();

        this.mockMvc.perform(post("/api/Utilisateur")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(laetitia)))
                .andExpect(status().isCreated());

    }

}
