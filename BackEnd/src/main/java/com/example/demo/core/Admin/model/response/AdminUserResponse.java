package com.example.demo.core.Admin.model.response;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.infrastructure.adapter.DtoToEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminUserResponse  {

    private Integer id;

    @NotBlank(message = "không bỏ trống tên")
    private String email;

    private Integer gioiTinh;

    private String ma;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String sdt;

    private String ten;

    private String userName;


    public AdminUserResponse (User e) {
       this.id = e.getId();
       this.email = e.getEmail();
       this.gioiTinh = e.getGioiTinh();
       this.ma = e.getMa();
       this.password = e.getPassword();
       this.role = e.getRole();
       this.sdt = e.getSdt();
       this.ten = e.getTen();
       this.userName = e.getUserName();


    }
}
