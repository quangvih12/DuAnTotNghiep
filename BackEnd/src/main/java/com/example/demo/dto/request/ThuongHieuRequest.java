package com.example.demo.dto.request;

import com.example.demo.adapter.DtoToEntity;
import com.example.demo.entity.ThuongHieu;
import com.example.demo.util.DatetimeUtil;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThuongHieuRequest implements DtoToEntity<ThuongHieu> {

    private Integer id;

    @NotBlank(message = "Không bỏ trống tên")
    private String ten;

    private String ma;

    private String ngaySua;

    private String ngayTao;

    private Integer trangThai;

    @Override
    public ThuongHieu dtoToEntity(ThuongHieu thuongHieu) {
        thuongHieu.setMa(this.ma);
        thuongHieu.setTen(this.ten);
        thuongHieu.setTrangThai(this.trangThai);
        thuongHieu.setNgayTao(DatetimeUtil.getCurrentDate());
        return thuongHieu;
    }
}
