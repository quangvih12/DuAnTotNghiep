package com.example.demo.core.khachHang.service.impl.KHDetailServiceImpl;

import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
import com.example.demo.core.khachHang.repository.KHSanPhamRepository;
import com.example.demo.core.khachHang.repository.KHchiTietSanPhamRepository;
import com.example.demo.core.khachHang.service.KHDetailService.DetaiService;
import com.example.demo.entity.SanPham;
import com.example.demo.entity.SanPhamChiTiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailServiceImpl implements DetaiService {

    @Autowired
    private KHchiTietSanPhamRepository repository;

    @Autowired
    private KHSanPhamRepository spRepo;

    @Override
    public AdminSanPhamChiTietResponse get(Integer id) {
        AdminSanPhamChiTietResponse optional = this.repository.get(id);
        return optional;
    }

    public List<SanPhamChiTiet> getAlls() {
        return repository.findAll();
    }

    @Override
    public List<SanPhamChiTiet> getAllByIdSp(Integer idSP) {
        return repository.findByIdSp(idSP);
    }

    @Override
    public SanPham findById(Integer id) {
        return spRepo.findById(id).get();
    }
}
