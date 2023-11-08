package com.example.demo.core.khachHang.repository;


import com.example.demo.core.khachHang.model.response.GioHangCTResponse;
import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.reponsitory.GioHangChiTietReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KHGioHangChiTietRepository extends GioHangChiTietReponsitory {
    @Query("SELECT ghct FROM GioHangChiTiet ghct WHERE ghct.sanPhamChiTiet.id =:idsp and ghct.gioHang.user.id=:idKh")
    GioHangChiTiet getChiTietSP(Integer idsp, Integer idKh);

    @Query("DELETE FROM GioHangChiTiet ghct WHERE ghct.id=:idgh")
    void deleteGhById(Integer idgh);

    @Query("Select pt from GioHangChiTiet pt where pt.gioHang.user.id=:id")
    List<GioHangChiTiet> findAllByTen(Integer id);

    @Query(value = """
           select ghct.id as idGHCT, spct.id as idCTSP , sp.ten as tenSP, sp.anh, ghct.ten_mau_sac as tenMauSac, ghct.ten_size as tenSize, ghct.so_luong as soLuong, spct.gia_ban as giaBan, spct.gia_sau_giam as giaSPSauGiam
           from gio_hang_chi_tiet ghct join san_pham_chi_tiet spct on ghct.id_san_pham_chi_tiet = spct.id
           join san_pham sp on spct.id_san_pham = sp.id where ghct.id =:id
             """, nativeQuery = true)
    GioHangCTResponse getGHCTByID(Integer id);


    @Query(value = """
           select ghct.id as idGHCT, spct.id as idCTSP , sp.ten as tenSP, sp.anh, ghct.ten_mau_sac as tenMauSac, ghct.ten_size as tenSize, ghct.so_luong as soLuong, spct.gia_ban as giaBan, spct.gia_sau_giam as giaSPSauGiam
           from gio_hang_chi_tiet ghct join san_pham_chi_tiet spct on ghct.id_san_pham_chi_tiet = spct.id
           join san_pham sp on spct.id_san_pham = sp.id
             """, nativeQuery = true)
    List<GioHangCTResponse> getListGHCT();

    @Query("Select pt from GioHangChiTiet pt where pt.gioHang.user.id=:id and pt.sanPhamChiTiet.id=:idctsp")
    List<GioHangChiTiet> findById(Integer id, Integer idctsp);


    @Query(value = """
        select count(*) from gio_hang_chi_tiet ghct join gio_hang gh on  ghct.id_gio_hang = gh.id\s
        join user u on gh.id_user = u.id  where u.id = :userId
        """, nativeQuery = true)
    Integer countGHCTByUser(Integer userId);

}
