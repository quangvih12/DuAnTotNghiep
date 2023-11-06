package com.example.demo.core.khachHang.controller;


import com.example.demo.core.khachHang.repository.KHUserRepository;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/khach-hang/user")
@CrossOrigin(origins = {"*"})
public class KHUserController {

    @Autowired
    KHUserRepository khUserRepo;

    @GetMapping()
    public User getUserByUsername(@RequestParam("username") String username) {
        User user = khUserRepo.findAllByUserName(username);
        return user;
    }

}
