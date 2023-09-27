package com.example.demo.core.Admin.model.request;

import com.example.demo.infrastructure.adapter.DtoToEntity;
import com.example.demo.entity.ChucVu;
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
public class ChucVuRequest implements DtoToEntity<ChucVu> {

    private Integer id;

    @NotBlank(message = "Không bỏ trống tên")
    private String ten;

    private String ma;

    private String ngaySua;

    private String ngayTao;

    private Integer trangThai;

    @Override
    public ChucVu dtoToEntity(ChucVu e) {
        e.setMa(this.ma);
        e.setNgayTao(DatetimeUtil.getCurrentDate());
        e.setTen(this.ten);
        e.setTrangThai(this.trangThai);
        return e;
    }
}
