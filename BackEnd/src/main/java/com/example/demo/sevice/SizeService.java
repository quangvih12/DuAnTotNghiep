package com.example.demo.sevice;

import com.example.demo.dto.request.SizeRequest;
import com.example.demo.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface SizeService {
    Page<Size> getAll(Integer page);

    Size getById(Integer id);

    Page<Size> search(String keyword, Integer page);

    HashMap<String, Object> add(SizeRequest dto);

    HashMap<String, Object> update(SizeRequest dto, Integer id);

    HashMap<String, Object> delete(SizeRequest dto, Integer id);

    void saveExcel(MultipartFile file);
}
