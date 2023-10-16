package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;
import com.example.demo.core.Admin.model.response.AdminUserResponse;
import com.example.demo.core.Admin.repository.AdDiaChiReponsitory;
import com.example.demo.core.Admin.repository.AdUserReponsitory;
import com.example.demo.core.Admin.service.AdUserService;
import com.example.demo.entity.DiaChi;
import com.example.demo.entity.User;
import com.example.demo.infrastructure.status.UserStatus;
import com.example.demo.util.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdUserService {

    @Autowired
    private AdUserReponsitory reponsitory;

    @Autowired
    private AdDiaChiReponsitory adDiaChiReponsitory;

    @Override
    public List<AdminUserResponse> getAll(String role) {
        return reponsitory.getAllByRole(role);
    }

    public List<AdminUserResponse> getKhachHang() {
        return reponsitory.getAllByRole("USER");
    }

    public List<AdminUserResponse> getNhanVien() {
        return reponsitory.getAllByRole("NHANVIEN");
    }

    public List<AdminUserResponse> getAdmin() {
        return reponsitory.getAllByRole("ADMIN");
    }

    @Override
    public List<DiaChi> getUserByDiaChi(Integer idUser) {
        return null;
    }

    @Override
    public User add(User user) {
        return reponsitory.save(user);
    }

    @Override
    public User delete(Integer id) {
        User user = reponsitory.findById(id).get();
        if (user != null){
            user.setTrangThai(UserStatus.XOA);
            reponsitory.save(user);
        }
        return null;
    }

    @Override
    public User update(User user, Integer id) {
        User u = reponsitory.findById(id).get();
        if (u != null){
            u.setMa(user.getMa());
            u.setEmail(user.getEmail());
            u.setNgaySua(DatetimeUtil.getCurrentDate());
            u.setTrangThai(UserStatus.XOA);
            reponsitory.save(user);
        }
        return null;
    }

    @Override
    public User VoHieuHoaUser(Integer id) {
        return null;
    }

    @Override
    public AdminHoaDonResponse getHoaDonByIdUser(Integer id) {
        return null;
    }
}
