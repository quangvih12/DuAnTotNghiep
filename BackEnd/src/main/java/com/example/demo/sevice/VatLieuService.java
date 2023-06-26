package com.example.demo.sevice;

import com.example.demo.dto.request.VatLieuRequest;
import com.example.demo.entity.VatLieu;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface VatLieuService {
    Page<VatLieu> getAll(Integer page);

    VatLieu getById(Integer id);

    HashMap<String, Object> add(VatLieuRequest vatLieu);

    HashMap<String, Object> update(VatLieuRequest vatLieu, Integer id);

    HashMap<String, Object> delete(VatLieuRequest vatLieu, Integer id);

    void saveExcel(MultipartFile file);
}
