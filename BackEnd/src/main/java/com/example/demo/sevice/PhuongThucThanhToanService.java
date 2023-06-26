package com.example.demo.sevice;

import com.example.demo.dto.request.PhuongThucThanhToanRequest;
import com.example.demo.entity.PhuongThucThanhToan;
import com.example.demo.entity.VatLieu;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public interface PhuongThucThanhToanService {
    Page<PhuongThucThanhToan> getAll(Integer page);

    PhuongThucThanhToan getById(Integer id);

    HashMap<String, Object> add(PhuongThucThanhToanRequest phuongThucThanhToan);

    HashMap<String, Object> update(PhuongThucThanhToanRequest phuongThucThanhToan, Integer id);

    HashMap<String, Object> delete(PhuongThucThanhToanRequest phuongThucThanhToan, Integer id);

}
