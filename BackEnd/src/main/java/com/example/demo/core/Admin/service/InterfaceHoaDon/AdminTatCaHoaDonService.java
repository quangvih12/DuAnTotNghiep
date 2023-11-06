package com.example.demo.core.Admin.service.InterfaceHoaDon;

import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;
import com.example.demo.entity.HoaDon;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface AdminTatCaHoaDonService {

    List<AdminHoaDonResponse> getAll();

    List<AdminHoaDonResponse> getHoaDonTrangThai(Integer trangThai);

    List<AdminHoaDonResponse> getHoaDonHoanThanh();

    List<AdminHoaDonResponse> getHoaDonHuy();

    List<AdminHoaDonResponse> getHoaDonDangGiao();

    List<AdminHoaDonResponse> getHoaDonYeuCauDoiTra();

    List<AdminHoaDonResponse> getHoaDonXacNhanDoiTra();

    List<AdminHoaDonResponse> getHoaDonDangChuanBiHang();

    List<AdminHoaDonResponse> searchDate(LocalDateTime startDate, LocalDateTime endDate, String  comboBoxValue);

    List<AdminHoaDonResponse> searchDateByTrangThai(LocalDateTime startDate, LocalDateTime endDate, String  comboBoxValue,Integer trangThai);

    AdminHoaDonResponse giaoHoaDonChoVanChuyen(Integer idHD);

}
