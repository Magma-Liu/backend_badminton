package com.example.backend_badminton.service;

import com.example.backend_badminton.mapper.EventMapper;
import com.example.backend_badminton.pojo.Event;
import com.example.backend_badminton.pojo.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventMapper eventMapper;

    public boolean isTeamApplied(Integer teamId, Integer eventId) {
        return eventMapper.selectApplication(teamId, eventId) != null;
    }

    public boolean deleteEvent(Integer eventId) {
        return eventMapper.deleteEvent(eventId) > 0;
    }

    public boolean insertEvent(Event event) {
        return eventMapper.insertEvent(event) > 0;
    }

    public List<Event> fetchAllEvents() {
        return eventMapper.selectAllEvents();
    }

    public Event fetchEventDetails(Integer eventId) {
        return eventMapper.selectEventById(eventId);
    }

    public boolean updateEvent(Event event) {
        return eventMapper.updateEvent(event) > 0;
    }

    public List<Match> fetchMatchesForEvent(Integer eventId) {
        return eventMapper.selectMatchesForEvent(eventId);
    }

    public boolean applyForEvent(Integer teamId, Integer eventId) {
        return eventMapper.insertApplication(teamId, eventId) > 0;
    }
}