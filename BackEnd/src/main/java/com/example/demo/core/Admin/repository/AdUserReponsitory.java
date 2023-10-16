package com.example.demo.core.Admin.repository;

import com.example.demo.core.Admin.model.response.AdminUserResponse;
import com.example.demo.entity.Role;
import com.example.demo.reponsitory.UserReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdUserReponsitory extends UserReponsitory {
    @Query(value = """
             SELECT ROW_NUMBER() OVER(ORDER BY u.id DESC) AS stt,
                   u.id as id, u.anh as anh, u.email as email,
                   u.gioi_tinh as gioiTinh, u.ma as ma, u.ngay_sinh as ngaySinh,
                   u.password as pass, u.role as roles, u.sdt as sdt,
                   u.ten as ten, u.trang_thai as trangThai, u.user_name as userName,
                   COUNT(hd.id) AS soLuongHoaDon
            FROM datn.user u
            LEFT JOIN datn.hoa_don hd ON u.id = hd.id_user
            WHERE u.role =:roless
            GROUP BY u.id, u.anh, u.email, u.gioi_tinh, u.ma, u.ngay_sinh, u.password, u.role, u.sdt, u.ten, u.trang_thai, u.user_name;
            """, nativeQuery = true)
    List<AdminUserResponse> getAllByRole(String roless);
}
