package com.example.demo.sevice;

import com.example.demo.dto.request.MauSacRequest;
import com.example.demo.dto.request.SizeRequest;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface MauSacService {
    Page<MauSac> getAll(Integer page);

    MauSac getById(Integer id);

    Page<MauSac> search(String keyword, Integer page);

    HashMap<String, Object> add(MauSacRequest dto);

    HashMap<String, Object> update(MauSacRequest dto, Integer id);

    HashMap<String, Object> delete(MauSacRequest dto, Integer id);

    void saveExcel(MultipartFile file);
}
