package com.example.demo.core.Admin.service;

import com.example.demo.entity.HoaDonChiTiet;

import java.util.List;

public interface AdHoaDonChiTietService {

    List<HoaDonChiTiet> findHDCTByIdHoaDon(Integer idHD);

}
