package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.service.AdImageServie;
import com.example.demo.core.Admin.service.impl.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin/image")
public class ImageApi {
    @Autowired
    private AdImageServie servie;

    @GetMapping("/find-by-id-ctsp/{id}")
    public ResponseEntity<?> findByIdCTSP(@PathVariable Integer id){
        return ResponseEntity.ok(servie.findByIdCTSP(id));
    }

}
