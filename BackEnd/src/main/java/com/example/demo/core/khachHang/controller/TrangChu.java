package com.example.demo.core.khachHang.controller;

import com.example.demo.core.khachHang.service.ChiTietSPService;
import com.example.demo.entity.ChucVu;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.util.DataUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/trang-chu")
@CrossOrigin(origins = {"*"})
public class TrangChu {

    @Autowired
    private ChiTietSPService service;

    @GetMapping("/loai")
    public ResponseEntity<?> getAllByTenLoai(@RequestParam("tenLoai") String tenLoai) {
        HashMap<String, Object> map = DataUltil.setData("ok", service.findAllByTenLoai(tenLoai).getContent());
        return ResponseEntity.ok(map);
    }

    @GetMapping("/ngay-tao")
    public ResponseEntity<?> getAllByNgayTao() {
        HashMap<String, Object> map = DataUltil.setData("ok", service.findAllByNgayTao().getContent());
        return ResponseEntity.ok(map);
    }

}
