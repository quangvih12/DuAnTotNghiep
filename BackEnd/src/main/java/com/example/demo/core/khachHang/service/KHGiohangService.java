package com.example.demo.core.khachHang.service;

import com.example.demo.core.khachHang.model.response.GioHangCTResponse;
import com.example.demo.core.khachHang.model.response.KhVoucherResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

public interface KHGiohangService {

    List<GioHangCTResponse> getListGHCT(String token);

    ResponseEntity<HttpStatus> deleteGioHangCT(Integer id);

    ResponseEntity<HttpStatus> deleteAllGioHangCT();

    GioHangCTResponse updateCongSoLuong(Integer id,String token);

    GioHangCTResponse updateTruSoLuong(Integer id,String token);

    HashMap<String, Object> updateMauSacSize(Integer idghct, Integer idSPCT);

    List<KhVoucherResponse> getListVoucher(String  token);
}
