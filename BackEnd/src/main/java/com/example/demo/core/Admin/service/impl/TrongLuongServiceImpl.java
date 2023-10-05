package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.model.request.AdminTrongLuongRequest;
import com.example.demo.core.Admin.repository.AdTrongLuongRepository;
import com.example.demo.core.Admin.service.AdTrongLuongService;
import com.example.demo.entity.TrongLuong;
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
import java.util.*;

@Service
public class TrongLuongServiceImpl implements AdTrongLuongService {

    @Autowired
    private AdTrongLuongRepository repository;

    @Override
    public List<TrongLuong> getAll() {
        return repository.findAll();
    }

    @Override
    public TrongLuong getById(Integer id) {
        Optional<TrongLuong> optional = repository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public Page<TrongLuong> search(String keyword, Integer page) {

            Pageable pageable = PageRequest.of(page, 5);
            return repository.search("%" + keyword + "%", pageable);

    }

    @Override
    public HashMap<String, Object> add(AdminTrongLuongRequest request) {
        TrongLuong trongLuong = request.dtoToEntity(new TrongLuong());
        try {
            TrongLuong trongLuong1 = repository.save(trongLuong);
            trongLuong1.setMa("TL"+trongLuong1.getId());
            repository.save(trongLuong1);
            return DataUltil.setData("success", "thêm thành công");
        } catch (Exception e) {
            return DataUltil.setData("error", "error");
        }
    }

    @Override
    public HashMap<String, Object> update(AdminTrongLuongRequest request, Integer id) {
        Optional<TrongLuong> optional = repository.findById(id);
        if (optional.isPresent()) {
            TrongLuong tl = optional.get();
            tl.setMa(tl.getMa());
            tl.setDonVi(request.getDonVi());
            tl.setTrangThai(request.getTrangThai());
            tl.setNgayTao(request.getNgayTao());
            tl.setNgaySua(DatetimeUtil.getCurrentDate());
            tl.setValue(request.getValue());
            try {
                repository.save(tl);
                return DataUltil.setData("success", "sửa thành công");
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy loại sản phẩm để sửa");
        }
    }

    @Override
    public HashMap<String, Object> delete(AdminTrongLuongRequest request, Integer id) {
        Optional<TrongLuong> optional = repository.findById(id);
        if (optional.isPresent()) {
            TrongLuong tl = optional.get();
            tl.setMa(tl.getMa());
            tl.setDonVi(request.getDonVi());
            tl.setTrangThai(request.getTrangThai());
            tl.setNgayTao(request.getNgayTao());
            tl.setNgaySua(DatetimeUtil.getCurrentDate());
            tl.setValue(request.getValue());
            try {
                repository.save(tl);
                return DataUltil.setData("success", "Xóa thành công");
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy loại sản phẩm để sửa");
        }
    }

    @Override
    public void saveExcel(MultipartFile file) {
        if (this.isValidExcelFile(file)) {
            try {
                List<TrongLuong> trongLuongs = this.getCustomersDataFromExcel(file.getInputStream());
                List<TrongLuong> saveAll = this.repository.saveAll(trongLuongs);
                for (int i = 0; i < saveAll.size(); i++) {
                    TrongLuong tl = saveAll.get(i);
                    tl.setMa("TL" + saveAll.get(i).getId());
                    tl.setNgayTao(DatetimeUtil.getCurrentDate());
                }
                this.repository.saveAll(trongLuongs);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }


    public static boolean isValidExcelFile(MultipartFile file) {

        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<TrongLuong> getCustomersDataFromExcel(InputStream inputStream) {
        List<TrongLuong> trongLuongList = new ArrayList<>();
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
                        TrongLuong trongLuong = new TrongLuong();
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            switch (cellIndex) {
                                case 0 -> trongLuong.setDonVi(cell.getStringCellValue());
                                case 1 -> trongLuong.setTrangThai((int) cell.getNumericCellValue());
                                default -> {
                                }
                            }
                            cellIndex++;
                        }
                        trongLuongList.add(trongLuong);
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
        return trongLuongList;
    }
}
