package com.example.backend_badminton.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend_badminton.pojo.Event;
import com.example.backend_badminton.pojo.MatchInfo;
import com.example.backend_badminton.pojo.Referee;
import com.example.backend_badminton.pojo.Result;
import com.example.backend_badminton.service.ChiefRefereeService;
import com.example.backend_badminton.utils.JwtUtil;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChiefRefereeController {

    @Autowired
    private ChiefRefereeService chiefRefereeService;

    @PostMapping("/chiefReferee/deleteMatch")
    public Result<?> deleteMatch(@RequestParam Integer match_id) {
        return chiefRefereeService.deleteMatch(match_id) ? Result.success() : Result.error("比赛删除失败");
    }

    @PostMapping("/chiefReferee/updateMatch")
    public Result<?> updateMatch(@RequestParam Integer match_id, @RequestParam Integer referee_id,
            @RequestParam Integer team1_id, @RequestParam Integer team2_id) {
        return chiefRefereeService.updateMatch(match_id, referee_id, team1_id, team2_id) ? Result.success()
                : Result.error("比赛信息更新失败");
    }

    @GetMapping("/teams/applicationsInEvent")
    public Result<?> fetchTeamApplications(@RequestParam Integer activityId) {
        return Result.success(chiefRefereeService.fetchTeamApplications(activityId));
    }

    @PostMapping("/chiefReferee/addRefereeToEvent")
    public Result<?> addRefereeToEvent(@RequestParam Integer eventID, @RequestParam Integer referee_id) {
        return chiefRefereeService.addRefereeToEvent(eventID, referee_id) ? Result.success() : Result.error("裁判添加失败");
    }

    @PostMapping("/chiefReferee/getRefereeInEvent")
    public Result<?> fetchRefereeInEvent(@RequestParam Integer eventID) {
        return Result.success(chiefRefereeService.fetchRefereeInEvent(eventID));
    }

    @PostMapping("/chiefReferee/removeRefereeFromEvent")
    public Result<?> removeRefereeFromEvent(@RequestParam Integer eventID, @RequestParam Integer memberId) {
        return chiefRefereeService.removeRefereeFromEvent(eventID, memberId) ? Result.success()
                : Result.error("裁判移除失败");
    }

    @PostMapping("/chiefReferee/deleteJudge")
    public Result<?> deleteJudge(@RequestParam Integer id) {
        if (chiefRefereeService.fetchProfile(id) == null) {
            return Result.error("裁判不存在");
        } else {
            return chiefRefereeService.deleteReferee(id) ? Result.success() : Result.error("裁判删除失败");
        }
    }

    @PostMapping("/chiefReferee/addJudge")
    public Result<?> addJudge(@RequestBody Referee referee) {
        if (chiefRefereeService.fetchProfile(referee.getReferee_id()) != null) {
            return Result.error("裁判已存在");
        } else {
            return chiefRefereeService.addReferee(referee.getName(), referee.getPassword(),
                    referee.getPhone()) ? Result.success() : Result.error("裁判添加失败");
        }
    }

    @PostMapping("/chiefReferee/updateJudge")
    public Result<?> updateJudge(@RequestBody Referee referee) {
        if (chiefRefereeService.fetchProfile(referee.getReferee_id()) == null) {
            return Result.error("裁判不存在");
        } else {
            return chiefRefereeService.updateRefereeProfile(referee.getReferee_id(), referee.getName(),
                    referee.getPassword(),
                    referee.getPhone()) ? Result.success() : Result.error("裁判信息更新失败");
        }
    }

    @GetMapping("/referees/all")
    public Result<?> fetchReferees() {
        return Result.success(chiefRefereeService.fetchAllReferees());
    }

    @GetMapping("/chiefReferee/all")
    public Result<?> fetchChiefReferees() {
        return Result.success(chiefRefereeService.fetchAllChiefReferees());
    }

    @PostMapping("/getChiefRefereeInfo")
    public Result<?> fetchProfile(@RequestParam Integer id) {
        return Result.success(chiefRefereeService.fetchProfile(id));
    }

    @PostMapping("/activitys/byChiefReferee")
    public Result<?> fetchActivitiesByRefereeId(@RequestParam String token) {
        DecodedJWT jwt = JwtUtil.verifyToken(token);
        Integer id = jwt.getClaim("userId").asInt();
        return Result.success(chiefRefereeService.fetchActivitiesByRefereeId(id));
    }

    @PostMapping("/matches/schedule/{activityId}")
    public Result<?> scheduleMatches(@PathVariable Integer activityId) {
        // 先取出该活动中批准参加的队伍id，再取出该活动对应的裁判id
        MatchInfo matchList = new MatchInfo();
        matchList.setEvent_id(activityId);
        Event event = chiefRefereeService.fetchActivityDetail(activityId);
        matchList.setScoring_system(event.getScoring_system());
        matchList.setCount(event.getMatch_types().split(",").length);
        matchList.setTeams(chiefRefereeService.fetchAgreedTeams(activityId));
        matchList.setReferees(chiefRefereeService.fetchRefereeInEvent(activityId).stream().map(Referee::getReferee_id)
                .collect(Collectors.toList()));

        try {
            // 删除原有的对战信息
            chiefRefereeService.deleteAllMatches(activityId);

            // 创建新的对战信息
            return chiefRefereeService.scheduleAllMatches(matchList) ? Result.success() : Result.error("比赛排程失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("比赛排程失败");
        }
    }

    @PostMapping("/teams/approve/{activityId}/{teamId}")
    public Result<?> approveTeamApplication(@PathVariable Integer activityId, @PathVariable Integer teamId) {
        return chiefRefereeService.approveTeamApplication(activityId, teamId) ? Result.success()
                : Result.error("队伍申请批准失败");
    }

    @PostMapping("/teams/reject/{activityId}/{teamId}")
    public Result<?> rejectTeamApplication(@PathVariable Integer activityId, @PathVariable Integer teamId) {
        return chiefRefereeService.rejectTeamApplication(activityId, teamId) ? Result.success()
                : Result.error("队伍申请拒绝失败");
    }
}
