package com.example.demo.core.Admin.repository;

import com.example.demo.entity.Size;
import com.example.demo.entity.SizeChiTiet;
import com.example.demo.reponsitory.SizeChiTietReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdSizeChiTietReponsitory extends SizeChiTietReponsitory {
    List<SizeChiTiet> findBySanPhamChiTietIdAndSizeId(Integer id, Integer idSize);

    @Query("select pt from SizeChiTiet  pt where  pt.sanPhamChiTiet.id=:id")
    List<SizeChiTiet> findSizeChiTietBySanPhamChiTiet(Integer id);

    @Query("select pt from SizeChiTiet  pt where  pt.sanPhamChiTiet.id=:id")
    List<SizeChiTiet> findSizeChiTiet(Integer id);


    @Query("select pt from SizeChiTiet  pt where  pt.sanPhamChiTiet.id=:idSP and pt.size.id=:idSize")
    SizeChiTiet findByIdSanPhamAndIdSize(Integer idSP,Integer idSize);

    @Query("select pt from SizeChiTiet  pt where  pt.sanPhamChiTiet.id=:id and pt.size.id=:idSize")
    SizeChiTiet findSizeChiTietBySanPhamChiTietAndSize(Integer id,Integer idSize);

}
