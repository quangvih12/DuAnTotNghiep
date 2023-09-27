package com.example.demo.core.Admin.service.impl.SanPham;

import com.example.demo.core.Admin.model.request.AdminSanPhamChiTietRequest;
import com.example.demo.core.Admin.service.AdSanPhamChiTietService;
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

public class UpdateSanPhamServiceIpml implements AdSanPhamChiTietService {
    @Override
    public Page<SanPhamChiTiet> getAll(Integer page, String upAndDown, Integer trangThai) {
        return null;
    }

    @Override
    public SanPhamChiTiet getOne(Integer id) {
        return null;
    }

    @Override
    public SanPhamChiTiet add(AdminSanPhamChiTietRequest dto) {
        return null;
    }

    @Override
    public HashMap<String, Object> update(AdminSanPhamChiTietRequest dto, Integer id) {
        return null;
    }

    @Override
    public HashMap<String, Object> delete(AdminSanPhamChiTietRequest dto, Integer id) {
        return null;
    }

    @Override
    public void saveExcel(MultipartFile file) throws IOException, StorageException, InvalidKeyException, URISyntaxException {

    }

    @Override
    public List<SanPhamChiTiet> exportCustomerToExcel(HttpServletResponse response) throws IOException {
        return null;
    }
}
