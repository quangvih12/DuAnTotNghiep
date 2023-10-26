package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@ToString
@Entity
@Table(name = "size_ctsp")
public class SizeChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "ngay_sua")
    private String ngaySua;

    @Column(name = "ngay_tao")
    private String ngayTao;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @ManyToOne
    @JoinColumn(name = "id_size")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "id_ctsp")
    private SanPhamChiTiet sanPhamChiTiet;

    @JsonIgnore
    @OneToMany(mappedBy = "sizeChiTiet", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<MauSacChiTiet> lstMauSacChiTiets = new ArrayList<>();
}
