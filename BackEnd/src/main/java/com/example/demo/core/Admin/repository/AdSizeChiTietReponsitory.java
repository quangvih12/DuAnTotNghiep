package com.example.demo.core.Admin.repository;

import com.example.demo.entity.SizeChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SizeChiTietReponsitory extends JpaRepository<SizeChiTiet, Integer> {
    List<SizeChiTiet> findBySanPhamChiTietIdAndSizeId(Integer id, Integer idSize);
}
