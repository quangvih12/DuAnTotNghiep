package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.Admin.service.EmailSenderService;
import com.example.demo.core.khachHang.model.request.hoadon.HoaDonRequest;
import com.example.demo.core.khachHang.model.request.hoadonchitiet.KHHoaDonChiTietRequest;

import com.example.demo.core.khachHang.model.response.BienLaiHoaDon;
import com.example.demo.core.khachHang.repository.*;
import com.example.demo.core.khachHang.service.HoaDonService;
import com.example.demo.entity.*;
import com.example.demo.infrastructure.constant.VNPayConstant;
import com.example.demo.infrastructure.exportPdf.ExportFilePdfFormHtml;

import com.example.demo.infrastructure.sendmail.SendEmailService;
import com.example.demo.infrastructure.status.ChiTietSanPhamStatus;
import com.example.demo.infrastructure.status.HinhThucGiaoHangStatus;
import com.example.demo.infrastructure.status.HoaDonStatus;
import com.example.demo.util.DatetimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
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

    @Autowired
    ExportFilePdfFormHtml exportFilePdfFormHtml;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    private VNPayConstant VnPayConstant;

    @Autowired
    HoaDonRepository hoaDonRepo;

    @Autowired
    EmailSenderService sendEmailService;

    @Autowired
    private ThongBaoServiceImpl thongBaoService;

    @Override
    public HoaDon createHoaDon(HoaDonRequest hoaDonRequest) {

        User kh = this.khUserRepo.findById(hoaDonRequest.getIdUser()).get();
        DiaChi diaChi = khDiaChiRepo.findDiaChiByIdUserAndTrangThai(kh.getId());

        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;

        LocalDateTime ngayThanhToan;
        if (hoaDonRequest.getIdPayMethod() == 1) {
            ngayThanhToan = DatetimeUtil.getCurrentDateAndTimeLocal();
        } else {
            ngayThanhToan = null;
        }

        HoaDon hoaDon = HoaDon.builder()
                .ma("HD" + randomNumber)
                .user(kh)
                .diaChi(diaChi)
                .tongTien(hoaDonRequest.getTongTien())
                .tenNguoiNhan(kh.getTen())
                .ngayTao(DatetimeUtil.getCurrentDateAndTimeLocal())
                .tienShip(hoaDonRequest.getTienShip())
                .ngayThanhToan(ngayThanhToan)
                .tienSauKhiGiam(hoaDonRequest.getTienSauGiam())
                .hinhThucGiaoHang(HinhThucGiaoHangStatus.GIAOHANG)
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


        }
        for (KHHoaDonChiTietRequest x : hoaDonRequest.getListHDCT()) {
            GioHangChiTiet gioHangChiTiet = khghctRepo.listGHCTByID(hoaDonRequest.getIdUser(), x.getIdCTSP());
            khghctRepo.deleteById(gioHangChiTiet.getId());
        }
        this.thongBaoService.thanhToan(saveHoaDon.getId());
        sendMailOnline(hoaDon.getId());
        return hoaDon;
    }


    public void sendMailOnline(Integer idHoaDon) {
        String finalHtml = null;
        HoaDon hoaDon = hoaDonRepo.findAllById(idHoaDon);
        BienLaiHoaDon invoice = exportFilePdfFormHtml.getInvoiceResponse(hoaDon);

        User user = khUserRepo.findAllById(hoaDon.getUser().getId()).get();

        sendMail(invoice, "http://localhost:5173/success", "ngockhanh107a@gmail.com");
        //}
    }

    public void sendMail(BienLaiHoaDon invoice, String url, String email) {

        String finalHtmlSendMail = null;
        Context dataContextSendMail = exportFilePdfFormHtml.setDataSendMail(invoice, url);
        finalHtmlSendMail = springTemplateEngine.process("Bill", dataContextSendMail);
        String subject = "Biên lai ";
        sendEmailService.sendSimpleEmail(email, subject, finalHtmlSendMail);

    }
}
