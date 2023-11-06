package com.example.demo.core.khachHang.repository;


import com.example.demo.core.khachHang.model.response.SizeResponse;
import com.example.demo.entity.SizeChiTiet;
import com.example.demo.reponsitory.SizeChiTietReponsitory;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KHSizeCTRepository extends SizeChiTietReponsitory {

    SizeChiTiet findAllById(Integer id);

    @Query(value = """
           	select sizeCT.id , s.ten as ten,s.mo_ta as moTa, sizeCT.so_luong as soLuong
                    from san_pham_chi_tiet spct
                   join size_ctsp sizeCT on sizeCT.id_ctsp = spct.id
                     join size s on s.id = sizeCT.id_size
                   where spct.id =:idctsp
            """,nativeQuery = true)
    List<SizeResponse> listSizectByIdctsp(Integer idctsp);
}
