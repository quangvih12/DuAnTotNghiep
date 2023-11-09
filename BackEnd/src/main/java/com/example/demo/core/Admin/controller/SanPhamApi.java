package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.model.request.AdminSanPhamRepuest2;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTiet2Response;
import com.example.demo.core.Admin.model.response.AdminSanPhamResponse;
import com.example.demo.core.Admin.service.AdSanPhamService.AdSanPhamService;
import com.example.demo.entity.Image;
import com.microsoft.azure.storage.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/san-pham")
public class SanPhamApi {
    @Autowired
    private AdSanPhamService sanPhamService;

    // getAll loai
    @GetMapping()
    public ResponseEntity<?> getAll() {
        List<AdminSanPhamResponse> page = sanPhamService.getAll();
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{idSP}")
    public ResponseEntity<?> getbySanPhamCT(@PathVariable Integer idSP) {
        List<AdminSanPhamChiTiet2Response> page = sanPhamService.findBySanPhamCT(idSP);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{idSP}/images")
    public ResponseEntity<?> getbyImage(@PathVariable Integer idSP) {
        List<Image> page = sanPhamService.getProductImages(idSP);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/loc")
    public ResponseEntity<?> loc(@RequestParam String comboBoxValue) {
        List<AdminSanPhamResponse> lisst = sanPhamService.loc(comboBoxValue);
        return ResponseEntity.ok(lisst);
    }

    @GetMapping("/check/{ten}")
    public ResponseEntity<?> check(@PathVariable String ten) {
        return ResponseEntity.ok(sanPhamService.findBySanPhamTen(ten));
    }

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody AdminSanPhamRepuest2 request) throws URISyntaxException, StorageException, InvalidKeyException, IOException {
        AdminSanPhamResponse save = sanPhamService.save(request);
        return ResponseEntity.ok(save);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        AdminSanPhamResponse sp = sanPhamService.delete(id);
        return ResponseEntity.ok(sp);
    }

    @PutMapping("/khoi-phuc/{id}")
    public ResponseEntity<?> khoiPhuc(@PathVariable Integer id) {
        AdminSanPhamResponse sp = sanPhamService.khoiPhuc(id);
        return ResponseEntity.ok(sp);
    }
}
