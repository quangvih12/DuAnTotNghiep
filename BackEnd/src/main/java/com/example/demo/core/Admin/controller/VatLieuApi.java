package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.service.AdVatLieuServcie;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.VatLieu;
import com.example.demo.util.DataUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/admin/vat-lieu")
@CrossOrigin(origins = {"*"})
public class VatLieuApi {

    @Autowired
    private AdVatLieuServcie adVatLieuServcie;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(adVatLieuServcie.getAll());
    }
}
