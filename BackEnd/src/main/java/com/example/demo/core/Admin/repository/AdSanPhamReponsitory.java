package com.example.demo.core.Admin.repository;

import com.example.demo.entity.SanPham;
import com.example.demo.reponsitory.SanPhamReponsitory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdSanPhamReponsitory extends SanPhamReponsitory {
    @Query("select  pot from  SanPham  pot where pot.ten like :keyword or pot.ma like :keyword or pot.loai.ten like :keyword or pot.thuongHieu.ten like :keyword")
    Page<SanPham> search(String keyword, Pageable pageable);
}
