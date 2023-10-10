package com.example.demo.core.Admin.repository;

import com.example.demo.entity.Voucher;
import com.example.demo.reponsitory.VoucherReponsitory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdVoucherReponsitory extends VoucherReponsitory {

    @Query("SELECT i FROM Voucher i WHERE i.thoiGianKetThuc <= CURRENT_DATE ")
    List<Voucher> findVoucherByHetHan();

    @Query("SELECT i FROM Voucher i WHERE i.thoiGianBatDau >= CURRENT_DATE")
    List<Voucher> findVoucherByChuaBatDAu();

    @Query("SELECT i FROM Voucher i WHERE i.thoiGianBatDau <= CURRENT_DATE and i.thoiGianKetThuc>= CURRENT_DATE ")
    List<Voucher> findVoucherByConHan();

    @Query("select  i  from  Voucher  i where i.trangThai =:trangThai")
    Page<Voucher> getbyTrangThai(Integer trangThai, Pageable pageable);

    // lấy danh sách voucher ngoại trừ trạng thái là đã xoá
    @Query("select i from Voucher  i where i.trangThai <> 4 order by  i.id desc ")
    List<Voucher> getVoucherByTrangThai();
}
