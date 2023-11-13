package com.example.demo.core.khachHang.controller;

import com.example.demo.core.khachHang.service.KHHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/khach-hang/hoa-don")
public class KHHoaDonApi {

    @Autowired
    private KHHoaDonService hdService;

    @GetMapping("/find-all")
    public ResponseEntity<?> getAll(@RequestParam("token") String token) {
        return ResponseEntity.ok(hdService.getAll(token));
    }

    @GetMapping("/find-all-by-trang-thai")
    public ResponseEntity<?> getAllByTrangThai(@RequestParam("token") String token, @RequestParam("trangThai") Integer trangThai) {
        return ResponseEntity.ok(hdService.getHoaDonTrangThai(token, trangThai));
    }

    @PutMapping("/huy-hoa-don/{id}")
    public ResponseEntity<?> huyHoaDon(@PathVariable Integer id, @RequestParam("lyDo") String lyDo) {
        return ResponseEntity.ok(hdService.huyHoaDonChoXacNhan(id, lyDo));
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(hdService.findById(id
        ));
    }

}