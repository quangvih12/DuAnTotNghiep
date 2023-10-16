package com.example.demo.core.khachHang.repository.KHDetailRepository;

import com.example.demo.entity.MauSacChiTiet;
import com.example.demo.reponsitory.MauSacReponsitory;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KHDetailMauSacRepository extends MauSacReponsitory {
    @Query("select pt from MauSacChiTiet  pt where  pt.sanPhamChiTiet.id=:id")
    List<MauSacChiTiet> findMauSacChiTiet(Integer id);
}
