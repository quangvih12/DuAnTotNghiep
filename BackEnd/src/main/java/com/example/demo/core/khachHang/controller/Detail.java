package com.example.demo.core.khachHang.controller;

import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
import com.example.demo.core.khachHang.service.KHDetailService.DetailSizeService;
import com.example.demo.core.khachHang.service.KHDetailService.ImageServie;
import com.example.demo.core.Admin.service.impl.SizeServiceImpl;
import com.example.demo.core.khachHang.service.KHDetailService.DetaiService;
import com.example.demo.core.khachHang.service.KHDetailService.DetailMauSacService;
import com.example.demo.entity.SanPhamChiTiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/khach-hang/detail")
@CrossOrigin(origins = {"*"})
public class Detail {

    @Autowired
    private DetaiService detaiService;

    @Autowired
    private DetailMauSacService mauSacService;

    @Autowired
    private DetailSizeService sizeService;

    @Autowired
    private ImageServie servie;

    @GetMapping("/getOne/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        AdminSanPhamChiTietResponse sanPhamChiTiet = detaiService.get(id);
        return ResponseEntity.ok(sanPhamChiTiet);
    }

    @GetMapping("/findByMauSac/{id}")
    public ResponseEntity<?> findByMauSac(@PathVariable Integer id){
        return ResponseEntity.ok(mauSacService.findByIdCTSP(id));
    }

    @GetMapping("/findBySize/{id}")
    public ResponseEntity<?> findBySize(@PathVariable Integer id){
        return ResponseEntity.ok(sizeService.findByIdCTSP(id));
    }

    @GetMapping("/findByImage/{id}")
    public ResponseEntity<?> findByImage(@PathVariable Integer id){
        return ResponseEntity.ok(servie.findByIdCTSP(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getList() {
        List<SanPhamChiTiet> lisst = detaiService.getAlls();
        return ResponseEntity.ok(lisst);
    }
}
