package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.model.request.AdminKhuyenMaiRequest;
import com.example.demo.core.Admin.model.response.AdminKhuyenMaiResponse;
import com.example.demo.core.Admin.repository.AdKhuyenMaiReponsitory;
import com.example.demo.core.Admin.service.AdKhuyenMaiService;
import com.example.demo.entity.KhuyenMai;
import com.example.demo.reponsitory.KhuyenMaiReponsitory;
import com.example.demo.util.DataUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
//@EnableScheduling
public class KhuyenMaiServiceImpl implements AdKhuyenMaiService {

    @Autowired
    AdKhuyenMaiReponsitory khuyenMaiRepo;

    @Override
    public List<AdminKhuyenMaiResponse> getAllKhuyenMai() {
        return khuyenMaiRepo.getKhuyenMaiByTrangThai();
    }

    @Override
    public HashMap<String, Object> add(AdminKhuyenMaiRequest dto) {
        KhuyenMai khuyenMai = dto.dtoToEntity(new KhuyenMai());
        LocalDateTime thoiGianHienTai = LocalDateTime.now();
        try {
            if(khuyenMai.getThoiGianKetThuc().isBefore(thoiGianHienTai)){
                khuyenMai.setTrangThai(1);
            }else if(khuyenMai.getThoiGianBatDau().isAfter(thoiGianHienTai)){
                khuyenMai.setTrangThai(3);
            }else{
                khuyenMai.setTrangThai(0);
            }
            KhuyenMai khuyenmais = khuyenMaiRepo.save(khuyenMai);
            return DataUltil.setData("success",  khuyenmais);
        } catch (Exception e) {
            return DataUltil.setData("error", "error");
        }
    }

    @Override
    public HashMap<String, Object> update(AdminKhuyenMaiRequest dto, Integer id) {
        Optional<KhuyenMai> optional = khuyenMaiRepo.findById(id);
        if (optional.isPresent()) {
            KhuyenMai khuyenMai = optional.get();
            khuyenMai.setTen(dto.getTen());
            khuyenMai.setMoTa(dto.getMoTa());
            khuyenMai.setThoiGianBatDau(dto.getThoiGianBatDau());
            khuyenMai.setThoiGianKetThuc(dto.getThoiGianKetThuc());
            khuyenMai.setSoLuong(dto.getSoLuong());
            khuyenMai.setGiaTriGiam(dto.getGiaTriGiam());

            try {
                khuyenMaiRepo.save(khuyenMai);
                return DataUltil.setData("success", khuyenMaiRepo.save(khuyenMai));
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy khuyến mại để sửa");
        }
    }

    @Override
    public HashMap<String, Object> delete(AdminKhuyenMaiRequest dto, Integer id) {
        Optional<KhuyenMai> optional = khuyenMaiRepo.findById(id);
        if (optional.isPresent()) {
            KhuyenMai khuyenMai = optional.get();
            khuyenMai.setTen(dto.getTen());
            khuyenMai.setMoTa(dto.getMoTa());
            khuyenMai.setThoiGianBatDau(dto.getThoiGianBatDau());
            khuyenMai.setThoiGianKetThuc(dto.getThoiGianKetThuc());
            khuyenMai.setSoLuong(dto.getSoLuong());
            khuyenMai.setGiaTriGiam(dto.getGiaTriGiam());
            khuyenMai.setTrangThai(4);
            try {
                khuyenMaiRepo.save(khuyenMai);
                return DataUltil.setData("success", khuyenMaiRepo.save(khuyenMai));
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy khuyến mại để xoá");
        }
    }

    // cập nhật lại số lượng km khi áp dụng khuyến mại
    @Override
    public HashMap<String, Object> updateSLKhuyenMai(Integer id, Integer soLuong) throws Exception {
        Optional<KhuyenMai> optional = khuyenMaiRepo.findById(id);

        if (optional.isPresent()) {
            try {
                KhuyenMai khuyenMai = optional.get();
                // nếu số lượng sản phẩm được chọn > số lượg khuyến mại
                if(khuyenMai.getSoLuong() < soLuong){
                    return DataUltil.setData("error", "Số lượng khuyến mại không đủ. Vui lòng chọn ít hơn hoặc bằng "+khuyenMai.getSoLuong()+ " sản phẩm");
                }

                // nếu số lượng km = 0 => cập nhật lại trạng thái km
                if((khuyenMai.getSoLuong()-soLuong) == 0){
                    khuyenMai.setTrangThai(3);

                }
                khuyenMai.setSoLuong(khuyenMai.getSoLuong()-soLuong);
                khuyenMaiRepo.save(khuyenMai);
                return DataUltil.setData("success", khuyenMaiRepo.save(khuyenMai));

            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }

        } else {
            return DataUltil.setData("error", "không tìm thấy khuyến mại để sửa");
        }
    }

    @Override
    public KhuyenMai getKhuyenMaiById(Integer id) {
       return khuyenMaiRepo.getOneById(id);
    }

    @Scheduled(cron = "0 0 0 * * *") // Lịch chạy hàng ngày vào lúc 00:00:00
    public void updateNgayHetHan() {
        List<KhuyenMai> khuyenMais = khuyenMaiRepo.findKhuyenMaiByHetHan();
        for (KhuyenMai khuyenMai : khuyenMais) {
            khuyenMai.setTrangThai(1);
            khuyenMaiRepo.save(khuyenMai);
        }
    }

    @Scheduled(cron = "0 0 0 * * *") // Lịch chạy hàng ngày vào lúc 00:00:00
    public void updateNgayChuaBatDau() {
        List<KhuyenMai> khuyenMais =  khuyenMaiRepo.findKhuyenMaiByChuaBatDau();
        for (KhuyenMai khuyenMai : khuyenMais) {
            khuyenMai.setTrangThai(2);
            khuyenMaiRepo.save(khuyenMai);
        }


    }
    @Scheduled(cron = "0 0 0 * * *") // Lịch chạy hàng ngày vào lúc 00:00:00
    public void updateNgayConHan() {
        List<KhuyenMai> khuyenMais =  khuyenMaiRepo.findKhuyenMaiByConHan();
        for (KhuyenMai khuyenMai : khuyenMais) {
            khuyenMai.setTrangThai(0);
            khuyenMaiRepo.save(khuyenMai);

        }
    }

}
