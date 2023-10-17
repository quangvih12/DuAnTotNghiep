package com.example.demo.core.khachHang.repository.KHDetailRepository;

import com.example.demo.entity.SizeChiTiet;
import com.example.demo.reponsitory.SizeChiTietReponsitory;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KHDetailSizeRepository extends SizeChiTietReponsitory {

    @Query("select pt from SizeChiTiet  pt where  pt.sanPhamChiTiet.id=:id")
    List<SizeChiTiet> findSizeChiTiet(Integer id);
}
