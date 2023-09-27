package com.example.demo.core.Admin.repository;

import com.example.demo.entity.MauSacChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MauSacChiTietReponsitory extends JpaRepository<MauSacChiTiet, Integer> {
    List<MauSacChiTiet> findBySanPhamChiTietIdAndMauSacId(Integer id, Integer idMauSac);
}
