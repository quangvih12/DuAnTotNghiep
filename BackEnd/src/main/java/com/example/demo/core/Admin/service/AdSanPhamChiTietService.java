package com.example.demo.core.Admin.service;

import com.example.demo.core.Admin.model.request.AdminSanPhamChiTietRequest;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
import com.example.demo.entity.SanPhamChiTiet;
import com.microsoft.azure.storage.StorageException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.List;

public interface AdSanPhamChiTietService {

    Page<SanPhamChiTiet> getAll(Integer page, String upAndDown, Integer trangThai);

    SanPhamChiTiet getOne(Integer id);

    AdminSanPhamChiTietResponse add(AdminSanPhamChiTietRequest dto
                                    //  , MultipartFile[] files, MultipartFile[] file
    );

    AdminSanPhamChiTietResponse update(AdminSanPhamChiTietRequest dto, Integer id);

    SanPhamChiTiet delete( Integer id);

    void saveExcel(MultipartFile file) throws IOException, StorageException, InvalidKeyException, URISyntaxException;

    List<SanPhamChiTiet> exportCustomerToExcel(HttpServletResponse response) throws IOException;

    List<SanPhamChiTiet> getAllSPCTByKhuyenMai();

    HashMap<String, Object>  updateProductDetail(Integer productId, Integer idkm);
}
