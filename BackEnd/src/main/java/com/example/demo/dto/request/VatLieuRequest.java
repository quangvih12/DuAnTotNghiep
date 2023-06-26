package com.example.demo.dto.request;

import com.example.demo.adapter.DtoToEntity;
import com.example.demo.entity.VatLieu;
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
public class VatLieuRequest implements DtoToEntity<VatLieu> {

    private Integer id;

    @NotBlank(message = "Không bỏ trống tên")
    private String ten;

    private String ma;

    private String ngaySua;

    private String ngayTao;

    private Integer trangThai;

    private String moTa;

    @Override
    public VatLieu dtoToEntity(VatLieu vatLieu) {
        vatLieu.setMa(this.ma);
        vatLieu.setTen(this.ten);
        vatLieu.setNgayTao(DatetimeUtil.getCurrentDate());
        vatLieu.setTrangThai(this.trangThai);
        vatLieu.setMoTa(this.moTa);
        return vatLieu;
    }
}
