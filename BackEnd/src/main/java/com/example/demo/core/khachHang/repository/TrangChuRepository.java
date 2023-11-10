package com.example.demo.core.khachHang.repository;

import com.example.demo.core.khachHang.model.response.TrangChuResponse;
import com.example.demo.reponsitory.SanPhamReponsitory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrangChuRepository extends SanPhamReponsitory {

    @Query(value = """
            SELECT sp.id as id, sp.anh as anh, sp.dem_lot as demLot, sp.ma as ma, sp.mo_ta as moTa, sp.quai_deo as quaiDeo, sp.ten as ten
            , sp.trang_thai as trangThai, l.ten as tenLoai,\s
            (select max(spct.gia_ban) as giaBan from datn.san_pham_chi_tiet spct where id_san_pham = sp.id) as giaBanMax,
            (select min(spct.gia_ban) as giaBan from datn.san_pham_chi_tiet spct where id_san_pham = sp.id) as giaBanMin,
            (select max(spct.gia_sau_giam) as giaSauGiam from datn.san_pham_chi_tiet spct where id_san_pham = sp.id and id_khuyen_mai is not null) as giaSauGiamMax,

            (select min(spct.gia_sau_giam) as giaSauGiam from datn.san_pham_chi_tiet spct where id_san_pham = sp.id ) as giaSauGiamMin

            FROM datn.san_pham sp
            join datn.loai l on l.id = sp.id_loai
            ORDER BY sp.id DESC;
            """, nativeQuery = true)
    List<TrangChuResponse> getAll(Pageable pageable);

    @Query(value = """
            SELECT sp.id as id, sp.anh as anh, sp.dem_lot as demLot, sp.ma as ma, sp.mo_ta as moTa, sp.quai_deo as quaiDeo, sp.ten as ten
                                , sp.trang_thai as trangThai, l.ten as tenLoai,\s
                                (select max(spct.gia_ban) as giaBan from datn.san_pham_chi_tiet spct where id_san_pham = sp.id) as giaBanMax,
                                (select min(spct.gia_ban) as giaBan from datn.san_pham_chi_tiet spct where id_san_pham = sp.id) as giaBanMin,
                                (select max(spct.gia_sau_giam) as giaSauGiam from datn.san_pham_chi_tiet spct where id_san_pham = sp.id and id_khuyen_mai is not null) as giaSauGiamMax,

                                (select min(spct.gia_sau_giam) as giaSauGiam from datn.san_pham_chi_tiet spct where id_san_pham = sp.id) as giaSauGiamMin

                                FROM datn.san_pham sp
                                join datn.loai l on l.id = sp.id_loai
                                where l.ten like :tenLoai
                                ORDER BY sp.id DESC;
            """, nativeQuery = true)
    List<TrangChuResponse> getAllByTenLoai(@Param("tenLoai") String tenLoai, Pageable pageable);

}
