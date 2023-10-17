package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.service.InterfaceHoaDon.AdDetailHoaDonChiTietService;
import com.example.demo.core.Admin.service.InterfaceHoaDon.AdHoaDonChoXacNhanService;
import com.example.demo.core.Admin.service.InterfaceHoaDon.AdHoaDonDangGiaoService;
import com.example.demo.core.Admin.service.InterfaceHoaDon.AdminTatCaHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin/hoaDon")
public class HoaDonApi {

    @Autowired
    private AdminTatCaHoaDonService adminTatCaHoaDonService;

    @Autowired
    private AdHoaDonChoXacNhanService adHoaDonChoXacNhanService;

    @Autowired
    private AdHoaDonDangGiaoService adHoaDonDangGiaoService;

    @Autowired
    private AdDetailHoaDonChiTietService adDetailHoaDonChiTietService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(adminTatCaHoaDonService.getAll());
    }

    @GetMapping("/hoaDonTrangThai/{trangThai}")
    public ResponseEntity<?> getHoaDonHuy(@PathVariable Integer trangThai) {
        return ResponseEntity.ok(adminTatCaHoaDonService.getHoaDonTrangThai(trangThai));
    }

    @GetMapping("/search-date")
    public ResponseEntity<?> getHoaDonHuy(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date date = format.parse(startDate);
        Date date2 = format.parse(endDate);
//        return null;
        return ResponseEntity.ok(adminTatCaHoaDonService.searchDate(date, date2));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> detailHoaDonCT(@PathVariable Integer id) {
        return ResponseEntity.ok(adDetailHoaDonChiTietService.getHoaDonChiTietByIdHD(id));
    }

    @GetMapping("/hoaDonHuy")
    public ResponseEntity<?> getHoaDonHuy() {
        return ResponseEntity.ok(adminTatCaHoaDonService.getHoaDonHuy());
    }

    @GetMapping("/hoaDonXacNhan")
    public ResponseEntity<?> getHoaDonXacNhan() {
        return ResponseEntity.ok(adHoaDonChoXacNhanService.getHoaDonChoXacNhan());
    }

    @GetMapping("/hoaDonHoanThanh")
    public ResponseEntity<?> getHoaDonHoanThanh() {
        return ResponseEntity.ok(adminTatCaHoaDonService.getHoaDonHoanThanh());
    }

    @GetMapping("/hoaDonDangGiao")
    public ResponseEntity<?> getHoaDonDangGiao() {
        return ResponseEntity.ok(adminTatCaHoaDonService.getHoaDonDangGiao());
    }

    @GetMapping("/hoaDonYeuCauDoiTra")
    public ResponseEntity<?> getHoaDonYeuCauDoiTra() {
        return ResponseEntity.ok(adminTatCaHoaDonService.getHoaDonYeuCauDoiTra());
    }

    @GetMapping("/hoaDonXacNhanDoiTra")
    public ResponseEntity<?> getHoaDonXacNhanDoiTra() {
        return ResponseEntity.ok(adminTatCaHoaDonService.getHoaDonXacNhanDoiTra());
    }

    @GetMapping("/hoaDonDangChuanBiHang")
    public ResponseEntity<?> getHoaDonDangChuanBiHang() {
        return ResponseEntity.ok(adminTatCaHoaDonService.getHoaDonDangChuanBiHang());
    }

    @PutMapping("/huyXacNhan/{id}")
    public ResponseEntity<?> huyHoaDon(@PathVariable Integer id, @RequestParam("lyDo") String lyDo) {
        return ResponseEntity.ok(adHoaDonChoXacNhanService.huyHoaDonChoXacNhan(id, lyDo));
    }

    // từ chờ xác nhận -> đang chuẩn bị
    @PutMapping("/XacNhan/{id}")
    public ResponseEntity<?> xacNhanHoaDon(@PathVariable Integer id) {
        return ResponseEntity.ok(adHoaDonChoXacNhanService.xacNhanHoaDon(id));
    }

    // chuẩn bị xong -> đang giao
    @PutMapping("/XacNhanGiaoHang/{id}")
    public ResponseEntity<?> XacNhanGiaoHang(@PathVariable Integer id) {
        return ResponseEntity.ok(adminTatCaHoaDonService.giaoHoaDonChoVanChuyen(id));
    }

    // từ đang giao -> hoàn thành
    @PutMapping("/hoan-thanh/{id}")
    public ResponseEntity<?> hoanThanh(@PathVariable Integer id) {
        return ResponseEntity.ok(adHoaDonDangGiaoService.xacNhanHoaDon(id));
    }
}
