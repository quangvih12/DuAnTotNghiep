package com.example.demo.reponsitory;

import com.example.demo.entity.MauSacChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MauSacChiTietReponsitory extends JpaRepository<MauSacChiTiet, Integer> {
    List<MauSacChiTiet> findBySanPhamChiTietIdAndMauSacId(Integer id, Integer idMauSac);
}
