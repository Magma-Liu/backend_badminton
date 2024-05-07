package com.example.backend_badminton.mapper;

import com.example.backend_badminton.pojo.Contestant;
import com.example.backend_badminton.pojo.Team;

import com.example.backend_badminton.pojo.Venue;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContestantMapper {

    @Insert("INSERT INTO leader_application(contestant_id, application_time) VALUES(#{contestant_id}, #{application_time})")
    int insertApplyLeader(Integer contestant_id, String application_time);

    @Select("SELECT * FROM contestant")
    List<Contestant> findAllContestants();

    @Select("SELECT * FROM team WHERE team_id = #{id}")
    Team findTeamById(Integer id);

    @Insert("INSERT INTO team (name, introduction, leader_name, leader_id, leader_phone) VALUES (#{name}, #{introduction}, #{leader_name}, #{leader_id}, #{leader_phone})")
    int insertTeam(Team team);

    @Select("SELECT * FROM contestant WHERE contestant_id = #{id}")
    Contestant findContestantById(Integer id);

    @Update("UPDATE contestant SET name = #{name}, password = #{password}, phone = #{phone} WHERE contestant_id = #{id}")
    int updateContestant(Integer id, String name, String password, String phone);

    @Select("SELECT * FROM team WHERE leader_id = #{contestantId}")
    List<Team> findTeamsByContestant(Integer contestantId);

    @Delete("DELETE FROM team WHERE team_id = #{teamId}")
    int deleteTeam(Integer teamId);

    // 添加队员到队伍
    @Insert("INSERT INTO team_members(team_id, contestant_id) VALUES(#{teamId}, #{contestantId})")
    void addMemberToTeam(Integer teamId, Integer contestantId);

    // 从队伍删除队员
    @Delete("DELETE FROM team_members WHERE team_id = #{teamId} AND contestant_id = #{contestantId}")
    void removeMemberFromTeam(Integer teamId, Integer contestantId);

    // 获取队伍所有成员的ID
    @Select("SELECT contestant_id FROM team_members WHERE team_id = #{teamId}")
    List<Integer> getMembersByTeamId(Integer teamId);

    @Select("<script>" +
            "SELECT * FROM contestant WHERE contestant_id IN " +
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    List<Contestant> findContestantsByListId(List<Integer> ids);

    @Select("SELECT * FROM venue WHERE venue_id = #{id}")
    Venue findVenueById(Integer id);
}
