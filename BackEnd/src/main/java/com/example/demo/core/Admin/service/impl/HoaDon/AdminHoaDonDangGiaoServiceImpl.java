package com.example.demo.core.Admin.service.impl.HoaDon;

import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;
import com.example.demo.core.Admin.repository.AdHoaDonReponsitory;
import com.example.demo.core.Admin.service.InterfaceHoaDon.AdHoaDonDangGiaoService;
import com.example.demo.entity.HoaDon;
import com.example.demo.infrastructure.status.HoaDonStatus;
import com.example.demo.util.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminHoaDonDangGiaoServiceImpl implements AdHoaDonDangGiaoService {

    @Autowired
    private AdHoaDonReponsitory hoaDonReponsitory;

    @Override
    public AdminHoaDonResponse xacNhanHoaDon(Integer idHD) {
        HoaDon hoaDon = hoaDonReponsitory.findById(idHD).get();
        if (hoaDon != null) {
            hoaDon.setNgaySua(DatetimeUtil.getCurrentDateAndTimeLocal());
            hoaDon.setTrangThai(HoaDonStatus.HOAN_THANH);
            HoaDon hd = hoaDonReponsitory.save(hoaDon);
            return hoaDonReponsitory.getByIds(hd.getId());
        } else {
            return null;
        }
    }
}
