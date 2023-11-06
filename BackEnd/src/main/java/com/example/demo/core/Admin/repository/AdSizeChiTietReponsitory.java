package com.example.demo.core.Admin.repository;

import com.example.demo.core.Admin.model.response.AdminSizeChiTietResponse;
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

    @Query(value = """
                 select s.id as id,si.ten as ten FROM datn.size_ctsp s join datn.size si on  s.id_size = si.id
                  join datn.san_pham_chi_tiet sp on s.id_ctsp = sp.id where sp.id =:id
            """, nativeQuery = true)
    List<AdminSizeChiTietResponse> findSizeChiTiet(Integer id);

    @Query(value = """
                 select s.id as id,si.ten as ten FROM datn.size_ctsp s join datn.size si on  s.id_size = si.id
                  join datn.san_pham_chi_tiet sp on s.id_ctsp = sp.id where s.id =:id
            """, nativeQuery = true)
    AdminSizeChiTietResponse findBySizeChiTiet(Integer id);

    @Query("select pt from SizeChiTiet  pt where  pt.sanPhamChiTiet.id=:idSP and pt.size.id=:idSize")
    SizeChiTiet findByIdSanPhamAndIdSize(Integer idSP, Integer idSize);

    @Query("select pt from SizeChiTiet  pt where  pt.sanPhamChiTiet.id=:id and pt.size.id=:idSize")
    SizeChiTiet findSizeChiTietBySanPhamChiTietAndSize(Integer id, Integer idSize);

}
