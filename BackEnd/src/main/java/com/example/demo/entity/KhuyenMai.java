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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
@Entity
@Table(name = "Khuyen_mai")
public class KhuyenMai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ten")
    private String ten;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ngay_sua")
    private String ngaySua;

    @Column(name = "ngay_tao")
    private String ngayTao;

    @Column(name = "mo_ta", length = 10000)
    private String moTa;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "thoi_gian_bat_dau")
    private String thoiGianBatDau;

    @Column(name = "thoi_gian_ket_thuc")
    private String thoiGianKetThuc;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(precision = 20, scale = 0)
    private BigDecimal dieuKien;

    @Enumerated(EnumType.STRING)
    private KieuGiamGia kieuGiamGia;

    @Column(precision = 20, scale = 0)
    private BigDecimal giaTriGiam;

    @Column(precision = 20, scale = 0)
    private BigDecimal giamToiDa;
}
