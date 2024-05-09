package com.example.backend_badminton.controller;

import com.example.backend_badminton.pojo.Event;
import com.example.backend_badminton.pojo.Result;
import com.example.backend_badminton.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @DeleteMapping("/delete/{eventId}")
    public Result<?> deleteEvent(@PathVariable Integer eventId) {
        return eventService.deleteEvent(eventId) ? Result.success() : Result.error("删除活动失败");
    }

    // 获取所有活动列表
    @GetMapping("/all")
    public Result<?> fetchActivities() {
        return Result.success(eventService.fetchAllEvents());
    }

    // 获取单个活动详细信息
    @GetMapping("/detail/{eventId}")
    public Result<?> fetchActivityDetail(@PathVariable Integer eventId) {
        return Result.success(eventService.fetchEventDetails(eventId));
    }

    // 领队，提交报名表单，申请加入该活动
    @PostMapping("/apply")
    public Result<?> applyForEvent(@RequestParam Integer teamId, @RequestParam Integer eventId) {
        try {
            if (eventService.isTeamApplied(teamId, eventId)) {
                return Result.error("已经申请过该活动");
            }
            return eventService.applyForEvent(teamId, eventId) ? Result.success() : Result.error("申请失败");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    // 更新活动信息
    @PostMapping("/update")
    public Result<?> updateActivity(@RequestBody Event event) {
        System.out.println(event.getEvent_id());
        if (event.getEvent_id() == null) {
            return eventService.insertEvent(event) ? Result.success() : Result.error("添加活动失败");
        }
        // 这里传递的event需要全属性
        return eventService.updateEvent(event) ? Result.success() : Result.error("更新活动失败");
    }

    // 获取活动下的所有比赛列表
    @GetMapping("/matches/{eventId}")
    public Result<?> fetchMatches(@PathVariable Integer eventId) {
        return Result.success(eventService.fetchMatchesForEvent(eventId));
    }
}