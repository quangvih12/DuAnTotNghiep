package com.example.demo.sevice;

import com.example.demo.dto.request.LoaiRequest;
import com.example.demo.dto.request.TrongLuongRequest;
import com.example.demo.entity.Loai;
import com.example.demo.entity.TrongLuong;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface TrongLuongService {

    Page<TrongLuong> getAll(Integer page);

    TrongLuong getById(Integer id);

    Page<TrongLuong> search(String keyword, Integer page);

    HashMap<String, Object> add(TrongLuongRequest TrongLuong);

    HashMap<String,Object> update(TrongLuongRequest trongLuongRequest, Integer id);

    HashMap<String,Object> delete(TrongLuongRequest trongLuongRequest, Integer id);

    void saveExcel(MultipartFile file);
}
