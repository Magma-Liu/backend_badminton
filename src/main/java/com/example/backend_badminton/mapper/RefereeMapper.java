package com.example.backend_badminton.mapper;

import com.example.backend_badminton.pojo.Event;
import com.example.backend_badminton.pojo.Match;
import org.apache.ibatis.annotations.*;

import com.example.backend_badminton.pojo.Referee;

import java.util.List;

@Mapper
public interface RefereeMapper {

    @Update("UPDATE `match` SET score1 = #{score} WHERE match_id = #{matchId}")
    int updateMatchScore1(Integer matchId, String score);

    @Update("UPDATE `match` SET score2 = #{score} WHERE match_id = #{matchId}")
    int updateMatchScore2(Integer matchId, String score);

    @Update("UPDATE `match` SET score3 = #{score} WHERE match_id = #{matchId}")
    int updateMatchScore3(Integer matchId, String score);

    @Update("UPDATE `match` SET score4 = #{score} WHERE match_id = #{matchId}")
    int updateMatchScore4(Integer matchId, String score);

    @Update("UPDATE `match` SET score5 = #{score} WHERE match_id = #{matchId}")
    int updateMatchScore5(Integer matchId, String score);

    @Update("UPDATE `match` SET score6 = #{score} WHERE match_id = #{matchId}")
    int updateMatchScore6(Integer matchId, String score);

    @Update("UPDATE `match` SET score7 = #{score} WHERE match_id = #{matchId}")
    int updateMatchScore7(Integer matchId, String score);

    @Select("SELECT * FROM event WHERE event_id IN (SELECT event_id FROM event_referees WHERE referee_id = #{refereeId})")
    List<Event> findActivitiesByReferee(Integer refereeId);

    @Update("UPDATE referee SET name = #{name}, phone = #{phone}, password = #{password} WHERE referee_id = #{referee_id}")
    int updateProfile(Integer referee_id, String name, String password, String phone);

    @Select("SELECT * FROM referee WHERE referee_id = #{referee_id}")
    Referee findRefereeById(Integer referee_id);
}
