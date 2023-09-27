package com.example.demo.core.Admin.service;

import com.example.demo.entity.Loai;
import com.example.demo.core.Admin.model.request.AdminLoaiRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface AdLoaiService {
    Page<Loai> getAll(Integer page);

    Loai getById(Integer id);

    Page<Loai> search(String keyword, Integer page);

    HashMap<String, Object> add(AdminLoaiRequest loai);

    HashMap<String,Object> update(AdminLoaiRequest loai, Integer id);

    HashMap<String,Object> delete(AdminLoaiRequest loai, Integer id);

    void saveExcel(MultipartFile file);
}
