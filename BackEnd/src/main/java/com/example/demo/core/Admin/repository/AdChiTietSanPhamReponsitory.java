package com.example.demo.core.Admin.repository;

import com.example.demo.entity.SanPhamChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChiTietSanPhamReponsitory extends  {
    @Query("select  pt  from  SanPhamChiTiet  pt where pt.trangThai =:trangThai")
    Page<SanPhamChiTiet> getbyTrangThai(Integer trangThai, Pageable pageable);

    Optional<SanPhamChiTiet> findBySanPhamId(Integer id);

}
