package com.example.demo.core.Admin.repository;

import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.reponsitory.ChiTietSanPhamReponsitory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdChiTietSanPhamReponsitory extends ChiTietSanPhamReponsitory {

    @Query("select  pt  from  SanPhamChiTiet  pt where pt.trangThai =:trangThai")
    Page<SanPhamChiTiet> getbyTrangThai(Integer trangThai, Pageable pageable);

    @Query(value = "select  pt  from  SanPhamChiTiet  pt where pt.sanPham.id =:id")
    SanPhamChiTiet findBySanPhamId(Integer id);

    @Query(value = "select  pt  from  SanPhamChiTiet  pt where pt.sanPham.ten like :ten")
    SanPhamChiTiet findBySanPhamTen(String ten);

}
