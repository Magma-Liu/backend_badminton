package com.example.backend_badminton.mapper;

import com.example.backend_badminton.pojo.*;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

@Mapper
public interface ChiefRefereeMapper {

    @Select("SELECT * FROM referee WHERE referee_id = #{id}")
    Referee selectJudgeProfile(Integer id);

    @Delete("DELETE FROM `match` WHERE match_id = #{match_id}")
    int deleteMatch(Integer match_id);

    @Delete("DELETE FROM `match` WHERE event_id = #{activityId}")
    int deleteAllMatches(Integer activityId);

    @Select("SELECT team_id FROM activity_application WHERE event_id = #{activityId} AND is_approved = TRUE")
    List<Integer> selectAgreedTeams(Integer activityId);

    @Update("UPDATE `match` SET referee_id = #{referee_id}, team1_id = #{team1_id}, team2_id = #{team2_id} WHERE match_id = #{match_id}")
    int updateMatch(Integer match_id, Integer referee_id, Integer team1_id, Integer team2_id);

    @Select("SELECT * FROM activity_application WHERE event_id = #{activityId}")
    List<ActivityApplication> selectTeamApplications(Integer activityId);

    @Insert("INSERT INTO event_referees (event_id, referee_id) VALUES (#{eventID}, #{referee_id})")
    int insertRefereeToEvent(Integer eventID, Integer referee_id);

    @Select("SELECT * FROM referee WHERE referee_id IN (SELECT referee_id FROM event_referees WHERE event_id = #{eventID})")
    List<Referee> selectRefereeInEvent(Integer eventID);

    @Delete("DELETE FROM event_referees WHERE event_id = #{eventID} AND referee_id = #{memberId}")
    int deleteRefereeFromEvent(Integer eventID, Integer memberId);

    @Update("UPDATE referee SET name = #{name}, password = #{password}, phone = #{phone} WHERE referee_id = #{id}")
    int updateRefereeProfile(Integer id, String name, String password, String phone);

    @Delete("DELETE FROM referee WHERE referee_id = #{id}")
    int deleteReferee(Integer id);

    @Insert("INSERT INTO referee (name, password, phone) VALUES (#{name}, #{password}, #{phone})")
    int insertReferee(String name, String password, String phone);

    @Select("SELECT * FROM referee")
    List<Referee> selectAllReferees();

    @Select("SELECT * FROM chief_referee")
    List<ChiefReferee> selectAllChiefReferees();

    @Update("UPDATE chief_referee SET name = #{name}, password = #{password}, phone = #{phone} WHERE referee_id = #{id}")
    int updateProfile(Integer id, String name, String password, String phone);

    @Select("SELECT * FROM chief_referee WHERE referee_id = #{refereeId}")
    ChiefReferee selectProfile(Integer refereeId);

    @Select("SELECT * FROM event WHERE chief_referee_id = #{refereeId}")
    List<Event> selectActivitiesByRefereeId(Integer refereeId);

    @Select("SELECT * FROM event WHERE event_id = #{activityId}")
    Event selectActivityDetail(Integer activityId);

    @Insert("INSERT INTO `match` (event_id, team1_id, team2_id, scoring_system, referee_id, score1, score2, score3, score4, score5, score6, score7) "
            +
            "VALUES (#{event_id}, #{team1_id}, #{team2_id}, #{scoring_system}, #{referee_id}, #{score1}, #{score2}, #{score3}, #{score4}, #{score5}, #{score6}, #{score7})")
    int insertMatch(Match match);

    @Update("UPDATE activity_application SET is_approved = TRUE WHERE team_id = #{teamId} AND event_id = #{activityId}")
    int approveTeamApplication(Integer activityId, Integer teamId);

    @Update("UPDATE activity_application SET is_approved = FALSE WHERE team_id = #{teamId} AND event_id = #{activityId}")
    int rejectTeamApplication(Integer activityId, Integer teamId);
}
