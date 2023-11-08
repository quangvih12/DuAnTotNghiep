package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.service.impl.ThongKe.AdminThongKeDoanhThuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public ResponseEntity<?> getAllBySanPham(@PathVariable Integer id, @RequestParam(required = false) String year) {
        return ResponseEntity.ok(adminThongKeDoanhThuService.getAllBySanPham(id, year));
    }

    @GetMapping("/thuong-hieu/{id}")
    public ResponseEntity<?> getAllByThuongHieu(@PathVariable Integer id,@RequestParam(required = false) String year) {
        return ResponseEntity.ok(adminThongKeDoanhThuService.getAllByThuongHieu(id,year));
    }

    @GetMapping("/loai/{id}")
    public ResponseEntity<?> getAllByLoai(@PathVariable Integer id,@RequestParam(required = false) String year) {
        return ResponseEntity.ok(adminThongKeDoanhThuService.getAllByLoai(id,year));
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<?> getAllByYear(@PathVariable String year) {
        return ResponseEntity.ok(adminThongKeDoanhThuService.getAllByYear(year));
    }

    @GetMapping("/month")
    public ResponseEntity<?> getAllByMonth(@RequestParam String startDate, @RequestParam String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse(startDate, formatter);
        LocalDateTime date2 = LocalDateTime.parse(endDate, formatter);

        return ResponseEntity.ok(adminThongKeDoanhThuService.getAllByMonth(date, date2));
    }

}
