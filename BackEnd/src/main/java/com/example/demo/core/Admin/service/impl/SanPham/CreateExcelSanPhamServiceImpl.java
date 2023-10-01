package com.example.demo.core.Admin.service.impl.SanPham;

import com.example.demo.core.Admin.model.request.AdminCreatExcelSanPhamRequest;
import com.example.demo.core.Admin.model.request.AdminSanPhamChiTietRequest;
import com.example.demo.core.Admin.repository.AdSanPhamReponsitory;
import com.example.demo.core.Admin.repository.AdTrongLuongRepository;
import com.example.demo.core.Admin.repository.AdVatLieuReponsitory;
import com.example.demo.core.Admin.service.AdSanPhamChiTietService;
import com.example.demo.core.Admin.service.impl.SanPhamServiceImpl;
import com.example.demo.core.Admin.service.impl.TrongLuongServiceImpl;
import com.example.demo.entity.SanPham;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.entity.TrongLuong;
import com.example.demo.entity.VatLieu;
import com.example.demo.reponsitory.VatLieuReponsitory;
import com.example.demo.util.ImageToAzureUtil;
import com.microsoft.azure.storage.StorageException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.*;

@Service
public class CreateExcelSanPhamServiceImpl implements AdSanPhamChiTietService {

    @Autowired
    private AdSanPhamReponsitory sanPhamReponsitory;

    @Autowired
    private AdVatLieuReponsitory vatLieuReponsitory;

    @Autowired
    private AdTrongLuongRepository adTrongLuongRepository;

    @Autowired
    private ImageToAzureUtil imageToAzureUtil;

    @Override
    public List<SanPhamChiTiet> exportCustomerToExcel(HttpServletResponse response) throws IOException {
        return null;
    }

