package com.example.demo.core.Admin.repository;

import com.example.demo.entity.SanPham;
import com.example.demo.entity.VatLieu;
import com.example.demo.reponsitory.VatLieuReponsitory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdVatLieuReponsitory extends VatLieuReponsitory {
    @Query("select  pot from  VatLieu  pot " +
            "where pot.ten like :keyword ")
    VatLieu findByTenVatLieuExcel(String keyword);
}
