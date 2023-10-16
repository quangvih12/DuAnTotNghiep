package com.example.demo.core.Admin.service.impl.HoaDon;

import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;
import com.example.demo.core.Admin.repository.AdHoaDonReponsitory;
import com.example.demo.core.Admin.service.InterfaceHoaDon.AdHoaDonChoXacNhanService;
import com.example.demo.entity.HoaDon;
import com.example.demo.infrastructure.status.HoaDonStatus;
import com.example.demo.util.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminHoaDonChoXacNhanServiceImpl implements AdHoaDonChoXacNhanService {

    @Autowired
    private AdHoaDonReponsitory hoaDonReponsitory;

    @Override
    public List<AdminHoaDonResponse> getHoaDonChoXacNhan() {
        return hoaDonReponsitory.getHoaDonTrangThai(HoaDonStatus.YEU_CAU_XAC_NHAN);
    }


    @Override
    public AdminHoaDonResponse huyHoaDonChoXacNhan(Integer idHD) {
        HoaDon hoaDon = hoaDonReponsitory.findById(idHD).get();
        if (hoaDon != null) {
            hoaDon.setNgaySua(DatetimeUtil.getCurrentDate());
            hoaDon.setTrangThai(HoaDonStatus.DA_HUY);
            HoaDon hd = hoaDonReponsitory.save(hoaDon);
            return hoaDonReponsitory.getByIds(hd.getId());
        } else {
            return null;
        }
    }

    @Override
    public AdminHoaDonResponse xacNhanHoaDon(Integer idHD) {
        HoaDon hoaDon = hoaDonReponsitory.findById(idHD).get();
        if (hoaDon != null) {
            hoaDon.setNgaySua(DatetimeUtil.getCurrentDate());
            hoaDon.setTrangThai(HoaDonStatus.DANG_CHUAN_BI_HANG);
            HoaDon hd = hoaDonReponsitory.save(hoaDon);
            return hoaDonReponsitory.getByIds(hd.getId());
        } else {
            return null;
        }
    }
}
