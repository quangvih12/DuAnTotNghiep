package com.example.demo.core.khachHang.model.response;


import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface GioHangCTResponse {

    @Value("#{target.idGHCT}")
    Integer getIdGHCT();

    @Value("#{target.idCTSP}")
    Integer getIdCTSP();

    @Value("#{target.tenSP}")
    String getTenSP();

    @Value("#{target.anh}")
    String getAnh();

    @Value("#{target.tenMauSac}")
    Integer getTenMauSac();

    @Value("#{target.tenSize}")
    Integer getTenSize();

    @Value("#{target.giaBan}")
    BigDecimal getGiaBan();

    @Value("#{target.giaSPSauGiam}")
    BigDecimal getGiaSPSauGiam();

    @Value("#{target.soLuong}")
    Integer getSoLuong();

}