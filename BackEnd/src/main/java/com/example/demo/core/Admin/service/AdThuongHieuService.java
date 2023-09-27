package com.example.demo.core.Admin.service;

import com.example.demo.core.Admin.model.request.LoaiRequest;
import com.example.demo.core.Admin.model.request.ThuongHieuRequest;
import com.example.demo.entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface ThuongHieuService {
    Page<ThuongHieu> getAll(Integer page);

    ThuongHieu getById(Integer id);

    Page<ThuongHieu> search(String keyword, Integer page);

    HashMap<String, Object> add(ThuongHieuRequest dto);

    HashMap<String, Object> update(ThuongHieuRequest dto, Integer id);

    HashMap<String, Object> delete(ThuongHieuRequest dto, Integer id);

    void saveExcel(MultipartFile file);
}
