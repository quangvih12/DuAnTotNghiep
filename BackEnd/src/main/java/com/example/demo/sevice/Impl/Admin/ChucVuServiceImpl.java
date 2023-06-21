package com.example.demo.sevice.Impl.Admin;

import com.example.demo.dto.request.ChucVuRequest;
import com.example.demo.entity.ChucVu;
import com.example.demo.entity.TrongLuong;
import com.example.demo.reponsitory.ChucVuRepository;
import com.example.demo.sevice.ChucVuService;
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
public class ChucVuServiceImpl implements ChucVuService {

    @Autowired
    private ChucVuRepository repository;

    @Override
    public Page<ChucVu> getAll(Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, 5 , sort);
        return repository.findAll(pageable);
    }

    @Override
    public ChucVu getById(Integer id) {
        Optional<ChucVu> optional = repository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public Page<ChucVu> search(String keyword, Integer page) {
        if (keyword == null) {
            return this.getAll(page);
        } else {
            Pageable pageable = PageRequest.of(page, 5);
            return repository.search("%" + keyword + "%", pageable);
        }
    }

    @Override
    public HashMap<String, Object> add(ChucVuRequest dto) {
        ChucVu chucVu = dto.dtoToEntity(new ChucVu());
        try {
            ChucVu cv = repository.save(chucVu);
            cv.setMa("CV"+cv.getId());
            repository.save(cv);
            return DataUltil.setData("success", "thêm thành công");
        } catch (Exception e) {
            return DataUltil.setData("error", "error");
        }
    }

    @Override
    public HashMap<String, Object> update(ChucVuRequest dto, Integer id) {
        Optional<ChucVu> optional = repository.findById(id);
        if (optional.isPresent()) {
            ChucVu cv = optional.get();
            cv.setMa(cv.getMa());
            cv.setTen(dto.getTen());
            cv.setTrangThai(dto.getTrangThai());
            cv.setNgayTao(dto.getNgayTao());
            cv.setNgaySua(DatetimeUtil.getCurrentDate());
            try {
                repository.save(cv);
                return DataUltil.setData("success", "sửa thành công");
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy loại sản phẩm để sửa");
        }
    }

    @Override
    public HashMap<String, Object> delete(ChucVuRequest dto, Integer id) {
        Optional<ChucVu> optional = repository.findById(id);
        if (optional.isPresent()) {
            ChucVu cv = optional.get();
            cv.setMa(cv.getMa());
            cv.setTen(dto.getTen());
            cv.setTrangThai(dto.getTrangThai());
            cv.setNgayTao(dto.getNgayTao());
            cv.setNgaySua(DatetimeUtil.getCurrentDate());
            try {
                repository.save(cv);
                return DataUltil.setData("success", "Xóa thành công");
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy loại sản phẩm để sửa");
        }
    }

    public void saveExcel(MultipartFile file) {
        if (this.isValidExcelFile(file)) {
            try {
                List<ChucVu> chucVus = this.getCustomersDataFromExcel(file.getInputStream());
                List<ChucVu> saveAll = this.repository.saveAll(chucVus);
                for (int i = 0; i < saveAll.size(); i++) {
                    ChucVu cv = saveAll.get(i);
                    cv.setMa("L" + saveAll.get(i).getId());
                    cv.setNgayTao(DatetimeUtil.getCurrentDate());
                }
                this.repository.saveAll(chucVus);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }


    public static boolean isValidExcelFile(MultipartFile file) {

        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<ChucVu> getCustomersDataFromExcel(InputStream inputStream) {
        List<ChucVu> chucVus = new ArrayList<>();
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
                        ChucVu cv = new ChucVu();
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            switch (cellIndex) {
                                case 0 -> cv.setTen(cell.getStringCellValue());
                                case 1 -> cv.setTrangThai((int) cell.getNumericCellValue());
                                default -> {
                                }
                            }
                            cellIndex++;
                        }
                        chucVus.add(cv);
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
        return chucVus;
    }
}
