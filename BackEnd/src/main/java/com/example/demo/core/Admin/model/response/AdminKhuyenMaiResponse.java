package com.example.demo.core.Admin.model.response;

import com.example.demo.entity.KhuyenMai;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminKhuyenMaiResponse {

    private Integer id;
    private String ten;

    private String ma;

    private String moTa;

    private String thoiGianBatDau;

    private String thoiGianKetThuc;

    private String ngayTao;

    private String ngaySua;

    private Integer trangThai;

    private Integer soLuong;

    private Integer giaTriGiam;

    public AdminKhuyenMaiResponse(KhuyenMai khuyenMai) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd"); // Định dạng tùy chỉnh
        String thoigianBĐ = khuyenMai.getThoiGianBatDau().format(formatter);
        String thoigianKT = khuyenMai.getThoiGianKetThuc().format(formatter);
        String ngayTao = khuyenMai.getNgayTao().format(formatter);
        String ngaySua = khuyenMai.getNgaySua().format(formatter);

        this.id = khuyenMai.getId();
        this.ten = khuyenMai.getTen();
        this.ma = khuyenMai.getMa();
        this.moTa = khuyenMai.getMoTa();
        this.thoiGianBatDau = thoigianKT;
        this.thoiGianKetThuc = thoigianKT;
        this.trangThai = khuyenMai.getTrangThai();
        this.soLuong = khuyenMai.getSoLuong();
        this.giaTriGiam = khuyenMai.getGiaTriGiam();
        this.ngayTao = ngayTao;
        this.ngaySua = ngaySua;
    }
}
