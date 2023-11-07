package com.example.demo.core.khachHang.controller;

import com.example.demo.core.Admin.model.response.AdminHoaDonChitietResponse;
import com.example.demo.core.khachHang.service.KHHoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/khach-hang/hoa-don-chi-tiet")
public class KHHoaDonChiTietApi {

    @Autowired
    private KHHoaDonChiTietService hdctService;

    @GetMapping("/find-by-id-hd/{id}")
    public ResponseEntity<?> findByIdHd(@PathVariable Integer id) {
        return ResponseEntity.ok(hdctService.findHDCTByIdHoaDon(id));
    }

}
