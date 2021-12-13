package com.meetingplanner.test.unit.repository;

import static org.junit.Assert.assertEquals;

import com.meetingplanner.Application;
import com.meetingplanner.model.User;
import com.meetingplanner.repository.UserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/*Classe de test du Repository User*/
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes= {Application.class})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetUserById() {
        assertEquals(this.userRepository.findById(1L).orElseThrow().getFirstName(), "Nicolas");
    }

    @Test
    public void testGetUserByEmail() {
        assertEquals(this.userRepository.findUserByEmail("media.svd@outlook.fr").orElseThrow().getFirstName(), "Nicolas");
    }

    @Test
    public void testGetUsers() {
        assertEquals(this.userRepository.findAll().size(), 3);
    }

    @Test
    public void testSaveUser() {
        User userSaved = this.userRepository.save(new User(4L, "Laetitia", "Dautrey", "laetitia.dautrey@alten.com", "123456"));
        User laetitia = this.userRepository.findById(4L).orElseThrow();
        assertEquals(userSaved.getFirstName(), laetitia.getFirstName());
    }

}
