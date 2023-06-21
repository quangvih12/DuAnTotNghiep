package com.example.demo.sevice;

import com.example.demo.dto.request.ImageRequest;
import com.example.demo.dto.request.SanPhamChiTietRequest;
import com.example.demo.entity.SanPhamChiTiet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface SanPhamChiTietService {

    Page<SanPhamChiTiet> getAll(Integer page, String upAndDown, Integer trangThai);

    SanPhamChiTiet getOne(Integer id);

    HashMap<String, Object> add(SanPhamChiTietRequest dto, MultipartFile file, Integer idSize, Integer idMauSac);

    HashMap<String, Object> update(SanPhamChiTietRequest dto, Integer id);

    HashMap<String, Object> delete(SanPhamChiTietRequest dto, Integer id);

    void saveExcel(MultipartFile file);

    List<SanPhamChiTiet> exportCustomerToExcel(HttpServletResponse response) throws IOException;
}
