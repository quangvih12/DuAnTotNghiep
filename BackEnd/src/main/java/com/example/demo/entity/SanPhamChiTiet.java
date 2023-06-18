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
@Table(name = "san_pham_chi_tiet")
public class SanPhamChiTiet {
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

    @Column(name = "so_luong_ton")
    private Integer soLuongTon;

    @Column(name = "gia_ban")
    private BigDecimal giaBan;

    @Column(name = "gia_nhap")
    private BigDecimal giaNhap;

    @ManyToOne
    @JoinColumn(name = "id_san_pham")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "id_mau_sac")
    private MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "id_khuyen_mai")
    private KhuyenMai khuyenMai;

    @ManyToOne
    @JoinColumn(name = "id_vat_lieu")
    private VatLieu vatLieu;

    @ManyToOne
    @JoinColumn(name = "id_trong_luong")
    private TrongLuong trongLuong;

    @JsonIgnore
    @OneToMany(mappedBy = "sanPhamChiTiet", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Image> imageList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "sanPhamChiTiet", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<SizeChiTiet> sizeChiTiets = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "sanPhamChiTiet", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<MauSacChiTiet> mauSacChiTiets = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "sanPhamChiTiet", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<GioHangChiTiet> gioHangList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "sanPhamChiTiet", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<HoaDonChiTiet> donChiTietList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "sanPhamChiTiet", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<DanhSachYeuThich> danhSachYeuThichList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "sanPhamChiTiet", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Comment> commentList = new ArrayList<>();
}
