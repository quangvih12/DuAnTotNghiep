package com.example.demo.core.Admin.service.AdSanPhamService;

import com.example.demo.core.Admin.model.request.AdminSanPhamChiTietRequest;
import com.example.demo.core.Admin.model.request.AdminSanPhamRequest;
import com.example.demo.core.Admin.model.request.AdminSearchRequest;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
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

    List<SizeChiTiet> getProductSize(Integer idProduct);

    List<MauSacChiTiet> getProductMauSac(Integer idProduct);

    AdminSanPhamChiTietResponse get(Integer id);

    Boolean findBySanPhamTen(String ten);

    AdminSanPhamChiTietResponse add(AdminSanPhamChiTietRequest request) throws IOException, StorageException, InvalidKeyException, URISyntaxException;

    SanPham saveSanPham(AdminSanPhamRequest request);

    void mutitheard(SanPhamChiTiet sanPhamChiTiet, AdminSanPhamChiTietRequest request);

    Iterable<MauSacChiTiet> saveMauSac(List<String> idMauSac, List<String> imgMauSac, SanPhamChiTiet sanPhamChiTiet) throws IOException, StorageException, InvalidKeyException, URISyntaxException;

    Iterable<SizeChiTiet> saveSize(List<String> idSize, SanPhamChiTiet sanPhamChiTiet, List<String> soLuongSize);

    Iterable<Image> saveImage(SanPhamChiTiet sanPhamChiTiet, List<String> imgSanPham) throws IOException, StorageException, InvalidKeyException, URISyntaxException;
}
