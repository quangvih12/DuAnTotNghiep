package com.example.demo.core.Admin.repository;

import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;
import com.example.demo.entity.HoaDon;
import com.example.demo.reponsitory.HoaDonReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface AdHoaDonReponsitory extends HoaDonReponsitory {

    @Query(value = """
                    SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,\s
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan
                    FROM  datn.hoa_don hd    join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                             join datn.user u on u.id = hd.id_user
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getAll();

    @Query(value = """
                     SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,\s
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan
                    FROM  datn.hoa_don hd    join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                             join datn.user u on u.id = hd.id_user
            	   WHERE  hd.id=:id
            """, nativeQuery = true)
    AdminHoaDonResponse getByIds(Integer id);

    @Query(value = """
                    SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,\s
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan
                    FROM  datn.hoa_don hd    join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                             join datn.user u on u.id = hd.id_user where hd.trang_thai =:trangThai
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getHoaDonTrangThai(Integer trangThai);

    @Query(value = """
                  SELECT hd
                   FROM  HoaDon hd
            	   where hd.ngayTao >= :startDate and hd.ngayTao <= :endDate
            """)
    List<HoaDon> getHoaDonByDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);



    @Query(value = """
                     SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,\s
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan
                    FROM  datn.hoa_don hd    join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                             join datn.user u on u.id = hd.id_user 
            	   WHERE  hd.id_user=:id
            """, nativeQuery = true)
    AdminHoaDonResponse getByIdUser(Integer id);

    @Query(value = """
                    SELECT ROW_NUMBER() OVER(ORDER BY hd.id DESC) AS stt,
                           u.email as email ,u.sdt,hd.hinh_thuc_giao_hang as hinhThucGiaoHang, hd.ly_do as lyDo,
                           hd.ma as maHD, u.ten as nguoiTao, hd.ngay_nhan as ngayNhan, hd.ngay_ship as ngayShip, hd.ngay_tao as ngayTao,\s
                           hd.ngay_sua as ngaySua, hd.ten_nguoi_nhan as tenNguoiNhan, hd.tien_sau_khi_giam_gia as tienSauKhiGiam,
                           hd.tien_ship as tienShip, hd.tong_tien as tongTien, hd.trang_thai as trangThai,
                           hd.id as idHD,
                           dc.dia_chi as diaChiCuThe, dc.id_tinh_thanh as idTinhThanh,\s
                           dc.ten_tinh_thanh as tenTinhThanh, dc.id_quan_huyen as idQuanHuyen, dc.ten_quan_huyen as tenQuanHuyen,\s
                           dc.id_phuong_xa as idPhuongXa, dc.ten_phuong_xa as tenPhuongXa,
                           pttt.ten as tenPTTT, hd.ngay_thanh_toan  as ngayThanhToan
                    FROM  datn.hoa_don hd    join datn.dia_chi dc on dc.id = hd.id_dia_chi_sdt\s
                                             join datn.phuong_thuc_thanh_toan pttt on pttt.id = hd.id_phuong_thuc_thanh_toan\s
                                             join datn.user u on u.id = hd.id_user 
            	   WHERE  hd.id=:id
            """, nativeQuery = true)
    List<AdminHoaDonResponse> getListByIds(Integer id);

}
