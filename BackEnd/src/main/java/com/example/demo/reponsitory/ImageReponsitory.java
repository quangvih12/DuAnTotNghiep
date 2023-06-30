package com.example.demo.reponsitory;

import com.example.demo.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageReponsitory extends JpaRepository<Image, Integer> {

    @Query("select im from  Image im where im.sanPhamChiTiet.sanPham.id =:id")
    List<Image> findBySanPhamId(Integer id);
}
