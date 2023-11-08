package com.example.demo.core.Admin.repository;

import com.example.demo.entity.MauSacChiTiet;
import com.example.demo.reponsitory.MauSacChiTietReponsitory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdMauSacChiTietReponsitory extends MauSacChiTietReponsitory {
    List<MauSacChiTiet> findBySanPhamChiTietIdAndMauSacId(Integer id, Integer idMauSac);
}
