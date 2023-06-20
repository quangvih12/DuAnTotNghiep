package com.example.demo.sevice.Impl.Admin;

import com.example.demo.dto.request.ThuongHieuRequest;
import com.example.demo.entity.ThuongHieu;
import com.example.demo.reponsitory.ThuongHieuReponsitory;
import com.example.demo.sevice.ThuongHieuService;
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
public class ThuongHieuServiceImpl implements ThuongHieuService {

    @Autowired
    private ThuongHieuReponsitory thuongHieuReponsitory;


    @Override
    public Page<ThuongHieu> getAll(Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, 5, sort);
        return thuongHieuReponsitory.findAll(pageable);
    }

    @Override
    public ThuongHieu getById(Integer id) {
        Optional<ThuongHieu> optional = this.thuongHieuReponsitory.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public Page<ThuongHieu> search(String keyword, Integer page) {
        if (keyword == null) {
            return this.getAll(page);
        } else {
            Pageable pageable = PageRequest.of(page, 5);
            return thuongHieuReponsitory.search("%" + keyword + "%", pageable);
        }
    }

    @Override
    public HashMap<String, Object> add(ThuongHieuRequest dto) {
        ThuongHieu thuongHieu = dto.dtoToEntity(new ThuongHieu());
        try {
            ThuongHieu thuongHieus = thuongHieuReponsitory.save(thuongHieu);
            thuongHieus.setMa("TH" + thuongHieus.getId());
            thuongHieuReponsitory.save(thuongHieus);
            return DataUltil.setData("success", "thêm thành công");
        } catch (Exception e) {
            return DataUltil.setData("error", "error");
        }
    }

    @Override
    public HashMap<String, Object> update(ThuongHieuRequest dto, Integer id) {
        Optional<ThuongHieu> optional = thuongHieuReponsitory.findById(id);
        if (optional.isPresent()) {
            ThuongHieu thuongHieu = optional.get();
            thuongHieu.setMa(thuongHieu.getMa());
            thuongHieu.setTen(dto.getTen());
            thuongHieu.setTrangThai(dto.getTrangThai());
            thuongHieu.setNgayTao(thuongHieu.getNgayTao());
            thuongHieu.setNgaySua(DatetimeUtil.getCurrentDate());
            try {
                thuongHieuReponsitory.save(thuongHieu);
                return DataUltil.setData("success", "sửa thành công");
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy loại sản phẩm để sửa");
        }
    }

    @Override
    public HashMap<String, Object> delete(ThuongHieuRequest dto, Integer id) {
        Optional<ThuongHieu> optional = thuongHieuReponsitory.findById(id);
        if (optional.isPresent()) {
            ThuongHieu thuongHieu = optional.get();
            thuongHieu.setMa(thuongHieu.getMa());
            thuongHieu.setTen(thuongHieu.getTen());
            thuongHieu.setTrangThai(0);
            thuongHieu.setNgayTao(thuongHieu.getNgayTao());
            thuongHieu.setNgaySua(DatetimeUtil.getCurrentDate());
            try {
                thuongHieuReponsitory.save(thuongHieu);
                return DataUltil.setData("success", "xóa thành công");
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
                List<ThuongHieu> thuongHieus = this.getCustomersDataFromExcel(file.getInputStream());
                List<ThuongHieu> savedThuongHieus = this.thuongHieuReponsitory.saveAll(thuongHieus);

                for (int i = 0; i < thuongHieus.size(); i++) {
                    ThuongHieu hieu = thuongHieus.get(i);
                    hieu.setMa("TH" + savedThuongHieus.get(i).getId());
                    hieu.setNgayTao(DatetimeUtil.getCurrentDate());
                }

                this.thuongHieuReponsitory.saveAll(thuongHieus);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }

    public static boolean isValidExcelFile(MultipartFile file) {

        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<ThuongHieu> getCustomersDataFromExcel(InputStream inputStream) {
        List<ThuongHieu> thuongHieuList = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(1);

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
                        ThuongHieu thuongHieu = new ThuongHieu();
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            switch (cellIndex) {
                                case 0 -> thuongHieu.setTen(cell.getStringCellValue());
                                case 1 -> thuongHieu.setTrangThai((int) cell.getNumericCellValue());
                                default -> {
                                }
                            }
                            cellIndex++;
                        }
                        thuongHieuList.add(thuongHieu);
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
        return thuongHieuList;
    }
}
