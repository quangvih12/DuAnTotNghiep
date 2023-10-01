package com.example.demo.core.Admin.repository;

import com.example.demo.entity.MauSac;
import com.example.demo.reponsitory.MauSacChiTietReponsitory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdMauSacReponsitory extends MauSacChiTietReponsitory {
    @Query("select pot from  MauSac pot where pot.ten like :keyword or pot.ma like :keyword")
    Page<MauSac> search(String keyword, Pageable pageable);
    List<MauSac> findAllByTrangThai(Integer trangThai, Sort sort);
}
