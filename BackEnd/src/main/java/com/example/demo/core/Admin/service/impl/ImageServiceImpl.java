package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.repository.AdImageReponsitory;
import com.example.demo.core.Admin.service.AdImageServie;
import com.example.demo.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements AdImageServie {

    @Autowired
    private AdImageReponsitory repo;

    @Override
    public List<Image> findByIdCTSP(Integer id) {
        return repo.findBySanPhamId(id);
    }
}
