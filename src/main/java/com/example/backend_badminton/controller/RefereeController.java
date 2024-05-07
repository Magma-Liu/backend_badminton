package com.example.backend_badminton.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend_badminton.pojo.Result;
import com.example.backend_badminton.service.RefereeService;
import com.example.backend_badminton.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/referee")
public class RefereeController {

    @Autowired
    private RefereeService refereeService;

    @PostMapping("/scoreUpdate")
    public Result<?> updateScore(@RequestParam Integer match_id, @RequestParam Integer index,
            @RequestParam String score) {
        if (index < 1 || index > 7) {
            return Result.error("比分编号不合法");
        }
        return refereeService.updateScore(match_id, index, score) ? Result.success() : Result.error("更新分数失败");
    }

    @PostMapping("/activitys")
    public Result<?> fetchActivitiesByRefereeToken(@RequestParam String token) {
        DecodedJWT jwt = JwtUtil.verifyToken(token);
        Integer id = jwt.getClaim("userId").asInt();
        return Result.success(refereeService.fetchActivitiesByRefereeId(id));
    }

    @PostMapping("/info")
    public Result<?> fetchReferee(@RequestParam Integer id) {
        return Result.success(refereeService.fetchReferee(id));
    }
}
