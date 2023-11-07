package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.khachHang.model.request.KHUserRequest;
import com.example.demo.core.khachHang.model.response.KHUserResponse;
import com.example.demo.core.khachHang.repository.KHUserRepository;
import com.example.demo.core.khachHang.service.KHUserService;
import com.example.demo.entity.DiaChi;
import com.example.demo.entity.User;
import com.example.demo.util.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KHUserServiceImpl implements KHUserService {

    @Autowired
    private KHUserRepository repository;

    @Override
    public KHUserResponse get(Integer id) {
        return repository.findByIdUser(id);
    }

    @Override
    public KHUserResponse update(KHUserRequest request, Integer id) {
        User u = repository.findById(id).get();
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
            User updatedUser = repository.save(u);
            return repository.findUserById(updatedUser.getId());
        }
        return null;
    }

}
