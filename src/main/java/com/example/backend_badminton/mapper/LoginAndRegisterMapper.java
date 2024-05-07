package com.example.backend_badminton.mapper;

import com.example.backend_badminton.pojo.Contestant;
import com.example.backend_badminton.pojo.ChiefReferee;
import com.example.backend_badminton.pojo.Admin;
import com.example.backend_badminton.pojo.Referee;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginAndRegisterMapper {

    @Select("SELECT * FROM contestant WHERE phone = #{phone} AND password = #{password}")
    Contestant findContestantByPhoneAndPassword(@Param("phone") String phone, @Param("password") String password);

    @Select("SELECT * FROM chief_referee WHERE phone = #{phone} AND password = #{password}")
    ChiefReferee findChiefRefereeByPhoneAndPassword(@Param("phone") String phone, @Param("password") String password);

    @Select("SELECT * FROM admin WHERE phone = #{phone} AND password = #{password}")
    Admin findAdminByPhoneAndPassword(@Param("phone") String phone, @Param("password") String password);

    @Select("SELECT * FROM referee WHERE phone = #{phone} AND password = #{password}")
    Referee findRefereeByPhoneAndPassword(@Param("phone") String phone, @Param("password") String password);

    @Insert("INSERT INTO contestant(name, phone, password, is_leader) " +
            "VALUES(#{name}, #{phone}, #{password}, false)")
    int insertContestant(Contestant contestant);

    @Select("SELECT * FROM contestant WHERE phone = #{phone}")
    Contestant findByPhone(String phone);

}