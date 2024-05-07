package com.example.backend_badminton.service;

import com.example.backend_badminton.mapper.ContestantMapper;
import com.example.backend_badminton.pojo.Contestant;
import com.example.backend_badminton.pojo.Team;
import com.example.backend_badminton.pojo.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestantService {

    @Autowired
    private ContestantMapper contestantMapper;

    public boolean applyLeader(Integer contestant_id, String application_time) {
        return contestantMapper.insertApplyLeader(contestant_id, application_time) > 0;
    }

    public List<Contestant> getAllContestants() {
        return contestantMapper.findAllContestants();
    }

    public Team getTeamInfo(Integer teamId) {
        return contestantMapper.findTeamById(teamId);
    }

    public boolean createTeam(String teamName, String introduction, Contestant contestant) {
        Team team = new Team();
        team.setName(teamName);
        team.setIntroduction(introduction);
        team.setLeader_id(contestant.getContestant_id());
        team.setLeader_name(contestant.getName());
        team.setLeader_phone(contestant.getPhone());
        return contestantMapper.insertTeam(team) > 0;
    }

    public Contestant fetchProfile(Integer id) {
        return contestantMapper.findContestantById(id);
    }

    public boolean updateProfile(Integer id, String name, String password, String phone) {
        return contestantMapper.updateContestant(id, name, password, phone) > 0;
    }

    public List<Team> fetchMyTeams(Integer contestantId) {
        return contestantMapper.findTeamsByContestant(contestantId);
    }

    public boolean deleteTeam(Integer teamId) {
        return contestantMapper.deleteTeam(teamId) > 0;
    }

    // 添加队员
    public void addTeamMember(Integer teamId, Integer contestantId) {
        contestantMapper.addMemberToTeam(teamId, contestantId);
    }

    // 删除队员
    public void removeTeamMember(Integer teamId, Integer contestantId) {
        contestantMapper.removeMemberFromTeam(teamId, contestantId);
    }

    // 获取队伍所有成员
    public List<Contestant> listTeamMembers(Integer teamId) {
        List<Integer> contestantIds = contestantMapper.getMembersByTeamId(teamId);
        if (contestantIds == null || contestantIds.size() == 0) {
            return null;
        } else
            return contestantMapper.findContestantsByListId(contestantIds);
    }

    // 根据场地id获取场地信息
    public Venue fetchVenuesByID(Integer id) {
        return contestantMapper.findVenueById(id);
    }
}
