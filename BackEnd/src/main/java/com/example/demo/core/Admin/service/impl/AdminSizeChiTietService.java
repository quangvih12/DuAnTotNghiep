package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.model.request.AdminSizeChiTietRequest;
import com.example.demo.core.Admin.model.response.AdminSizeChiTietResponse;

public interface AdminSizeChiTietService {

    public AdminSizeChiTietResponse add(AdminSizeChiTietRequest request);

    public AdminSizeChiTietResponse update(Integer id, AdminSizeChiTietRequest request);

    public AdminSizeChiTietResponse delete(Integer id);

    Boolean check(Integer idSP, Integer idSize);
}
