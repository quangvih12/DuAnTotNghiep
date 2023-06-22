package com.example.demo.reponsitory;

import com.example.demo.entity.VatLieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VatLieuReponsitory extends JpaRepository<VatLieu, Integer> {
}
