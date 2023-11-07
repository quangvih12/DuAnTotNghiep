package com.example.demo.core.khachHang.controller;

import com.example.demo.core.Admin.model.request.AdminDiaChiRequest;
import com.example.demo.core.khachHang.service.DiaChiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/khach-hang/dia-chi")
@CrossOrigin(origins = {"*"})
public class DiaChiApi {

    @Autowired
    private DiaChiService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> getDiaChi(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getUserByDiaChi(id));
    }

    @PutMapping("{id}/delete")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody AdminDiaChiRequest request) {
        HashMap<String, Object> map = service.addDiaChi(request);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody AdminDiaChiRequest request) {
        HashMap<String, Object> map = service.updateDiaChi(request, id);
        return ResponseEntity.ok(map);
    }
}
