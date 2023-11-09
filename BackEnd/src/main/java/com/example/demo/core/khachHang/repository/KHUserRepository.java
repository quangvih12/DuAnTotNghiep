package com.example.demo.core.khachHang.repository;

import com.example.demo.core.khachHang.model.response.KHUserResponse;
import com.example.demo.entity.User;
import com.example.demo.reponsitory.UserReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KHUserRepository extends UserReponsitory {

    User findAllByUserName(String userName);

    @Query(value = "SELECT u.email, u.gioi_tinh, u.anh, u.ngay_sinh, u.sdt, u.ten, u.user_name FROM datn.user u WHERE u.id =:id", nativeQuery = true)
    KHUserRepository get(@Param("id") Integer id);

    @Query(value = """
             SELECT ROW_NUMBER() OVER(ORDER BY u.id DESC) AS stt,
                   u.id as id, u.anh as anh, u.email as email,
                   u.gioi_tinh as gioiTinh, u.ma as ma, u.ngay_sinh as ngaySinh,
                   u.password as pass, u.role as role, u.sdt as sdt,
                   u.ten as ten, u.trang_thai as trangThai, u.user_name as userName,
                   COUNT(hd.id) AS soLuongHoaDon
            FROM datn.user u
            LEFT JOIN datn.hoa_don hd ON u.id = hd.id_user
            WHERE u.id =:id
            GROUP BY u.id, u.anh, u.email, u.gioi_tinh, u.ma, u.ngay_sinh, u.password, u.role, u.sdt, u.ten, u.trang_thai, u.user_name;
            """, nativeQuery = true)
    KHUserResponse findByIdUser(@Param("id") Integer id);

    @Query(value = """
             SELECT ROW_NUMBER() OVER(ORDER BY u.id DESC) AS stt,
                   u.id as id, u.anh as anh, u.email as email,
                   u.gioi_tinh as gioiTinh, u.ma as ma, u.ngay_sinh as ngaySinh,
                   u.password as pass, u.role as role, u.sdt as sdt,
                   u.ten as ten, u.trang_thai as trangThai, u.user_name as userName,
                   COUNT(hd.id) AS soLuongHoaDon
            FROM datn.user u
            LEFT JOIN datn.hoa_don hd ON u.id = hd.id_user
            WHERE u.id =:id
            GROUP BY u.id, u.anh, u.email, u.gioi_tinh, u.ma, u.ngay_sinh, u.password, u.role, u.sdt, u.ten, u.trang_thai, u.user_name;
            """, nativeQuery = true)
    KHUserResponse findUserById(@Param("id") Integer id);
}
