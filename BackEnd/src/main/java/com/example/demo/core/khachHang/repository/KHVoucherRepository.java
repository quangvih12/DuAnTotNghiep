package com.example.demo.core.khachHang.repository;

import com.example.demo.core.khachHang.model.response.KhVoucherResponse;
import com.example.demo.core.khachHang.model.response.VoucherResponse;
import com.example.demo.reponsitory.VoucherReponsitory;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KHVoucherRepository extends VoucherReponsitory {

    @Query(value = """
                select v.id, v.ten as ten, v.mo_ta as moTa, v.giam_toi_da as giamToiDa ,v.so_luong as soLuong, v.thoi_gian_ket_thuc as thoiGianKetThuc  from voucher v join user_voucher us on v.id = us.id_voucher
                join user u on u.id = us.id_user where u.id = 1 and v.trang_thai = 0 """, nativeQuery = true)
    List<VoucherResponse> listVoucher();
}
