package com.example.demo.core.Admin.service;


import com.example.demo.core.Admin.model.request.ChucVuRequest;
import com.example.demo.entity.ChucVu;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface ChucVuService {
    Page<ChucVu> getAll(Integer page);

    ChucVu getById(Integer id);

    Page<ChucVu> search(String keyword, Integer page);

    HashMap<String, Object> add(ChucVuRequest dto);

    HashMap<String, Object> update(ChucVuRequest dto, Integer id);

    HashMap<String, Object> delete(ChucVuRequest dto, Integer id);

    void saveExcel(MultipartFile file);
}
