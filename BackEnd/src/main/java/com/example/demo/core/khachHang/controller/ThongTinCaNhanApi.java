package com.example.demo.core.khachHang.controller;

import com.example.demo.core.Admin.model.request.AdminUserRequest;
import com.example.demo.core.khachHang.model.request.KHUserRequest;
import com.example.demo.core.khachHang.service.KHUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/khach-hang/thong-tin-ca-nhan")
@CrossOrigin(origins = {"*"})
public class ThongTinCaNhanApi {

    @Autowired
    private KHUserService service;

    @GetMapping("/getOne/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody KHUserRequest request, @PathVariable Integer id) {
        return ResponseEntity.ok(service.update(request, id));
    }
}
