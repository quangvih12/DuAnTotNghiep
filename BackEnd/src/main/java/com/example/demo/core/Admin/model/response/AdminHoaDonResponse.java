package com.example.demo.core.Admin.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface AdminHoaDonResponse {
    Integer getStt();

    @Value("#{target.idHD}")
    Integer getIdHD();

    @Value("#{target.maHD}")
    String getMaHD();

    @Value("#{target.nguoiTao}")
    String getNguoiTao();

    @Value("#{target.tenSP}")
    String getTenSP();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.sdt}")
    String getSdt();

    @Value("#{target.hinhThucGiaoHang}")
    String getHinhThucGiaoHang();

    @Value("#{target.ngayThanhToan}")
    String getNgayThanhToan();

    @Value("#{target.ngayNhan}")
    String getNgayNhan();

    @Value("#{target.ngayShip}")
    String getNgayShip();

    @Value("#{target.ngaySua}")
    String getNgaySua();

    @Value("#{target.ngayTao}")
    String getNgayTao();

    @Value("#{target.tenNguoiNhan}")
    String getTenNguoiNhan();

    @Value("#{target.tienSauKhiGiam}")
    String getTienSauKhiGiam();

    @Value("#{target.tienShip}")
    String getTienShip();

    @Value("#{target.tongTien}")
    String getTongTien();

    @Value("#{target.trangThai}")
    String getTrangThai();

    @Value("#{target.giaBan}")
    String getGiaBan();

    @Value("#{target.giaSPSauGiam}")
    String getGiaSPSauGiam();

    @Value("#{target.diaChi}")
    String getDiaChi();

    @Value("#{target.tenPTTT}")
    String getTenPTTT();

}
