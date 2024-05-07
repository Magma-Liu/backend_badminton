package com.example.backend_badminton.service;

import com.example.backend_badminton.pojo.*;
import com.example.backend_badminton.mapper.LoginAndRegisterMapper;
import com.example.backend_badminton.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginAndRegisterService {

    @Autowired
    private LoginAndRegisterMapper loginAndRegisterMapper;

    public Integer loginContestant(String phone, String password) {
        Contestant contestant = loginAndRegisterMapper.findContestantByPhoneAndPassword(phone, password);
        if (contestant != null)
            return contestant.getContestant_id();
        else
            return null;
    }

    public Integer loginChiefReferee(String phone, String password) {
        ChiefReferee chiefReferee = loginAndRegisterMapper.findChiefRefereeByPhoneAndPassword(phone, password);
        if (chiefReferee != null)
            return chiefReferee.getReferee_id();
        else
            return null;
    }

    public Integer loginAdmin(String phone, String password) {
        Admin admin = loginAndRegisterMapper.findAdminByPhoneAndPassword(phone, password);
        if (admin != null)
            return admin.getAdmin_id();
        else
            return null;
    }

    public Integer loginReferee(String phone, String password) {
        Referee referee = loginAndRegisterMapper.findRefereeByPhoneAndPassword(phone, password);
        if (referee != null)
            return referee.getReferee_id();
        else
            return null;
    }

    // 返回Token
    public Result<?> registerContestant(String phone, String password, String name) {
        Object existing = loginAndRegisterMapper.findByPhone(phone);
        if (existing != null) {
            return Result.error("手机号已被注册");
        }
        Contestant contestant = new Contestant();
        contestant.setPhone(phone);
        contestant.setPassword(password);
        contestant.setName(name);
        try {
            loginAndRegisterMapper.insertContestant(contestant);
        } catch (IllegalStateException e) {
            return Result.error(e.getMessage());
        }
        contestant = loginAndRegisterMapper.findByPhone(phone);
        return Result.success(JwtUtil.createToken(contestant.getContestant_id(), "Contestant", contestant.getPhone()));
    }
}
