package com.example.demo.core.Admin.repository;

import com.example.demo.entity.TrongLuong;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrongLuongRepository extends JpaRepository<TrongLuong, Integer> {
    @Query("select  pot from  TrongLuong  pot where pot.donVi like :keyword or pot.ma like :keyword")
    Page<TrongLuong> search(String keyword, Pageable pageable);
}
