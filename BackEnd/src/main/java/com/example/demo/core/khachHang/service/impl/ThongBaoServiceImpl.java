package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.core.khachHang.repository.KHHoaDonRepository;
import com.example.demo.core.khachHang.repository.KHThongBaoRepository;
import com.example.demo.core.token.service.TokenService;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.ThongBao;
import com.example.demo.entity.User;
import com.example.demo.infrastructure.status.ThongBaoStatus;
import com.example.demo.infrastructure.status.ThongBaoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThongBaoServiceImpl {
    @Autowired
    TokenService tokenService;

    @Autowired
    AdUserRepository userRepository;

    @Autowired
    KHHoaDonRepository hoaDonRepository;

    @Autowired
    private KHThongBaoRepository khThongBaoRepository;

    public List<ThongBao> getAll(String token) {
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);
        return khThongBaoRepository.findAll(user.getId());
    }

    public Integer dem(String token) {
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);
        return khThongBaoRepository.dem(user.getId());
    }

    public ThongBao daXem(Integer id) {
        ThongBao thongBao = khThongBaoRepository.findById(id).get();
        if (thongBao != null) {
            thongBao.setTrangThai(ThongBaoStatus.DA_XEM);
            return khThongBaoRepository.save(thongBao);
        }
        return null;
    }

    public void thanhToan(Integer idHD) {
        HoaDon hoaDon = hoaDonRepository.findById(idHD).get();
        ThongBao thongBao = new ThongBao();
        thongBao.setType(ThongBaoType.THANH_TOAN);
        thongBao.setUser(User.builder().id(hoaDon.getUser().getId()).build());
        thongBao.setContent("hóa đơn " + hoaDon.getMa() + " chờ xác nhận");
        thongBao.setTrangThai(ThongBaoStatus.CHUA_XEM);
        khThongBaoRepository.save(thongBao);
    }

    public void yeuCauDoiTra(Integer idHD, String maSP) {
        HoaDon hoaDon = hoaDonRepository.findById(idHD).get();
        ThongBao thongBao = new ThongBao();
        thongBao.setType(ThongBaoType.YEU_CAU_DOI_TRA);
        thongBao.setUser(User.builder().id(hoaDon.getUser().getId()).build());
        thongBao.setContent("yêu cầu đổi trả sản phẩm có mã: "  + maSP);
        thongBao.setTrangThai(ThongBaoStatus.CHUA_XEM);
        khThongBaoRepository.save(thongBao);
    }

    public void huyDonHang(Integer idHD) {
        HoaDon hoaDon = hoaDonRepository.findById(idHD).get();
        ThongBao thongBao = new ThongBao();
        thongBao.setType(ThongBaoType.KH_HUY_DON_HANG);
        thongBao.setUser(User.builder().id(hoaDon.getUser().getId()).build());
        thongBao.setContent("Khách Hàng hủy đơn hàng có mã là: " + hoaDon.getMa());
        thongBao.setTrangThai(ThongBaoStatus.CHUA_XEM);
        khThongBaoRepository.save(thongBao);
    }
}
