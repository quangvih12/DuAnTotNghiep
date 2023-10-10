package com.example.demo.core.Admin.repository;

import com.example.demo.core.Admin.model.response.AdminKhuyenMaiResponse;
import com.example.demo.entity.KhuyenMai;
import com.example.demo.reponsitory.KhuyenMaiReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdKhuyenMaiReponsitory extends KhuyenMaiReponsitory {

    KhuyenMai getOneById(Integer id);

    // lấy danh sách khuyến mại ngoại trừ những khuyến mại có trạng thái là đã xoá
    @Query("select i from KhuyenMai  i where i.trangThai <> 4 order by  i.id desc ")
    List<AdminKhuyenMaiResponse> getKhuyenMaiByTrangThai();

    @Query("SELECT i FROM KhuyenMai i WHERE i.thoiGianKetThuc <= CURRENT_DATE")
    List<KhuyenMai> findKhuyenMaiByHetHan();

    @Query("SELECT i FROM KhuyenMai i WHERE i.thoiGianBatDau >= CURRENT_DATE")
    List<KhuyenMai> findKhuyenMaiByChuaBatDau();

    @Query("SELECT i FROM KhuyenMai i WHERE i.thoiGianBatDau <= CURRENT_DATE and i.thoiGianKetThuc>= CURRENT_DATE ")
    List<KhuyenMai> findKhuyenMaiByConHan();
}
