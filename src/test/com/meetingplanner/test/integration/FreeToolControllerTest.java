package com.meetingplanner.test.unit.integration;

import com.meetingplanner.Application;
import com.meetingplanner.repository.FreeToolRepository;

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
    private FreeToolRepository freeToolRepository;

    @Test
    public void testListeEquipementsLibres() throws Exception {
        this.mockMvc.perform(get("/api/EquipementsLibres"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(equalTo(15))));
    }

}
