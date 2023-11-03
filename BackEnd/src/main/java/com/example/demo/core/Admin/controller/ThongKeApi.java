package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.service.impl.ThongKe.AdminThongKeDoanhThuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin/Thong-ke")
public class ThongKeApi {
    @Autowired
    private AdminThongKeDoanhThuServiceImpl adminThongKeDoanhThuService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(adminThongKeDoanhThuService.getAll());
    }

    @GetMapping("/san-pham/{id}")
    public ResponseEntity<?> getAllBySanPham(@PathVariable Integer id) {
        return ResponseEntity.ok(adminThongKeDoanhThuService.getAllBySanPham(id));
    }

    @GetMapping("/thuong-hieu/{id}")
    public ResponseEntity<?> getAllByThuongHieu(@PathVariable Integer id) {
        return ResponseEntity.ok(adminThongKeDoanhThuService.getAllByThuongHieu(id));
    }

    @GetMapping("/loai/{id}")
    public ResponseEntity<?> getAllByLoai(@PathVariable Integer id) {
        return ResponseEntity.ok(adminThongKeDoanhThuService.getAllByLoai(id));
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<?> getAllByYear(@PathVariable String year) {
        return ResponseEntity.ok(adminThongKeDoanhThuService.getAllByYear(year));
    }

    @GetMapping("/month")
    public ResponseEntity<?> getAllByMonth(@RequestParam Integer startMonth, @RequestParam Integer endMonth) {
        return ResponseEntity.ok(adminThongKeDoanhThuService.getAllByMonth(startMonth,endMonth));
    }

}
