package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "hoa_don")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "tong_tien", precision = 20, scale = 0)
    private BigDecimal tongTien;

    @Column(name = "ten_nguoi_nhan")
    private String tenNguoiNhan;

    @Column(name = "ngay_nhan")
    private String ngayNhan;

    @Column(name = "tien_ship", precision = 20, scale = 0)
    private BigDecimal tienShip;

    @Column(name = "tien_sau_khi_giam_gia", precision = 20, scale = 0)
    private BigDecimal tienSauKhiGiam;

    @Column(name = "ngay_sua")
    private String ngaySua;

    @Column(name = "ngay_tao")
    private String ngayTao;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "ngay_thanh_toan")
    private String ngayThanhToan;

    @Column(name = "ngay_ship")
    private String ngayShip;

    @Column(name = "hinh_thuc_giao_hang")
    private Integer hinhThucGiaoHang;

    @ManyToOne
    @JoinColumn(name = "id_dia_chi_sdt")
    private DiaChi diaChi;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_phuong_thuc_thanh_toan")
    private PhuongThucThanhToan phuongThucThanhToan;


    @JsonIgnore
    @OneToMany(mappedBy = "hoaDon", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<HoaDonChiTiet> hoaDonChiTietList = new ArrayList<>();

}
