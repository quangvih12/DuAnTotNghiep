package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.khachHang.model.request.KHUserRequest;
import com.example.demo.core.khachHang.model.response.KHUserResponse;
import com.example.demo.core.khachHang.repository.KHUserRepository;
import com.example.demo.core.khachHang.service.ThongTinService;
import com.example.demo.core.token.service.TokenService;
import com.example.demo.entity.User;
import com.example.demo.util.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThongTinServiceImpl implements ThongTinService {

    @Autowired
    private KHUserRepository repository;

    @Autowired
    TokenService tokenService;

    @Override
    public KHUserResponse getAll(String token) {
        Integer idKh;
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = repository.findAllByUserName(userName);
        idKh = user.getId();
        return repository.findUserById(idKh);
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
            User updatedUser = repository.save(u);
            return repository.findUserById(updatedUser.getId());
        }
        return null;
    }
}
