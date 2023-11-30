package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.model.request.BHTQHoaDonRequest;
import com.example.demo.core.Admin.model.request.BHTQUserRequest;
import com.example.demo.core.Admin.model.response.BHTQChiTietSanPhamResponse;
import com.example.demo.core.Admin.model.response.BHTQHoaDonChiTietResponse;
import com.example.demo.core.Admin.model.response.BHTQHoaDonResponse;
import com.example.demo.core.Admin.model.response.BHTQPhuongThucThanhToanResponse;
import com.example.demo.core.Admin.model.response.BHTQUserResponse;
import com.example.demo.core.Admin.repository.AdHoaDonChiTietReponsitory;
import com.example.demo.core.Admin.repository.AdHoaDonReponsitory;
import com.example.demo.core.Admin.repository.AdSanPhamChiTietRepository;
import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.core.Admin.service.BanHangTaiQuayService;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.entity.PhuongThucThanhToan;
import com.example.demo.entity.Role;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.entity.User;
import com.example.demo.infrastructure.mapper.BHTQChiTietSanPhamRespMapper;
import com.example.demo.infrastructure.mapper.BHTQHoaDonChiTietRespMapper;
import com.example.demo.infrastructure.mapper.BHTQHoaDonRespMapper;
import com.example.demo.infrastructure.mapper.BHTQPhuongThucThanhToanRespMapper;
import com.example.demo.infrastructure.mapper.BHTQUserReqMapper;
import com.example.demo.infrastructure.mapper.BHTQUserRespMapper;
import com.example.demo.infrastructure.status.HoaDonStatus;
import com.example.demo.infrastructure.status.UserStatus;
import com.example.demo.reponsitory.PhuongThucThanhToanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BanHangTaiQuayServiceImpl implements BanHangTaiQuayService {
    private final BHTQHoaDonRespMapper hoaDonMapper;
    private final BHTQHoaDonChiTietRespMapper hoaDonChiTietMapper;
    private final BHTQChiTietSanPhamRespMapper chiTietSanPhamMapper;
    private final BHTQPhuongThucThanhToanRespMapper phuongThucThanhToanMapper;
    private final BHTQUserRespMapper userRespMapper;
    private final BHTQUserReqMapper userReqMapper;
    private final AdHoaDonReponsitory hoaDonRepository;
    private final AdHoaDonChiTietReponsitory hoaDonChiTietRepository;
    private final AdUserRepository userRepository;
    private final AdSanPhamChiTietRepository sanPhamChiTietRepository;
    private final PhuongThucThanhToanRepository phuongThucThanhToanRepository;

    public BanHangTaiQuayServiceImpl(AdHoaDonReponsitory hoaDonRepository,
                                     AdHoaDonChiTietReponsitory hoaDonChiTietRepository,
                                     AdUserRepository userRepository,
                                     AdSanPhamChiTietRepository sanPhamChiTietRepository,
                                     PhuongThucThanhToanRepository phuongThucThanhToanRepository,
                                     BHTQHoaDonRespMapper hoaDonMapper,
                                     BHTQHoaDonChiTietRespMapper hoaDonChiTietMapper,
                                     BHTQChiTietSanPhamRespMapper chiTietSanPhamMapper,
                                     BHTQPhuongThucThanhToanRespMapper phuongThucThanhToanMapper,
                                     BHTQUserRespMapper userRespMapper,
                                     BHTQUserReqMapper userReqMapper) {
        this.hoaDonMapper = hoaDonMapper;
        this.hoaDonChiTietMapper = hoaDonChiTietMapper;
        this.chiTietSanPhamMapper = chiTietSanPhamMapper;
        this.hoaDonRepository = hoaDonRepository;
        this.hoaDonChiTietRepository = hoaDonChiTietRepository;
        this.userRepository = userRepository;
        this.sanPhamChiTietRepository = sanPhamChiTietRepository;
        this.phuongThucThanhToanRepository = phuongThucThanhToanRepository;
        this.phuongThucThanhToanMapper = phuongThucThanhToanMapper;
        this.userRespMapper = userRespMapper;
        this.userReqMapper = userReqMapper;
    }

    @Override
    @Transactional
    public void taoHDCho(Integer idNV, Integer idKH) {
        User nv = userRepository.findById(idNV).orElse(null);
        HoaDon hd = hoaDonRepository.save(new HoaDon());
        hd.setMa("HD".concat(hd.getId().toString()));
        hd.setNgayTao(LocalDateTime.now());
        hd.setTrangThai(HoaDonStatus.CHO_THANH_TOAN);
        hd.setNguoiTao(nv);
        if (idKH != null) hd.setUser(userRepository.findById(idKH).orElse(null));
        else hd.setUser(userRepository.findByRoleAndTrangThaiOrderByNgayTaoDesc(Role.USER, -1).get(0));
        hoaDonRepository.save(hd);
    }

    @Override
    @Transactional
    public void huyHDCho(Integer idHoaDon) {
        HoaDon hd = hoaDonRepository.findById(idHoaDon).orElse(null);
        List<HoaDonChiTiet> hdctList = hoaDonChiTietRepository.findByHoaDonId(hd.getId());
        hd.setTrangThai(HoaDonStatus.DA_HUY);
        hdctList.forEach(hdct -> {
            SanPhamChiTiet spct = sanPhamChiTietRepository.findById(hdct.getSanPhamChiTiet().getId()).orElse(null);
            spct.setSoLuongTon(spct.getSoLuongTon() + hdct.getSoLuong());
            sanPhamChiTietRepository.save(spct);
        });
        hoaDonRepository.save(hd);
    }

    @Override
    @Transactional
    public void thanhToanHD(Integer idHoaDon, BHTQHoaDonRequest dto) {
        BigDecimal[] tien = {new BigDecimal(0), new BigDecimal(0)};
        HoaDon hd = hoaDonRepository.findById(idHoaDon).orElse(null);
        List<HoaDonChiTiet> hdctList = hoaDonChiTietRepository.findByHoaDonId(hd.getId());
        PhuongThucThanhToan pttt = phuongThucThanhToanRepository.findById(dto.getIdPTTT()).orElse(null);
        hd.setPhuongThucThanhToan(pttt);
        hd.setHinhThucGiaoHang(dto.getIdHTGH());
        hd.setMoTa(dto.getMoTa());
        hd.setNgayThanhToan(LocalDateTime.now());
        hdctList.forEach(hdct -> {
            tien[0] = tien[0].add(hdct.getDonGia().multiply(BigDecimal.valueOf(hdct.getSoLuong()))); //Tong tien hang
            tien[1] = tien[1].add(hdct.getChietKhau().multiply(BigDecimal.valueOf(hdct.getSoLuong()))); //Tong chiet khau
        });
        hd.setTongTien(tien[0]);
        if (tien[1].compareTo(BigDecimal.valueOf(0)) == 1) hd.setTienSauKhiGiam(tien[0].subtract(tien[1]));
        hd.setTienKhachDua(dto.getTienKhachDua());
        hd.setTrangThai(HoaDonStatus.HOAN_THANH);
        hoaDonRepository.save(hd);
    }

    @Override
    public List<BHTQHoaDonResponse> getAllHDCho() {
        return hoaDonRepository.findByTrangThaiOrderByNgayTaoDesc(HoaDonStatus.CHO_THANH_TOAN).stream().map(hoaDonMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BHTQHoaDonChiTietResponse> getAllHDCT(Integer idHoaDon) {
        HoaDon hd = hoaDonRepository.findById(idHoaDon).orElse(null);
        return hoaDonChiTietRepository.findByHoaDonId(hd.getId()).stream().map(hoaDonChiTietMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BHTQChiTietSanPhamResponse> getAllCTSP() {
        return sanPhamChiTietRepository.findAllByTrangThaiAndSoLuongTonGreaterThan(HoaDonStatus.CHO_THANH_TOAN, 0).stream().map(chiTietSanPhamMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BHTQHoaDonChiTietResponse addSPToHDCT(Integer idHoaDon, Integer idCTSP, Integer soLuong) {
        HoaDon hd = hoaDonRepository.findById(idHoaDon).orElse(null);
        SanPhamChiTiet spct = sanPhamChiTietRepository.findById(idCTSP).orElse(null);
        HoaDonChiTiet existedHDCT = hoaDonChiTietRepository.findByHoaDonIdAndSanPhamChiTietId(idHoaDon, idCTSP).orElse(null);
        if (existedHDCT == null) {
            HoaDonChiTiet newHDCT = new HoaDonChiTiet();
            newHDCT.setHoaDon(hd);
            newHDCT.setSanPhamChiTiet(spct);
            newHDCT.setDonGia(spct.getGiaBan());
            if (spct.getGiaSauGiam() != null) {
                newHDCT.setChietKhau(spct.getGiaBan().subtract(spct.getGiaSauGiam()));
            } else {
                newHDCT.setChietKhau(BigDecimal.valueOf(0));
            }
            newHDCT.setSoLuong(soLuong);
            spct.setSoLuongTon(spct.getSoLuongTon() - soLuong);
            hoaDonChiTietRepository.save(newHDCT);
            sanPhamChiTietRepository.save(spct);
            return hoaDonChiTietMapper.toDto(newHDCT);
        } else {
            if (spct.getGiaSauGiam() != null) {
                existedHDCT.setChietKhau(spct.getGiaBan().subtract(spct.getGiaSauGiam()));
            } else {
                existedHDCT.setChietKhau(BigDecimal.valueOf(0));
            }
            existedHDCT.setSoLuong(existedHDCT.getSoLuong() + soLuong);
            spct.setSoLuongTon(spct.getSoLuongTon() - soLuong);
            hoaDonChiTietRepository.save(existedHDCT);
            sanPhamChiTietRepository.save(spct);
            return hoaDonChiTietMapper.toDto(existedHDCT);
        }
    }

    @Override
    @Transactional
    public void deleteHDCT(Integer idHDCT) {
        HoaDonChiTiet hdct = hoaDonChiTietRepository.findById(idHDCT).orElse(null);
        SanPhamChiTiet spct = sanPhamChiTietRepository.findById(hdct.getSanPhamChiTiet().getId()).orElse(null);
        spct.setSoLuongTon(spct.getSoLuongTon() + hdct.getSoLuong());
        sanPhamChiTietRepository.save(spct);
        hoaDonChiTietRepository.delete(hdct);
    }

    @Override
    public List<BHTQPhuongThucThanhToanResponse> getAllPTTT() {
        return phuongThucThanhToanRepository.findAll().stream().map(phuongThucThanhToanMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BHTQUserResponse> getAllKH() {
        return userRepository.findByRoleAndTrangThaiOrderByNgayTaoDesc(Role.USER, UserStatus.DANG_HOAT_DONG).stream().map(userRespMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateKHForHD(Integer idHD, Integer idKH) {
        HoaDon hd = hoaDonRepository.findById(idHD).orElse(null);
        hd.setUser(userRepository.findById(idKH).orElse(null));
        hoaDonRepository.save(hd);
    }

    @Override
    @Transactional
    public BHTQUserResponse addOrUpdateKH(BHTQUserRequest dto) {
        User userWithSdt = userRepository.findBySdt(dto.getSdt()).orElse(null);
        if (dto.getId() == null) {
            if (userWithSdt == null) {
                User newUser = new User();
                newUser.setTen(dto.getTen());
                newUser.setSdt(dto.getSdt());
                newUser.setRole(Role.USER);
                newUser.setTrangThai(UserStatus.DANG_HOAT_DONG);
                newUser.setNgayTao(LocalDateTime.now());
                userRepository.save(newUser);
                newUser.setMa("U".concat(newUser.getId().toString()));
                return userRespMapper.toDto(userRepository.save(newUser));
            }
            return new BHTQUserResponse("SĐT đã tồn tại trong hệ thống");
        } else {
            User user = userRepository.findById(dto.getId()).orElse(null);
            if (user.getSdt().equals(dto.getSdt())) {
                user.setTen(dto.getTen());
                return userRespMapper.toDto(userRepository.save(user));
            } else {
                if (userWithSdt == null) {
                    user.setTen(dto.getTen());
                    user.setSdt(dto.getSdt());
                    return userRespMapper.toDto(userRepository.save(user));
                }
                return new BHTQUserResponse("SĐT đã tồn tại trong hệ thống");
            }
        }
    }
}
