package com.example.demo.core.Admin.model.request;

import com.example.demo.entity.ChucVu;
import com.example.demo.entity.Role;
import com.example.demo.entity.ThuongHieu;
import com.example.demo.entity.User;
import com.example.demo.infrastructure.adapter.DtoToEntity;
import com.example.demo.util.DatetimeUtil;
import com.example.demo.util.ImageToAzureUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
public class AdminUserRequest implements DtoToEntity<User> {

    private String ten;

    private Integer trangThai;

    private String userName;

    private String ngayTao;

    private String password;

    private String ngaySinh;

    private String image;

    private String email;

    private String sdt;

    private Integer gioiTinh;

    private String role;

    private Integer chucVu;

    @Autowired
    ImageToAzureUtil getImageToAzureUtil;

    @Override
    public User dtoToEntity(User user) {
        user.setTen(this.getTen());
        user.setTrangThai(this.getTrangThai());
        user.setNgayTao(DatetimeUtil.getCurrentDate());
        user.setPassword(this.getPassword());
        user.setNgaySinh(this.getNgaySinh());
        user.setImage(this.getImage());
        user.setEmail(this.getEmail());
        user.setSdt(this.getSdt());
        user.setGioiTinh(this.getGioiTinh());
        user.setRole(Role.valueOf(this.getRole()));
        user.setChucVu(ChucVu.builder().id(this.getChucVu()).build());
        return user;
    }
}
