package com.example.demo.core.Admin.service.AdSanPhamService;

import com.example.demo.core.Admin.model.request.AdminSanPhamRepuest2;
import com.example.demo.core.Admin.model.response.AdminImageResponse;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTiet2Response;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
import com.example.demo.core.Admin.model.response.AdminSanPhamResponse;
import com.example.demo.entity.Image;
import com.example.demo.entity.SanPham;
import com.example.demo.entity.SanPhamChiTiet;
import com.microsoft.azure.storage.StorageException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.List;

public interface AdSanPhamService {


    List<AdminSanPhamResponse> getAll();

    AdminSanPhamResponse findByIdSP(Integer id);

    List<AdminSanPhamChiTiet2Response> findBySanPhamCT(Integer id);

    List<AdminImageResponse> getProductImages(Integer idProduct);

    Boolean findBySanPhamTen(AdminSanPhamRepuest2 request);

    List<AdminSanPhamResponse> loc(String comboBoxValue);

    AdminSanPhamResponse save(AdminSanPhamRepuest2 adminSanPhamRepuest2) throws IOException, StorageException, InvalidKeyException, URISyntaxException;


    List<SanPhamChiTiet> saveSanPhamChiTiet(AdminSanPhamRepuest2 repuest2, SanPham sanPham) throws URISyntaxException, StorageException, InvalidKeyException, IOException;

    AdminSanPhamResponse delete(Integer id);

    AdminSanPhamResponse khoiPhuc(Integer id);

    List<AdminSanPhamChiTiet2Response> locCTSP(String comboBoxValue);

    List<AdminSanPhamResponse> getSanPhamByIdLoai( Integer idloai);

    List<AdminSanPhamResponse> getSanPhamByIdThuongHieu( Integer idthuonghieu);
}
