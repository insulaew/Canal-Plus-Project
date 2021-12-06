package com.meetingplanner.test.unit.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.meetingplanner.model.FreeTool;
import com.meetingplanner.model.ToolType;
import com.meetingplanner.repository.FreeToolRepository;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FreeToolRepositoryTest {

    @Autowired
    private FreeToolRepository freeToolRepository;

    @Test
    public void testGetFreeToolById() {
        assertEquals(this.freeToolRepository.findById(1L).orElseThrow().getType().toString(), "Pieuvre");
    }

    @Test
    public void testGetFreeTools() {
        assertEquals(this.freeToolRepository.findAll().size(), 15);
    }

    @Test
    public void testGetFreeToolsByTypeCompatibleForMeeting() {
        assertEquals(this.freeToolRepository.findFreeToolsByTypeCompatibleForMeeting("Pieuvre", 9).size(), 3);
    }

    @Test
    public void testGetFreeToolsByType() {
        assertEquals(this.freeToolRepository.findFreeToolsByType("Webcam").size(), 4);
    }

    @Test
    public void testGetFreeToolsByMeeting() {
        assertEquals(this.freeToolRepository.findFreeToolByMeeting(1L).get(0).getFreeToolId(), 1);
    }

    @Test
    public void testUpdateFreeTool() {
        FreeTool freeTool = this.freeToolRepository.getById(1L);
        freeTool.setType(ToolType.Ecran);
        FreeTool savedFreeTool = this.freeToolRepository.save(freeTool);
        assertNotEquals(ToolType.Pieuvre, this.freeToolRepository.getById(1L).getType());
    }

}
