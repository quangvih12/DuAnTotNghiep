package com.example.demo.reponsitory;

import com.example.demo.entity.MauSacChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MauSacChiTietReponsitory extends JpaRepository<MauSacChiTiet, Integer> {

    @Query("select pt from MauSacChiTiet  pt where  pt.sanPhamChiTiet.id=:id")
    List<MauSacChiTiet> findMauSacChiTietBySanPhamChiTiet(Integer id);


    @Query("select pt from MauSacChiTiet  pt where  pt.sanPhamChiTiet.id=:id")
    List<MauSacChiTiet> findMauSacChiTiet(Integer id);

    @Query("select pt from MauSacChiTiet  pt where  pt.sanPhamChiTiet.id=:id")
    MauSacChiTiet findMauSacChiTietById(Integer id);

    @Query("select pt from MauSacChiTiet  pt where  pt.sanPhamChiTiet.id=:idSP and pt.mauSac.id=:idMauSac")
    MauSacChiTiet findMSBySPAndMS(Integer idSP, Integer idMauSac);

    @Query("select pt from MauSacChiTiet  pt where  pt.sanPhamChiTiet.id=:id and pt.mauSac.id=:idMau")
    List<MauSacChiTiet> findMauSacChiTietBySanPhamChiTietAndMauSac(Integer id, Integer idMau);

    @Query("select pt from MauSacChiTiet  pt where  pt.sanPhamChiTiet.id=:id and pt.mauSac.id=:idMau and pt.sizeChiTiet.id=:idSize")
    List<MauSacChiTiet> findMauSac(Integer id, Integer idMau, Integer idSize);

    @Query("select pt from MauSacChiTiet  pt where  pt.sizeChiTiet.id=:id ")
    List<MauSacChiTiet> findMauSacChiTietBySizeChiTiet(Integer id);

    @Query("select pt from MauSacChiTiet  pt where  pt.sanPhamChiTiet.id=:id and  pt.sizeChiTiet.id is null")
    List<MauSacChiTiet> findMauSacChiTietBySanPhamChiTietAndSize(Integer id);
}
