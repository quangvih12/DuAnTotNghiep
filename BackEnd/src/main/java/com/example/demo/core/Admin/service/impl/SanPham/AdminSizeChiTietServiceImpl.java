package com.example.demo.core.Admin.service.impl.SanPham;

import com.example.demo.core.Admin.model.request.AdminSizeChiTietRequest;
import com.example.demo.core.Admin.model.response.AdminSizeChiTietResponse;
import com.example.demo.core.Admin.repository.AdSizeChiTietReponsitory;
import com.example.demo.core.Admin.service.impl.AdminSizeChiTietService;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.entity.Size;
import com.example.demo.entity.SizeChiTiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminSizeChiTietServiceImpl implements AdminSizeChiTietService {

    @Autowired
    private AdSizeChiTietReponsitory adSizeChiTietReponsitory;

    @Override
    public AdminSizeChiTietResponse add(AdminSizeChiTietRequest request) {
        SizeChiTiet sizeChiTiet = request.dtoToEntity(new SizeChiTiet());
        SizeChiTiet ChiTiet = adSizeChiTietReponsitory.save(sizeChiTiet);
        return adSizeChiTietReponsitory.findBySizeChiTiet(ChiTiet.getId());
    }

    @Override
    public AdminSizeChiTietResponse update(Integer id, AdminSizeChiTietRequest request) {
        Optional<SizeChiTiet> optional = adSizeChiTietReponsitory.findById(id);
        if (optional.isPresent()) {
            SizeChiTiet sizeChiTiet = optional.get();
            sizeChiTiet.setSanPhamChiTiet(SanPhamChiTiet.builder().id(request.getIdSanPhamChiTiet()).build());
            sizeChiTiet.setSize(Size.builder().id(request.getIdSize()).build());
            adSizeChiTietReponsitory.save(sizeChiTiet);
            return adSizeChiTietReponsitory.findBySizeChiTiet(id);
        }
        return null;
    }

    @Override
    public AdminSizeChiTietResponse delete(Integer id) {
        adSizeChiTietReponsitory.deleteById(id);
        return adSizeChiTietReponsitory.findBySizeChiTiet(id);
    }

    @Override
    public Boolean check(Integer idSP, Integer idSize) {
        SizeChiTiet chiTiet = adSizeChiTietReponsitory.findSizeChiTietBySanPhamChiTietAndSize(idSP, idSize);
        if (chiTiet == null) {
            return true;
        } else {
            return false;
        }
    }
}
