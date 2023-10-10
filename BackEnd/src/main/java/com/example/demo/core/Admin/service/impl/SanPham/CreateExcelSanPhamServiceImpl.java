package com.example.demo.core.Admin.service.impl.SanPham;

import com.example.demo.core.Admin.model.request.AdminCreatExcelSanPhamRequest;
import com.example.demo.core.Admin.model.request.AdminSanPhamChiTietRequest;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
import com.example.demo.core.Admin.repository.*;
import com.example.demo.core.Admin.service.AdSanPhamChiTietService;
import com.example.demo.entity.*;
import com.example.demo.util.DatetimeUtil;
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
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class CreateExcelSanPhamServiceImpl implements AdSanPhamChiTietService {

    @Autowired
    private AdSanPhamReponsitory sanPhamReponsitory;

    @Autowired
    private AdChiTietSanPhamReponsitory chiTietSanPhamReponsitory;

    @Autowired
    private AdVatLieuReponsitory vatLieuReponsitory;

    @Autowired
    private AdTrongLuongRepository adTrongLuongRepository;

    @Autowired
    private AdMauSacChiTietReponsitory mauSacChiTietReponsitory;

    @Autowired
    private AdSizeChiTietReponsitory sizeChiTietReponsitory;

    @Autowired
    private ImageToAzureUtil imageToAzureUtil;

    @Autowired
    private AdImageReponsitory imageReponsitory;

    @Autowired
    private AdLoaiReponsitory loaiReponsitory;

    @Autowired
    private AdThuongHieuReponsitory thuongHieuReponsitory;

    @Override
    public List<SanPhamChiTiet> exportCustomerToExcel(HttpServletResponse response) throws IOException {
        return null;
    }


    @Override
    public void saveExcel(MultipartFile file) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
        if (this.isValidExcelFile(file)) {
            List<AdminCreatExcelSanPhamRequest> creatExcelSanPhamRequests = this.getCustomersDataFromExcel(file.getInputStream());
            List<SanPhamChiTiet> saveSanPhamChiTiet = this.saveAll(creatExcelSanPhamRequests);

            this.mutitheard(saveSanPhamChiTiet, creatExcelSanPhamRequests);
        }
    }

    public void mutitheard(List<SanPhamChiTiet> saveSanPhamChiTiet, List<AdminCreatExcelSanPhamRequest> creatExcelSanPhamRequests) {

        // Tạo các luồng cho các công việc cần thực hiện đồng thời
        Thread mauSacThread = new Thread(() -> this.saveAllMauSacChiTiet(creatExcelSanPhamRequests, saveSanPhamChiTiet));
        Thread sizeThread = new Thread(() -> this.saveAllSizeChiTiet(creatExcelSanPhamRequests, saveSanPhamChiTiet));
        Thread imageThread = new Thread(() -> this.saveAllImage(creatExcelSanPhamRequests, saveSanPhamChiTiet));

        // Bắt đầu chạy các luồng
        mauSacThread.start();
        sizeThread.start();
        imageThread.start();

        try {
            // Đợi cho tất cả các luồng hoàn thành
            mauSacThread.join();
            sizeThread.join();
            imageThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidExcelFile(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    // Lưu danh sách sản phẩm chi tiết vào cơ sở dữ liệu
    public List<SanPhamChiTiet> saveAll(List<AdminCreatExcelSanPhamRequest> sanPhamChiTietRequests) {
        List<SanPhamChiTiet> sanPhamChiTiets = new ArrayList<>();

        sanPhamChiTietRequests.forEach(request -> {

            SanPhamChiTiet chiTiet = this.findBySanPhamTen(request.getSanPham());

            if (chiTiet == null) {
                SanPham sanPham = SanPham.builder()
                        .ngayTao(DatetimeUtil.getCurrentDate())
                        .quaiDeo(request.getQuaiDeo())
                        .demLot(request.getDemLot())
                        .ten(request.getSanPham())
                        .moTa(request.getMoTa())
                        .loai(Loai.builder().id(Integer.valueOf(request.getLoai())).build())
                        .thuongHieu(ThuongHieu.builder().id(Integer.valueOf(request.getThuongHieu())).build())
                        .trangThai(1)
                        .build();
                SanPham newSanPham = sanPhamReponsitory.save(sanPham);
                newSanPham.setMa("SP " + newSanPham.getId());
                sanPhamReponsitory.save(newSanPham);

                SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
                // Thiết lập thông tin sản phẩm chi tiết mới
                sanPhamChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
                sanPhamChiTiet.setSanPham(newSanPham);
                sanPhamChiTiet.setVatLieu(VatLieu.builder().id(Integer.valueOf(request.getVatLieu())).build());
                sanPhamChiTiet.setTrongLuong(TrongLuong.builder().id(Integer.parseInt(request.getTrongLuong())).build());
                sanPhamChiTiet.setTrangThai(Integer.valueOf(request.getTrangThai()));
                sanPhamChiTiet.setSoLuongTon(Integer.valueOf(request.getSoLuongTon()));
                sanPhamChiTiet.setGiaBan(BigDecimal.valueOf(Long.valueOf(request.getGiaBan())));
                sanPhamChiTiet.setGiaNhap(BigDecimal.valueOf(Long.valueOf(request.getGiaNhap())));
                sanPhamChiTiets.add(sanPhamChiTiet);
            } else {
                SanPhamChiTiet existingChiTiet = chiTietSanPhamReponsitory.findById(chiTiet.getId()).get();
                SanPham sanPham = sanPhamReponsitory.findByTenSanPhamExcel(request.getSanPham());
                existingChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());
                existingChiTiet.setSanPham(sanPham);
                existingChiTiet.setVatLieu(VatLieu.builder().id(Integer.valueOf(request.getVatLieu())).build());
                existingChiTiet.setTrongLuong(TrongLuong.builder().id(Integer.parseInt(request.getTrongLuong())).build());
                existingChiTiet.setTrangThai(Integer.valueOf(request.getTrangThai()));
                existingChiTiet.setSoLuongTon(Integer.valueOf(request.getSoLuongTon()));
                existingChiTiet.setGiaBan(BigDecimal.valueOf(Long.valueOf(request.getGiaBan())));
                existingChiTiet.setGiaNhap(BigDecimal.valueOf(Long.valueOf(request.getGiaNhap())));
                sanPhamChiTiets.add(existingChiTiet);
            }

        });
        return chiTietSanPhamReponsitory.saveAll(sanPhamChiTiets);
    }

    //
//    // Lưu danh sách màu sắc chi tiết vào cơ sở dữ liệu
    public List<MauSacChiTiet> saveAllMauSacChiTiet(List<AdminCreatExcelSanPhamRequest> sanPhamChiTietRequests, List<SanPhamChiTiet> savedSanPhamChiTiets) {
        List<MauSacChiTiet> mauSacChiTiets = new ArrayList<>();

        sanPhamChiTietRequests.forEach(request -> {
            int index = sanPhamChiTietRequests.indexOf(request);
            SanPhamChiTiet sanPhamChiTiet = savedSanPhamChiTiets.get(index);
            request.getIdMauSac().forEach(mauSac -> {
                List<MauSacChiTiet> mauSacChiTietList = mauSacChiTietReponsitory.findBySanPhamChiTietIdAndMauSacId(sanPhamChiTiet.getId(), Integer.valueOf(mauSac));
                if (mauSacChiTietList.isEmpty()) {
                    System.out.println("save mau sac");
                    // Thiết lập thông tin màu sắc chi tiết mới và thêm vào danh sách
                    MauSacChiTiet mauSacChiTiet = new MauSacChiTiet();
                    mauSacChiTiet.setMauSac(MauSac.builder().id(Integer.valueOf(mauSac)).build());
                    mauSacChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                    mauSacChiTiet.setTrangThai(1);
                    mauSacChiTiet.setAnh(request.getImgMauSac().get(Integer.parseInt(mauSac)));
                    mauSacChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
                    mauSacChiTiet.setMoTa(request.getMoTaMauSacChiTiet());
                    mauSacChiTiets.add(mauSacChiTiet);

                } else {
                    // Xóa các phần tử cũ khỏi danh sách mauSacChiTiets
                    mauSacChiTiets.removeAll(mauSacChiTietList);

                    // Cập nhật thông tin màu sắc chi tiết cho danh sách đã tìm thấy
                    mauSacChiTietList.stream().map(mauSacChiTiet -> {
                        mauSacChiTiet.setMauSac(MauSac.builder().id(Integer.valueOf(mauSac)).build());
                        mauSacChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                        mauSacChiTiet.setTrangThai(1);
                        mauSacChiTiet.setAnh(request.getImgMauSac().get(Integer.parseInt(mauSac)));
                        mauSacChiTiet.setNgayTao(mauSacChiTiet.getNgayTao());
                        mauSacChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());
                        mauSacChiTiet.setMoTa(request.getMoTaMauSacChiTiet());
                        return mauSacChiTiet;
                    }).forEach(mauSacChiTiets::add);
                }
            });
        });
        return this.mauSacChiTietReponsitory.saveAll(mauSacChiTiets);
    }

    //
//
//    // Lưu danh sách size chi tiết vào cơ sở dữ liệu
    public List<SizeChiTiet> saveAllSizeChiTiet(List<AdminCreatExcelSanPhamRequest> sanPhamChiTietRequests, List<SanPhamChiTiet> savedSanPhamChiTiets) {
        List<SizeChiTiet> sizeChiTiets = new ArrayList<>();
        sanPhamChiTietRequests.forEach(request -> {
            int index = sanPhamChiTietRequests.indexOf(request);
            SanPhamChiTiet sanPhamChiTiet = savedSanPhamChiTiets.get(index);

            request.getIdSize().forEach(size -> {
                SanPhamChiTiet existingChiTiet = this.findBySanPhamTen(request.getSanPham());
                List<SizeChiTiet> sizeChiTietList = sizeChiTietReponsitory.findBySanPhamChiTietIdAndSizeId(existingChiTiet.getId(), Integer.valueOf(size));
                if (!sizeChiTietList.isEmpty()) {
                    // Cập nhật thông tin size chi tiết
                    sizeChiTietList.stream().map(sizeChiTiet -> {
                        sizeChiTiet.setSize(Size.builder().id(Integer.valueOf(size)).build());
                        sizeChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                        sizeChiTiet.setTrangThai(1);
                        sizeChiTiet.setMoTa(request.getMoTaMauSacChiTiet());

                        request.getSoLuongSize().forEach(i -> {
                            sizeChiTiet.setSoLuong(Integer.valueOf(i));
                        });
                        sizeChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());
                        return sizeChiTiet;
                    }).forEach(sizeChiTiets::add);
                } else {
                    // Thiết lập thông tin size  chi tiết mới
                    SizeChiTiet sizeChiTiet = new SizeChiTiet();
                    sizeChiTiet.setSize(Size.builder().id(Integer.valueOf(size)).build());
                    sizeChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                    sizeChiTiet.setTrangThai(1);
                    sizeChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
                    sizeChiTiet.setMoTa(request.getMoTaMauSacChiTiet());
                    request.getSoLuongSize().forEach(i -> {
                        sizeChiTiet.setSoLuong(Integer.valueOf(i));
                    });
                    sizeChiTiets.add(sizeChiTiet);
                }
            });
        });
        return this.sizeChiTietReponsitory.saveAll(sizeChiTiets);
    }

    public SanPhamChiTiet findBySanPhamTen(String ten) {
        return chiTietSanPhamReponsitory.findBySanPhamTen(ten);
    }

    //
