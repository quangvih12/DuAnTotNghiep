package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.khachHang.model.request.hoadon.HoaDonRequest;
import com.example.demo.core.khachHang.model.request.hoadonchitiet.KHHoaDonChiTietRequest;
import com.example.demo.core.khachHang.repository.*;
import com.example.demo.core.khachHang.service.HoaDonService;
import com.example.demo.entity.*;
import com.example.demo.infrastructure.constant.VNPayConstant;
import com.example.demo.infrastructure.status.ChiTietSanPhamStatus;
import com.example.demo.infrastructure.status.HoaDonStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    KHUserRepository khUserRepo;

    @Autowired
    KHDiaChiRepository khDiaChiRepo;

    @Autowired
    KHHoaDonRepository khHoaDonRepo;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepo;

    @Autowired
    KHChiTietSPRepository chiTietSPRepo;

    @Autowired
    KHVoucherRepository voucherRepo;

    @Autowired
    KHGioHangRepository khGioHangRepo;

    @Autowired
    KHGioHangChiTietRepository khghctRepo;

    private VNPayConstant VnPayConstant;

    @Autowired
    private ThongBaoServiceImpl thongBaoService;

    @Override
    public HoaDon createHoaDon(HoaDonRequest hoaDonRequest) {

        User kh = this.khUserRepo.findById(hoaDonRequest.getIdUser()).get();
        DiaChi diaChi = khDiaChiRepo.findDiaChiByIdUserAndTrangThai(kh.getId());

        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;

        HoaDon hoaDon = HoaDon.builder()
                .ma("HD" + randomNumber)
                .user(kh)
                .diaChi(diaChi)
                .tongTien(hoaDonRequest.getTongTien())
                .tienShip(hoaDonRequest.getTienShip())
                .tienSauKhiGiam(hoaDonRequest.getTienSauGiam())
                .trangThai(HoaDonStatus.YEU_CAU_XAC_NHAN)
                .phuongThucThanhToan(PhuongThucThanhToan.builder().id(hoaDonRequest.getIdPayMethod()).build())
                .build();

        HoaDon saveHoaDon = khHoaDonRepo.save(hoaDon);

        for (KHHoaDonChiTietRequest request : hoaDonRequest.getListHDCT()) {

            SanPhamChiTiet spct = chiTietSPRepo.findById(request.getIdCTSP()).get();

            if (spct.getSoLuongTon() < request.getSoLuong()) {
                throw new RuntimeException("So luong khong du");
            }

            HoaDonChiTiet hdct = HoaDonChiTiet.builder()
                    .sanPhamChiTiet(spct)
                    .ma("HDCT" + randomNumber)
                    .soLuong(request.getSoLuong())
                    .donGia(spct.getGiaSauGiam() == null ? spct.getGiaBan() : spct.getGiaSauGiam())
                    .trangThai(HoaDonStatus.YEU_CAU_XAC_NHAN)
                    .hoaDon(hoaDon)
                    .build();

            hoaDonChiTietRepo.save(hdct);

            spct.setSoLuongTon(spct.getSoLuongTon() - request.getSoLuong());

            if (spct.getSoLuongTon() == 0) {
                spct.setTrangThai(ChiTietSanPhamStatus.HET_HANG);
            }

            chiTietSPRepo.save(spct);


//            GioHang gioHang = khGioHangRepo.finbyIdKH(hoaDonRequest.getIdUser());


        }
        for (KHHoaDonChiTietRequest x : hoaDonRequest.getListHDCT()) {
            GioHangChiTiet gioHangChiTiet = khghctRepo.listGHCTByID(hoaDonRequest.getIdUser(), x.getIdCTSP());
            khghctRepo.deleteById(gioHangChiTiet.getId());
        }
        this.thongBaoService.thanhToan(saveHoaDon.getId());
        return hoaDon;
    }
}
