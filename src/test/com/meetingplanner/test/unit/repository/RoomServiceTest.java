package com.meetingplanner.test.unit.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.meetingplanner.repository.RoomRepository;
import com.meetingplanner.service.RoomService;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private RoomService roomService;

}
