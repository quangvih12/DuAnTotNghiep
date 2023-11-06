package com.example.demo.core.Admin.model.request;

import com.example.demo.entity.MauSac;
import com.example.demo.entity.MauSacChiTiet;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.entity.SizeChiTiet;
import com.example.demo.infrastructure.adapter.DtoToEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminMauSacChiTietRequest implements DtoToEntity<MauSacChiTiet> {

    private String anh;

    private Integer idSizeChiTiet;

    private Integer idSanPhamChiTiet;

    private Integer idMauSac;

    private Integer soLuong;


    @Override
    public MauSacChiTiet dtoToEntity(MauSacChiTiet e) {
        if(this.getIdSizeChiTiet() == null){
            e.setSizeChiTiet(null);
        }else {
            e.setSizeChiTiet(SizeChiTiet.builder().id(this.getIdSizeChiTiet()).build());
        }
        e.setAnh(this.getAnh());
        e.setSanPhamChiTiet(SanPhamChiTiet.builder().id(this.getIdSanPhamChiTiet()).build());
        e.setMauSac(MauSac.builder().id(this.getIdMauSac()).build());
        e.setSoLuong(this.getSoLuong());
        return e;
    }
}
