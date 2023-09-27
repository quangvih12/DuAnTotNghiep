package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.model.request.AdminSanPhamChiTietRequest;
import com.example.demo.entity.Image;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.MauSacChiTiet;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.entity.Size;
import com.example.demo.entity.SizeChiTiet;
import com.example.demo.reponsitory.ChiTietSanPhamReponsitory;
import com.example.demo.reponsitory.ImageReponsitory;
import com.example.demo.reponsitory.MauSacChiTietReponsitory;
import com.example.demo.reponsitory.SizeChiTietReponsitory;
import com.example.demo.reponsitory.VatLieuReponsitory;
import com.example.demo.core.Admin.service.AdSanPhamChiTietService;
import com.example.demo.util.DatetimeUtil;
import com.example.demo.util.ExcelExportUtils;
import com.example.demo.util.ImageToAzureUtil;
import com.microsoft.azure.storage.StorageException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SanPhamChiTietServiceImpl implements AdSanPhamChiTietService {

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

    @Autowired
    private ImageToAzureUtil imageToAzureUtil;


    @Override
    public Page<SanPhamChiTiet> getAll(Integer page, String upAndDown, Integer trangThai) {
//        if (upAndDown == null && trangThai == null) {
//            Sort sort = Sort.by(Sort.Direction.DESC, "id");
//            Pageable pageable = PageRequest.of(page, 5, sort);
//            return chiTietSanPhamReponsitory.findAll(pageable);
//        } else if (trangThai != null && upAndDown == null) {
//            Pageable pageable = PageRequest.of(page, 5);
//            return chiTietSanPhamReponsitory.getbyTrangThai(trangThai, pageable);
//        } else if (trangThai == null && upAndDown != null) {
//            Sort sort = (upAndDown == null || upAndDown.equals("asc")) ? Sort.by(Sort.Direction.ASC, "giaBan") : Sort.by(Sort.Direction.DESC, "giaBan");
//            Pageable pageable = PageRequest.of(page, 5, sort);
//            return chiTietSanPhamReponsitory.findAll(pageable);
//        } else {
//            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            Pageable pageable = PageRequest.of(page, 5);
            return chiTietSanPhamReponsitory.findAll(pageable);
     //   }
    }

    @Override
    public SanPhamChiTiet getOne(Integer id) {
        Optional<SanPhamChiTiet> optional = this.chiTietSanPhamReponsitory.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public SanPhamChiTiet add(AdminSanPhamChiTietRequest dto) {
//        SanPhamChiTiet sanPhamChiTiet = dto.dtoToEntity(new SanPhamChiTiet());
        System.out.println(dto.toString());
//            SanPhamChiTiet sanPhamChiTiet = this.chiTietSanPhamReponsitory.save(sanPham);
//            this.saveMauSac(dto.getIdMauSac(), dto.getMoTaMauSacChiTiet(), sanPhamChiTiet, file);
//            this.saveSize(dto.getIdSize(), sanPhamChiTiet, Integer.valueOf(dto.getSoLuongSize()));
//            this.saveImage(files, sanPhamChiTiet);
      return  null;

    }

    //lưu mau sắc chi tiết
    public Iterable<MauSacChiTiet> saveMauSac(List<String> idMauSac, String moTa, SanPhamChiTiet sanPhamChiTiet, MultipartFile[] files) {
        // gán id màu sắc  và id san pham chi tiết vào đối tượng màu sắc chi tiết
        List<MauSacChiTiet> mauSacChiTietList = new ArrayList<>();

        // duyệt qua danh sách files theo thứ tự và lấy phần tử tương ứng
        AtomicInteger count = new AtomicInteger(0);

        idMauSac.forEach(mauSac -> {
            MauSacChiTiet mauSacChiTiet = new MauSacChiTiet();
            mauSacChiTiet.setMauSac(MauSac.builder().id(Integer.valueOf(mauSac)).build());
            mauSacChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
            mauSacChiTiet.setMoTa(moTa);
            mauSacChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
            mauSacChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
            MultipartFile file = files[count.get()];
            try {
                String imageUrl = imageToAzureUtil.uploadImage(file);
                mauSacChiTiet.setAnh(imageUrl);
            } catch (IOException | StorageException | URISyntaxException e) {
                e.printStackTrace();
            }
            mauSacChiTietList.add(mauSacChiTiet);
            count.getAndIncrement();
        });

        return this.mauSacChiTietReponsitory.saveAll(mauSacChiTietList);
    }

    // lưu size chi tiet
    public SizeChiTiet saveSize(List<String> idSize, SanPhamChiTiet sanPhamChiTiet, Integer soLuongSize) {
        // gán id size  và id san pham chi tiết vào đối tượng size chi tiết
        SizeChiTiet sizeChiTiet = new SizeChiTiet();
        idSize.forEach(size -> {
            sizeChiTiet.setSize(Size.builder().id(Integer.valueOf(size)).build());
        });
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
                String imageUrl = imageToAzureUtil.uploadImage(file);
                image.setAnh(imageUrl);
                image.setSanPhamChiTiet(sanPhamChiTiet);
                image.setTrangThai(1);
                image.setNgayTao(DatetimeUtil.getCurrentDate());
                imageList.add(image);
            } catch (IOException | StorageException | URISyntaxException e) {
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
    public HashMap<String, Object> update(AdminSanPhamChiTietRequest dto, Integer id) {
        return null;
    }

    @Override
    public HashMap<String, Object> delete(AdminSanPhamChiTietRequest dto, Integer id) {
        return null;
    }

    @Override
    public void saveExcel(MultipartFile file) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
//        if (this.isValidExcelFile(file)) {
//            List<SanPhamChiTietRequest> sanPhamChiTietRequests = this.getCustomersDataFromExcel(file.getInputStream());
//            List<SanPhamChiTiet> saveSanPhamChiTiet = this.saveAll(sanPhamChiTietRequests);
//            List<MauSacChiTiet> mauSac = this.saveAllMauSacChiTiet(sanPhamChiTietRequests, saveSanPhamChiTiet);
//            this.saveAllSizeChiTiet(sanPhamChiTietRequests, saveSanPhamChiTiet);
//            this.saveAllImage(sanPhamChiTietRequests, saveSanPhamChiTiet);
//        }
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

//    // Lưu danh sách sản phẩm chi tiết vào cơ sở dữ liệu
//    public List<SanPhamChiTiet> saveAll(List<SanPhamChiTietRequest> sanPhamChiTietRequests) {
//        List<SanPhamChiTiet> sanPhamChiTiets = new ArrayList<>();
//        sanPhamChiTietRequests.forEach(request -> {
//            Optional<SanPhamChiTiet> existingChiTiet = chiTietSanPhamReponsitory.findBySanPhamId(Integer.valueOf(request.getSanPham()));
//            if (existingChiTiet.isPresent()) {
//                SanPhamChiTiet sanPhamChiTiet = existingChiTiet.get();
//                // Cập nhật thông tin sản phẩm chi tiết
//                sanPhamChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
//                sanPhamChiTiet.setSanPham(SanPham.builder().id(Integer.valueOf(request.getSanPham())).build());
//                sanPhamChiTiet.setVatLieu(VatLieu.builder().id(Integer.valueOf(request.getVatLieu())).build());
//                sanPhamChiTiet.setTrongLuong(TrongLuong.builder().id(Integer.parseInt(request.getTrongLuong())).build());
//                sanPhamChiTiet.setTrangThai(Integer.valueOf(request.getTrangThai()));
//                sanPhamChiTiet.setSoLuongTon(Integer.valueOf(request.getSoLuongTon()));
//                sanPhamChiTiet.setGiaBan(BigDecimal.valueOf(Long.valueOf(request.getGiaBan())));
//                sanPhamChiTiet.setGiaNhap(BigDecimal.valueOf(Long.valueOf(request.getGiaNhap())));
//                sanPhamChiTiets.add(sanPhamChiTiet);
//            } else {
//                SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
//                // Thiết lập thông tin sản phẩm chi tiết mới
//                sanPhamChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
//                sanPhamChiTiet.setSanPham(SanPham.builder().id(Integer.valueOf(request.getSanPham())).build());
//                sanPhamChiTiet.setVatLieu(VatLieu.builder().id(Integer.valueOf(request.getVatLieu())).build());
//                sanPhamChiTiet.setTrongLuong(TrongLuong.builder().id(Integer.parseInt(request.getTrongLuong())).build());
//                sanPhamChiTiet.setTrangThai(Integer.valueOf(request.getTrangThai()));
//                sanPhamChiTiet.setSoLuongTon(Integer.valueOf(request.getSoLuongTon()));
//                sanPhamChiTiet.setGiaBan(BigDecimal.valueOf(Long.valueOf(request.getGiaBan())));
//                sanPhamChiTiet.setGiaNhap(BigDecimal.valueOf(Long.valueOf(request.getGiaNhap())));
//                sanPhamChiTiets.add(sanPhamChiTiet);
//            }
//        });
//        return chiTietSanPhamReponsitory.saveAll(sanPhamChiTiets);
//    }
//
//    // Lưu danh sách màu sắc chi tiết vào cơ sở dữ liệu
//    public List<MauSacChiTiet> saveAllMauSacChiTiet(List<SanPhamChiTietRequest> sanPhamChiTietRequests, List<SanPhamChiTiet> savedSanPhamChiTiets) {
//        List<MauSacChiTiet> mauSacChiTiets = new ArrayList<>();
//        sanPhamChiTietRequests.forEach(request -> {
//            int index = sanPhamChiTietRequests.indexOf(request);
//            SanPhamChiTiet sanPhamChiTiet = savedSanPhamChiTiets.get(index);
//            request.getIdMauSac().forEach(mauSac -> {
//                List<MauSacChiTiet> mauSacChiTietList = mauSacChiTietReponsitory.findBySanPhamChiTietIdAndMauSacId(sanPhamChiTiet.getId(), Integer.valueOf(mauSac));
//                if (mauSacChiTietList.isEmpty()) {
//                    // Thiết lập thông tin màu sắc chi tiết mới và thêm vào danh sách
//                    MauSacChiTiet mauSacChiTiet = new MauSacChiTiet();
//                    mauSacChiTiet.setMauSac(MauSac.builder().id(Integer.valueOf(mauSac)).build());
//                    mauSacChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
//                    mauSacChiTiet.setTrangThai(1);
//                    mauSacChiTiet.setAnh(request.getImgMauSac().get(Integer.parseInt(mauSac)));
//                    mauSacChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
//                    mauSacChiTiet.setMoTa(request.getMoTaMauSacChiTiet());
//                    mauSacChiTiets.add(mauSacChiTiet);
//                } else {
//                    // Xóa các phần tử cũ khỏi danh sách mauSacChiTiets
//                    mauSacChiTiets.removeAll(mauSacChiTietList);
//
//                    // Cập nhật thông tin màu sắc chi tiết cho danh sách đã tìm thấy
//                    mauSacChiTietList.stream().map(mauSacChiTiet -> {
//                        mauSacChiTiet.setMauSac(MauSac.builder().id(Integer.valueOf(mauSac)).build());
//                        mauSacChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
//                        mauSacChiTiet.setTrangThai(1);
//                        mauSacChiTiet.setAnh(request.getImgMauSac().get(Integer.parseInt(mauSac)));
//                        mauSacChiTiet.setNgayTao(mauSacChiTiet.getNgayTao());
//                        mauSacChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());
//                        mauSacChiTiet.setMoTa(request.getMoTaMauSacChiTiet());
//                        return mauSacChiTiet;
//                    }).forEach(mauSacChiTiets::add);
//                }
//            });
//        });
//        return this.mauSacChiTietReponsitory.saveAll(mauSacChiTiets);
//    }
//
//
//    // Lưu danh sách size chi tiết vào cơ sở dữ liệu
//    public List<SizeChiTiet> saveAllSizeChiTiet(List<SanPhamChiTietRequest> sanPhamChiTietRequests, List<SanPhamChiTiet> savedSanPhamChiTiets) {
//        List<SizeChiTiet> sizeChiTiets = new ArrayList<>();
//        sanPhamChiTietRequests.forEach(request -> {
//            int index = sanPhamChiTietRequests.indexOf(request);
//            SanPhamChiTiet sanPhamChiTiet = savedSanPhamChiTiets.get(index);
//
//            request.getIdSize().forEach(size -> {
//                List<SizeChiTiet> sizeChiTietList = sizeChiTietReponsitory.findBySanPhamChiTietIdAndSizeId(Integer.valueOf(request.getSanPham()), Integer.valueOf(size));
//                if (!sizeChiTietList.isEmpty()) {
//                    // Cập nhật thông tin size chi tiết
//                    sizeChiTietList.stream().map(sizeChiTiet -> {
//                        sizeChiTiet.setSize(Size.builder().id(Integer.valueOf(size)).build());
//                        sizeChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
//                        sizeChiTiet.setTrangThai(1);
//                        sizeChiTiet.setMoTa(request.getMoTaMauSacChiTiet());
//                        sizeChiTiet.setSoLuong(Integer.valueOf(request.getSoLuongSize()));
//                        sizeChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());
//                        return sizeChiTiet;
//                    }).forEach(sizeChiTiets::add);
//                } else {
//                    // Thiết lập thông tin size  chi tiết mới
//                    SizeChiTiet sizeChiTiet = new SizeChiTiet();
//                    sizeChiTiet.setSize(Size.builder().id(Integer.valueOf(size)).build());
//                    sizeChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
//                    sizeChiTiet.setTrangThai(1);
//                    sizeChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
//                    sizeChiTiet.setMoTa(request.getMoTaMauSacChiTiet());
//                    sizeChiTiet.setSoLuong(Integer.valueOf(request.getSoLuongSize()));
//                    sizeChiTiets.add(sizeChiTiet);
//                }
//            });
//        });
//        return this.sizeChiTietReponsitory.saveAll(sizeChiTiets);
//    }
//
//    // Lưu danh sách image  vào cơ sở dữ liệu
//    public void saveAllImage(List<SanPhamChiTietRequest> sanPhamChiTietRequests, List<SanPhamChiTiet> savedSanPhamChiTiets) {
//        List<Image> imageList = new ArrayList<>();
//        sanPhamChiTietRequests.forEach(request -> {
//            int index = sanPhamChiTietRequests.indexOf(request);
//            SanPhamChiTiet sanPhamChiTiet = savedSanPhamChiTiets.get(index);
//            List<Image> list = imageReponsitory.findBySanPhamId(Integer.valueOf(request.getSanPham()));
//            request.getImages().forEach(images -> {
//                if (!list.isEmpty()) {
//                    // Cập nhật thông tin image
//                    list.stream().map(image -> {
//                        image.setSanPhamChiTiet(sanPhamChiTiet);
//                        image.setTrangThai(1);
//                        image.setNgaySua(DatetimeUtil.getCurrentDate());
//                        image.setAnh(images);
//                        imageList.add(image);
//                        return image;
//                    }).forEach(imageList::add);
//
//                } else {
//                    // Thiết lập thông tin image mới
//                    Image image = new Image();
//                    image.setSanPhamChiTiet(sanPhamChiTiet);
//                    image.setTrangThai(1);
//                    image.setNgayTao(DatetimeUtil.getCurrentDate());
//                    image.setAnh(images);
//                    imageList.add(image);
//                }
//
//            });
//        });
//        List<Image> list = this.imageReponsitory.saveAll(imageList);
//        list.forEach(image -> {
//            image.setMa("IM" + image.getId());
//        });
//        this.imageReponsitory.saveAll(list);
//    }
//
//
//    public List<SanPhamChiTietRequest> getCustomersDataFromExcel(InputStream inputStream) {
//        List<SanPhamChiTietRequest> sanPhamChiTietList = new ArrayList<>();
//        try {
//            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//            XSSFSheet sheet = workbook.getSheetAt(3);
//
//            if (workbook != null) {
////                System.out.println("Workbook co ton tai");
//                if (sheet != null) {
////                    System.out.println("sheet ton tai");
//                    int rowIndex = 0;
//                    XSSFDrawing drawing = sheet.createDrawingPatriarch();
//                    for (Row row : sheet) {
//                        if (rowIndex == 0) {
//                            rowIndex++;
//                            continue;
//                        }
//                        Iterator<Cell> cellIterator = row.iterator();
//                        int cellIndex = 0;
//                        SanPhamChiTietRequest sanPhamChiTiet = new SanPhamChiTietRequest();
//                        while (cellIterator.hasNext()) {
//
//                            Cell cell = cellIterator.next();
//                            switch (cellIndex) {
//                                case 0 -> {
//                                    int id = (int) cell.getNumericCellValue();
//                                    SanPham sanPham = sanPhamService.getById(id);
//                                    sanPhamChiTiet.setSanPham(sanPham.getId().toString());
//                                }
//                                case 1 -> sanPhamChiTiet.setTrangThai(String.valueOf((int) cell.getNumericCellValue()));
//                                case 2 -> {
//                                    int id = (int) cell.getNumericCellValue();
//                                    VatLieu vatLieu = vatLieuReponsitory.findById(id).orElse(null);
//                                    sanPhamChiTiet.setVatLieu(vatLieu.getId().toString());
//                                }
//                                case 3 -> {
//                                    int id = (int) cell.getNumericCellValue();
//                                    TrongLuong trongLuong = trongLuongService.getById(id);
//                                    sanPhamChiTiet.setTrongLuong(trongLuong.getId().toString());
//                                }
//                                case 4 -> sanPhamChiTiet.setGiaBan(String.valueOf((int) cell.getNumericCellValue()));
//                                case 5 -> sanPhamChiTiet.setGiaNhap(String.valueOf((int) cell.getNumericCellValue()));
//                                case 6 -> sanPhamChiTiet.setSoLuongTon(String.valueOf((int) cell.getNumericCellValue()));
//                                case 7 -> {
//                                    String id = cell.getStringCellValue();
//                                    sanPhamChiTiet.setIdMauSac(new ArrayList<>(Arrays.asList(id.split(","))));
//                                }
//                                case 8 -> {
//                                    String id = cell.getStringCellValue();
//                                    sanPhamChiTiet.setIdSize(new ArrayList<>(Arrays.asList(id.split(","))));
//                                }
//                                case 9 -> sanPhamChiTiet.setSoLuongSize(String.valueOf((int) cell.getNumericCellValue()));
////                                case 9 -> sanPhamChiTiet.setMoTaMauSacChiTiet(cell.getStringCellValue());
//                                case 10 -> {
//                                    String id = cell.getStringCellValue();
//                                    List<String> url = new ArrayList<>(Arrays.asList(id.split(",")));
//                                    List<String> imgMauSacList = new ArrayList<>();
//                                    url.forEach(s -> {
//                                        String azureImageUrl = null;
//                                        try {
//                                            azureImageUrl = imageToAzureUtil.uploadImageToAzure(s);
//                                        } catch (URISyntaxException | StorageException | IOException | InvalidKeyException e) {
//                                            e.printStackTrace();
//                                        }
//                                        imgMauSacList.add(azureImageUrl);
//
//                                    });
//                                    sanPhamChiTiet.setImgMauSac(imgMauSacList);
//                                }
//                                case 11 -> {
//                                    String id = cell.getStringCellValue();
//                                    List<String> url = new ArrayList<>(Arrays.asList(id.split(",")));
//                                    List<String> imgList = new ArrayList<>();
//                                    url.forEach(s -> {
//                                        String azureImageUrl = null;
//                                        try {
//                                            azureImageUrl = imageToAzureUtil.uploadImageToAzure(s);
//                                        } catch (URISyntaxException | StorageException | IOException | InvalidKeyException e) {
//                                            e.printStackTrace();
//                                        }
//                                        imgList.add(azureImageUrl);
//                                    });
//                                    sanPhamChiTiet.setImages(imgList);
//                                }
//                                default -> {
//                                }
//                            }
//                            cellIndex++;
//                        }
//                        sanPhamChiTietList.add(sanPhamChiTiet);
//                    }
//
//                } else {
//                    System.out.println("sheet ko ton tai.");
//                }
//            } else {
//                System.out.println("Workbook is null. Không thể đọc dữ liệu từ Excel.");
//            }
//        } catch (
//                IOException e) {
//            e.printStackTrace();
//        }
//        return sanPhamChiTietList;
//    }

}
