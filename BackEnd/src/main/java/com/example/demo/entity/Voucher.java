package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "voucher")
@Builder
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ten;

    private LocalDateTime thoiGianBatDau;

    private LocalDateTime thoiGianKetThuc;

    @Column(length = 10000)
    private String moTa;

    @Column(precision = 20, scale = 0)
    private BigDecimal giamToiDa;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "gia_tri_giam")
    private Integer giaTriGiam;

    private Integer soLuong;
}
