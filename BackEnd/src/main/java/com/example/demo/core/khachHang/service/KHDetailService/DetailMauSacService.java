package com.example.demo.core.khachHang.service.KHDetailService;

import com.example.demo.entity.MauSacChiTiet;

import java.util.List;

public interface DetailMauSacService {

    List<MauSacChiTiet> findByIdCTSP(Integer id);

}
