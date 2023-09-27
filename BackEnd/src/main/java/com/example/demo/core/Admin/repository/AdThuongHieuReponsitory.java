package com.example.demo.core.Admin.repository;

import com.example.demo.entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ThuongHieuReponsitory extends JpaRepository<ThuongHieu, Integer> {
    @Query("select  pot from  ThuongHieu  pot where pot.ten like :keyword or pot.ma like :keyword")
    Page<ThuongHieu> search(String keyword, Pageable pageable);
}
