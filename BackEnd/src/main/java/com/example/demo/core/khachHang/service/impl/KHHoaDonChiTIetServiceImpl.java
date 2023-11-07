package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.Admin.model.response.AdminHoaDonChitietResponse;
import com.example.demo.core.khachHang.model.response.KHHoaDonChitietResponse;
import com.example.demo.core.khachHang.repository.KHHoaDonChiTietRepository;
import com.example.demo.core.khachHang.service.KHHoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KHHoaDonChiTIetServiceImpl implements KHHoaDonChiTietService {

    @Autowired
    private KHHoaDonChiTietRepository hdctRepo;

    @Override
    public List<KHHoaDonChitietResponse> findHDCTByIdHoaDon(Integer idHD) {
        return hdctRepo.findHDCTByIDHD(idHD);
    }
}
