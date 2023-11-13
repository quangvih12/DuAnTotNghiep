package com.example.demo.core.khachHang.repository;

import com.example.demo.entity.DiaChi;
import com.example.demo.reponsitory.DiaChiReponsitory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("khDiaChiRepo")
public interface KHDiaChiRepository extends DiaChiReponsitory {

    DiaChi findAllByUserId(Integer id);

    @Query("select u from DiaChi u where u.user.id =:id")
    List<DiaChiResponse> findDiaChiByIdUser(Integer id);
}
