package com.example.demo.reponsitory;

import com.example.demo.entity.ChucVu;
import com.example.demo.entity.TrongLuong;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChucVuRepository extends JpaRepository<ChucVu, Integer> {
    @Query("select pot from ChucVu pot where pot.ten like :keyword or pot.ma like :keyword")
    Page<ChucVu> search(String keyword, Pageable pageable);
}
