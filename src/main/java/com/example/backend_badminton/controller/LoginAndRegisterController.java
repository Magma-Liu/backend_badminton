package com.example.backend_badminton.controller;

import com.example.backend_badminton.pojo.*;
import com.example.backend_badminton.service.*;
import com.example.backend_badminton.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.auth0.jwt.interfaces.DecodedJWT;

@RestController
@CrossOrigin
public class LoginAndRegisterController {

    @Autowired
    private LoginAndRegisterService loginAndRegisterService;

    @Autowired
    private ContestantService contestantService;

    @Autowired
    private ChiefRefereeService chiefRefereeService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RefereeService refereeService;

    @PostMapping("/login")
    public Result<?> login(String phone, String password, Integer identity) {
        Integer id = null;
        String identityName = null;
        switch (identity) {
            case 0:
                id = loginAndRegisterService.loginContestant(phone, password);
                identityName = "Contestant";
                break;
            case 1:
                id = loginAndRegisterService.loginChiefReferee(phone, password);
                identityName = "ChiefReferee";
                break;
            case 2:
                id = loginAndRegisterService.loginAdmin(phone, password);
                identityName = "Admin";
                break;
            case 3:
                id = loginAndRegisterService.loginReferee(phone, password);
                identityName = "Referee";
                break;
        }
        if (id != null) {
            return Result.success(JwtUtil.createToken(id, identityName, phone));
        } else {
            return Result.error("Login failed");
        }
    }

    @PostMapping("/register")
    public Result<?> registerContestant(String phone, String password, String name) {
        return loginAndRegisterService.registerContestant(phone, password, name);
    }

    @PostMapping("/getInfo")
    public Result<?> fetchProfile(@RequestParam String token) {
        DecodedJWT jwt = JwtUtil.verifyToken(token);
        String identity = jwt.getClaim("identity").asString();
        if (identity.equals("Contestant"))
            return Result.success(contestantService.fetchProfile(jwt.getClaim("userId").asInt()));
        else if (identity.equals("ChiefReferee"))
            return Result.success(chiefRefereeService.fetchProfile(jwt.getClaim("userId").asInt()));
        else if (identity.equals("Admin"))
            return Result.success(adminService.fetchAdmin(jwt.getClaim("userId").asInt()));
        else if (identity.equals("Referee"))
            return Result.success(refereeService.fetchReferee(jwt.getClaim("userId").asInt()));
        return Result.error("身份识别有误");
    }

    @PostMapping("/updateProfile")
    public Result<?> updateProfile(@RequestParam String token, @RequestParam String name, @RequestParam String password,
                                   @RequestParam String phone) {
        DecodedJWT jwt = JwtUtil.verifyToken(token);
        String identity = jwt.getClaim("identity").asString();
        Integer id = jwt.getClaim("userId").asInt();
        if (identity.equals("Contestant"))
            return contestantService.updateProfile(id, name, password, phone) ? Result.success("更新成功")
                    : Result.error("更新失败");
        else if (identity.equals("ChiefReferee"))
            return chiefRefereeService.updateProfile(id, name, password, phone) ? Result.success("更新成功")
                    : Result.error("更新失败");
        else if (identity.equals("Admin"))
            return adminService.updateProfile(id, name, password, phone) ? Result.success("更新成功")
                    : Result.error("更新失败");
        else if (identity.equals("Referee"))
            return refereeService.updateProfile(id, name, password, phone) ? Result.success("更新成功")
                    : Result.error("更新失败");
        return Result.error("更新失败");
    }
}
