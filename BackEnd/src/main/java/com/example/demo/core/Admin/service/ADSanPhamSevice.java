package com.example.demo.core.Admin.service;

import com.example.demo.core.Admin.model.request.SanPhamRequest;
import com.example.demo.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface SanPhamSevice {

    Page<SanPham> getAll(Integer page);

    SanPham getById(Integer id);

    Page<SanPham> search(String keyword, Integer page);

    HashMap<String, Object> add(SanPhamRequest dto);

    HashMap<String, Object> update(SanPhamRequest dto, Integer id);

    HashMap<String, Object> delete(SanPhamRequest dto, Integer id);

    void saveExcel(MultipartFile file);
}
