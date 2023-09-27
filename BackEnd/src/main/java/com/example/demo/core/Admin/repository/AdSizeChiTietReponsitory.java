package com.example.demo.core.Admin.repository;

import com.example.demo.entity.SizeChiTiet;
import com.example.demo.reponsitory.SizeChiTietReponsitory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdSizeChiTietReponsitory extends SizeChiTietReponsitory {
    List<SizeChiTiet> findBySanPhamChiTietIdAndSizeId(Integer id, Integer idSize);
}
