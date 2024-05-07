package com.example.backend_badminton.service;

import com.example.backend_badminton.mapper.AdminMapper;
import com.example.backend_badminton.pojo.*;
import com.example.backend_badminton.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public List<Admin> getAllAdmins() {
        return adminMapper.selectAllAdmins();
    }

    public boolean updateProfile(Integer id, String name, String password, String phone) {
        return adminMapper.updateAdmin(id, name, password, phone) > 0;
    }

    public Admin fetchAdmin(int id) {
        return adminMapper.selectAdminByID(id);
    }

    public boolean addAdmin(Admin admin) {
        return adminMapper.insertAdmin(admin) > 0;
    }

    public List<ChiefReferee> getAllJudges() {
        return adminMapper.selectAllJudges();
    }

    public boolean addJudge(ChiefReferee judge) {
        return adminMapper.insertJudge(judge) > 0;
    }

    public boolean updateJudge(ChiefReferee judge) {
        return adminMapper.updateJudge(judge) > 0;
    }

    public boolean deleteJudge(int id) {
        return adminMapper.deleteJudge(id) > 0;
    }

    public List<Venue> getAllVenues() {
        return adminMapper.selectAllVenues();
    }

    public boolean addVenue(Venue venue) {
        return adminMapper.insertVenue(venue) > 0;
    }

    public boolean updateVenue(Venue venue) {
        return adminMapper.updateVenue(venue) > 0;
    }

    public boolean deleteVenue(int id) {
        return adminMapper.deleteVenue(id) > 0;
    }

    public List<LeaderApplication> getAllLeaderApplications() {
        return adminMapper.fetchAllApplications();
    }

    @Transactional
    public boolean approveLeaderApplication(int contestant_id, int admin_id) {
        return adminMapper.updateApplicationStatus(
                contestant_id, admin_id, true, TimeUtil.getCurrentFormattedDate()) == 1
                && adminMapper.updateLeaderStatus(contestant_id, true) == 1;
    }

    @Transactional
    public boolean rejectLeaderApplication(int contestant_id, int admin_id) {
        return adminMapper.updateApplicationStatus(contestant_id, admin_id, false,
                TimeUtil.getCurrentFormattedDate()) == 1
                && adminMapper.updateLeaderStatus(contestant_id, false) == 1;
    }

    public boolean isChiefRefereeExistByPhone(String phone) {
        return adminMapper.selectChiefRefereeByPhone(phone) != null;
    }

    public boolean isChiefRefereeExistByID(Integer id) {
        return adminMapper.selectChiefRefereeByID(id) != null;
    }

    public boolean isVenueExistByName(String name) {
        return adminMapper.selectVenueByName(name) != null;
    }

    public boolean isVenueExistByID(Integer id) {
        return adminMapper.selectVenueByID(id) != null;
    }
}