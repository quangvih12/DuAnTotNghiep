package com.example.demo.sevice.Impl.Admin;

import com.example.demo.dto.request.SanPhamChiTietRequest;
import com.example.demo.entity.Image;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.MauSacChiTiet;
import com.example.demo.entity.SanPham;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.entity.Size;
import com.example.demo.entity.SizeChiTiet;
import com.example.demo.entity.TrongLuong;
import com.example.demo.entity.VatLieu;
import com.example.demo.reponsitory.ChiTietSanPhamReponsitory;
import com.example.demo.reponsitory.ImageReponsitory;
import com.example.demo.reponsitory.MauSacChiTietReponsitory;
import com.example.demo.reponsitory.SizeChiTietReponsitory;
import com.example.demo.reponsitory.VatLieuReponsitory;
import com.example.demo.sevice.SanPhamChiTietService;
import com.example.demo.util.DataUltil;
import com.example.demo.util.DatetimeUtil;
import com.example.demo.util.ExcelExportUtils;
import com.example.demo.util.FileUtil;
import jakarta.servlet.http.HttpServletResponse;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SanPhamChiTietServiceImpl implements SanPhamChiTietService {

    @Autowired
    private ChiTietSanPhamReponsitory chiTietSanPhamReponsitory;

    @Autowired
    private ImageReponsitory imageReponsitory;

    @Autowired
    private SizeChiTietReponsitory sizeChiTietReponsitory;

    @Autowired
    private MauSacChiTietReponsitory mauSacChiTietReponsitory;

    @Autowired
    private SanPhamServiceImpl sanPhamService;

    @Autowired
    private VatLieuReponsitory vatLieuReponsitory;

    @Autowired
    private TrongLuongServiceImpl trongLuongService;

    @Override
    public Page<SanPhamChiTiet> getAll(Integer page, String upAndDown, Integer trangThai) {
        if (upAndDown == null && trangThai == null) {
            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            Pageable pageable = PageRequest.of(page, 5, sort);
            return chiTietSanPhamReponsitory.findAll(pageable);
        } else if (trangThai != null && upAndDown == null) {
            Pageable pageable = PageRequest.of(page, 5);
            return chiTietSanPhamReponsitory.getbyTrangThai(trangThai, pageable);
        } else if (trangThai == null && upAndDown != null) {
            Sort sort = (upAndDown == null || upAndDown.equals("asc")) ? Sort.by(Sort.Direction.ASC, "giaBan") : Sort.by(Sort.Direction.DESC, "giaBan");
            Pageable pageable = PageRequest.of(page, 5, sort);
            return chiTietSanPhamReponsitory.findAll(pageable);
        } else {
            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            Pageable pageable = PageRequest.of(page, 5, sort);
            return chiTietSanPhamReponsitory.findAll(pageable);
        }
    }

    @Override
    public SanPhamChiTiet getOne(Integer id) {
        Optional<SanPhamChiTiet> optional = this.chiTietSanPhamReponsitory.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public HashMap<String, Object> add(SanPhamChiTietRequest dto, MultipartFile[] files, MultipartFile file) {
        SanPhamChiTiet sanPham = dto.dtoToEntity(new SanPhamChiTiet());
        try {
            SanPhamChiTiet sanPhamChiTiet = this.chiTietSanPhamReponsitory.save(sanPham);
            this.saveMauSac(dto.getIdMauSac(), dto.getMoTaMauSacChiTiet(), sanPhamChiTiet, file);
            this.saveSize(dto.getIdSize(), sanPhamChiTiet, Integer.valueOf(dto.getSoLuongSize()));
            this.saveImage(files, sanPhamChiTiet);

            return DataUltil.setData("success", "thêm thành công");
        } catch (Exception e) {
            return DataUltil.setData("error", "error");
        }
    }

    //lưu mau sắc chi tiết
    public MauSacChiTiet saveMauSac(List<String> idMauSac, String moTa, SanPhamChiTiet sanPhamChiTiet, MultipartFile file) {
        // gán id màu sắc  và id san pham chi tiết vào đối tượng màu sắc chi tiết
        MauSacChiTiet mauSacChiTiet = new MauSacChiTiet();
        for (String mauSac : idMauSac) {
            mauSacChiTiet.setMauSac(MauSac.builder().id(Integer.valueOf(mauSac)).build());
        }
        mauSacChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
        mauSacChiTiet.setMoTa(moTa);
        mauSacChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
        mauSacChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
        String encode;
        try {
            encode = FileUtil.fileToBase64(file);
            mauSacChiTiet.setAnh(encode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.mauSacChiTietReponsitory.save(mauSacChiTiet);
    }

    // lưu size chi tiet
    public SizeChiTiet saveSize(List<String> idSize, SanPhamChiTiet sanPhamChiTiet, Integer soLuongSize) {
        // gán id size  và id san pham chi tiết vào đối tượng size chi tiết
        SizeChiTiet sizeChiTiet = new SizeChiTiet();
        for (String size : idSize) {
            sizeChiTiet.setSize(Size.builder().id(Integer.valueOf(size)).build());
        }
        sizeChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
        sizeChiTiet.setSoLuong(soLuongSize);
        sizeChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
        sizeChiTiet.setTrangThai(1);
        return this.sizeChiTietReponsitory.save(sizeChiTiet);
    }


    // lưu ảnh
    public Iterable<Image> saveImage(MultipartFile[] files, SanPhamChiTiet sanPhamChiTiet) {
        List<Image> imageList = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                String encode = FileUtil.fileToBase64(file);
                image.setAnh(encode);
                image.setSanPhamChiTiet(sanPhamChiTiet);
                image.setTrangThai(1);
                image.setNgayTao(DatetimeUtil.getCurrentDate());
                imageList.add(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<Image> images = this.imageReponsitory.saveAll(imageList);
        for (int i = 0; i < images.size(); i++) {
            Image image = images.get(i);
            image.setMa("IM" + images.get(i).getId());
        }
        return this.imageReponsitory.saveAll(imageList);
    }


    @Override
    public HashMap<String, Object> update(SanPhamChiTietRequest dto, Integer id) {
        return null;
    }

    @Override
    public HashMap<String, Object> delete(SanPhamChiTietRequest dto, Integer id) {
        return null;
    }

    @Override
    public void saveExcel(MultipartFile file) {
        if (this.isValidExcelFile(file)) {
            try {
                List<SanPhamChiTietRequest> sanPhamChiTietRequests = this.getCustomersDataFromExcel(file.getInputStream());
                List<SanPhamChiTiet> saveSanPhamChiTiet = this.saveAll(sanPhamChiTietRequests);
                this.saveAllMauSacChiTiet(sanPhamChiTietRequests, saveSanPhamChiTiet);
                this.saveAllSizeChiTiet(sanPhamChiTietRequests, saveSanPhamChiTiet);
                this.saveAllImage(sanPhamChiTietRequests, saveSanPhamChiTiet);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }

    @Override
    public List<SanPhamChiTiet> exportCustomerToExcel(HttpServletResponse response) throws IOException {
        List<SanPhamChiTiet> sanPhamChiTietList = chiTietSanPhamReponsitory.findAll();
        ExcelExportUtils exportUtils = new ExcelExportUtils(sanPhamChiTietList);
        exportUtils.exportDataToExcel(response);
        return sanPhamChiTietList;
    }

    public static boolean isValidExcelFile(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    // Lưu danh sách sản phẩm chi tiết vào cơ sở dữ liệu
    public List<SanPhamChiTiet> saveAll(List<SanPhamChiTietRequest> sanPhamChiTietRequests) {
        List<SanPhamChiTiet> sanPhamChiTiets = new ArrayList<>();
        for (SanPhamChiTietRequest request : sanPhamChiTietRequests) {
            SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
            sanPhamChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
            sanPhamChiTiet.setSanPham(SanPham.builder().id(Integer.valueOf(request.getSanPham())).build());
            sanPhamChiTiet.setVatLieu(VatLieu.builder().id(Integer.valueOf(request.getVatLieu())).build());
            sanPhamChiTiet.setTrongLuong(TrongLuong.builder().id(Integer.parseInt(request.getTrongLuong())).build());
            sanPhamChiTiet.setTrangThai(Integer.valueOf(request.getTrangThai()));
            sanPhamChiTiet.setSoLuongTon(Integer.valueOf(request.getSoLuongTon()));
            sanPhamChiTiet.setGiaBan(BigDecimal.valueOf(Long.valueOf(request.getGiaBan())));
            sanPhamChiTiet.setGiaNhap(BigDecimal.valueOf(Long.valueOf(request.getGiaNhap())));
            sanPhamChiTiets.add(sanPhamChiTiet);
        }
        return this.chiTietSanPhamReponsitory.saveAll(sanPhamChiTiets);
    }

    // Lưu danh sách màu sắc chi tiết vào cơ sở dữ liệu
    public List<MauSacChiTiet> saveAllMauSacChiTiet(List<SanPhamChiTietRequest> sanPhamChiTietRequests, List<SanPhamChiTiet> savedSanPhamChiTiets) {
        List<MauSacChiTiet> mauSacChiTiets = new ArrayList<>();
        for (SanPhamChiTietRequest request : sanPhamChiTietRequests) {
            int index = sanPhamChiTietRequests.indexOf(request);
            SanPhamChiTiet sanPhamChiTiet = savedSanPhamChiTiets.get(index);
            for (String mauSac : request.getIdMauSac()) {
                MauSacChiTiet mauSacChiTiet = new MauSacChiTiet();
                mauSacChiTiet.setMauSac(MauSac.builder().id(Integer.valueOf(mauSac)).build());
                mauSacChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                mauSacChiTiet.setTrangThai(1);
                for (String img : request.getImgMauSac()) {
                    mauSacChiTiet.setAnh(img);
                }
                mauSacChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
                mauSacChiTiet.setMoTa(request.getMoTaMauSacChiTiet());
                mauSacChiTiets.add(mauSacChiTiet);
            }
        }
        return this.mauSacChiTietReponsitory.saveAll(mauSacChiTiets);
    }

    // Lưu danh sách size chi tiết vào cơ sở dữ liệu
    public List<SizeChiTiet> saveAllSizeChiTiet(List<SanPhamChiTietRequest> sanPhamChiTietRequests, List<SanPhamChiTiet> savedSanPhamChiTiets) {
        List<SizeChiTiet> sizeChiTiets = new ArrayList<>();
        for (SanPhamChiTietRequest request : sanPhamChiTietRequests) {
            int index = sanPhamChiTietRequests.indexOf(request);
            SanPhamChiTiet sanPhamChiTiet = savedSanPhamChiTiets.get(index);
            for (String size : request.getIdSize()) {
                SizeChiTiet sizeChiTiet = new SizeChiTiet();
                sizeChiTiet.setSize(Size.builder().id(Integer.valueOf(size)).build());
                sizeChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                sizeChiTiet.setTrangThai(1);
                sizeChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
                sizeChiTiet.setMoTa(request.getMoTaMauSacChiTiet());
                sizeChiTiet.setSoLuong(Integer.valueOf(request.getSoLuongSize()));
                sizeChiTiets.add(sizeChiTiet);
            }
        }
        return this.sizeChiTietReponsitory.saveAll(sizeChiTiets);
    }

    // Lưu danh sách image  vào cơ sở dữ liệu
    public void saveAllImage(List<SanPhamChiTietRequest> sanPhamChiTietRequests, List<SanPhamChiTiet> savedSanPhamChiTiets) {
        List<Image> imageList = new ArrayList<>();
        for (SanPhamChiTietRequest request : sanPhamChiTietRequests) {
            int index = sanPhamChiTietRequests.indexOf(request);
            SanPhamChiTiet sanPhamChiTiet = savedSanPhamChiTiets.get(index);
            for (String images : request.getImages()) {
                Image image = new Image();
                image.setSanPhamChiTiet(sanPhamChiTiet);
                image.setTrangThai(1);
                image.setNgayTao(DatetimeUtil.getCurrentDate());
                image.setAnh(images);
                imageList.add(image);
            }
        }
        List<Image> listImg = this.imageReponsitory.saveAll(imageList);
        for (int i = 0; i < listImg.size(); i++) {
            Image img = listImg.get(i);
            img.setMa("IM" + listImg.get(i).getId());
        }
        this.imageReponsitory.saveAll(listImg);
    }


    public List<SanPhamChiTietRequest> getCustomersDataFromExcel(InputStream inputStream) {
        List<SanPhamChiTietRequest> sanPhamChiTietList = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(3);

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
                        SanPhamChiTietRequest sanPhamChiTiet = new SanPhamChiTietRequest();
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            switch (cellIndex) {
                                case 0 -> {
                                    int id = (int) cell.getNumericCellValue();
                                    SanPham sanPham = sanPhamService.getById(id);
                                    sanPhamChiTiet.setSanPham(sanPham.getId().toString());
                                }
                                case 1 -> sanPhamChiTiet.setTrangThai(String.valueOf((int) cell.getNumericCellValue()));
                                case 2 -> {
                                    int id = (int) cell.getNumericCellValue();
                                    VatLieu vatLieu = vatLieuReponsitory.findById(id).orElse(null);
                                    sanPhamChiTiet.setVatLieu(vatLieu.getId().toString());
                                }
                                case 3 -> {
                                    int id = (int) cell.getNumericCellValue();
                                    TrongLuong trongLuong = trongLuongService.getById(id);
                                    sanPhamChiTiet.setTrongLuong(trongLuong.getId().toString());
                                }
                                case 4 -> sanPhamChiTiet.setGiaBan(String.valueOf((int) cell.getNumericCellValue()));
                                case 5 -> sanPhamChiTiet.setGiaNhap(String.valueOf((int) cell.getNumericCellValue()));
                                case 6 -> sanPhamChiTiet.setSoLuongTon(String.valueOf((int) cell.getNumericCellValue()));
                                case 7 -> {
                                    String id = cell.getStringCellValue();
                                    sanPhamChiTiet.setIdMauSac(new ArrayList<>(Arrays.asList(id.split(","))));
                                }
                                case 8 -> {
                                    String id = cell.getStringCellValue();
                                    sanPhamChiTiet.setIdSize(new ArrayList<>(Arrays.asList(id.split(","))));
                                }
                                case 9 -> sanPhamChiTiet.setSoLuongSize(String.valueOf((int) cell.getNumericCellValue()));
                                case 10 -> {
                                    String img = cell.getStringCellValue();
                                    sanPhamChiTiet.setImgMauSac(new ArrayList<>(Arrays.asList(img.split(","))));
                                }
                                case 11 -> {
                                    String img = cell.getStringCellValue();
                                    sanPhamChiTiet.setImages(new ArrayList<>(Arrays.asList(img.split(","))));
                                }
                                default -> {
                                }
                            }
                            cellIndex++;
                        }
                        sanPhamChiTietList.add(sanPhamChiTiet);
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
        return sanPhamChiTietList;
    }
}
