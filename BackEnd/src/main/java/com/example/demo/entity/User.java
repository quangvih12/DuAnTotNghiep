package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "ten")
    private String ten;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ngay_sua")
    private String ngaySua;

    @Column(name = "ngay_tao")
    private String ngayTao;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "ngay_sinh")
    private String ngaySinh;

    @Column(name = "anh")
    private String image;

    @Column(name = "email")
    private String email;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "gioi_tinh")
    private Integer gioiTinh;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "id_chuc_vu")
    private ChucVu chucVu;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ThongBao> thongBaoList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<DiaChi> diaChiList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<GioHang> gioHangList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<HoaDon> hoaDonList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Comment> commentList = new ArrayList<>();


}
