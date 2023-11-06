package com.example.demo.core.Admin.model.request;

import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.entity.Size;
import com.example.demo.entity.SizeChiTiet;
import com.example.demo.infrastructure.adapter.DtoToEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSizeChiTietRequest implements DtoToEntity<SizeChiTiet> {

    private Integer idSize;

    private Integer idSanPhamChiTiet;

    @Override
    public SizeChiTiet dtoToEntity(SizeChiTiet e) {
        e.setSanPhamChiTiet(SanPhamChiTiet.builder().id(this.getIdSanPhamChiTiet()).build());
        e.setSize(Size.builder().id(this.getIdSize()).build());
        return e;
    }
}
