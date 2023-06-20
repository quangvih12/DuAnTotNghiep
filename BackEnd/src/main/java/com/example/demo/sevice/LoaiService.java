package com.example.demo.sevice;

import com.example.demo.entity.Loai;
import com.example.demo.dto.request.LoaiRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;

public interface LoaiService {
    Page<Loai> getAll(Integer page);

    Loai getById(Integer id);

    Page<Loai> search(String keyword, Integer page);

    HashMap<String, Object> add(LoaiRequest loai);

    HashMap<String,Object> update(LoaiRequest loai, Integer id);

    HashMap<String,Object> delete(LoaiRequest loai, Integer id);
}
