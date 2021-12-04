package com.meetingplanner.test.unit.repository;

import static org.junit.Assert.assertEquals;

import com.meetingplanner.repository.RoomToolRepository;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoomToolRepositoryTest {

    @Autowired
    private RoomToolRepository roomToolRepository;

    @Test
    public void testGetRoomToolById() {
        assertEquals(this.roomToolRepository.findById(1L).orElseThrow().getType().toString(), "Ecran");
    }

    @Test
    public void testGetRoomTools() {
        assertEquals(this.roomToolRepository.findAll().size(), 11);
    }

    @Test
    public void testFindRoomToolsByType() {
        assertEquals(this.roomToolRepository.findRoomToolsByType("Tableau").size(), 2);
    }

    @Test
    public void testFindRoomToolsByRoom() {
        assertEquals(this.roomToolRepository.findRoomToolsByRoom("E3003").size(), 2);
    }

}
