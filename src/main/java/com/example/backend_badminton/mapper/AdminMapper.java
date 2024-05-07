package com.example.backend_badminton.mapper;

import com.example.backend_badminton.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Select("SELECT * FROM admin")
    List<Admin> selectAllAdmins();

    @Update("UPDATE admin SET name = #{name}, password = #{password}, phone = #{phone} WHERE admin_id = #{id}")
    int updateAdmin(Integer id, String name, String password, String phone);

    @Select("SELECT * FROM admin WHERE admin_id = #{id}")
    Admin selectAdminByID(Integer id);

    @Insert("INSERT INTO admin (name, phone, password) VALUES (#{name}, #{phone}, #{password})")
    int insertAdmin(Admin admin);

    @Select("SELECT * FROM chief_referee")
    List<ChiefReferee> selectAllJudges();

    @Insert("INSERT INTO chief_referee (name, phone, password) VALUES (#{name}, #{phone}, #{password})")
    int insertJudge(ChiefReferee judge);

    @Update("UPDATE chief_referee SET name = #{name}, phone = #{phone}, password = #{password} WHERE referee_id = #{referee_id}")
    int updateJudge(ChiefReferee judge);

    @Delete("DELETE FROM chief_referee WHERE referee_id = #{id}")
    int deleteJudge(int id);

    @Select("SELECT * FROM venue")
    List<Venue> selectAllVenues();

    @Insert("INSERT INTO venue (name, address, area_count) VALUES (#{name}, #{address}, #{area_count})")
    int insertVenue(Venue venue);

    @Update("UPDATE venue SET name = #{name}, address = #{address}, area_count = #{area_count} WHERE venue_id = #{venue_id}")
    int updateVenue(Venue venue);

    @Delete("DELETE FROM venue WHERE venue_id = #{venue_id}")
    int deleteVenue(int id);

    @Select("SELECT * FROM leader_application")
    List<LeaderApplication> fetchAllApplications();

    @Update("UPDATE leader_application SET is_approved = #{is_approved}, admin_id = #{adminId}, approval_time = #{approvalTime} WHERE contestant_id = #{contestant_id}")
    int updateApplicationStatus(int contestant_id, int adminId, boolean is_approved, String approvalTime);

    @Select("SELECT * FROM chief_referee WHERE phone = #{phone}")
    Contestant selectChiefRefereeByPhone(String phone);

    @Select("SELECT * FROM chief_referee WHERE referee_id = #{id}")
    ChiefReferee selectChiefRefereeByID(Integer id);

    @Select("SELECT * FROM venue WHERE name = #{name}")
    Venue selectVenueByName(String name);

    @Select("SELECT * FROM venue WHERE venue_id = #{id}")
    Venue selectVenueByID(Integer id);

    @Update("UPDATE contestant SET is_leader = #{is_leader} WHERE contestant_id = #{contestant_id}")
    int updateLeaderStatus(int contestant_id, boolean is_leader);
}
