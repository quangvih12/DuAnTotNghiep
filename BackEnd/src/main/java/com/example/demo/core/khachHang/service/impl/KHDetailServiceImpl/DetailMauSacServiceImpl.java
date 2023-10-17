package com.example.demo.core.khachHang.service.impl.KHDetailServiceImpl;

import com.example.demo.core.khachHang.repository.KHDetailRepository.KHDetailMauSacRepository;
import com.example.demo.core.khachHang.service.KHDetailService.DetailMauSacService;
import com.example.demo.entity.MauSacChiTiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailMauSacServiceImpl implements DetailMauSacService {

    @Autowired
    KHDetailMauSacRepository mauSacRepository;

    @Override
    public List<MauSacChiTiet> findByIdCTSP(Integer id) {
        return mauSacRepository.findMauSacChiTiet(id);
    }
}
