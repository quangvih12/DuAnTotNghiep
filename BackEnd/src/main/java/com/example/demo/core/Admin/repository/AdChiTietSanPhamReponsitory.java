package com.example.demo.core.Admin.repository;

import com.example.demo.core.Admin.model.request.AdminSearchRequest;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.reponsitory.ChiTietSanPhamReponsitory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdChiTietSanPhamReponsitory extends ChiTietSanPhamReponsitory {

//    @Query("select  pt  from  SanPhamChiTiet  pt where ( ( :#{#request.search} IS NULL   OR :#{#request.search} LIKE ''   OR sp.ma LIKE %:#{#request.search}% )")
//    Page<SanPhamChiTiet> getbyTrangThai(Integer trangThai, Pageable pageable);

    @Query("select  pt  from  SanPhamChiTiet  pt where pt.trangThai =:trangThai")
    List<SanPhamChiTiet> findAlls(Integer trangThai, Pageable pageable);

    @Query(value = "select  pt  from  SanPhamChiTiet  pt where pt.sanPham.id =:id")
    SanPhamChiTiet findBySanPhamId(Integer id);

    @Query(value = "select  pt  from  SanPhamChiTiet  pt where pt.sanPham.ten like :ten")
    SanPhamChiTiet findBySanPhamTen(String ten);

    @Query(value = """ 
                          SELECT ROW_NUMBER() OVER(ORDER BY spct.id DESC) AS stt,
                                                         spct.id,sp.ma,sp.ten,spct.gia_ban as giaBan,spct.gia_nhap as giaNhap,
                                                         spct.so_luong_ton as soLuongTon,spct.trang_thai as trangThai,
                                                         sp.quai_deo as quaiDeo,sp.dem_lot as demLot,
                                                         sp.mo_ta as moTa,l.ten AS loai,sp.anh as anh,
                                                         th.ten as thuongHieu,
                                                         vl.ten as vatLieu,tl.value as trongLuong
                                                  FROM datn.san_pham_chi_tiet spct join datn.san_pham sp on spct.id_san_pham = sp.id
                         								  join datn.loai l  on sp.id_loai = l.id
                         								  join datn.thuong_hieu th on sp.id_thuong_hieu = th.id
                         								  join datn.trong_luong tl on spct.id_trong_luong = tl.id
                                                           join datn.vat_lieu vl on spct.id_vat_lieu = vl.id
                         						WHERE (spct.trang_thai = 1 or spct.trang_thai = 3)
            """, nativeQuery = true)
    List<AdminSanPhamChiTietResponse> getAll(@Param("request") AdminSearchRequest request);

    @Query(value = """ 
                          SELECT ROW_NUMBER() OVER(ORDER BY spct.id DESC) AS stt,
                                                         spct.id,sp.ma,sp.ten,spct.gia_ban as giaBan,spct.gia_nhap as giaNhap,
                                                         spct.so_luong_ton as soLuongTon,spct.trang_thai as trangThai,
                                                         sp.quai_deo as quaiDeo,sp.dem_lot as demLot,
                                                         sp.mo_ta as moTa,l.ten AS loai,sp.anh as anh,
                                                         th.ten as thuongHieu,
                                                         vl.ten as vatLieu,tl.value as trongLuong
                                                  FROM datn.san_pham_chi_tiet spct join datn.san_pham sp on spct.id_san_pham = sp.id
                         								  join datn.loai l  on sp.id_loai = l.id
                         								  join datn.thuong_hieu th on sp.id_thuong_hieu = th.id
                         								  join datn.trong_luong tl on spct.id_trong_luong = tl.id
                                                           join datn.vat_lieu vl on spct.id_vat_lieu = vl.id
                         						WHERE spct.id =:id
            """, nativeQuery = true)
    AdminSanPhamChiTietResponse get(@Param("id") Integer id);

}
