package com.example.backend_badminton.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend_badminton.pojo.*;
import com.example.backend_badminton.service.AdminService;
import com.example.backend_badminton.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/fetchAdmins")
    public Result<?> fetchAdmins() {
        try {
            return Result.success(adminService.getAllAdmins());
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    // 获取管理员信息
    @PostMapping("/info")
    public Result<?> fetchAdmin(@RequestParam Integer id) {
        return Result.success(adminService.fetchAdmin(id));
    }

    // 添加管理员
    @PostMapping("/add")
    public Result<?> addAdmin(@RequestBody Admin admin) {
        return adminService.addAdmin(admin) ? Result.success() : Result.error("添加管理员失败");
    }

    // 获取领队申请信息
    @GetMapping("/fetchApplications")
    public Result<?> fetchLeaderApplications() {
        try {
            return Result.success(adminService.getAllLeaderApplications());
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    // 通过领队申请
    @PostMapping("/approveApplication")
    public Result<?> approveLeaderApplication(@RequestParam int contestant_id, @RequestParam String admin_token) {
        DecodedJWT jwt = JwtUtil.verifyToken(admin_token);
        Integer admin_id = jwt.getClaim("userId").asInt();
        return adminService.approveLeaderApplication(contestant_id, admin_id) ? Result.success()
                : Result.error("通过领队申请失败");
    }

    // 拒绝领队申请
    @PostMapping("/rejectApplication")
    public Result<?> rejectLeaderApplication(@RequestParam int contestant_id, @RequestParam String admin_token) {
        DecodedJWT jwt = JwtUtil.verifyToken(admin_token);
        Integer admin_id = jwt.getClaim("userId").asInt();
        return adminService.rejectLeaderApplication(contestant_id, admin_id) ? Result.success()
                : Result.error("拒绝领队申请失败");
    }

    // 获取所有裁判长信息
    @GetMapping("/fetchJudges")
    public Result<?> fetchJudges() {
        try {
            return Result.success(adminService.getAllJudges());
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    // 添加裁判长
    @PostMapping("/addJudge")
    public Result<?> addJudge(@RequestBody ChiefReferee judge) {
        // 判断账户是否存在
        if (adminService.isChiefRefereeExistByPhone(judge.getPhone())) {
            return Result.error("该手机账户已存在");
        } else
            return adminService.addJudge(judge) ? Result.success() : Result.error("添加裁判长失败");
    }

    // 更新裁判长信息
    @PostMapping("/updateJudge")
    public Result<?> updateJudge(@RequestBody ChiefReferee judge) {
        if (adminService.isChiefRefereeExistByID(judge.getReferee_id())) {
            return adminService.updateJudge(judge) ? Result.success() : Result.error("更新裁判长信息失败");
        } else
            return Result.error("该账户不存在");
    }

    // 删除裁判长
    @PostMapping("/deleteJudge")
    public Result<?> deleteJudge(@RequestParam int id) {
        return adminService.deleteJudge(id) ? Result.success() : Result.error("删除裁判长失败");
    }

    // 获取所有场地信息
    @GetMapping("/fetchVenues")
    public Result<?> fetchVenues() {
        try {
            return Result.success(adminService.getAllVenues());
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    // 添加场地
    @PostMapping("/addVenue")
    public Result<?> addVenue(@RequestBody Venue venue) {
        // 判断是否有该场地
        if (adminService.isVenueExistByName(venue.getName())) {
            return Result.error("该场地已存在");
        } else
            return adminService.addVenue(venue) ? Result.success() : Result.error("添加场地失败");
    }

    // 更新场地信息
    @PostMapping("/updateVenue")
    public Result<?> updateVenue(@RequestBody Venue venue) {
        // 判断是否有该场地
        if (!adminService.isVenueExistByID(venue.getVenue_id())) {
            return Result.error("该场地不存在");
        } else
            return adminService.updateVenue(venue) ? Result.success() : Result.error("更新场地失败");
    }

    // 删除场地
    @PostMapping("/deleteVenue")
    public Result<?> deleteVenue(@RequestParam int id) {
        return adminService.deleteVenue(id) ? Result.success() : Result.error("删除场地失败");
    }
}
