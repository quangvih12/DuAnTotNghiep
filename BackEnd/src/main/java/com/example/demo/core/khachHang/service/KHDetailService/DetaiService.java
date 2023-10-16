package com.example.demo.core.khachHang.service.KHDetailService;

import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
import com.example.demo.entity.SanPhamChiTiet;

import java.util.List;

public interface DetaiService {

    AdminSanPhamChiTietResponse get(Integer id);

    List<SanPhamChiTiet> getAlls();

}
