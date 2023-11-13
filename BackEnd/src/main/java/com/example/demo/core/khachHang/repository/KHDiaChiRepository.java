package com.example.demo.core.khachHang.repository;

import com.example.demo.core.khachHang.model.response.DiaChiResponse;
import com.example.demo.reponsitory.DiaChiReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KHDiaChiRepository extends DiaChiReponsitory {

    @Query("select u from DiaChi u where u.user.id =:id")
    List<DiaChiResponse> findDiaChiByIdUser(Integer id);
}
