package com.example.demo.dto.request;

import com.example.demo.adapter.DtoToEntity;
import com.example.demo.entity.PhuongThucThanhToan;
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
public class PhuongThucThanhToanRequest implements DtoToEntity<PhuongThucThanhToan> {

    private Integer id;

    @NotBlank(message = "Không bỏ trống tên")
    private String ten;

    private String ma;

    private String ngaySua;

    private String ngayTao;

    private Integer trangThai;

    @Override
    public PhuongThucThanhToan dtoToEntity(PhuongThucThanhToan phuongThucThanhToan) {
        phuongThucThanhToan.setMa(this.ma);
        phuongThucThanhToan.setTen(this.ten);
        phuongThucThanhToan.setNgayTao(DatetimeUtil.getCurrentDate());
        phuongThucThanhToan.setTrangThai(this.trangThai);
        return phuongThucThanhToan;
    }
}
