package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.service.InterfaceHoaDon.AdDetailHoaDonChiTietService;
import com.example.demo.core.Admin.service.InterfaceHoaDon.AdHoaDonChoXacNhanService;
import com.example.demo.core.Admin.service.InterfaceHoaDon.AdHoaDonDangGiaoService;
import com.example.demo.core.Admin.service.InterfaceHoaDon.AdHoaDonDoiTraService;
import com.example.demo.core.Admin.service.InterfaceHoaDon.AdminTatCaHoaDonService;
import com.example.demo.core.Admin.service.impl.AdThongBaoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private AdHoaDonDoiTraService doiTraService;

    @Autowired
    private AdThongBaoServiceImpl adThongBaoService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(adminTatCaHoaDonService.getAll());
    }

    @GetMapping("/hoaDonTrangThai/{trangThai}")
    public ResponseEntity<?> getHoaDonHuy(@PathVariable Integer trangThai) {
        return ResponseEntity.ok(adminTatCaHoaDonService.getHoaDonTrangThai(trangThai));
    }

    @GetMapping("/search-date")
    public ResponseEntity<?> getHoaDonHuy(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam String comboBoxValue) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse(startDate, formatter);
        LocalDateTime date2 = LocalDateTime.parse(endDate, formatter);
//        return null;

        return ResponseEntity.ok(adminTatCaHoaDonService.searchDate(date, date2, comboBoxValue));
    }

    @GetMapping("/search-date-by-trang-thai")
    public ResponseEntity<?> getHDByTrangThai(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
                                              @RequestParam String comboBoxValue, @RequestParam("trangThai") Integer trangThai) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse(startDate, formatter);
        LocalDateTime date2 = LocalDateTime.parse(endDate, formatter);
//        return null;

        return ResponseEntity.ok(adminTatCaHoaDonService.searchDateByTrangThai(date, date2, comboBoxValue, trangThai));
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
        adThongBaoService.huyHoaDon(id);
        return ResponseEntity.ok(adHoaDonChoXacNhanService.huyHoaDonChoXacNhan(id, lyDo));
    }

    // từ chờ xác nhận -> đang chuẩn bị
    @PutMapping("/XacNhan/{id}")
    public ResponseEntity<?> xacNhanHoaDon(@PathVariable Integer id) {
        return ResponseEntity.ok(adHoaDonChoXacNhanService.xacNhanHoaDon(id));
    }

    // chuẩn bị xong -> đang giao
    @PutMapping("/XacNhanGiaoHang/{id}")
    public ResponseEntity<?> XacNhanGiaoHang(@PathVariable Integer id, @RequestParam("ngayShip") String ngayShip) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse(ngayShip, formatter);
        adThongBaoService.xacNhanHoaDon(id);
        return ResponseEntity.ok(adminTatCaHoaDonService.giaoHoaDonChoVanChuyen(id, date));
    }

    // Trả hàng -> Xác nhận trả
    @PutMapping("/xac-nhan-doi-tra/{id}")
    public ResponseEntity<?> XacNhanDoiTra(@PathVariable Integer id) {
        adThongBaoService.xacNhanDoiTra(id);
        return ResponseEntity.ok(doiTraService.xacNhanHoaDonTraHang(id));
    }

    @PutMapping("/huy-doi-tra/{id}")
    public ResponseEntity<?> huyHoaDonDoiTra(@PathVariable Integer id, @RequestParam("lyDo") String lyDo) {
        adThongBaoService.HuyDoiTra(id);
        return ResponseEntity.ok(doiTraService.huyHoaDonTrahang(id, lyDo));
    }

    //Xác nhận trả hàng => hoàn thành trả hang
    @PutMapping("/hoan-thanh-doi-tra/{id}")
    public ResponseEntity<?> hoanThanhDoiTra(@PathVariable Integer id) {
        adThongBaoService.hoanThanhDoiTra(id);
        return ResponseEntity.ok(doiTraService.congSoLuongSP(id));
    }

    // từ đang giao -> hoàn thành
    @PutMapping("/hoan-thanh/{id}")
    public ResponseEntity<?> hoanThanh(@PathVariable Integer id) {
        adThongBaoService.hoanThanh(id);
        return ResponseEntity.ok(adHoaDonDangGiaoService.xacNhanHoaDon(id));
    }
}
