package com.example.backend_badminton.service;

import com.example.backend_badminton.mapper.ChiefRefereeMapper;
import com.example.backend_badminton.pojo.ActivityApplication;
import com.example.backend_badminton.pojo.ChiefReferee;
import com.example.backend_badminton.pojo.Event;
import com.example.backend_badminton.pojo.Match;
import com.example.backend_badminton.pojo.MatchInfo;
import com.example.backend_badminton.pojo.Referee;
import com.example.backend_badminton.pojo.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChiefRefereeService {

    @Autowired
    private ChiefRefereeMapper chiefRefereeMapper;

    public Boolean deleteMatch(Integer match_id) {
        return chiefRefereeMapper.deleteMatch(match_id) > 0;
    }

    public void deleteAllMatches(Integer activityId) {
        chiefRefereeMapper.deleteAllMatches(activityId);
    }

    public List<Integer> fetchAgreedTeams(Integer activityId) {
        return chiefRefereeMapper.selectAgreedTeams(activityId);
    }

    public Boolean updateMatch(Integer match_id, Integer referee_id, Integer team1_id, Integer team2_id) {
        return chiefRefereeMapper.updateMatch(match_id, referee_id, team1_id, team2_id) > 0;
    }

    public List<ActivityApplication> fetchTeamApplications(Integer activityId) {
        return chiefRefereeMapper.selectTeamApplications(activityId);
    }

    public Boolean addRefereeToEvent(Integer eventID, Integer referee_id) {
        return chiefRefereeMapper.insertRefereeToEvent(eventID, referee_id) > 0;
    }

    public List<Referee> fetchRefereeInEvent(Integer eventID) {
        return chiefRefereeMapper.selectRefereeInEvent(eventID);
    }

    public Boolean removeRefereeFromEvent(Integer eventID, Integer memberId) {
        return chiefRefereeMapper.deleteRefereeFromEvent(eventID, memberId) > 0;
    }

    public Boolean updateRefereeProfile(Integer id, String name, String password, String phone) {
        return chiefRefereeMapper.updateRefereeProfile(id, name, password, phone) > 0;
    }

    public Boolean deleteReferee(Integer id) {
        return chiefRefereeMapper.deleteReferee(id) > 0;
    }

    public Boolean addReferee(String name, String password, String phone) {
        return chiefRefereeMapper.insertReferee(name, password, phone) > 0;
    }

    public List<Referee> fetchAllReferees() {
        return chiefRefereeMapper.selectAllReferees();
    }

    public List<ChiefReferee> fetchAllChiefReferees() {
        return chiefRefereeMapper.selectAllChiefReferees();
    }

    public Boolean updateProfile(Integer id, String name, String password, String phone) {
        return chiefRefereeMapper.updateProfile(id, name, password, phone) > 0;
    }

    public ChiefReferee fetchProfile(Integer id) {
        return chiefRefereeMapper.selectProfile(id);
    }

    public List<Event> fetchActivitiesByRefereeId(Integer refereeId) {
        return chiefRefereeMapper.selectActivitiesByRefereeId(refereeId);
    }

    public Event fetchActivityDetail(Integer activityId) {
        return chiefRefereeMapper.selectActivityDetail(activityId);
    }

    public boolean scheduleAllMatches(MatchInfo matchList) {
        List<Integer> teams = matchList.getTeams();
        int refereeCount = matchList.getReferees().size();
        int count = matchList.getCount();
        try {
            for (int i = 0; i < teams.size() / 2; i++) {
                Match match = createMatch(matchList.getEvent_id(), teams.get(i * 2), teams.get(i * 2 + 1),
                        matchList.getScoring_system(), matchList.getReferees().get(i % refereeCount), count);
                chiefRefereeMapper.insertMatch(match);
            }
            if (teams.size() % 2 == 1) {
                Match match = createMatch(matchList.getEvent_id(), teams.get(teams.size() - 1),
                        teams.get(teams.size() - 1), matchList.getScoring_system(),
                        matchList.getReferees().get(teams.size() / 2 % refereeCount), count);
                chiefRefereeMapper.insertMatch(match);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Match createMatch(int eventId, int team1Id, int team2Id, String scoringSystem, int refereeId, int count) {
        Match match = new Match();
        match.setEvent_id(eventId);
        match.setTeam1_id(team1Id);
        match.setTeam2_id(team2Id);
        match.setScoring_system(scoringSystem);
        match.setReferee_id(refereeId);
        for (int j = 1; j <= 7 && j <= count; j++) {
            // 根据对局数量设置对应的比分字段为0
            switch (j) {
                case 1:
                    match.setScore1("0-0");
                    break;
                case 2:
                    match.setScore2("0-0");
                    break;
                case 3:
                    match.setScore3("0-0");
                    break;
                case 4:
                    match.setScore4("0-0");
                    break;
                case 5:
                    match.setScore5("0-0");
                    break;
                case 6:
                    match.setScore6("0-0");
                    break;
                case 7:
                    match.setScore7("0-0");
                    break;
            }
        }
        return match;
    }


    public boolean approveTeamApplication(Integer activityId, Integer teamId) {
        return chiefRefereeMapper.approveTeamApplication(activityId, teamId) > 0;
    }

    public boolean rejectTeamApplication(Integer activityId, Integer teamId) {
        return chiefRefereeMapper.rejectTeamApplication(activityId, teamId) > 0;
    }
}
