package com.example.demo.core.Admin.service;

import com.example.demo.core.Admin.model.request.AdminMauSacRequest;
import com.example.demo.entity.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface AdMauSacService {
    Page<MauSac> getAll(Integer page);

    MauSac getById(Integer id);

    Page<MauSac> search(String keyword, Integer page);

    HashMap<String, Object> add(AdminMauSacRequest dto);

    HashMap<String, Object> update(AdminMauSacRequest dto, Integer id);

    HashMap<String, Object> delete(AdminMauSacRequest dto, Integer id);

    void saveExcel(MultipartFile file);
}
