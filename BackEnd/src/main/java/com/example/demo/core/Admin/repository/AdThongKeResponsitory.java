package com.example.demo.core.Admin.repository;

import com.example.demo.core.Admin.model.response.*;
import com.example.demo.reponsitory.HoaDonReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdThongKeResponsitory extends HoaDonReponsitory {

    @Query(value = """
            SELECT SUM(hd.tong_tien) as tongTien
            FROM  datn.hoa_don hd
            where hd.trang_thai in(1,2,3);
            """, nativeQuery = true)
    Integer tongDoanhThu();


    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN (1, 2, 3)
              GROUP BY spct.id, sp.ma, sp.ten, spct.gia_ban, spct.gia_nhap, spct.so_luong_ton, spct.trang_thai, sp.quai_deo, sp.dem_lot, sp.mo_ta, spct.gia_sau_giam
            )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien DESC LIMIT 5;
              """, nativeQuery = true)
    List<AdminThongKeSanPhamCaoResponse> sanPhamDoanhThuCaoNhat();

    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN (1, 2, 3)
              GROUP BY spct.id, sp.ma, sp.ten, spct.gia_ban, spct.gia_nhap, spct.so_luong_ton, spct.trang_thai, sp.quai_deo, sp.dem_lot, sp.mo_ta, spct.gia_sau_giam
            )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien  LIMIT 5;
              """, nativeQuery = true)
    List<AdminThongKeSanPhamThapResponse> sanPhamDoanhThuThapNhat();

    @Query(value = """
            SELECT  MONTH(hd.ngay_tao) AS thang, SUM(hd.tong_tien) AS tongTien
            FROM datn.hoa_don hd
            GROUP BY MONTH(hd.ngay_tao) ORDER BY MONTH(hd.ngay_tao) ;
              """, nativeQuery = true)
    List<AdminThongKeThangResponse> doanhThuTheoThang();

    @Query(value = """
            WITH DoanhThuThuongHieu AS (
               SELECT
                  th.ten AS ten,
                   th.ma as ma,
                  SUM(hd.tong_tien) AS tongTien
               FROM datn.san_pham_chi_tiet spct
               JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
               JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
               JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
               JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
               WHERE hd.trang_thai IN (1, 2, 3)
               GROUP BY   th.ten,  th.ma
            )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuThuongHieu GROUP BY ten,ma ORDER BY tongTien DESC;
              """, nativeQuery = true)
    List<AdminThongKeThuongHieuResponse> doanhThuTheoThuongHieu();

    @Query(value = """
            WITH DoanhThuLoai AS (
              SELECT
                th.ten AS ten,
                th.ma as ma,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.loai th ON sp.id_loai = th.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN (1, 2, 3)
              GROUP BY   th.ten,  th.ma
            )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuLoai GROUP BY ten,ma ORDER BY tongTien DESC;
                """, nativeQuery = true)
    List<AdminThongKeLoaiResponse> doanhThuTheoLoai();


    // tổng doanh thu theo sản phẩm
    @Query(value = """
            SELECT SUM(hd.tong_tien) as tongTien
                                            FROM datn.san_pham_chi_tiet spct
                                            JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                            JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                            JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                            WHERE hd.trang_thai IN (1, 2, 3) AND spct.id =:id
              """, nativeQuery = true)
    Integer tongDoanhThuBySanPham(@Param("id") Integer id);

    // bảng top 5 sp có doanh thu cao và thấp nhất
    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN (1, 2, 3) AND spct.id =:id
              GROUP BY spct.id, sp.ma, sp.ten, spct.gia_ban, spct.gia_nhap, spct.so_luong_ton, spct.trang_thai, sp.quai_deo, sp.dem_lot, sp.mo_ta, spct.gia_sau_giam
            )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien DESC LIMIT 5;
              """, nativeQuery = true)
    List<AdminThongKeSanPhamCaoResponse> sanPhamDoanhThuCaoNhatBySanPham(Integer id);

    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN (1, 2, 3)  AND spct.id =:id
              GROUP BY spct.id, sp.ma, sp.ten, spct.gia_ban, spct.gia_nhap, spct.so_luong_ton, spct.trang_thai, sp.quai_deo, sp.dem_lot, sp.mo_ta, spct.gia_sau_giam
            )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien  LIMIT 5;
              """, nativeQuery = true)
    List<AdminThongKeSanPhamThapResponse> sanPhamDoanhThuThapNhatBySanPham(Integer id);

    @Query(value = """
            SELECT  MONTH(hd.ngay_tao) AS thang, SUM(hd.tong_tien) AS tongTien\s
            FROM datn.san_pham_chi_tiet spct
            JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
            JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
            JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don\s
             WHERE spct.id =:id
            GROUP BY MONTH(hd.ngay_tao) ORDER BY MONTH(hd.ngay_tao)
                """, nativeQuery = true)
    List<AdminThongKeThangResponse> doanhThuTheoThangBySanPham(Integer id);

    @Query(value = """
            WITH DoanhThuThuongHieu AS (
               SELECT
                  th.ten AS ten,
                   th.ma as ma,
                  SUM(hd.tong_tien) AS tongTien
               FROM datn.san_pham_chi_tiet spct
               JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
               JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
               JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
               JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
               WHERE hd.trang_thai IN (1, 2, 3)  AND spct.id =:id
               GROUP BY   th.ten,th.ma
            )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuThuongHieu GROUP BY ten ORDER BY tongTien DESC;
              """, nativeQuery = true)
    List<AdminThongKeThuongHieuResponse> doanhThuTheoThuongHieuBySanPham(Integer id);

    @Query(value = """
            WITH DoanhThuLoai AS (
              SELECT\s
                th.ten AS ten,
                th.ma as ma,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.loai th ON sp.id_loai = th.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN (1, 2, 3)  AND spct.id =:id
              GROUP BY   th.ten,th.ma
            )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuLoai GROUP BY ten ORDER BY tongTien DESC;
                """, nativeQuery = true)
    List<AdminThongKeLoaiResponse> doanhThuTheoLoaiBySanPham(Integer id);


    // tổng doanh thu theo thương hiệu
    @Query(value = """
            SELECT SUM(hd.tong_tien) as tongTien
                                    FROM datn.san_pham_chi_tiet spct
                                    JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                    JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
                                    JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                    JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                    WHERE hd.trang_thai IN (1, 2, 3) AND th.id =:id
              """, nativeQuery = true)
    Integer tongDoanhThuByThuongHieu(@Param("id") Integer id);

    // bảng top 5 sp có doanh thu cao và thấp nhất
    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN (1, 2, 3)  AND th.id =:id
              GROUP BY spct.id, sp.ma, sp.ten, spct.gia_ban, spct.gia_nhap, spct.so_luong_ton, spct.trang_thai, sp.quai_deo, sp.dem_lot, sp.mo_ta, spct.gia_sau_giam
            )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien DESC LIMIT 5;
              """, nativeQuery = true)
    List<AdminThongKeSanPhamCaoResponse> sanPhamDoanhThuCaoNhatByThuongHieu(Integer id);

    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN (1, 2, 3)   AND th.id =:id
              GROUP BY spct.id, sp.ma, sp.ten, spct.gia_ban, spct.gia_nhap, spct.so_luong_ton, spct.trang_thai, sp.quai_deo, sp.dem_lot, sp.mo_ta, spct.gia_sau_giam
            )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien  LIMIT 5;
              """, nativeQuery = true)
    List<AdminThongKeSanPhamThapResponse> sanPhamDoanhThuThapNhatByThuongHieu(Integer id);

    @Query(value = """
            SELECT  MONTH(hd.ngay_tao) AS thang, SUM(hd.tong_tien) AS tongTien\s
            FROM datn.san_pham_chi_tiet spct
            JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
            JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
            JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
            JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don\s
            WHERE th.id =:id
            GROUP BY MONTH(hd.ngay_tao) ORDER BY MONTH(hd.ngay_tao)
               """, nativeQuery = true)
    List<AdminThongKeThangResponse> doanhThuTheoThangByThuongHieu(Integer id);

    @Query(value = """
            WITH DoanhThuThuongHieu AS (
               SELECT
                  th.ten AS ten,
                   th.ma as ma,
                  SUM(hd.tong_tien) AS tongTien
               FROM datn.san_pham_chi_tiet spct
               JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
               JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
               JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
               JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
               WHERE hd.trang_thai IN (1, 2, 3)  AND th.id =:id
               GROUP BY   th.ten,th.ma
            )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuThuongHieu GROUP BY ten,ma ORDER BY tongTien DESC;
              """, nativeQuery = true)
    List<AdminThongKeThuongHieuResponse> doanhThuTheoThuongHieuByThuongHieu(Integer id);

    @Query(value = """
            WITH DoanhThuLoai AS (
                                    SELECT
                                         l.ten AS ten,
                                         l.ma as ma,
                                         SUM(hd.tong_tien) AS tongTien
                                    FROM datn.san_pham_chi_tiet spct
                                    JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                    JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
                                    JOIN datn.loai l ON sp.id_loai = l.id
                                    JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                    JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                    WHERE hd.trang_thai IN (1, 2, 3)  AND th.id =:id
                                    GROUP BY   l.ten,l.ma
                                  )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuLoai GROUP BY ten,ma ORDER BY tongTien DESC;
               """, nativeQuery = true)
    List<AdminThongKeLoaiResponse> doanhThuTheoLoaiByThuongHieu(Integer id);


    // tổng doanh thu theo loai
    @Query(value = """
            SELECT SUM(hd.tong_tien) as tongTien
                                    FROM datn.san_pham_chi_tiet spct
                                    JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                    JOIN datn.loai th ON sp.id_loai = th.id
                                    JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                    JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                    WHERE hd.trang_thai IN (1, 2, 3) AND th.id =:id
              """, nativeQuery = true)
    Integer tongDoanhThuByLoai(@Param("id") Integer id);

    // bảng top 5 sp có doanh thu cao và thấp nhất
    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
               JOIN datn.loai th ON sp.id_loai = th.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN (1, 2, 3)  AND th.id =:id
              GROUP BY spct.id, sp.ma, sp.ten, spct.gia_ban, spct.gia_nhap, spct.so_luong_ton, spct.trang_thai, sp.quai_deo, sp.dem_lot, sp.mo_ta, spct.gia_sau_giam
            )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien DESC LIMIT 5;
              """, nativeQuery = true)
    List<AdminThongKeSanPhamCaoResponse> sanPhamDoanhThuCaoNhatByLoai(Integer id);

    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.loai th ON sp.id_loai = th.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN (1, 2, 3)   AND th.id =:id
              GROUP BY spct.id, sp.ma, sp.ten, spct.gia_ban, spct.gia_nhap, spct.so_luong_ton, spct.trang_thai, sp.quai_deo, sp.dem_lot, sp.mo_ta, spct.gia_sau_giam
            )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien  LIMIT 5;
              """, nativeQuery = true)
    List<AdminThongKeSanPhamThapResponse> sanPhamDoanhThuThapNhatByLoai(Integer id);

    @Query(value = """
            SELECT  MONTH(hd.ngay_tao) AS thang, SUM(hd.tong_tien) AS tongTien\s
            FROM datn.san_pham_chi_tiet spct
            JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
            JOIN datn.loai th ON sp.id_loai = th.id
            JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
            JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don\s
            WHERE th.id =:id
            GROUP BY MONTH(hd.ngay_tao) ORDER BY MONTH(hd.ngay_tao)
               """, nativeQuery = true)
    List<AdminThongKeThangResponse> doanhThuTheoThangByLoai(Integer id);

    @Query(value = """
            WITH DoanhThuThuongHieu AS (
               SELECT
                  th.ten AS ten,
                   th.ma as ma,
                  SUM(hd.tong_tien) AS tongTien
               FROM datn.san_pham_chi_tiet spct
               JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
               JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
               JOIN datn.loai l ON sp.id_loai = l.id
               JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
               JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
               WHERE hd.trang_thai IN (1, 2, 3)  AND l.id =:id
               GROUP BY   th.ten,th.ma
            )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuThuongHieu GROUP BY ten,ma ORDER BY tongTien DESC;
              """, nativeQuery = true)
    List<AdminThongKeThuongHieuResponse> doanhThuTheoThuongHieuByLoai(Integer id);

    @Query(value = """
            WITH DoanhThuThuongHieu AS (
                                        SELECT
                                            l.ten AS ten,
                                            l.ma as ma,
                                            SUM(hd.tong_tien) AS tongTien
                                        FROM datn.san_pham_chi_tiet spct
                                        JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                        JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
                                        JOIN datn.loai l ON sp.id_loai = l.id
                                        JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                        JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                        WHERE hd.trang_thai IN (1, 2, 3)  AND l.id =:id
                                        GROUP BY   l.ten,l.ma
                                        )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuThuongHieu GROUP BY ten,ma ORDER BY tongTien DESC;
                """, nativeQuery = true)
    List<AdminThongKeLoaiResponse> doanhThuTheoLoaiByLoai(Integer id);

    // doanh số theo năm

    @Query(value = """
            SELECT SUM(hd.tong_tien) as tongTien
            FROM  datn.hoa_don hd
            where hd.trang_thai in(1,2,3) AND YEAR(hd.ngay_tao) =:year
            """, nativeQuery = true)
    Integer tongDoanhThuByNam(String year);

    @Query(value = """
            WITH DoanhThuSanPham AS (
                                       SELECT\s
                                            sp.ma as ma,
                                            sp.ten as ten,
                                            SUM(hd.tong_tien) AS tongTien
                                       FROM datn.san_pham_chi_tiet spct
                                       JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                       JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                       JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                       WHERE hd.trang_thai IN (1,2,3) AND  YEAR(hd.ngay_tao)  =:year
                                       GROUP BY spct.id, sp.ma, sp.ten, spct.gia_ban, spct.gia_nhap, spct.so_luong_ton, spct.trang_thai, sp.quai_deo, sp.dem_lot, sp.mo_ta, spct.gia_sau_giam
                                      )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien DESC LIMIT 5;
             """, nativeQuery = true)
    List<AdminThongKeSanPhamCaoResponse> sanPhamDoanhThuCaoNhatByNam(String year);

    @Query(value = """
            WITH DoanhThuSanPham AS (
                                       SELECT\s
                                            sp.ma as ma,
                                            sp.ten as ten,
                                            SUM(hd.tong_tien) AS tongTien
                                       FROM datn.san_pham_chi_tiet spct
                                       JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                       JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                       JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                       WHERE hd.trang_thai IN (1,2,3) AND  YEAR(hd.ngay_tao)  =:year
                                       GROUP BY spct.id, sp.ma, sp.ten, spct.gia_ban, spct.gia_nhap, spct.so_luong_ton, spct.trang_thai, sp.quai_deo, sp.dem_lot, sp.mo_ta, spct.gia_sau_giam
                                      )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien DESC LIMIT 5;
             """, nativeQuery = true)
    List<AdminThongKeSanPhamThapResponse> sanPhamDoanhThuThapNhatByNam(String year);

    @Query(value = """
            SELECT    MONTH(hd.ngay_tao) AS thang,  SUM(hd.tong_tien) AS tongTien\s
            FROM datn.hoa_don hd  WHERE	  YEAR(hd.ngay_tao) =:year
            GROUP BY MONTH(hd.ngay_tao) ORDER BY MONTH(hd.ngay_tao) ;
              """, nativeQuery = true)
    List<AdminThongKeThangResponse> doanhThuTheoThangByNam(String year);

    @Query(value = """
            WITH DoanhThuSanPham AS (
                                      SELECT\s
                                           th.ten AS ten,
                                           th.ma AS ma,
                                           SUM(hd.tong_tien) AS tongTien
                                      FROM datn.san_pham_chi_tiet spct
                                      JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                      JOIN datn.loai th ON sp.id_loai = th.id
                                      JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                      JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                      WHERE hd.trang_thai IN (1,2,3) AND  YEAR(hd.ngay_tao) =:year
                                      GROUP BY   th.ten,th.ma
                                     )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuSanPham GROUP BY ten,ma ORDER BY tongTien DESC;
              """, nativeQuery = true)
    List<AdminThongKeLoaiResponse> doanhThuTheoLoaiByNam(String year);

    @Query(value = """
            WITH DoanhThuSanPham AS (
                                      SELECT\s
                                           th.ten AS ten,
                                           th.ma AS ma,
                                           SUM(hd.tong_tien) AS tongTien
                                      FROM datn.san_pham_chi_tiet spct
                                      JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
                                      JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
                                      JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
                                      JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
                                      WHERE hd.trang_thai IN (1,2,3) AND  YEAR(hd.ngay_tao) =:year
                                      GROUP BY   th.ten,th.ma
                                     )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuSanPham GROUP BY ten,ma ORDER BY tongTien DESC;
              """, nativeQuery = true)
    List<AdminThongKeThuongHieuResponse> doanhThuTheoThuongHieuByNam(String year);


    // tổng doanh thu theo tháng
    @Query(value = """
            SELECT SUM(hd.tong_tien) as tongTien
            FROM datn.hoa_don hd
            WHERE hd.trang_thai IN (1, 2, 3) AND MONTH(hd.ngay_tao) BETWEEN :startMonth AND :endMonth
                        """, nativeQuery = true)
    Integer tongDoanhThuTheoThang(Integer startMonth, Integer endMonth);


    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT\s
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN (1, 2, 3) AND MONTH(hd.ngay_tao) BETWEEN :startMonth AND :endMonth
              GROUP BY spct.id, sp.ma, sp.ten, spct.gia_ban, spct.gia_nhap, spct.so_luong_ton, spct.trang_thai, sp.quai_deo, sp.dem_lot, sp.mo_ta, spct.gia_sau_giam
            )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien DESC LIMIT 5;                      
                       """, nativeQuery = true)
    List<AdminThongKeSanPhamCaoResponse> sanPhamDoanhThuCaoNhatByThang(Integer startMonth, Integer endMonth);

    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT\s
                sp.ma as ma,
                sp.ten as ten,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN (1, 2, 3) AND MONTH(hd.ngay_tao) BETWEEN :startMonth AND :endMonth
              GROUP BY spct.id, sp.ma, sp.ten, spct.gia_ban, spct.gia_nhap, spct.so_luong_ton, spct.trang_thai, sp.quai_deo, sp.dem_lot, sp.mo_ta, spct.gia_sau_giam
            )
            SELECT * FROM DoanhThuSanPham ORDER BY tongTien  LIMIT 5;                      
                       """, nativeQuery = true)
    List<AdminThongKeSanPhamThapResponse> sanPhamDoanhThuThapNhatByThang(Integer startMonth, Integer endMonth);

    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT\s
                th.ten AS ten,
                th.ma as ma,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.loai th ON sp.id_loai = th.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN (1, 2, 3) AND MONTH(hd.ngay_tao) BETWEEN :startMonth AND :endMonth
              GROUP BY   th.ten , th.ma\s
            )
            SELECT ten,ma,SUM(tongTien) AS tongTien
            FROM DoanhThuSanPham GROUP BY ten,ma ORDER BY tongTien DESC;                      
                       """, nativeQuery = true)
    List<AdminThongKeLoaiResponse> doanhThuTheoLoaiByThang(Integer startMonth, Integer endMonth);

    @Query(value = """
            WITH DoanhThuSanPham AS (
              SELECT\s
                th.ten AS ten,
                th.ma as ma,
                SUM(hd.tong_tien) AS tongTien
              FROM datn.san_pham_chi_tiet spct
              JOIN datn.san_pham sp ON spct.id_san_pham = sp.id
              JOIN datn.thuong_hieu th ON sp.id_thuong_hieu = th.id
              JOIN datn.hoa_don_chi_tiet hdct ON hdct.id_san_pham_chi_tiet = spct.id
              JOIN datn.hoa_don hd ON hd.id = hdct.id_hoa_don
              WHERE hd.trang_thai IN (1, 2, 3) AND MONTH(hd.ngay_tao) BETWEEN :startMonth AND :endMonth
              GROUP BY   th.ten ,th.ma\s
            )
            SELECT ten,ma, SUM(tongTien) AS tongTien
            FROM DoanhThuSanPham GROUP BY ten,ma ORDER BY tongTien DESC;                     
                      """, nativeQuery = true)
    List<AdminThongKeThuongHieuResponse> doanhThuTheoThuongHieuByThang(Integer startMonth, Integer endMonth);

    @Query(value = """
            SELECT    MONTH(hd.ngay_tao) AS thang,  SUM(hd.tong_tien) AS tongTien\s
            FROM datn.hoa_don hd  WHERE	 MONTH(hd.ngay_tao) BETWEEN :startMonth AND :endMonth
            GROUP BY MONTH(hd.ngay_tao) ORDER BY MONTH(hd.ngay_tao) ;                   
                    """, nativeQuery = true)
    List<AdminThongKeThangResponse> doanhThuTheoThangByThang(Integer startMonth, Integer endMonth);

}