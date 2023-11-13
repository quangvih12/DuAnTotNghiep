package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.core.khachHang.repository.KHUserRepository;
import com.example.demo.core.khachHang.service.KHUserService;
import com.example.demo.core.token.service.TokenService;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KHUserServiceImpl implements KHUserService {

    @Autowired
    private KHUserRepository khUserRepo;

    @Autowired
    TokenService tokenService;



    @Override
    public User dangNhapGoogle(String email, String ten) {
        User user = khUserRepo.findUserByEmail(email);
        if (user != null) {
            return user;
        } else {
            User addUser = User.builder().email(email).userName(email).ten(ten).build();
            addUser.setRole(Role.USER);
            addUser.setPassword("$2a$12$Xcp214DEIsQr61KrINMt5egl.2Tqfcjwhu32Y9Y5TCEFzH5yiEOlS");
            User user1 = khUserRepo.save(addUser);
            return user1;
        }
    }

    @Override
    public User findByToken(String token) {
        System.out.println("token"+token);
        Integer idKh;
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        System.out.println("aaaaaaaaa "+userName);
        User user = khUserRepo.findAllByUserName(userName);
        return user;
    }

}
