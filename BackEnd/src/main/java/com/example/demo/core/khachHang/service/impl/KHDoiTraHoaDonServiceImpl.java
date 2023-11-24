package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.core.khachHang.model.request.KhDoiTraRequest;
import com.example.demo.core.khachHang.repository.*;
import com.example.demo.core.token.service.TokenService;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.infrastructure.status.HoaDonStatus;
import com.example.demo.util.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class KHDoiTraHoaDonServiceImpl {

    @Autowired
    KHUserRepository khUserRepo;

    @Autowired
    KHHoaDonRepository khHoaDonRepo;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepo;

    @Autowired
    TokenService tokenService;

    @Autowired
    KHDiaChiRepository khDiaChiRepo;

    @Autowired
    AdUserRepository userRepository;

    @Autowired
    private KHHoaDonChiTietRepository hdctRepo;

    @Autowired
    private ThongBaoServiceImpl thongBaoService;

    public HoaDon doiTra(KhDoiTraRequest request) {

        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;



        HoaDonChiTiet hdct = hoaDonChiTietRepo.findById(request.getIdHDCT()).get();



        HoaDonChiTiet hoaDonChi = hoaDonChiTietRepo.findByidSPandAndTrangThai(hdct.getSanPhamChiTiet().getId(),hdct.getHoaDon().getId());

        if (hoaDonChi != null) {
            if(hdct != null){
                hdct.setSoLuong(hdct.getSoLuong() - request.getSoLuong());
                hoaDonChiTietRepo.save(hdct);
            }
            hoaDonChi.setDonGia(hdct.getDonGia());
            hoaDonChi.setNgaySua(DatetimeUtil.getCurrentDate());
            hoaDonChi.setLyDo(request.getLyDo());
            hoaDonChi.setSoLuong(request.getSoLuong() + hoaDonChi.getSoLuong());
            hoaDonChiTietRepo.save(hoaDonChi);
            thongBaoService.yeuCauDoiTra(hdct.getHoaDon().getId(), hdct.getSanPhamChiTiet().getSanPham().getMa());
            return khHoaDonRepo.findById(hdct.getHoaDon().getId()).get();

        }

        if (request.getSoLuong() == hdct.getSoLuong()) {
            if (hdct != null) {
                hdct.setTrangThai(HoaDonStatus.YEU_CAU_DOI_TRA);
                hoaDonChiTietRepo.save(hdct);
                thongBaoService.yeuCauDoiTra(hdct.getHoaDon().getId(), hdct.getSanPhamChiTiet().getSanPham().getMa());
                return khHoaDonRepo.findById(hdct.getHoaDon().getId()).get();
            }
        }else{
            if(hdct != null){
                hdct.setSoLuong(hdct.getSoLuong() - request.getSoLuong());
                hoaDonChiTietRepo.save(hdct);
            }
        }
        if (request.getSoLuong() > hdct.getSoLuong()) {
            return khHoaDonRepo.findById(hdct.getHoaDon().getId()).get();
        }

        HoaDonChiTiet hoaDonChiTiet = HoaDonChiTiet.builder()
                .sanPhamChiTiet(SanPhamChiTiet.builder().id(hdct.getSanPhamChiTiet().getId()).build())
                .ma("HDCTDT" + randomNumber)
                .soLuong(request.getSoLuong())
                .donGia(hdct.getDonGia())
                .hoaDon(hdct.getHoaDon())
                .lyDo(request.getLyDo())
                .ngayTao(DatetimeUtil.getCurrentDate())
                .trangThai(HoaDonStatus.YEU_CAU_DOI_TRA)
                .build();

        hoaDonChiTietRepo.save(hoaDonChiTiet);
        thongBaoService.yeuCauDoiTra(hdct.getHoaDon().getId(), hdct.getSanPhamChiTiet().getSanPham().getMa());
        return khHoaDonRepo.findById(hdct.getHoaDon().getId()).get();
    }

}
