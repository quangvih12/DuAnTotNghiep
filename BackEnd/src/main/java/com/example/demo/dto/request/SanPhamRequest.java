package com.example.demo.dto.request;

import com.example.demo.adapter.DtoToEntity;
import com.example.demo.entity.Loai;
import com.example.demo.entity.SanPham;
import com.example.demo.entity.ThuongHieu;
import com.example.demo.util.DatetimeUtil;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SanPhamRequest implements DtoToEntity<SanPham> {

    private Integer id;

    @NotBlank(message = "không bỏ trống tên")
    private String ten;
    @NotBlank(message = "không bỏ trống mã")
    private String ma;

    private String ngaySua;

    private String ngayTao;

    @NotBlank(message = "không bỏ trống trạng thái")
    private String trangThai;

    private String moTa;

    @NotBlank(message = "không bỏ trống đệm lót")
    private String demLot;

    @NotBlank(message = "không bỏ trống quai đeo")
    private String quaiDeo;

    @NotBlank(message = "không bỏ trống thương hiệu")
    private String thuongHieu;

    @NotBlank(message = "không bỏ trống lọai")
    private String loai;

    @Override
    public SanPham dtoToEntity(SanPham sanPham) {
        sanPham.setMa(this.ma);
        sanPham.setTen(this.ten);
        sanPham.setNgayTao(DatetimeUtil.getCurrentDate());
        sanPham.setTrangThai(Integer.parseInt(this.trangThai));
        sanPham.setMoTa(this.moTa);
        sanPham.setDemLot(this.demLot);
        sanPham.setQuaiDeo(this.quaiDeo);
        sanPham.setThuongHieu(ThuongHieu.builder().id(Integer.parseInt(this.thuongHieu)).build());
        sanPham.setLoai(Loai.builder().id(Integer.parseInt(this.loai)).build());
        return sanPham;
    }
}
