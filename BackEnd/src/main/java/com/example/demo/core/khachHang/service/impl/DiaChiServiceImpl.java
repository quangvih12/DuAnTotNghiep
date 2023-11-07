package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.Admin.model.request.AdminDiaChiRequest;
import com.example.demo.core.khachHang.repository.KHDiaChiRepository;
import com.example.demo.core.khachHang.repository.KHUserRepository;
import com.example.demo.core.khachHang.service.DiaChiService;
import com.example.demo.entity.DiaChi;
import com.example.demo.entity.User;
import com.example.demo.util.DataUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class DiaChiServiceImpl implements DiaChiService {

    @Autowired
    private KHUserRepository repository;

    @Autowired
    private KHDiaChiRepository DCrepository;

    @Override
    public List<DiaChi> getUserByDiaChi(Integer idUser) {
        return DCrepository.findDiaChiByIdUser(idUser);
    }

    @Override
    public HashMap<String, Object> addDiaChi(AdminDiaChiRequest request) {
        DiaChi diaChi = request.dtoToEntity(new DiaChi());
        try {
            DiaChi diaChis = DCrepository.save(diaChi);
            return DataUltil.setData("success", DCrepository.save(diaChis));
        } catch (Exception e) {
            return DataUltil.setData("error", "error");
        }
    }

    @Override
    public HashMap<String, Object> updateDiaChi(AdminDiaChiRequest request, Integer id) {
        Optional<DiaChi> optional = DCrepository.findById(id);
        if (optional.isPresent()) {
            DiaChi diaChi = optional.get();
            diaChi.setIdTinhThanh(request.getIdTinhThanh());
            diaChi.setTenTinhThanh(request.getTinhThanh());
            diaChi.setIdQuanHuyen(request.getIdQuanHuyen());
            diaChi.setTenQuanHuyen(request.getQuanHuyen());
            diaChi.setIdphuongXa(request.getIdPhuongXa());
            diaChi.setTenphuongXa(request.getPhuongXa());
            diaChi.setDiaChi(request.getDiaChi());
            diaChi.setUser(User.builder().id(request.getUser()).build());
            try {
                System.out.println(diaChi.toString());
                return DataUltil.setData("success", DCrepository.save(diaChi));
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy size để sửa");
        }
    }

    @Override
    public Optional<DiaChi> delete(Integer id) {
        Optional<DiaChi> optional = DCrepository.findById(id);
        if (optional.isPresent()) {
            DCrepository.deleteById(id);
        }
        return optional;
    }
}
