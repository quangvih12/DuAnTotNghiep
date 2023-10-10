package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.model.request.AdminKhuyenMaiRequest;
import com.example.demo.core.Admin.model.response.AdminKhuyenMaiResponse;
import com.example.demo.core.Admin.service.AdKhuyenMaiService;
import com.example.demo.entity.KhuyenMai;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/khuyenMai/")
public class KhuyenMaiApi {

    @Autowired
    AdKhuyenMaiService khuyenMaiService;

    @GetMapping("/getAll")
    public List<AdminKhuyenMaiResponse> getAllKhuyenMai(){
        return khuyenMaiService.getAllKhuyenMai();
    }

    @GetMapping("/getById/{id}")
    public KhuyenMai getKhuyenmaiByID(@PathVariable("id") Integer id){
        return khuyenMaiService.getKhuyenMaiById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody AdminKhuyenMaiRequest request) {
        HashMap<String, Object> map = khuyenMaiService.add(request);
        return ResponseEntity.ok(map);
    }

    // sửa
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody AdminKhuyenMaiRequest request) {
        HashMap<String, Object> map = khuyenMaiService.update(request, id);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, @RequestBody AdminKhuyenMaiRequest request) {
        HashMap<String, Object> map = khuyenMaiService.delete(request, id);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/updateSLKhuyenMai/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestParam("sl") Integer sl) throws Exception {
        HashMap<String, Object> map = khuyenMaiService.updateSLKhuyenMai(id,sl);
        return ResponseEntity.ok(map);
    }

}
