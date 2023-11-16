package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.core.khachHang.model.request.KhDoiTraRequest;
import com.example.demo.core.khachHang.repository.*;
import com.example.demo.core.token.service.TokenService;
import com.example.demo.entity.*;
import com.example.demo.infrastructure.status.HoaDonStatus;
import com.example.demo.infrastructure.status.TrangThaiHoaDon;
import com.example.demo.util.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
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

    public HoaDon doiTra(String token, KhDoiTraRequest request) {

        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);

        User kh = khUserRepo.findById(request.getIdUser()).get();
        DiaChi diaChi = khDiaChiRepo.findById(request.getIdDiaChi()).get();
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;

        HoaDonChiTiet hdct = hoaDonChiTietRepo.findById(request.getIdHDCT()).get();
        if (hdct != null) {
            hdct.setTrangThai(HoaDonStatus.YEU_CAU_DOI_TRA);
            hoaDonChiTietRepo.save(hdct);
        }

        BigDecimal donGia = new BigDecimal(hdct.getDonGia().toString());
        BigDecimal tienShip = new BigDecimal(request.getTienShip());
        BigDecimal sum = donGia.add(tienShip);

        HoaDon hoaDon = HoaDon.builder()
                .ma("HDDT" + randomNumber)
                .user(kh)
                .tongTien(sum)
                .lyDo(request.getLyDo())
                .moTa(request.getMoTa())
                .ngayTao(DatetimeUtil.getCurrentDateAndTimeLocal())
                .tenNguoiNhan(kh.getTen())
                .tienShip(tienShip)
                .diaChi(diaChi)
                .trangThai(HoaDonStatus.YEU_CAU_DOI_TRA)
                .phuongThucThanhToan(PhuongThucThanhToan.builder().id(TrangThaiHoaDon.OFFLINE).build())
                .build();
        HoaDon saveHoaDon = khHoaDonRepo.save(hoaDon);

        HoaDonChiTiet hoaDonChiTiet = HoaDonChiTiet.builder()
                .sanPhamChiTiet(SanPhamChiTiet.builder().id(hdct.getSanPhamChiTiet().getId()).build())
                .ma("HDCTDT" + randomNumber)
                .soLuong(hdct.getSoLuong())
                .donGia(hdct.getDonGia())
                .hoaDon(saveHoaDon)
                .ngaySua(DatetimeUtil.getCurrentDate())
                .trangThai(HoaDonStatus.YEU_CAU_DOI_TRA)
                .build();

        hoaDonChiTietRepo.save(hoaDonChiTiet);
        thongBaoService.yeuCauDoiTra(saveHoaDon.getId(),hdct.getSanPhamChiTiet().getSanPham().getMa());
        return hoaDon;
    }

}
