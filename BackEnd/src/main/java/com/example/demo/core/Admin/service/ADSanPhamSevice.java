package com.example.demo.core.Admin.service;

import com.example.demo.core.Admin.model.request.AdminSanPhamRequest;
import com.example.demo.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface ADSanPhamSevice {

    Page<SanPham> getAll(Integer page);

    SanPham getById(Integer id);

    Page<SanPham> search(String keyword, Integer page);

    HashMap<String, Object> add(AdminSanPhamRequest dto);

    HashMap<String, Object> update(AdminSanPhamRequest dto, Integer id);

    HashMap<String, Object> delete(AdminSanPhamRequest dto, Integer id);

    void saveExcel(MultipartFile file);
}
