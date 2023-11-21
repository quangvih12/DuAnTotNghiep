package com.example.demo.core.khachHang.repository;

import com.example.demo.core.khachHang.model.response.KHHoaDonChiTietResponse;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.entity.Image;
import com.example.demo.reponsitory.HoaDonChiTietReponsitory;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HoaDonChiTietRepository extends HoaDonChiTietReponsitory {
    @Query("select im from  HoaDonChiTiet im where im.sanPhamChiTiet.id=:idSp and im.trangThai =7")
    HoaDonChiTiet findByidSPandAndTrangThai(Integer idSp);

}
