package com.example.demo.core.khachHang.repository;



import com.example.demo.core.khachHang.model.response.MauSacResponse;
import com.example.demo.entity.MauSacChiTiet;
import com.example.demo.reponsitory.MauSacChiTietReponsitory;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KHMauSacCTRepository extends MauSacChiTietReponsitory {
    MauSacChiTiet findAllById(Integer id);

    @Query(value = """
             select msct.id, ms.ten as ten,msct.anh as anh,msct.mo_ta as moTa, msct.so_luong as soLuong
                   ,ms.id as idMS  from san_pham_chi_tiet spct
                    join mau_sac_ctsp msct on msct.id_ctsp = spct.id
                    join mau_sac ms on ms.id = msct.id_mau_sac
                    where spct.id =:idctsp
            """,nativeQuery = true)
    List<MauSacResponse> listMsctByIdctsp(Integer idctsp);

//    @Query("select pt from MauSacChiTiet pt where pt.sanPhamChiTiet.id=:idSp and pt.mauSac.id=:idMS and pt.sizeChiTiet.id=:idSize")
//    MauSacChiTiet findByIdSPAndIdMsAndIdSizeCT(Integer idSp,Integer idMS,Integer idSize);
}
