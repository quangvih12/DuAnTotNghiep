package com.example.demo.core.Admin.repository;

import com.example.demo.core.Admin.model.response.AdminUserResponse;
import com.example.demo.core.Admin.model.response.AdminUserVoucherResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.reponsitory.UserReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AdUserRepository extends UserReponsitory {

    @Query(value = """
             SELECT ROW_NUMBER() OVER(ORDER BY u.id DESC) AS stt,
                   u.id as id, u.anh as anh, u.email as email,
                   u.gioi_tinh as gioiTinh, u.ma as ma, u.ngay_sinh as ngaySinh,
                   u.password as pass, u.role as role, u.sdt as sdt,
                   u.ten as ten, u.trang_thai as trangThai, u.user_name as userName,
                   COUNT(hd.id) AS soLuongHoaDon
            FROM datn.user u
            LEFT JOIN datn.hoa_don hd ON u.id = hd.id_user
            WHERE u.role =:roles
            GROUP BY u.id, u.anh, u.email, u.gioi_tinh, u.ma, u.ngay_sinh, u.password, u.role, u.sdt, u.ten, u.trang_thai, u.user_name;
            """, nativeQuery = true)
    List<AdminUserResponse> findUserByRole(@Param("roles") String roles);


    @Query(value = """
             SELECT ROW_NUMBER() OVER(ORDER BY u.id DESC) AS stt,
                   u.id as id, u.anh as anh, u.email as email,
                   u.gioi_tinh as gioiTinh, u.ma as ma, u.ngay_sinh as ngaySinh,
                   u.password as pass, u.role as role, u.sdt as sdt,
                   u.ten as ten, u.trang_thai as trangThai, u.user_name as userName,
                   COUNT(hd.id) AS soLuongHoaDon
            FROM datn.user u
            LEFT JOIN datn.hoa_don hd ON u.id = hd.id_user
            GROUP BY u.id, u.anh, u.email, u.gioi_tinh, u.ma, u.ngay_sinh, u.password, u.role, u.sdt, u.ten, u.trang_thai, u.user_name;
            """, nativeQuery = true)
    List<AdminUserResponse> getAllUser();

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
    AdminUserResponse findUserById(@Param("id") Integer id);

    @Query(value = """
             select u.id as id, u.anh as anh, u.ma as ma, u.ten as ten, u.sdt as sdt, u.email as email, u.ngay_sinh as ngaySinh,
              sum(hd.tong_tien) as tongTienDaMua, u.role as role, u.trang_thai as trangThai, u.user_name as userName,
              u.gioi_tinh as gioiTinh, COUNT(hd.id) AS soLuongHoaDon, u.password as pass 
               from datn.user u\s
                join datn.hoa_don hd on u.id = hd.id_user
                 where u.role = 'USER'
             	group by anh,ma,ten,sdt,email,ngaySinh,id
             	having (CASE
             				WHEN :comboBoxValue = 'duoi1Trieu' THEN  tongTienDaMua < 1000000
             				WHEN :comboBoxValue = '1den3' THEN tongTienDaMua >= 1000000 and tongTienDaMua <= 3000000
             				WHEN :comboBoxValue = '3den6' THEN  tongTienDaMua >= 3000000 and tongTienDaMua <= 6000000
             				WHEN :comboBoxValue = '6den10' THEN  tongTienDaMua >= 6000000 and tongTienDaMua <= 10000000
             			END)\s
            """, nativeQuery = true)
    List<AdminUserVoucherResponse> getAllUserByTongTien(@Param("comboBoxValue") String cbbValue);

    Optional<User> findUsersByUserNameOrEmail(String userNam, String enail);

    User findByUserName(String username);

    Optional<User> findByEmail(String email);
}
