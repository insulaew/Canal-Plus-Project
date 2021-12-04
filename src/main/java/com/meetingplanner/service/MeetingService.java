package com.meetingplanner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetingplanner.model.Meeting;
import com.meetingplanner.repository.MeetingRepository;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    public List<Meeting> getMeetings() {
        return meetingRepository.findAll();
    }

    public List<Meeting> getMeetingsByIds(List<Long> ids) {
        return meetingRepository.findAllById(ids);
    }

    public List<Meeting> getNotReservedMeetings() {
        return meetingRepository.findNotReservedMeetings();
    }

    public List<Meeting> getReservedMeetings() {
        return meetingRepository.findReservedMeetings();
    }

}
