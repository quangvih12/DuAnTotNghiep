package com.example.demo.sevice.Impl.Admin;

import com.example.demo.dto.request.SizeRequest;
import com.example.demo.entity.Loai;
import com.example.demo.entity.SanPham;
import com.example.demo.entity.Size;
import com.example.demo.entity.ThuongHieu;
import com.example.demo.reponsitory.SizeReponsitory;
import com.example.demo.sevice.SizeService;
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
public class SizeServiceImpl implements SizeService {
    @Autowired
    private SizeReponsitory sizeReponsitory;

    @Override
    public Page<Size> getAll(Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, 5, sort);
        return sizeReponsitory.findAll(pageable);
    }

    @Override
    public Size getById(Integer id) {
        Optional<Size> optional = this.sizeReponsitory.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public Page<Size> search(String keyword, Integer page) {
        return null;
    }

    @Override
    public HashMap<String, Object> add(SizeRequest dto) {
        Size size = dto.dtoToEntity(new Size());
        try {
            Size sizes = sizeReponsitory.save(size);
            sizes.setMa("SP" + sizes.getId());
            sizeReponsitory.save(sizes);
            return DataUltil.setData("success", "thêm thành công");
        } catch (Exception e) {
            return DataUltil.setData("error", "error");
        }
    }

    @Override
    public HashMap<String, Object> update(SizeRequest dto, Integer id) {
        Optional<Size> optional = sizeReponsitory.findById(id);
        if (optional.isPresent()) {
            Size size = optional.get();
            size.setMa(size.getMa());
            size.setTen(dto.getTen());
            size.setTrangThai(Integer.parseInt(dto.getTrangThai()));
            size.setNgayTao(size.getNgayTao());
            size.setNgaySua(DatetimeUtil.getCurrentDate());
            try {
                sizeReponsitory.save(size);
                return DataUltil.setData("success", "sửa thành công");
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy size để sửa");
        }
    }

    @Override
    public HashMap<String, Object> delete(SizeRequest dto, Integer id) {
        Optional<Size> optional = sizeReponsitory.findById(id);
        if (optional.isPresent()) {
            Size size = optional.get();
            size.setMa(size.getMa());
            size.setTen(size.getTen());
            size.setTrangThai(0);
            size.setNgayTao(size.getNgayTao());
            size.setNgaySua(DatetimeUtil.getCurrentDate());
            try {
                sizeReponsitory.save(size);
                return DataUltil.setData("success", "xóa thành công");
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy size để xóa");
        }
    }

    @Override
    public void saveExcel(MultipartFile file) {
        if (this.isValidExcelFile(file)) {
            try {
                List<Size> sizes = this.getCustomersDataFromExcel(file.getInputStream());
                List<Size> savedSizes = this.sizeReponsitory.saveAll(sizes);

                for (int i = 0; i < sizes.size(); i++) {
                    Size size = sizes.get(i);
                    size.setMa("S" + savedSizes.get(i).getId());
                    size.setNgayTao(DatetimeUtil.getCurrentDate());
                }

                this.sizeReponsitory.saveAll(sizes);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }

    }
    public static boolean isValidExcelFile(MultipartFile file) {

        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<Size> getCustomersDataFromExcel(InputStream inputStream) {
        List<Size> LisSizes = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(2);

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
                        Size size = new Size();
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            switch (cellIndex) {
                                case 0 -> size.setTen(cell.getStringCellValue());
                                case 1 -> size.setTrangThai((int) cell.getNumericCellValue());

                                default -> {
                                }
                            }
                            cellIndex++;
                        }
                        LisSizes.add(size);
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
        return LisSizes;
    }
}
