package com.example.demo.core.khachHang.service.impl.KHDetailServiceImpl;

import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
import com.example.demo.core.khachHang.repository.KHchiTietSanPhamRepository;
import com.example.demo.core.khachHang.service.KHDetailService.DetaiService;
import com.example.demo.entity.SanPhamChiTiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailServiceImpl implements DetaiService {

    @Autowired
    private KHchiTietSanPhamRepository repository;

    @Override
    public AdminSanPhamChiTietResponse get(Integer id) {
        AdminSanPhamChiTietResponse optional = this.repository.get(id);
        return optional;
    }

    public List<SanPhamChiTiet> getAlls() {
        return repository.findAll();
    }
}
