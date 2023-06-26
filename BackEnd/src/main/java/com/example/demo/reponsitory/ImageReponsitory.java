package com.example.demo.reponsitory;

import com.example.demo.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageReponsitory extends JpaRepository<Image, Integer> {
    Optional<Image> findBySanPhamChiTietId(Integer id);
}
