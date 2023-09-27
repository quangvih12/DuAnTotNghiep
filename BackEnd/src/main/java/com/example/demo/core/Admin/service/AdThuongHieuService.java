package com.example.demo.core.Admin.service;

import com.example.demo.core.Admin.model.request.AdminThuongHieuRequest;
import com.example.demo.entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface AdThuongHieuService {
    Page<ThuongHieu> getAll(Integer page);

    ThuongHieu getById(Integer id);

    Page<ThuongHieu> search(String keyword, Integer page);

    HashMap<String, Object> add(AdminThuongHieuRequest dto);

    HashMap<String, Object> update(AdminThuongHieuRequest dto, Integer id);

    HashMap<String, Object> delete(AdminThuongHieuRequest dto, Integer id);

    void saveExcel(MultipartFile file);
}
