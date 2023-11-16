package com.example.demo.core.khachHang.controller;

import com.example.demo.core.khachHang.service.ChiTietSPService;
import com.example.demo.core.khachHang.service.TrangChuService;
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
@RequestMapping("/api/khach-hang/trang-chu")
@CrossOrigin(origins = {"*"})
public class TrangChuApi {

    @Autowired
    private TrangChuService trangChuService;

    @GetMapping("/get-all-by-ten-loai")
    public ResponseEntity<?> getAllByTenLoai(@RequestParam("tenLoai") String tenLoai) {
        return ResponseEntity.ok(trangChuService.getAllByTenLoai(tenLoai));
    }

    //sp mới
    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(trangChuService.getAll());
    }

    //sp mới
    @GetMapping("/get-san-pham-ban-chay")
    public ResponseEntity<?> getSPBanChay() {
        return ResponseEntity.ok(trangChuService.getSPBanChay());
    }
}
