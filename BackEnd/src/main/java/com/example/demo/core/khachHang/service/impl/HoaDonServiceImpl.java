package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.khachHang.model.request.hoadon.HoaDonRequest;
import com.example.demo.core.khachHang.model.request.hoadonchitiet.KHHoaDonChiTietRequest;
import com.example.demo.core.khachHang.model.response.KHHoaDonResponse;
import com.example.demo.core.khachHang.repository.*;
import com.example.demo.core.khachHang.service.HoaDonService;
import com.example.demo.entity.*;
import com.example.demo.infrastructure.config.PaymentConfig;
import com.example.demo.infrastructure.constant.VNPayConstant;
import com.example.demo.infrastructure.status.ChiTietSanPhamStatus;
import com.example.demo.infrastructure.status.HoaDonStatus;
import com.example.demo.infrastructure.status.TrangThaiHoaDon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    KHUserRepository khUserRepo;

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

    @Override
    public HoaDon createHoaDon(HoaDonRequest hoaDonRequest) {

//        if(hoaDonRequest.getIdPayMethod() !=  TrangThaiHoaDon.OFFLINE){
//            log.info("test");
//            if(!PaymentConfig.decodeHmacSha512(hoaDonRequest.getResponsePayment().toParamsString(), hoaDonRequest.getResponsePayment().getVnp_SecureHash(), VnPayConstant.vnp_HashSecret)){
//                throw new RuntimeException("Loi");
//            }
////            List<String> findAllByVnpTransactionNo = paymentsMethodRepository.findAllByVnpTransactionNo(request.getResponsePayment().getVnp_TransactionNo());
////            if (findAllByVnpTransactionNo.size() > 0) {
////                throw new RestApiException(Message.PAYMENT_TRANSACTION);
////            }
//            if(!hoaDonRequest.getResponsePayment().getVnp_TransactionStatus().equals("00")){
//                throw new RuntimeException("Loi");
//            }
//        }

        User kh = User.builder().id(hoaDonRequest.getIdUser()).build();

        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;

        HoaDon hoaDon = HoaDon.builder()
                .ma("HD"+randomNumber)
                .user(kh)
                .tongTien(hoaDonRequest.getTongTien())
                .tienShip(hoaDonRequest.getTienShip())
                .tienSauKhiGiam(hoaDonRequest.getTienSauGiam())
                .trangThai(HoaDonStatus.YEU_CAU_XAC_NHAN)
                .phuongThucThanhToan(PhuongThucThanhToan.builder().id(hoaDonRequest.getIdPayMethod()).build())
                .build();

//        if(hoaDonRequest.getIdPayMethod() !=  TrangThaiHoaDon.OFFLINE){
//            hoaDon.setMa(hoaDonRequest.getResponsePayment().getVnp_TxnRef().split("-")[0]);
//        }
//        if(hoaDonRequest.getIdPayMethod() !=  TrangThaiHoaDon.OFFLINE){
//            log.info("chay vao day");
//            hoaDon.setMa(hoaDonRequest.getResponsePayment().getVnp_TxnRef().split("-")[0]);
//        }

        khHoaDonRepo.save(hoaDon);

        for (KHHoaDonChiTietRequest request : hoaDonRequest.getListHDCT()) {

            SanPhamChiTiet spct = chiTietSPRepo.findById(request.getIdCTSP()).get();

            if (spct.getSoLuongTon() < request.getSoLuong()) {
                throw new RuntimeException("So luong khong du");
            }

            HoaDonChiTiet hdct = HoaDonChiTiet.builder()
                    .sanPhamChiTiet(spct)
                    .ma("HDCT"+randomNumber)
                    .soLuong(request.getSoLuong())
                    .donGia(request.getDonGia())
                    .trangThai(HoaDonStatus.YEU_CAU_XAC_NHAN)
                    .hoaDon(hoaDon)
                    .build();

           hoaDonChiTietRepo.save(hdct);

           spct.setSoLuongTon(spct.getSoLuongTon() - request.getSoLuong());

            if (spct.getSoLuongTon() == 0) {
                spct.setTrangThai(ChiTietSanPhamStatus.HET_HANG);
            }

            chiTietSPRepo.save(spct);

//        PhuongThucThanhToan paymentsMethod = PhuongThucThanhToan.builder()
//        paymentsMethodRepository.save(paymentsMethod);


//        if (hoaDonRequest.getIdVoucher() != null) {
//
//            Voucher voucher = voucherRepo.findById(hoaDonRequest.getIdVoucher()).get();
//        }

        GioHang gioHang = khGioHangRepo.finbyIdKH(hoaDonRequest.getIdUser());

        for (KHHoaDonChiTietRequest x : hoaDonRequest.getListHDCT()) {
            GioHangChiTiet gioHangChiTiet = khghctRepo.listGHCTByID(hoaDonRequest.getIdUser(), x.getIdCTSP());
             khghctRepo.deleteById(gioHangChiTiet.getId());
        }

        }

        return hoaDon;
    }
}
