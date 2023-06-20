package com.example.demo.sevice.Impl.Admin;

import com.example.demo.dto.request.SanPhamRequest;
import com.example.demo.entity.Loai;
import com.example.demo.entity.SanPham;
import com.example.demo.entity.ThuongHieu;
import com.example.demo.reponsitory.SanPhamReponsitory;
import com.example.demo.sevice.SanPhamSevice;
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
public class SanPhamServiceImpl implements SanPhamSevice {

    @Autowired
    private SanPhamReponsitory sanPhamReponsitory;
    @Autowired
    private LoaiServiceImpl loaiService;
    @Autowired
    private ThuongHieuServiceImpl thuongHieuService;

    @Override
    public Page<SanPham> getAll(Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, 5, sort);
        return sanPhamReponsitory.findAll(pageable);
    }

    @Override
    public SanPham getById(Integer id) {
        Optional<SanPham> optional = this.sanPhamReponsitory.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public Page<SanPham> search(String keyword, Integer page) {
        if (keyword == null) {
            return this.getAll(page);
        } else {
            Pageable pageable = PageRequest.of(page, 5);
            return sanPhamReponsitory.search("%" + keyword + "%", pageable);
        }
    }

    @Override
    public HashMap<String, Object> add(SanPhamRequest dto) {
        SanPham sanPham = dto.dtoToEntity(new SanPham());
        try {
            SanPham sanPhams = sanPhamReponsitory.save(sanPham);
            sanPhams.setMa("SP" + sanPhams.getId());
            sanPhamReponsitory.save(sanPhams);
            return DataUltil.setData("success", "thêm thành công");
        } catch (Exception e) {
            return DataUltil.setData("error", "error");
        }
    }

    @Override
    public HashMap<String, Object> update(SanPhamRequest dto, Integer id) {
        Optional<SanPham> optional = sanPhamReponsitory.findById(id);
        if (optional.isPresent()) {
            SanPham sanPham = optional.get();
            sanPham.setMa(sanPham.getMa());
            sanPham.setTen(dto.getTen());
            sanPham.setTrangThai(Integer.parseInt(dto.getTrangThai()));
            sanPham.setNgayTao(sanPham.getNgayTao());
            sanPham.setNgaySua(DatetimeUtil.getCurrentDate());
            try {
                sanPhamReponsitory.save(sanPham);
                return DataUltil.setData("success", "sửa thành công");
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy loại sản phẩm để sửa");
        }
    }

    @Override
    public HashMap<String, Object> delete(SanPhamRequest dto, Integer id) {
        Optional<SanPham> optional = sanPhamReponsitory.findById(id);
        if (optional.isPresent()) {
            SanPham sanPham = optional.get();
            sanPham.setMa(sanPham.getMa());
            sanPham.setTen(sanPham.getTen());
            sanPham.setTrangThai(0);
            sanPham.setNgayTao(sanPham.getNgayTao());
            sanPham.setNgaySua(DatetimeUtil.getCurrentDate());
            try {
                sanPhamReponsitory.save(sanPham);
                return DataUltil.setData("success", "xóa thành công");
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy loại sản phẩm để xóa");
        }
    }

    @Override
    public void saveExcel(MultipartFile file) {
        if (this.isValidExcelFile(file)) {
            try {
                List<SanPham> sanPhams = this.getCustomersDataFromExcel(file.getInputStream());
                List<SanPham> savedLoais = this.sanPhamReponsitory.saveAll(sanPhams);
                for (int i = 0; i < savedLoais.size(); i++) {
                    SanPham sanPham = savedLoais.get(i);
                    sanPham.setMa("SP" + savedLoais.get(i).getId());
                    sanPham.setNgayTao(DatetimeUtil.getCurrentDate());
                }
                this.sanPhamReponsitory.saveAll(sanPhams);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }


    public static boolean isValidExcelFile(MultipartFile file) {

        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<SanPham> getCustomersDataFromExcel(InputStream inputStream) {
        List<SanPham> LisSanPhams = new ArrayList<>();
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
                        SanPham sanPham = new SanPham();
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            switch (cellIndex) {
                                case 0 -> sanPham.setTen(cell.getStringCellValue());
                                case 1 -> sanPham.setTrangThai((int) cell.getNumericCellValue());
                                case 2 -> sanPham.setMoTa(cell.getStringCellValue());
                                case 3 -> sanPham.setDemLot(cell.getStringCellValue());
                                case 4 -> sanPham.setQuaiDeo(cell.getStringCellValue());
                                case 5 -> {
                                    int id = (int) cell.getNumericCellValue();
                                    ThuongHieu thuongHieu = thuongHieuService.getById(id);
                                    sanPham.setThuongHieu(thuongHieu);
                                }
                                case 6 -> {
                                    int id = (int) cell.getNumericCellValue();
                                    Loai loai = loaiService.getById(id);
                                    sanPham.setLoai(loai);
                                }
                                default -> {
                                }
                            }
                            cellIndex++;
                        }
                        LisSanPhams.add(sanPham);
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
        return LisSanPhams;
    }
}
