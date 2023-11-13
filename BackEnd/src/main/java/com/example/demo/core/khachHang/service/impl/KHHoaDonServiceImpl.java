package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.khachHang.model.request.hoadon.HoaDonRequest;
import com.example.demo.core.khachHang.model.response.KHHoaDonResponse;
import com.example.demo.core.khachHang.repository.KHHoaDonChiTietRepository;
import com.example.demo.core.khachHang.repository.KHHoaDonRepository;
import com.example.demo.core.khachHang.repository.KHchiTietSanPhamRepository;
import com.example.demo.core.khachHang.service.KHHoaDonService;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.infrastructure.status.HoaDonStatus;
import com.example.demo.util.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KHHoaDonServiceImpl implements KHHoaDonService {

    @Autowired
    private KHHoaDonRepository hdRepo;

    @Autowired
    private KHHoaDonChiTietRepository hdctRepo;

    @Autowired
    private KHchiTietSanPhamRepository ctspRepo;

    @Override
    public List<KHHoaDonResponse> getAll(Integer id) {
        return hdRepo.getHoaDonByIdUser(id);
    }

    @Override
    public List<KHHoaDonResponse> getHoaDonTrangThai(Integer id, Integer trangThai) {
        return hdRepo.getHoaDonTrangThai(id, trangThai);
    }

    @Override
    public KHHoaDonResponse huyHoaDonChoXacNhan(Integer idHD, String lyDo) {
        HoaDon hoaDon = hdRepo.findById(idHD).get();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        HashMap<Integer, Integer> quantityMap = new HashMap<>();
        if (hoaDon != null) {
            hoaDon.setNgaySua(DatetimeUtil.getCurrentDateAndTimeLocal());
            hoaDon.setTrangThai(HoaDonStatus.DA_HUY);
            hoaDon.setLyDo(lyDo);
            HoaDon hd = hdRepo.save(hoaDon);
            List<HoaDonChiTiet> lstHDCT = hdctRepo.findByIdHoaDon(idHD, sort);
            for (HoaDonChiTiet hdct : lstHDCT) {
                int idSP = hdct.getSanPhamChiTiet().getId();
                int quantity = hdct.getSoLuong();
                if (quantityMap.containsKey(idSP)) {
                    quantityMap.put(idSP, quantityMap.get(idSP) + quantity);
                } else {
                    quantityMap.put(idSP, quantity);
                }
            }
            for (Map.Entry<Integer, Integer> entry : quantityMap.entrySet()) {
                int idSP = entry.getKey();
                int quantity = entry.getValue();
                System.out.println("idSP: " + idSP);
                System.out.println("số lượng: " + quantity);
                SanPhamChiTiet spct = ctspRepo.findById(idSP).get();
                spct.setSoLuongTon(spct.getSoLuongTon() + quantity);
                ctspRepo.save(spct);
            }
            return hdRepo.getByIds(hd.getId());
        } else {
            return null;
        }
    }

    @Override
    public KHHoaDonResponse findById(Integer idHD) {
        return hdRepo.getByIds(idHD);
    }


}