//    // Lưu danh sách image  vào cơ sở dữ liệu
    public void saveAllImage(List<AdminCreatExcelSanPhamRequest> sanPhamChiTietRequests, List<SanPhamChiTiet> savedSanPhamChiTiets) {
        List<Image> imageList = new ArrayList<>();

        sanPhamChiTietRequests.forEach(request -> {
            int index = sanPhamChiTietRequests.indexOf(request);
            SanPhamChiTiet sanPhamChiTiet = savedSanPhamChiTiets.get(index);
            SanPhamChiTiet existingChiTiet = this.findBySanPhamTen(request.getSanPham());
            List<Image> list = imageReponsitory.findBySanPhamId(existingChiTiet.getId());
            request.getImagesProduct().forEach(images -> {
                if (!list.isEmpty()) {
                    // Cập nhật thông tin image
                    list.stream().map(image -> {
                        image.setSanPhamChiTiet(sanPhamChiTiet);
                        image.setTrangThai(1);
                        image.setNgaySua(DatetimeUtil.getCurrentDate());
                        image.setAnh(images);
                        imageList.add(image);
                        return image;
                    }).forEach(imageList::add);

                } else {
                    // Thiết lập thông tin image mới
                    Image image = new Image();
                    image.setSanPhamChiTiet(sanPhamChiTiet);
                    image.setTrangThai(1);
                    image.setNgayTao(DatetimeUtil.getCurrentDate());
                    image.setAnh(images);
                    imageList.add(image);
                }

            });
        });
        List<Image> list = this.imageReponsitory.saveAll(imageList);
        list.forEach(image -> {
            image.setMa("IM" + image.getId());
        });
        this.imageReponsitory.saveAll(list);
    }
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
                                    if (sanPham != null) {
                                        adCreateExcel.setSanPham(ten);
                                    } else {
                                        adCreateExcel.setSanPham(ten);
                                    }

                                }
                                case 1 -> {
                                    adCreateExcel.setTrangThai(String.valueOf((int) cell.getNumericCellValue()));
                                }
                                case 2 -> {
                                    String ten = cell.getStringCellValue();
                                    VatLieu vatLieu = vatLieuReponsitory.findByTenVatLieuExcel(ten);
                                    adCreateExcel.setVatLieu(vatLieu.getId().toString());
                                }
                                case 3 -> {
                                    int value = (int) cell.getNumericCellValue();
                                    TrongLuong trongLuong = adTrongLuongRepository.findByTenTrongLuongExcel(value);
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
                                case 10 -> adCreateExcel.setMoTaMauSacChiTiet(cell.getStringCellValue());
                                case 11 -> {
                                    String id = cell.getStringCellValue();
                                    List<String> url = new ArrayList<>(Arrays.asList(id.split(",")));
                                    List<String> imgMauSacList = azureImgProduct(url);
                                    adCreateExcel.setImgMauSac(imgMauSacList);
                                }
                                case 12 -> {
                                    String id = cell.getStringCellValue();
                                    List<String> url = new ArrayList<>(Arrays.asList(id.split(",")));
                                    List<String> imgList = azureImgProduct(url);
                                    adCreateExcel.setImagesProduct(imgList);
                                }
                                case 13 -> {
                                    String id = cell.getStringCellValue();
                                    adCreateExcel.setQuaiDeo(id);
                                }
                                case 14 -> {
                                    String id = cell.getStringCellValue();
                                    adCreateExcel.setDemLot(id);
                                }
                                case 15 -> {
                                    String id = cell.getStringCellValue();
                                    adCreateExcel.setMoTa(id);
                                }
                                case 16 -> {
                                    String id = cell.getStringCellValue();
                                    Loai loai = loaiReponsitory.findByTen(id).get();
                                    adCreateExcel.setLoai(loai.getId().toString());
                                }
                                case 17 -> {
                                    String id = cell.getStringCellValue();
                                    ThuongHieu thuongHieu = thuongHieuReponsitory.findByTen(id);
                                    adCreateExcel.setThuongHieu(thuongHieu.getId().toString());
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

    public List<String> azureImgProduct(List<String> url) {
        ExecutorService executor = Executors.newFixedThreadPool(20); // Số lượng luồng tối đa là 10
        List<CompletableFuture<String>> futures = url.stream()
                .map(s -> CompletableFuture.supplyAsync(() -> {
                    String azureImageUrl = null;
                    try {
                        azureImageUrl = imageToAzureUtil.uploadImageToAzure(s);
                    } catch (URISyntaxException | StorageException | IOException | InvalidKeyException e) {
                        e.printStackTrace();
                    }
                    return azureImageUrl;
                }, executor))
                .collect(Collectors.toList());

        List<String> imgList = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        executor.shutdown();
        return imgList;
    }


    @Override
    public Page<SanPhamChiTiet> getAll(Integer page, String upAndDown, Integer trangThai) {
        return null;
    }

    @Override
    public SanPhamChiTiet getOne(Integer id) {
        return null;
    }

    public SanPham getSp(String ten) {
        return sanPhamReponsitory.findByTenSanPhamExcel(ten);
    }

    @Override
    public AdminSanPhamChiTietResponse add(AdminSanPhamChiTietRequest dto) {
        return null;
    }

    @Override
    public AdminSanPhamChiTietResponse update(AdminSanPhamChiTietRequest dto, Integer id) {
        return null;
    }

    @Override
    public SanPhamChiTiet delete(Integer id) {
        return null;
    }


}
