package com.example.demo.sevice.Impl.Admin;

import com.example.demo.dto.request.VatLieuRequest;
import com.example.demo.entity.VatLieu;
import com.example.demo.reponsitory.VatLieuReponsitory;
import com.example.demo.sevice.VatLieuService;
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
public class VatLieuServiceImpl implements VatLieuService {
    @Autowired
    private VatLieuReponsitory vatLieuReponsitory;

    @Override
    public Page<VatLieu> getAll(Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, 5, sort);
        return vatLieuReponsitory.findAll(pageable);
    }

    @Override
    public VatLieu getById(Integer id) {
        Optional<VatLieu> optional = this.vatLieuReponsitory.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public HashMap<String, Object> add(VatLieuRequest dto) {
        VatLieu vatLieu = dto.dtoToEntity(new VatLieu());
        try {
            VatLieu vatLieus = vatLieuReponsitory.save(vatLieu);
            vatLieus.setMa("VL" + vatLieus.getId());
            vatLieuReponsitory.save(vatLieus);
            return DataUltil.setData("success", "Thêm thành công");
        } catch (Exception e) {

            return DataUltil.setData("error", "error");
        }
    }

    @Override
    public HashMap<String, Object> update(VatLieuRequest dto, Integer id) {
        Optional<VatLieu> optional = vatLieuReponsitory.findById(id);
        if (optional.isPresent()) {
            VatLieu vatLieu = optional.get();
            vatLieu.setMa(vatLieu.getMa());
            vatLieu.setTen(dto.getTen());
            vatLieu.setNgayTao(vatLieu.getNgayTao());
            vatLieu.setTrangThai(dto.getTrangThai());
            vatLieu.setMoTa(dto.getMoTa());
            vatLieu.setNgaySua(DatetimeUtil.getCurrentDate());
            try {
                vatLieuReponsitory.save(vatLieu);
                return DataUltil.setData("success", "Sửa thành công");
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy vật liệu để sửa");

        }
    }

    @Override
    public HashMap<String, Object> delete(VatLieuRequest dto, Integer id) {
        Optional<VatLieu> optional = vatLieuReponsitory.findById(id);
        if (optional.isPresent()) {
            VatLieu vatLieu = optional.get();
            vatLieu.setMa(vatLieu.getMa());
            vatLieu.setTen(vatLieu.getTen());
            vatLieu.setNgayTao(vatLieu.getNgayTao());
            vatLieu.setTrangThai(0);
            vatLieu.setMoTa(vatLieu.getMoTa());
            vatLieu.setNgaySua(DatetimeUtil.getCurrentDate());
            try {
                vatLieuReponsitory.save(vatLieu);
                return DataUltil.setData("success", "Xóa thành công");
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy vật liệu để xóa");

        }
    }

    //    @Override
//    public Page<Loai> search(String keyword, Integer page) {
//        if (keyword == null) {
//            return this.getAll(page);
//        } else {
//            Pageable pageable = PageRequest.of(page, 5);
//            return loaiReponsitory.search("%" + keyword + "%", pageable);
//        }
//    }
    public void saveExcel(MultipartFile file) {
        if (this.isValidExcelFile(file)) {
            try {
                List<VatLieu> vatLieus = this.getCustomersDataFromExcel(file.getInputStream());
                List<VatLieu> saveVatLieus = this.vatLieuReponsitory.saveAll(vatLieus);
                for (int i = 0; i < saveVatLieus.size(); i++) {
                    VatLieu vatLieu = saveVatLieus.get(i);
                    vatLieu.setMa("L" + saveVatLieus.get(i).getId());
                    vatLieu.setNgayTao(DatetimeUtil.getCurrentDate());
                }
                this.vatLieuReponsitory.saveAll(vatLieus);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }

    public static boolean isValidExcelFile(MultipartFile file) {

        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<VatLieu> getCustomersDataFromExcel(InputStream inputStream) {
        List<VatLieu> list = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            if (workbook != null) {
//                System.out.println("Workbook co ton tai");
                if (sheet != null) {
//                    System.out.println("sheet ton tai");
                    int rowIndex = 0;
                    for (Row row : sheet) {
                        if (rowIndex == 0) {
                            rowIndex++;
                            continue;
                        }
                        Iterator<Cell> cellIterator = row.iterator();
                        int cellIndex = 0;
                        VatLieu vatLieu = new VatLieu();
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            switch (cellIndex) {
                                case 0 -> vatLieu.setTen(cell.getStringCellValue());
                                case 1 -> vatLieu.setTrangThai((int) cell.getNumericCellValue());
                                default -> {
                                }
                            }
                            cellIndex++;
                        }
                        list.add(vatLieu);
                    }
                } else {
                    System.out.println("sheet ko ton tai.");
                }
            } else {
                System.out.println("Workbook is null. Không thể đọc dữ liệu từ Excel.");
            }

        } catch (IOException e) {
            e.getStackTrace();
        }
        return list;
    }
}
