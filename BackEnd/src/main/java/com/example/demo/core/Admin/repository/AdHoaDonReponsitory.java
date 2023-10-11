package com.example.demo.core.Admin.repository;

import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;
import com.example.demo.reponsitory.HoaDonReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdHoaDonReponsitory extends HoaDonReponsitory {

    @Query(value = """
                   SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                    u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, 
                    hd.ma as maHD, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,
            		hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
            		hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
            		hd.id as idHD, spct.gia_ban as giaBan, spct.gia_sau_giam as giaSPSauGiam,
            		sp.ten as tenSP, dc.dia_chi as diaChi, pttt.ten as tenPTTT
                   FROM datn.hoa_don_chi_tiet hdct join datn.hoa_don hd on hdct.id_hoa_don = hd.id 
            										join datn.san_pham_chi_tiet spct on hdct.id_san_pham_chi_tiet = spct.id
                                                    join datn.san_pham sp on sp.id = spct.id_san_pham
            										join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt 
                                                    join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan 
            										join datn.user u on u.id = hd.id_user
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getAll();

    @Query(value = """
                   SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                    u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, 
                    hd.ma as maHD, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,
            		hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
            		hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
            		hd.id as idHD, spct.gia_ban as giaBan, spct.gia_sau_giam as giaSPSauGiam,
            		sp.ten as tenSP, dc.dia_chi as diaChi, pttt.ten as tenPTTT
                   FROM datn.hoa_don_chi_tiet hdct join datn.hoa_don hd on hdct.id_hoa_don = hd.id 
            										join datn.san_pham_chi_tiet spct on hdct.id_san_pham_chi_tiet = spct.id
                                                    join datn.san_pham sp on sp.id = spct.id_san_pham
            										join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt 
                                                    join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan 
            										join datn.user u on u.id = hd.id_user where hd.trang_thai =:trangThai
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getHoaDonTrangThai(Integer trangThai);



}
