package com.example.demo.core.Admin.repository;

import com.example.demo.entity.ChucVu;
import com.example.demo.reponsitory.ChucVuRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdChucVuRepository extends ChucVuRepository {
    @Query("select pot from ChucVu pot where pot.ten like :keyword or pot.ma like :keyword")
    Page<ChucVu> search(String keyword, Pageable pageable);
}
