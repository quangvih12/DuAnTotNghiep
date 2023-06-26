package com.example.demo.reponsitory;

import com.example.demo.entity.SizeChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SizeChiTietReponsitory extends JpaRepository<SizeChiTiet, Integer> {
    List<SizeChiTiet> findBySanPhamChiTietIdAndSizeId(Integer id, Integer idSize);
}
