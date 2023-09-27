package com.example.demo.core.Admin.service;


import com.example.demo.core.Admin.model.request.AdminChucVuRequest;
import com.example.demo.entity.ChucVu;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface AdChucVuService {
    Page<ChucVu> getAll(Integer page);

    ChucVu getById(Integer id);

    Page<ChucVu> search(String keyword, Integer page);

    HashMap<String, Object> add(AdminChucVuRequest dto);

    HashMap<String, Object> update(AdminChucVuRequest dto, Integer id);

    HashMap<String, Object> delete(AdminChucVuRequest dto, Integer id);

    void saveExcel(MultipartFile file);
}
