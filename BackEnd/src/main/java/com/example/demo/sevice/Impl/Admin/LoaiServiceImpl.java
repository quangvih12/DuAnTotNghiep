package com.example.demo.sevice.Impl.Admin;

import com.example.demo.dto.request.LoaiRequest;
import com.example.demo.entity.Loai;
import com.example.demo.reponsitory.LoaiReponsitory;
import com.example.demo.sevice.LoaiService;
import com.example.demo.util.DataUltil;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
            loaiReponsitory.save(loai);
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
            loai.setMa(dto.getMa());
            loai.setTen(dto.getTen());
            loai.setTrangThai(dto.getTrangThai());
            loai.setNgaySua(dto.getNgaySua());
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
            loai.setMa(dto.getMa());
            loai.setTen(dto.getTen());
            loai.setTrangThai(0);
            loai.setNgaySua(dto.getNgaySua());
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


    public static boolean isValidExcelFile(MultipartFile file) {

        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

//    public List<Loai> getCustomersDataFromExcel(InputStream inputStream) {
//        List<Loai> listChiTietSp = new ArrayList<>();
////        try {
////            XSSFWorkbook  workbook = new XSSFWorkbook(inputStream);
////            XSSFSheet sheet = workbook.getSheetAt(0);
////
////            if (workbook != null) {
//////                System.out.println("Workbook co ton tai");
////                if (sheet != null) {
//////                    System.out.println("sheet ton tai");
////                    int rowIndex = 0;
////                    for (Row row : sheet) {
////                        if (rowIndex == 0) {
////                            rowIndex++;
////                            continue;
////                        }
////                        Iterator<Cell> cellIterator = row.iterator();
////                        int cellIndex = 0;
////                        ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
////                        while (cellIterator.hasNext()) {
////                            Cell cell = cellIterator.next();
////                            switch (cellIndex) {
////
////                                case 0 -> {
////                                    int idSanPham = (int) cell.getNumericCellValue();
////                                    SanPham sanPham = sanPhamReponsitory.findById(idSanPham).orElse(null);
////                                    chiTietSanPham.setSanPham(sanPham);
////                                }
////
////
////                                case 1 -> {
////                                    int id = (int) cell.getNumericCellValue();
////                                    DongSp dongSp = dongSanPhamReponsitory.findById(id).orElse(null);
////                                    chiTietSanPham.setDongsp(dongSp);
////                                }
////                                case 2 -> {
////                                    int id = (int) cell.getNumericCellValue();
////                                    MauSac mauSac = mauSacReponsitory.findById(id).orElse(null);
////                                    chiTietSanPham.setMauSac(mauSac);
////                                }
////                                case 3 -> {
////                                    int id = (int) cell.getNumericCellValue();
////                                    thuongHieu thuongHieu = thuongHieuReponsitory.findById(id).orElse(null);
////                                    chiTietSanPham.setThuongHieu(thuongHieu);
////                                }
////                                case 4 -> {
////                                    int id = (int) cell.getNumericCellValue();
////                                    NamSanXuat namSanXuat = namSanXuatReponsitory.findById(id).orElse(null);
////                                    chiTietSanPham.setNSX(namSanXuat);
////                                }
////                                case 5 -> chiTietSanPham.setGiaBan(new BigDecimal(cell.getNumericCellValue()));
////                                case 6 -> chiTietSanPham.setGiaNhap(new BigDecimal(cell.getNumericCellValue()));
////                                case 7 -> chiTietSanPham.setHinhAnh(cell.getStringCellValue());
////                                case 8 -> chiTietSanPham.setSoLuongTon((int) cell.getNumericCellValue());
////                                case 9 -> chiTietSanPham.setTrangThai((int) cell.getNumericCellValue());
////                                case 10 -> chiTietSanPham.setMoTa(cell.getStringCellValue());
////                                default -> {
////                                }
////                            }
////                            cellIndex++;
////                        }
////                        listChiTietSp.add(chiTietSanPham);
////
////                    }
////                } else {
////                    System.out.println("sheet ko ton tai.");
////                }
////            } else {
////                System.out.println("Workbook is null. Không thể đọc dữ liệu từ Excel.");
////            }
////            listChiTietSp.forEach(o -> System.out.println(
////                    "sp: " + o.getSanPham().getId()
////                            + "dsp: " + o.getDongsp().getId()
////                            + "ms: " + o.getMauSac().getId()
////                            + "th: " + o.getThuongHieu().getId()
////                            + "nsx: " + o.getNSX().getId()
////                            + "gB: " + o.getGiaBan()
////                            + "gN: " + o.getGiaNhap()
////                            + "ha: " + o.getHinhAnh()
////                            + " sl: " + o.getSoLuongTon()
////                            + "tt: " + o.getTrangThai()
////                            + "mt: " + o.getMoTa()
////
////            ));
//        } catch (IOException e) {
//            e.getStackTrace();
//        }
//        return listChiTietSp;
//    }
}
