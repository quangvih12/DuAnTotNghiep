package com.example.demo.infrastructure.exportPdf;

import com.example.demo.core.khachHang.model.response.BienLaiHoaDon;
import com.example.demo.core.khachHang.model.response.HDCTRespon;
import com.example.demo.core.khachHang.model.response.KHHoaDonChiTietResponse;
import com.example.demo.core.khachHang.repository.KHDiaChiRepository;
import com.example.demo.core.khachHang.repository.KHHoaDonChiTietRepository;
import com.example.demo.entity.DiaChi;
import com.example.demo.entity.HoaDon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ExportFilePdfFormHtml {

    @Autowired
    KHHoaDonChiTietRepository hdctRepo ;

    @Autowired
    KHDiaChiRepository khDiaChiRepo;

    public Context setDataSendMail(BienLaiHoaDon invoice, String url) {

        Context context = new Context();

        Map<String, Object> data = new HashMap<>();

        data.put("invoice", invoice);
        data.put("url", url);
        context.setVariables(data);

        return context;
    }

    public  NumberFormat formatCurrency() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("vi-VN"));
        formatter.setCurrency(Currency.getInstance("VND"));
        return formatter;
    }
    public BienLaiHoaDon getInvoiceResponse(HoaDon hoaDon) {

        List<KHHoaDonChiTietResponse> billDetailResponses = hdctRepo.findHDCTByIDHD(hoaDon.getId());
        NumberFormat formatter = formatCurrency();
        DiaChi diaChi = khDiaChiRepo.findAllById(hoaDon.getDiaChi().getId());


        BienLaiHoaDon invoice = BienLaiHoaDon.builder()
                .sdt(hoaDon.getUser().getSdt())
                .diaChi(diaChi.getDiaChi()+", "+diaChi.getTenphuongXa()+", "+diaChi.getTenQuanHuyen() +", "+diaChi.getTenTinhThanh())
                .ten(hoaDon.getUser().getTen())
                .ma(hoaDon.getMa())
                .phiShip(formatter.format(hoaDon.getTienShip()))
                .tongTien(formatter.format(hoaDon.getTongTien()))
                .tongThanhToan(formatter.format(hoaDon.getTienSauKhiGiam()))
                .date(hoaDon.getNgayThanhToan())
                .build();

        List<HDCTRespon> items = billDetailResponses.stream()
                .map(billDetailRequest -> {

                    HDCTRespon invoiceItemResponse = HDCTRespon.builder()
                            .ma(billDetailRequest.getTenSP())
                            .donGia(billDetailRequest.getDonGia())
                            .soLuong(billDetailRequest.getSoLuong())
                            .build();

                    return invoiceItemResponse;
                })
                .collect(Collectors.toList());

        invoice.setItems(items);

        return invoice;
    }
}
