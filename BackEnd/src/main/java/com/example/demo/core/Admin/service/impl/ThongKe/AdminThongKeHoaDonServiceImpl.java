package com.example.demo.core.Admin.service.impl.ThongKe;

import com.example.demo.core.Admin.repository.AdHoaDonReponsitory;
import com.example.demo.core.Admin.service.AdThongKeService.AdThongKeHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminThongKeHoaDonServiceImpl implements AdThongKeHoaDonService {

    @Autowired
    private AdHoaDonReponsitory hoaDonReponsitory;

    @Override
    public Integer tongDonhang() {
        return null;
    }

    @Override
    public Integer tongDonhangHoanThanh() {
        return null;
    }

    @Override
    public Integer tongDonhangDangGiao() {
        return null;
    }

    @Override
    public Integer tongDonhangHuy() {
        return null;
    }

    @Override
    public Integer tongDonhangHoanTra() {
        return null;
    }
}
