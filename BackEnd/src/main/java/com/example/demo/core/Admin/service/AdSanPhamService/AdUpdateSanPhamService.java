package com.example.demo.core.Admin.service.AdSanPhamService;

import com.example.demo.core.Admin.model.request.AdminSanPhamChiTietRequest;
import com.example.demo.core.Admin.model.request.AdminSanPhamRequest;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
import com.example.demo.entity.*;
import com.microsoft.azure.storage.StorageException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.List;

public interface AdUpdateSanPhamService {
    AdminSanPhamChiTietResponse update(AdminSanPhamChiTietRequest dto, Integer id) throws URISyntaxException, StorageException, InvalidKeyException, IOException;

    void mutitheard(SanPhamChiTiet sanPhamChiTiet, AdminSanPhamChiTietRequest request);

    List<Image> updateImage(SanPhamChiTiet sanPhamChiTiet, AdminSanPhamChiTietRequest dto) throws URISyntaxException, StorageException, InvalidKeyException, IOException;

    List<SizeChiTiet> updateSizeChiTiet(SanPhamChiTiet sanPhamChiTiet, AdminSanPhamChiTietRequest dto);

    List<MauSacChiTiet> updateMauSacChiTiet(SanPhamChiTiet sanPhamChiTiet, AdminSanPhamChiTietRequest dto) throws URISyntaxException, StorageException, InvalidKeyException, IOException;

    SanPham updateSanPham(SanPhamChiTiet sanPhamChiTiet, AdminSanPhamChiTietRequest dto) throws URISyntaxException, StorageException, InvalidKeyException, IOException;

    SanPham saveSanPham(AdminSanPhamRequest request);

    SanPhamChiTiet delete(Integer id);

    void deleteSize(Integer idSp, Integer idSize);

    void deleteMauSac(Integer idSp, Integer idMau);

    void deleteImg(Integer idSp, String img);
}
