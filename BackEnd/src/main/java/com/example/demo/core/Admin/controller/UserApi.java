package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.service.AdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin/user")
public class UserApi {

    @Autowired
    private AdUserService adUserService;

    @GetMapping("/{role}")
    public ResponseEntity<?> getHoaDonHuy(@PathVariable String role) {
        return ResponseEntity.ok(adUserService.getAll(role));
    }

}
