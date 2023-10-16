package com.example.demo.core.Admin.service;

import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;
import com.example.demo.core.Admin.model.response.AdminUserResponse;
import com.example.demo.entity.DiaChi;
import com.example.demo.entity.User;

import java.util.List;

public interface AdUserService {

    List<AdminUserResponse> getAll(String Role);

    List<AdminUserResponse> getKhachHang();

    List<AdminUserResponse> getNhanVien();

    List<AdminUserResponse> getAdmin();

    List<DiaChi> getUserByDiaChi(Integer idUser);

    User add(User user);

    User delete(Integer id);

    User update(User user, Integer id);

    User VoHieuHoaUser(Integer id);

    AdminHoaDonResponse getHoaDonByIdUser(Integer id);

}
