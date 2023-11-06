package com.example.demo.reponsitory;

import com.example.demo.core.Admin.model.response.AdminMauSacChiTietResponse;
import com.example.demo.entity.MauSacChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MauSacChiTietReponsitory extends JpaRepository<MauSacChiTiet, Integer> {

    @Query("select pt from MauSacChiTiet  pt where  pt.sanPhamChiTiet.id=:id")
    List<MauSacChiTiet> findMauSacChiTietBySanPhamChiTiet(Integer id);


    @Query(value = """
            SELECT msct.id as id,msct.anh as anh, msct.so_luong as soLuong, ms.ten as ten, si.ten as tenSize FROM datn.mau_sac_ctsp msct join datn.mau_sac ms on msct.id_mau_sac = ms.id\s
                                                 join  datn.san_pham_chi_tiet sp on msct.id_ctsp = sp.id
                                               left  join datn.size_ctsp sizect on msct.id_size_ctsp = sizect.id
                                                left join datn.size si on sizect.id_size = si.id
                                                 where sp.id =:id
            """, nativeQuery = true)
    List<AdminMauSacChiTietResponse> findMauSacChiTiet(Integer id);

    @Query(value = """
            SELECT msct.id as id,msct.anh as anh, msct.so_luong as soLuong, ms.ten as ten, si.ten as tenSize FROM datn.mau_sac_ctsp msct join datn.mau_sac ms on msct.id_mau_sac = ms.id\s
                                                 join  datn.san_pham_chi_tiet sp on msct.id_ctsp = sp.id
                                                left  join datn.size_ctsp sizect on msct.id_size_ctsp = sizect.id
                                               left   join datn.size si on sizect.id_size = si.id
                                                 where msct.id =:id
            """, nativeQuery = true)
    AdminMauSacChiTietResponse findByMauSacChiTiet(Integer id);

    @Query("select pt from MauSacChiTiet  pt where  pt.sanPhamChiTiet.id=:id")
    MauSacChiTiet findMauSacChiTietById(Integer id);

    @Query("select pt from MauSacChiTiet  pt where  pt.sanPhamChiTiet.id=:idSP and pt.mauSac.id=:idMauSac and pt.sizeChiTiet.id=:idSizeCT")
    MauSacChiTiet findMSBySPAndMS(Integer idSP, Integer idMauSac,Integer idSizeCT);

    @Query("select pt from MauSacChiTiet  pt where  pt.sanPhamChiTiet.id=:idSP and pt.mauSac.id=:idMauSac ")
    List<MauSacChiTiet> findMSBySPAndMSs(Integer idSP, Integer idMauSac);

    @Query("select pt from MauSacChiTiet  pt where  pt.sanPhamChiTiet.id=:id and pt.mauSac.id=:idMau")
    List<MauSacChiTiet> findMauSacChiTietBySanPhamChiTietAndMauSac(Integer id, Integer idMau);

    @Query("select pt from MauSacChiTiet  pt where  pt.sanPhamChiTiet.id=:id and pt.mauSac.id=:idMau and pt.sizeChiTiet.id=:idSize")
    List<MauSacChiTiet> findMauSac(Integer id, Integer idMau, Integer idSize);

    @Query("select pt from MauSacChiTiet  pt where  pt.sizeChiTiet.id=:id ")
    List<MauSacChiTiet> findMauSacChiTietBySizeChiTiet(Integer id);

    @Query("select pt from MauSacChiTiet  pt where  pt.sanPhamChiTiet.id=:id and  pt.sizeChiTiet.id is null")
    List<MauSacChiTiet> findMauSacChiTietBySanPhamChiTietAndSize(Integer id);
}
