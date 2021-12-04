package com.meetingplanner.test.unit.repository;

import static org.junit.Assert.assertEquals;

import com.meetingplanner.model.Room;
import com.meetingplanner.repository.RoomRepository;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    /*Ce test va échouer car DATA JPA ne semble pas comprendre le logarithme dans la requête SQL,
    * alors que Hibernate le reconnaît depuis le controller*/
    @Test
    public void testGetRoomsWithEnoughCapacity() {
        List<Room > rooms = roomRepository.findRoomsWithEnoughCapacity(6);
        assertEquals(5,roomRepository.findRoomsWithEnoughCapacity(6).size());
    }

}
