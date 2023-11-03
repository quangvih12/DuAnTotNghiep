package com.example.demo.core.Admin.repository;

import com.example.demo.entity.MauSacChiTiet;
import com.example.demo.reponsitory.MauSacChiTietReponsitory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdMauSacChiTietReponsitory extends MauSacChiTietReponsitory {
    List<MauSacChiTiet> findBySanPhamChiTietIdAndMauSacId(Integer id, Integer idMauSac);

    @Query("select msct from MauSacChiTiet msct where msct.sizeChiTiet.id = :idSizeCT " +
            "and msct.mauSac.id = :idMS and msct.sanPhamChiTiet.id = :idSP")
    MauSacChiTiet findByIdMauSacAndIdSpctAndIdSizeCtsp(@Param("idSizeCT") Integer idSizeCT, @Param("idMS") Integer idMS, @Param("idSP") Integer idSP);
}
