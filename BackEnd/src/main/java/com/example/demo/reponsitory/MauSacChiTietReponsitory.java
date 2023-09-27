package com.example.demo.reponsitory;

import com.example.demo.entity.MauSacChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MauSacChiTietReponsitory extends JpaRepository<MauSacChiTiet, Integer> {

}
