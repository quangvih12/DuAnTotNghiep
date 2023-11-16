package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.model.request.AdminUserRequest;
import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;
import com.example.demo.core.Admin.model.response.AdminUserResponse;
import com.example.demo.core.Admin.model.response.AdminUserVoucherResponse;
import com.example.demo.core.Admin.repository.AdDiaChiReponsitory;
import com.example.demo.core.Admin.repository.AdHoaDonReponsitory;
import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.core.Admin.service.AdUserService;
import com.example.demo.entity.DiaChi;
import com.example.demo.entity.User;
import com.example.demo.infrastructure.status.UserStatus;
import com.example.demo.util.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements AdUserService {
    @Autowired
    AdUserRepository userRepository;

    @Autowired
    private AdDiaChiReponsitory adDiaChiReponsitory;

    @Autowired
    private AdHoaDonReponsitory hoaDonReponsitory;

    public List<AdminUserResponse> getKhachHang() {
        return userRepository.findUserByRole("USER");
    }

    public List<AdminUserResponse> getNhanVien() {
        return userRepository.findUserByRole("NHANVIEN");
    }

    public List<AdminUserResponse> getAdmin() {
        return userRepository.findUserByRole("ADMIN");
    }

    @Override
    public List<DiaChi> getUserByDiaChi(Integer idUser) {
        return adDiaChiReponsitory.findDiaChiByIdUser(idUser);
    }

    @Override
    public AdminUserResponse add(AdminUserRequest request) {
        User user = request.dtoToEntity(new User());
        if (request.getDiaChi() == null) {
            User newUser = userRepository.save(user);
            newUser.setMa("US" + newUser.getId());
            User u = userRepository.save(newUser);
            return userRepository.findUserById(u.getId());
        } else {
            User newUser = userRepository.save(user);
            DiaChi diaChi = new DiaChi();
            diaChi.setUser(newUser);
            diaChi.setNgayTao(DatetimeUtil.getCurrentDate());
            diaChi.setDiaChi(request.getDiaChi());
            adDiaChiReponsitory.save(diaChi);
            newUser.setMa("US" + newUser.getId());
            User u = userRepository.save(newUser);
            return userRepository.findUserById(u.getId());
        }
    }

    @Override
    public AdminUserResponse delete(Integer id) {
        User user = userRepository.findById(id).get();
        if (user != null) {
            user.setTrangThai(UserStatus.XOA);
            User newUser = userRepository.save(user);
            return userRepository.findUserById(newUser.getId());
        }
        return null;
    }

    @Override
    public AdminUserResponse update(AdminUserRequest request, Integer id) {
        User u = userRepository.findById(id).get();
        if (u != null) {
            u.setEmail(request.getEmail());
            u.setNgaySua(DatetimeUtil.getCurrentDate());
            u.setTen(request.getTen());
            u.setNgaySinh(request.getNgaySinh());
            u.setSdt(request.getSdt());
            u.setGioiTinh(request.getGioiTinh());
            u.setImage(request.getImage());
            String newDiaChi = request.getDiaChi();
            if (newDiaChi != null) {
                // Cập nhật địa chỉ hiện tại của người dùng với địa chỉ mới
                u.getDiaChiList().forEach(diaChi -> diaChi.setDiaChi(newDiaChi));
            }
            // Lưu thông tin người dùng và trả về thông tin đã được cập nhật
            User updatedUser = userRepository.save(u);
            return userRepository.findUserById(updatedUser.getId());
        }
        return null;
    }

    @Override
    public AdminUserResponse VoHieuHoaUser(Integer id) {
        User user = userRepository.findById(id).get();
        if (user != null) {
            user.setTrangThai(UserStatus.TAI_KHOAN_BI_KHOA);
            User newUser = userRepository.save(user);
            return userRepository.findUserById(newUser.getId());
        }
        return null;
    }

    @Override
    public AdminHoaDonResponse getHoaDonByIdUser(Integer id) {
        return hoaDonReponsitory.getByIdUser(id);
    }

    @Override
    public List<AdminUserResponse> getAllUserByRole(String role) {
        return userRepository.findUserByRole(role);
    }

    @Override
    public List<AdminUserResponse> getAllUser() {
        return userRepository.getAllUser();
    }

    @Override
    public List<AdminUserVoucherResponse> getAllUserByTongTien(String cbbValue) {
        return userRepository.getAllUserByTongTien(cbbValue);
    }
}