    @Override
    public void saveExcel(MultipartFile file) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
        if (this.isValidExcelFile(file)) {
            List<AdminCreatExcelSanPhamRequest> creatExcelSanPhamRequests = this.getCustomersDataFromExcel(file.getInputStream());
            creatExcelSanPhamRequests.forEach(System.out::println);
//            List<SanPhamChiTiet> saveSanPhamChiTiet = this.saveAll(sanPhamChiTietRequests);
//            List<MauSacChiTiet> mauSac = this.saveAllMauSacChiTiet(sanPhamChiTietRequests, saveSanPhamChiTiet);
//            this.saveAllSizeChiTiet(sanPhamChiTietRequests, saveSanPhamChiTiet);
//            this.saveAllImage(sanPhamChiTietRequests, saveSanPhamChiTiet);
        }
    }

    public static boolean isValidExcelFile(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    // Lưu danh sách sản phẩm chi tiết vào cơ sở dữ liệu
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


    public List<AdminCreatExcelSanPhamRequest> getCustomersDataFromExcel(InputStream inputStream) {
        List<AdminCreatExcelSanPhamRequest> createExcel = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(3);

            if (workbook != null) {
//                System.out.println("Workbook co ton tai");
                if (sheet != null) {
//                    System.out.println("sheet ton tai");
                    int rowIndex = 0;
                    XSSFDrawing drawing = sheet.createDrawingPatriarch();
                    for (Row row : sheet) {
                        if (rowIndex == 0) {
                            rowIndex++;
                            continue;
                        }
                        Iterator<Cell> cellIterator = row.iterator();
                        int cellIndex = 0;
                        AdminCreatExcelSanPhamRequest adCreateExcel = new AdminCreatExcelSanPhamRequest();
                        while (cellIterator.hasNext()) {

                            Cell cell = cellIterator.next();
                            switch (cellIndex) {
                                case 0 -> {
                                    String ten = cell.getStringCellValue();
                                    SanPham sanPham = sanPhamReponsitory.findByTenSanPhamExcel(ten);
                                    adCreateExcel.setSanPham(sanPham.getId().toString());
                                }
                                case 1 -> adCreateExcel.setTrangThai(String.valueOf((int) cell.getNumericCellValue()));
                                case 2 -> {
                                    String ten = cell.getStringCellValue();
                                    VatLieu vatLieu = vatLieuReponsitory.findByTenVatLieuExcel(ten);
                                    System.out.println(vatLieu.getTen());

//                                    adCreateExcel.setVatLieu(vatLieu.getId().toString());
                                }
                                case 3 -> {
                                    String ten = cell.getStringCellValue();
                                    TrongLuong trongLuong = adTrongLuongRepository.findByTenTrongLuongExcel(ten);
                                    adCreateExcel.setTrongLuong(trongLuong.getId().toString());
                                }
                                case 4 -> adCreateExcel.setGiaBan(String.valueOf((int) cell.getNumericCellValue()));
                                case 5 -> adCreateExcel.setGiaNhap(String.valueOf((int) cell.getNumericCellValue()));
                                case 6 -> adCreateExcel.setSoLuongTon(String.valueOf((int) cell.getNumericCellValue()));
                                case 7 -> {
                                    String id = cell.getStringCellValue();
                                    adCreateExcel.setIdMauSac(new ArrayList<>(Arrays.asList(id.split(","))));
                                }
                                case 8 -> {
                                    String id = cell.getStringCellValue();
                                    adCreateExcel.setIdSize(new ArrayList<>(Arrays.asList(id.split(","))));
                                }
                                case 9 -> {
                                    String id = String.valueOf((int) cell.getNumericCellValue());
                                    adCreateExcel.setSoLuongSize(new ArrayList<>(Arrays.asList(id.split(","))));
                                }
//                                case 9 -> sanPhamChiTiet.setMoTaMauSacChiTiet(cell.getStringCellValue());
                                case 10 -> {
                                    String id = cell.getStringCellValue();
                                    List<String> url = new ArrayList<>(Arrays.asList(id.split(",")));
                                    List<String> imgMauSacList = new ArrayList<>();
                                    url.forEach(s -> {
                                        String azureImageUrl = null;
                                        try {
                                            azureImageUrl = imageToAzureUtil.uploadImageToAzure(s);
                                        } catch (URISyntaxException | StorageException | IOException | InvalidKeyException e) {
                                            e.printStackTrace();
                                        }
                                        imgMauSacList.add(azureImageUrl);

                                    });
                                    adCreateExcel.setImgMauSac(imgMauSacList);
                                }
                                case 11 -> {
                                    String id = cell.getStringCellValue();
                                    List<String> url = new ArrayList<>(Arrays.asList(id.split(",")));
                                    List<String> imgList = new ArrayList<>();
                                    url.forEach(s -> {
                                        String azureImageUrl = null;
                                        try {
                                            azureImageUrl = imageToAzureUtil.uploadImageToAzure(s);
                                        } catch (URISyntaxException | StorageException | IOException | InvalidKeyException e) {
                                            e.printStackTrace();
                                        }
                                        imgList.add(azureImageUrl);
                                    });
                                    adCreateExcel.setImagesProduct(imgList);
                                }
                                default -> {
                                }
                            }
                            cellIndex++;
                        }
                        createExcel.add(adCreateExcel);
                    }

                } else {
                    System.out.println("sheet ko ton tai.");
                }
            } else {
                System.out.println("Workbook is null. Không thể đọc dữ liệu từ Excel.");
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return createExcel;
    }

    @Override
    public Page<SanPhamChiTiet> getAll(Integer page, String upAndDown, Integer trangThai) {
        return null;
    }

    @Override
    public SanPhamChiTiet getOne(Integer id) {
        return null;
    }

    @Override
    public SanPhamChiTiet add(AdminSanPhamChiTietRequest dto) {
        return null;
    }

    @Override
    public HashMap<String, Object> update(AdminSanPhamChiTietRequest dto, Integer id) {
        return null;
    }

    @Override
    public HashMap<String, Object> delete(AdminSanPhamChiTietRequest dto, Integer id) {
        return null;
    }


}
