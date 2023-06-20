package com.example.demo.reponsitory;

import com.example.demo.entity.Loai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiReponsitory extends JpaRepository<Loai, Integer> {
    @Query("select  pot from  Loai  pot where pot.ten like :keyword or pot.ma like :keyword")
    Page<Loai> search(String keyword, Pageable pageable);

}
