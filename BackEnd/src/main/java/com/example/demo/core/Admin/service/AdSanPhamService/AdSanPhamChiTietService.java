package com.example.demo.core.Admin.service.AdSanPhamService;

import com.example.demo.core.Admin.model.request.AdminSanPhamChiTietRequest;
import com.example.demo.core.Admin.model.request.AdminSanPhamRequest;
import com.example.demo.core.Admin.model.request.AdminSearchRequest;
import com.example.demo.core.Admin.model.response.AdminMauSacChiTietResponse;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
import com.example.demo.core.Admin.model.response.AdminSizeChiTietResponse;
import com.example.demo.entity.*;
import com.microsoft.azure.storage.StorageException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.List;

public interface AdSanPhamChiTietService {

    List<AdminSanPhamChiTietResponse> getList(AdminSearchRequest request);

    List<AdminSanPhamChiTietResponse> loc(String comboBoxValue);

    List<Image> getProductImages(Integer idProduct);

    List<Image> getProductImages();

    List<AdminSizeChiTietResponse> getProductSize(Integer idProduct);

    List<AdminMauSacChiTietResponse> getProductMauSac(Integer idProduct);

    AdminSanPhamChiTietResponse get(Integer id);

    Boolean findBySanPhamTen(String ten);

    AdminSanPhamChiTietResponse add(AdminSanPhamChiTietRequest request) throws IOException, StorageException, InvalidKeyException, URISyntaxException;

    SanPham saveSanPham(AdminSanPhamRequest request);

    void mutitheard(SanPhamChiTiet sanPhamChiTiet, AdminSanPhamChiTietRequest request);

    List<MauSacChiTiet> saveMauSac( List<SizeChiTiet> sizes,List<String> idMauSac, List<String> imgMauSac,List<String> lstSoLuong, SanPhamChiTiet sanPhamChiTiet) throws IOException, StorageException, InvalidKeyException, URISyntaxException;

    List<SizeChiTiet> saveSize(List<String> idSize, SanPhamChiTiet sanPhamChiTiet, List<String> soLuongSize);

    List<Image> saveImage(SanPhamChiTiet sanPhamChiTiet, List<String> imgSanPham) throws IOException, StorageException, InvalidKeyException, URISyntaxException;
}
