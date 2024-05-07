package com.example.backend_badminton.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend_badminton.pojo.Result;
import com.example.backend_badminton.pojo.Contestant;
import com.example.backend_badminton.pojo.Venue;
import com.example.backend_badminton.service.ContestantService;
import com.example.backend_badminton.utils.JwtUtil;
import com.example.backend_badminton.utils.TimeUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contestant")
public class ContestantController {

    @Autowired
    private ContestantService contestantService;

    @PostMapping("/apply-leader")
    public Result<?> applyLeader(@RequestParam String token) {
        DecodedJWT jwt = JwtUtil.verifyToken(token);
        Integer contestant_id = jwt.getClaim("userId").asInt();
        return contestantService.applyLeader(contestant_id,
                TimeUtil.getCurrentFormattedDate()) ? Result.success("申请成功") : Result.error("申请失败");
    }

    @GetMapping("/all")
    public Result<?> getAllContestants() {
        return Result.success(contestantService.getAllContestants());
    }

    @GetMapping("/team/info/{teamId}")
    public Result<?> getTeamInfo(@PathVariable Integer teamId) {
        return Result.success(contestantService.getTeamInfo(teamId));
    }

    @PostMapping("/check-leader")
    public Result<?> checkLeader(@RequestParam String token) {
        DecodedJWT jwt = JwtUtil.verifyToken(token);
        if (jwt.getClaim("identity").asString() != "Contestant")
            return Result.success(false);
        Contestant contestant = contestantService.fetchProfile(jwt.getClaim("userId").asInt());
        return Result.success(contestant.getIs_leader());
    }

    @PostMapping("/createTeam")
    public Result<?> createTeam(@RequestParam String teamName, @RequestParam String introduction,
            @RequestParam String token) {
        DecodedJWT jwt = JwtUtil.verifyToken(token);
        Integer contestant_id = jwt.getClaim("userId").asInt();
        Contestant contestant = contestantService.fetchProfile(contestant_id);
        if (!contestant.getIs_leader())
            return Result.error("该用户没有领队权限");
        else
            return contestantService.createTeam(teamName, introduction, contestant) ? Result.success("创建团队成功")
                    : Result.error("创建团队失败");
    }

    @PostMapping("/getInfo")
    public Result<?> fetchProfile(@RequestParam Integer id) {
        return Result.success(contestantService.fetchProfile(id));
    }

    @PostMapping("/updateProfile")
    public Result<?> updateProfile(@RequestParam String token, @RequestParam String name, @RequestParam String password,
            @RequestParam String phone) {
        DecodedJWT jwt = JwtUtil.verifyToken(token);
        Integer id = jwt.getClaim("userId").asInt();
        Contestant contestant = contestantService.fetchProfile(id);
        if (contestant == null)
            return Result.error("用户不存在");
        if (name == null || name.equals("")) {
            name = contestant.getName();
        }
        if (password == null || password.equals("")) {
            password = contestant.getPassword();
        }
        if (phone == null || phone.equals("")) {
            phone = contestant.getPhone();
        }
        return contestantService.updateProfile(id, name, password, phone) ? Result.success("更新成功")
                : Result.error("更新失败");
    }

    @GetMapping("/fetchMyTeams")
    public Result<?> fetchMyTeams(@RequestParam String token) {
        DecodedJWT jwt = JwtUtil.verifyToken(token);
        return Result.success(contestantService.fetchMyTeams(jwt.getClaim("userId").asInt()));
    }

    // 添加队员
    @PostMapping("/addMember")
    public Result<?> addMemberToTeam(@RequestParam Integer teamId, @RequestParam Integer contestantId) {
        try {
            contestantService.addTeamMember(teamId, contestantId);
            return Result.success();
        } catch (Exception e) {
            return Result.error("添加队员失败: " + e.getMessage());
        }
    }

    // 删除队员
    @DeleteMapping("/removeMember")
    public Result<?> removeMemberFromTeam(@RequestParam Integer teamId, @RequestParam Integer contestantId) {
        try {
            contestantService.removeTeamMember(teamId, contestantId);
            return Result.success();
        } catch (Exception e) {
            return Result.error("删除队员失败: " + e.getMessage());
        }
    }

    // 获取队伍成员
    @GetMapping("/fetchMembers")
    public Result<?> getTeamMembers(@RequestParam Integer teamId) {
        try {
            List<Contestant> members = contestantService.listTeamMembers(teamId);
            return Result.success(members);
        } catch (Exception e) {
            return Result.error("获取队员列表失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteTeam")
    public Result<?> deleteTeam(@RequestParam Integer teamId) {
        return contestantService.deleteTeam(teamId) ? Result.success("团队删除成功") : Result.error("删除失败");
    }

    // 获取场地信息
    @GetMapping("/fetchVenues/{id}")
    public Result<?> fetchVenuesByID(@PathVariable Integer id) {
        try {
            Venue venue = contestantService.fetchVenuesByID(id);
            return Result.success(venue);
        } catch (Exception e) {
            return Result.error("获取场地信息失败: " + e.getMessage());
        }
    }

}
