package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.khachHang.model.response.KHHoaDonChiTietResponse;
import com.example.demo.core.khachHang.repository.KHHoaDonChiTietRepository;
import com.example.demo.core.khachHang.service.KHHoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KHHoaDonChiTietServiceImpl implements KHHoaDonChiTietService {

    @Autowired
    private KHHoaDonChiTietRepository hdctRepo;

    @Override
    public List<KHHoaDonChiTietResponse> findHDCTByIdHoaDon(Integer idHD) {
        return hdctRepo.findHDCTByIDHD(idHD);
    }

}
