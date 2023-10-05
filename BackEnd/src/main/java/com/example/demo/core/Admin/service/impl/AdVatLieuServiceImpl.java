package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.repository.AdVatLieuReponsitory;
import com.example.demo.core.Admin.service.AdVatLieuServcie;
import com.example.demo.entity.VatLieu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdVatLieuServiceImpl implements AdVatLieuServcie {
   @Autowired
   private AdVatLieuReponsitory adVatLieuReponsitory;

    @Override
    public List<VatLieu> getAll() {
        return adVatLieuReponsitory.findAll();
    }
}
