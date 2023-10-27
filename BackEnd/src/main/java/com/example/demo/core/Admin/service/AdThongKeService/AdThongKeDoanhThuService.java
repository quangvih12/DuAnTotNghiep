package com.example.demo.core.Admin.service.AdThongKeService;

import com.example.demo.core.Admin.model.response.AdminThongKeBO;

import java.util.List;

public interface AdThongKeDoanhThuService {

    AdminThongKeBO getAll();


    AdminThongKeBO getAllBySanPham(Integer id);

    AdminThongKeBO getAllByThuongHieu(Integer id);

    AdminThongKeBO getAllByLoai(Integer id);

    AdminThongKeBO getAllByYear(String year);

    AdminThongKeBO getAllByMonth(Integer ngayBatDau, Integer ngayKetThuc);
}
