package com.example.demo.core.Admin.service.impl.HoaDon;

import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;
import com.example.demo.core.Admin.repository.AdHoaDonChiTietReponsitory;
import com.example.demo.core.Admin.repository.AdHoaDonReponsitory;
import com.example.demo.core.Admin.service.InterfaceHoaDon.AdminTatCaHoaDonService;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.infrastructure.status.HoaDonStatus;
import com.example.demo.util.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminTatCaHoaDonServiceImpl implements AdminTatCaHoaDonService {

    @Autowired
    private AdHoaDonChiTietReponsitory hdctRepo;

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
    public List<AdminHoaDonResponse> searchDate(LocalDateTime startDate, LocalDateTime endDate, String comboBoxValue) {
        return hoaDonReponsitory.getHoaDonByDate(startDate, endDate, comboBoxValue);
    }

    @Override
    public List<AdminHoaDonResponse> searchDateByTrangThai(LocalDateTime startDate, LocalDateTime endDate, String comboBoxValue, Integer trangThai) {
        return hoaDonReponsitory.getHoaDonByDateByTrangThai(startDate, endDate, comboBoxValue, trangThai);
    }

    @Override
    public AdminHoaDonResponse giaoHoaDonChoVanChuyen(Integer idHD, LocalDateTime ngayShip) {
        HoaDon hoaDon = hoaDonReponsitory.findById(idHD).get();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        if (hoaDon != null) {
            List<HoaDonChiTiet> lstHDCT = hdctRepo.findByIdHoaDon(idHD, sort);
            for (HoaDonChiTiet hdct : lstHDCT) {
                hdct.setTrangThai(HoaDonStatus.GIAO_CHO_DON_VI_VAN_CHUYEN);
                hdctRepo.save(hdct);
            }
            hoaDon.setNgaySua(DatetimeUtil.getCurrentDateAndTimeLocal());
            hoaDon.setNgayShip(ngayShip);
            hoaDon.setTrangThai(HoaDonStatus.GIAO_CHO_DON_VI_VAN_CHUYEN);
            HoaDon hd = hoaDonReponsitory.save(hoaDon);
            return hoaDonReponsitory.getByIds(hd.getId());
        } else {
            return null;
        }
    }

}
