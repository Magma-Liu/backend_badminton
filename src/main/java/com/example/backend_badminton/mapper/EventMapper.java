package com.example.backend_badminton.mapper;

import com.example.backend_badminton.pojo.Event;
import com.example.backend_badminton.pojo.Match;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EventMapper {

        @Select("SELECT * FROM activity_application WHERE team_id = #{teamId} AND event_id = #{eventId}")
        Object selectApplication(Integer teamId, Integer eventId);

        @Delete("DELETE FROM event WHERE event_id = #{eventId}")
        int deleteEvent(Integer eventId);

        @Insert("INSERT INTO event (name, introduction, venue_id, required_area_count, chief_referee_id, activity_time, scoring_system, match_types) "
                        +
                        "VALUES (#{name}, #{introduction}, #{venue_id}, #{required_area_count}, #{chief_referee_id}, #{activity_time}, #{scoring_system}, #{match_types})")
        int insertEvent(Event event);

        @Select("SELECT * FROM event")
        List<Event> selectAllEvents();

        @Select("SELECT * FROM event WHERE event_id = #{eventId}")
        Event selectEventById(Integer eventId);

        @Update("UPDATE event SET name = #{name}, introduction = #{introduction}, venue_id = #{venue_id}, "
                        +
                        "required_area_count = #{required_area_count}, chief_referee_id = #{chief_referee_id}, activity_time = #{activity_time}, "
                        +
                        " scoring_system = #{scoring_system}, match_types = #{match_types} WHERE event_id = #{event_id}")
        int updateEvent(Event event);

        @Select("SELECT * FROM `match` WHERE event_id = #{eventId}")
        List<Match> selectMatchesForEvent(Integer eventId);

        @Update("INSERT INTO activity_application (team_id, event_id) VALUES (#{teamId}, #{eventId})")
        int insertApplication(Integer teamId, Integer eventId);
}