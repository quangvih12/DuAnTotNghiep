package com.example.demo.sevice.Impl.Admin;

import com.example.demo.dto.request.PhuongThucThanhToanRequest;
import com.example.demo.entity.PhuongThucThanhToan;
import com.example.demo.entity.VatLieu;
import com.example.demo.reponsitory.PhuongThucThanhToanReponsitory;
import com.example.demo.sevice.PhuongThucThanhToanService;
import com.example.demo.util.DataUltil;
import com.example.demo.util.DatetimeUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PhuongThucThanhToanServiceImpl implements PhuongThucThanhToanService {
    @Autowired
    private PhuongThucThanhToanReponsitory phuongThucThanhToanReponsitory;

    @Override
    public Page<PhuongThucThanhToan> getAll(Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, 5, sort);
        return phuongThucThanhToanReponsitory.findAll(pageable);
    }

    @Override
    public PhuongThucThanhToan getById(Integer id) {
        Optional<PhuongThucThanhToan> optional = this.phuongThucThanhToanReponsitory.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public HashMap<String, Object> add(PhuongThucThanhToanRequest dto) {
        PhuongThucThanhToan phuongThucThanhToan = dto.dtoToEntity(new PhuongThucThanhToan());
        try {
            PhuongThucThanhToan pttt = phuongThucThanhToanReponsitory.save(phuongThucThanhToan);
            pttt.setMa("TT" + pttt.getId());
            phuongThucThanhToanReponsitory.save(pttt);
            return DataUltil.setData("success", "thêm thành công");
        } catch (Exception e) {
            return DataUltil.setData("error", "error");

        }
    }

    @Override
    public HashMap<String, Object> update(PhuongThucThanhToanRequest dto, Integer id) {
        Optional<PhuongThucThanhToan> optional = phuongThucThanhToanReponsitory.findById(id);
        if (optional.isPresent()) {
            PhuongThucThanhToan phuongThucThanhToan = optional.get();
            phuongThucThanhToan.setMa(phuongThucThanhToan.getMa());
            phuongThucThanhToan.setNgayTao(phuongThucThanhToan.getNgayTao());
            phuongThucThanhToan.setTen(dto.getTen());
            phuongThucThanhToan.setTrangThai(dto.getTrangThai());
            phuongThucThanhToan.setNgaySua(DatetimeUtil.getCurrentDate());
            try {
                phuongThucThanhToanReponsitory.save(phuongThucThanhToan);
                return DataUltil.setData("success", "Update thành công");
            } catch (Exception e) {
                return DataUltil.setData("error", "error");

            }
        } else {
            return DataUltil.setData("error", "không tìm thấy phương thức thanh toán để sửa");

        }
    }

    @Override
    public HashMap<String, Object> delete(PhuongThucThanhToanRequest dto, Integer id) {
        Optional<PhuongThucThanhToan> optional = phuongThucThanhToanReponsitory.findById(id);
        if (optional.isPresent()) {
            PhuongThucThanhToan phuongThucThanhToan = optional.get();
            phuongThucThanhToan.setMa(phuongThucThanhToan.getMa());
            phuongThucThanhToan.setNgayTao(phuongThucThanhToan.getNgayTao());
            phuongThucThanhToan.setTen(phuongThucThanhToan.getTen());
            phuongThucThanhToan.setTrangThai(0);
            phuongThucThanhToan.setNgaySua(DatetimeUtil.getCurrentDate());
            try {
                phuongThucThanhToanReponsitory.save(phuongThucThanhToan);
                return DataUltil.setData("success", "Xóa thành công");
            } catch (Exception e) {
                return DataUltil.setData("error", "error");

            }
        } else {
            return DataUltil.setData("error", "không tìm thấy phương thức thanh toán để xóa");

        }
    }



}
