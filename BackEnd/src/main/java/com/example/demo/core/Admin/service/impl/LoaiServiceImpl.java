package com.example.demo.sevice.Impl.Admin;

import com.example.demo.dto.request.LoaiRequest;
import com.example.demo.entity.Loai;
import com.example.demo.entity.ThuongHieu;
import com.example.demo.reponsitory.LoaiReponsitory;
import com.example.demo.sevice.LoaiService;
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
public class LoaiServiceImpl implements LoaiService {
    @Autowired
    private LoaiReponsitory loaiReponsitory;

    @Override
    public Page<Loai> getAll(Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, 5, sort);
        return loaiReponsitory.findAll(pageable);
    }

    @Override
    public Loai getById(Integer id) {
        Optional<Loai> optional = this.loaiReponsitory.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public Page<Loai> search(String keyword, Integer page) {
        if (keyword == null) {
            return this.getAll(page);
        } else {
            Pageable pageable = PageRequest.of(page, 5);
            return loaiReponsitory.search("%" + keyword + "%", pageable);
        }
    }

    @Override
    public HashMap<String, Object> add(LoaiRequest dto) {
        Loai loai = dto.dtoToEntity(new Loai());
        try {
            Loai loais = loaiReponsitory.save(loai);
            loais.setMa("L" + loais.getId());
            loaiReponsitory.save(loais);
            return DataUltil.setData("success", "thêm thành công");
        } catch (Exception e) {
            return DataUltil.setData("error", "error");
        }
    }

    @Override
    public HashMap<String, Object> update(LoaiRequest dto, Integer id) {
        Optional<Loai> optional = loaiReponsitory.findById(id);
        if (optional.isPresent()) {
            Loai loai = optional.get();
            loai.setMa(loai.getMa());
            loai.setTen(dto.getTen());
            loai.setTrangThai(dto.getTrangThai());
            loai.setNgayTao(loai.getNgayTao());
            loai.setNgaySua(DatetimeUtil.getCurrentDate());
            try {
                loaiReponsitory.save(loai);
                return DataUltil.setData("success", "sửa thành công");
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy loại sản phẩm để sửa");
        }
    }

    @Override
    public HashMap<String, Object> delete(LoaiRequest dto, Integer id) {
        Optional<Loai> optional = loaiReponsitory.findById(id);
        if (optional.isPresent()) {
            Loai loai = optional.get();
            loai.setMa(loai.getMa());
            loai.setTen(loai.getTen());
            loai.setTrangThai(0);
            loai.setNgayTao(loai.getNgayTao());
            loai.setNgaySua(DatetimeUtil.getCurrentDate());
            try {
                loaiReponsitory.save(loai);
                return DataUltil.setData("success", "xóa thành công");
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy loại sản phẩm để xóa");
        }
    }

    public void saveExcel(MultipartFile file) {
        if (this.isValidExcelFile(file)) {
            try {
                List<Loai> loais = this.getCustomersDataFromExcel(file.getInputStream());
                List<Loai> listLoai = new ArrayList<>();
                for (Loai o : loais) {
                    Optional<Loai> optional = loaiReponsitory.findByTen(o.getTen());
                    if (optional.isPresent()) {
                        // Cập nhật thông tin loai
                        Loai loai = optional.get();
                        loai.setTen(o.getTen());
                        loai.setTrangThai(o.getTrangThai());
                        listLoai.add(loai);
                    } else {
                        // Thiết lập thông tin loai mới
                        Loai loai = new Loai();
                        loai.setTen(o.getTen());
                        loai.setTrangThai(o.getTrangThai());
                        listLoai.add(loai);
                    }
                }
                List<Loai> savedLoais = this.loaiReponsitory.saveAll(listLoai);
                for (int i = 0; i < savedLoais.size(); i++) {
                    Loai loai = savedLoais.get(i);
                    loai.setMa("L" + savedLoais.get(i).getId());
                    loai.setNgayTao(DatetimeUtil.getCurrentDate());
                }
                this.loaiReponsitory.saveAll(listLoai);

            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }


    public static boolean isValidExcelFile(MultipartFile file) {

        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<Loai> getCustomersDataFromExcel(InputStream inputStream) {
        List<Loai> listLoais = new ArrayList<>();
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
                        Loai loai = new Loai();
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            switch (cellIndex) {
                                case 0 -> loai.setTen(cell.getStringCellValue());
                                case 1 -> loai.setTrangThai((int) cell.getNumericCellValue());
                                default -> {
                                }
                            }
                            cellIndex++;
                        }
                        listLoais.add(loai);
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
        return listLoais;
    }
}
