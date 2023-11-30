package com.example.demo.core.Admin.service;

import com.example.demo.core.Admin.model.request.BHTQHoaDonRequest;
import com.example.demo.core.Admin.model.request.BHTQUserRequest;
import com.example.demo.core.Admin.model.response.BHTQChiTietSanPhamResponse;
import com.example.demo.core.Admin.model.response.BHTQHoaDonChiTietResponse;
import com.example.demo.core.Admin.model.response.BHTQHoaDonResponse;
import com.example.demo.core.Admin.model.response.BHTQPhuongThucThanhToanResponse;
import com.example.demo.core.Admin.model.response.BHTQUserResponse;

import java.util.List;

public interface BanHangTaiQuayService {
    void taoHDCho(Integer idNV, Integer idKH);
    void huyHDCho(Integer idHoaDon);
    void thanhToanHD(Integer idHoaDon, BHTQHoaDonRequest dto);
    List<BHTQHoaDonResponse> getAllHDCho();
    List<BHTQHoaDonChiTietResponse> getAllHDCT(Integer idHoaDon);
    List<BHTQChiTietSanPhamResponse> getAllCTSP();
    BHTQHoaDonChiTietResponse addSPToHDCT(Integer idHoaDon, Integer idSPCT, Integer soLuong);
    void deleteHDCT(Integer idHDCT);
    List<BHTQPhuongThucThanhToanResponse> getAllPTTT();
    List<BHTQUserResponse> getAllKH();
    void updateKHForHD(Integer idHD, Integer idKH);
    BHTQUserResponse addOrUpdateKH(BHTQUserRequest dto);
}
