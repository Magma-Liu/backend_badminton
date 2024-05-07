package com.example.backend_badminton.service;

import com.example.backend_badminton.mapper.RefereeMapper;
import com.example.backend_badminton.pojo.Event;
import com.example.backend_badminton.pojo.Match;
import com.example.backend_badminton.pojo.Referee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefereeService {

    @Autowired
    private RefereeMapper refereeMapper;

    public boolean updateScore(Integer matchId, Integer index, String score) {
        if (index == 1) {
            return refereeMapper.updateMatchScore1(matchId, score) > 0;
        } else if (index == 2) {
            return refereeMapper.updateMatchScore2(matchId, score) > 0;
        } else if (index == 3) {
            return refereeMapper.updateMatchScore3(matchId, score) > 0;
        } else if (index == 4) {
            return refereeMapper.updateMatchScore4(matchId, score) > 0;
        } else if (index == 5) {
            return refereeMapper.updateMatchScore5(matchId, score) > 0;
        } else if (index == 6) {
            return refereeMapper.updateMatchScore6(matchId, score) > 0;
        } else
            return refereeMapper.updateMatchScore7(matchId, score) > 0;
    }

    public List<Event> fetchActivitiesByRefereeId(Integer refereeId) {
        return refereeMapper.findActivitiesByReferee(refereeId);
    }

    public Referee fetchReferee(Integer refereeId) {
        return refereeMapper.findRefereeById(refereeId);
    }

    public boolean updateProfile(Integer id, String name, String password, String phone) {
        return refereeMapper.updateProfile(id, name, password, phone) > 0;
    }
}
