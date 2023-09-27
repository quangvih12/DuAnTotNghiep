package com.example.demo.core.Admin.model.request;

import com.example.demo.infrastructure.adapter.DtoToEntity;
import com.example.demo.entity.Loai;
import com.example.demo.entity.SanPham;
import com.example.demo.entity.ThuongHieu;
import com.example.demo.util.DatetimeUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminSanPhamRequest implements DtoToEntity<SanPham> {


    @NotBlank(message = "không bỏ trống tên")
    private String ten;

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
        sanPham.setTen(this.getTen());
        sanPham.setNgayTao(DatetimeUtil.getCurrentDate());
        sanPham.setTrangThai(1);
        sanPham.setMoTa(this.getMoTa());
        sanPham.setDemLot(this.getDemLot());
        sanPham.setQuaiDeo(this.getQuaiDeo());
        sanPham.setThuongHieu(ThuongHieu.builder().id(Integer.parseInt(this.getThuongHieu())).build());
        sanPham.setLoai(Loai.builder().id(Integer.parseInt(this.getLoai())).build());
        return sanPham;
    }
}
