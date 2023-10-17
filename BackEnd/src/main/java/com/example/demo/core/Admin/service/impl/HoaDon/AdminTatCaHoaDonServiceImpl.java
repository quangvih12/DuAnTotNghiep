package com.example.demo.core.Admin.service.impl.HoaDon;

import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;
import com.example.demo.core.Admin.repository.AdHoaDonReponsitory;
import com.example.demo.core.Admin.service.InterfaceHoaDon.AdminTatCaHoaDonService;
import com.example.demo.entity.HoaDon;
import com.example.demo.infrastructure.status.HoaDonStatus;
import com.example.demo.util.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class AdminTatCaHoaDonServiceImpl implements AdminTatCaHoaDonService {

    @Autowired
    private AdHoaDonReponsitory hoaDonReponsitory;

    @Override
    public List<AdminHoaDonResponse> getAll() {
        return hoaDonReponsitory.getAll();
    }

    @Override
    public List<AdminHoaDonResponse> getHoaDonTrangThai(Integer trangThai) {
        return hoaDonReponsitory.getHoaDonTrangThai(trangThai);
    }

    @Override
    public List<AdminHoaDonResponse> getHoaDonHoanThanh() {
        return hoaDonReponsitory.getHoaDonTrangThai(HoaDonStatus.HOAN_THANH);
    }
    @Override
    public List<AdminHoaDonResponse> getHoaDonHuy() {
        return hoaDonReponsitory.getHoaDonTrangThai(HoaDonStatus.DA_HUY);
    }

    @Override
    public List<AdminHoaDonResponse> getHoaDonDangGiao() {
        return hoaDonReponsitory.getHoaDonTrangThai(HoaDonStatus.GIAO_CHO_DON_VI_VAN_CHUYEN);
    }


    @Override
    public List<AdminHoaDonResponse> getHoaDonYeuCauDoiTra() {
        return hoaDonReponsitory.getHoaDonTrangThai(HoaDonStatus.YEU_CAU_DOI_TRA);
    }

    @Override
    public List<AdminHoaDonResponse> getHoaDonXacNhanDoiTra() {
        return hoaDonReponsitory.getHoaDonTrangThai(HoaDonStatus.XAC_NHAN_DOI_TRA);
    }

    @Override
    public List<AdminHoaDonResponse> getHoaDonDangChuanBiHang() {
        return hoaDonReponsitory.getHoaDonTrangThai(HoaDonStatus.DANG_CHUAN_BI_HANG);
    }

    @Override
    public List<AdminHoaDonResponse> searchDate(Date startDate, Date endDate) {
        List<AdminHoaDonResponse> test = hoaDonReponsitory.getHoaDonByDate(startDate,endDate);
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        test.forEach(i -> System.out.println(i.getIdHD()));
        return hoaDonReponsitory.getHoaDonByDate(startDate,endDate);
    }

    public AdminHoaDonResponse giaoHoaDonChoVanChuyen(Integer idHD) {
        HoaDon hoaDon = hoaDonReponsitory.findById(idHD).get();
        if (hoaDon != null) {
            hoaDon.setNgaySua(DatetimeUtil.getCurrentDateAndTimeLocal());
            hoaDon.setTrangThai(HoaDonStatus.GIAO_CHO_DON_VI_VAN_CHUYEN);
            HoaDon hd = hoaDonReponsitory.save(hoaDon);
            return hoaDonReponsitory.getByIds(hd.getId());
        } else {
            return null;
        }
    }

}
