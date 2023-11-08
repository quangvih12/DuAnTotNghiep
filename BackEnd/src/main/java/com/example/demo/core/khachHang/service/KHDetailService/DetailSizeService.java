package com.example.demo.core.khachHang.service.KHDetailService;

import com.example.demo.entity.SizeChiTiet;

import java.util.List;

public interface DetailSizeService {

    List<SizeChiTiet> findByIdCTSP(Integer id);

}
