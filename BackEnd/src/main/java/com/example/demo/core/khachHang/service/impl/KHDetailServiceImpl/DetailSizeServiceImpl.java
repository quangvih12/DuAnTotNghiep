package com.example.demo.core.khachHang.service.impl.KHDetailServiceImpl;

import com.example.demo.core.khachHang.repository.KHSizeRepository;
import com.example.demo.core.khachHang.service.KHDetailService.DetailSizeService;
import com.example.demo.entity.SizeChiTiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailSizeServiceImpl implements DetailSizeService {

    @Autowired
    private KHSizeRepository repository;

    @Override
    public List<SizeChiTiet> findByIdCTSP(Integer id) {
        return repository.findSizeChiTiet(id);
    }
}
