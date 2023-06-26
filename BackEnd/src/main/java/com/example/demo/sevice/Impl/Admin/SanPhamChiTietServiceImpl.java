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
import com.example.demo.util.ImageToAzureUtil;
import com.microsoft.azure.storage.StorageException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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

    @Autowired
    private ImageToAzureUtil imageToAzureUtil;


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
            String imageUrl = imageToAzureUtil.uploadImage(file);
            mauSacChiTiet.setAnh(imageUrl);
        } catch (IOException | StorageException | URISyntaxException e) {
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
    public HashMap<String, Object> update(SanPhamChiTietRequest dto, Integer id) {
        return null;
    }

    @Override
    public HashMap<String, Object> delete(SanPhamChiTietRequest dto, Integer id) {
        return null;
    }

    @Override
    public void saveExcel(MultipartFile file) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
        if (this.isValidExcelFile(file)) {
            List<SanPhamChiTietRequest> sanPhamChiTietRequests = this.getCustomersDataFromExcel(file.getInputStream());
            sanPhamChiTietRequests.forEach(sanPhamChiTietRequest -> {
                sanPhamChiTietRequest.getImgMauSac().forEach(s -> {
                    System.out.println("sa: " + s);
                });
            });

            List<SanPhamChiTiet> saveSanPhamChiTiet = this.saveAll(sanPhamChiTietRequests);
            List<MauSacChiTiet> mauSac = this.saveAllMauSacChiTiet(sanPhamChiTietRequests, saveSanPhamChiTiet);

//            this.saveAllSizeChiTiet(sanPhamChiTietRequests, saveSanPhamChiTiet);
//            this.saveAllImage(sanPhamChiTietRequests, saveSanPhamChiTiet);
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
            Optional<SanPhamChiTiet> existingChiTiet = chiTietSanPhamReponsitory.findBySanPhamId(Integer.valueOf(request.getSanPham()));
            if (existingChiTiet.isPresent()) {
                SanPhamChiTiet sanPhamChiTiet = existingChiTiet.get();
                // Cập nhật thông tin sản phẩm chi tiết
                sanPhamChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
                sanPhamChiTiet.setSanPham(SanPham.builder().id(Integer.valueOf(request.getSanPham())).build());
                sanPhamChiTiet.setVatLieu(VatLieu.builder().id(Integer.valueOf(request.getVatLieu())).build());
                sanPhamChiTiet.setTrongLuong(TrongLuong.builder().id(Integer.parseInt(request.getTrongLuong())).build());
                sanPhamChiTiet.setTrangThai(Integer.valueOf(request.getTrangThai()));
                sanPhamChiTiet.setSoLuongTon(Integer.valueOf(request.getSoLuongTon()));
                sanPhamChiTiet.setGiaBan(BigDecimal.valueOf(Long.valueOf(request.getGiaBan())));
                sanPhamChiTiet.setGiaNhap(BigDecimal.valueOf(Long.valueOf(request.getGiaNhap())));

                sanPhamChiTiets.add(sanPhamChiTiet);
            } else {
                SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
                // Thiết lập thông tin sản phẩm chi tiết mới
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
        }
        return chiTietSanPhamReponsitory.saveAll(sanPhamChiTiets);
    }

    // Lưu danh sách màu sắc chi tiết vào cơ sở dữ liệu
    public List<MauSacChiTiet> saveAllMauSacChiTiet(List<SanPhamChiTietRequest> sanPhamChiTietRequests, List<SanPhamChiTiet> savedSanPhamChiTiets) {
        List<MauSacChiTiet> mauSacChiTiets = new ArrayList<>();
        for (SanPhamChiTietRequest request : sanPhamChiTietRequests) {
            int index = sanPhamChiTietRequests.indexOf(request);
            SanPhamChiTiet sanPhamChiTiet = savedSanPhamChiTiets.get(index);
            request.getIdMauSac().forEach(mauSac -> {
                List<MauSacChiTiet> mauSacChiTietList = mauSacChiTietReponsitory.findBySanPhamChiTietIdAndMauSacId(Integer.valueOf(request.getSanPham()), Integer.valueOf(mauSac));

                if (mauSacChiTietList.isEmpty()) {
                    // Thiết lập thông tin màu sắc chi tiết mới và thêm vào danh sách
                    MauSacChiTiet mauSacChiTiet = new MauSacChiTiet();
                    mauSacChiTiet.setMauSac(MauSac.builder().id(Integer.valueOf(mauSac)).build());
                    mauSacChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                    mauSacChiTiet.setTrangThai(1);
                    List<String> tempImgMauSac = new ArrayList<>();
                    request.getImgMauSac().forEach(s -> {
                        tempImgMauSac.add(s);
                    });
//                    mauSacChiTiet.setImgMauSac(tempImgMauSac);
                    mauSacChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());
                    mauSacChiTiet.setMoTa(request.getMoTaMauSacChiTiet());
                    mauSacChiTiets.add(mauSacChiTiet);
                } else {
                    // Xóa các phần tử cũ khỏi danh sách mauSacChiTiets
                    mauSacChiTiets.removeAll(mauSacChiTietList);

                    // Cập nhật thông tin màu sắc chi tiết cho danh sách đã tìm thấy
                    mauSacChiTietList.forEach(mauSacChiTiet -> {
                        mauSacChiTiet.setMauSac(MauSac.builder().id(Integer.valueOf(mauSac)).build());
                        mauSacChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                        mauSacChiTiet.setTrangThai(1);
                        List<String> tempImgMauSac = new ArrayList<>();
                        request.getImgMauSac().forEach(s -> {
                            tempImgMauSac.add(s);
                        });
//                        mauSacChiTiet.setImgMauSac(tempImgMauSac);
                        mauSacChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
                        mauSacChiTiet.setMoTa(request.getMoTaMauSacChiTiet());
                        mauSacChiTiets.add(mauSacChiTiet);
                    });
                }
            });
        }
        return this.mauSacChiTietReponsitory.saveAll(mauSacChiTiets);
        //        this.mauSacChiTietReponsitory.saveAll(mauSacChiTiets);
    }


    // Lưu danh sách size chi tiết vào cơ sở dữ liệu
    public List<SizeChiTiet> saveAllSizeChiTiet(List<SanPhamChiTietRequest> sanPhamChiTietRequests, List<SanPhamChiTiet> savedSanPhamChiTiets) {
        List<SizeChiTiet> sizeChiTiets = new ArrayList<>();
        for (SanPhamChiTietRequest request : sanPhamChiTietRequests) {
            int index = sanPhamChiTietRequests.indexOf(request);
            SanPhamChiTiet sanPhamChiTiet = savedSanPhamChiTiets.get(index);

            for (String size : request.getIdSize()) {
                List<SizeChiTiet> sizeChiTietList = sizeChiTietReponsitory.findBySanPhamChiTietIdAndSizeId(Integer.valueOf(request.getSanPham()), Integer.valueOf(size));
                if (!sizeChiTietList.isEmpty()) {
                    // Cập nhật thông tin size chi tiết
                    for (SizeChiTiet sizeChiTiet : sizeChiTietList) {
                        sizeChiTiet.setSize(Size.builder().id(Integer.valueOf(size)).build());
                        sizeChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                        sizeChiTiet.setTrangThai(1);
                        sizeChiTiet.setMoTa(request.getMoTaMauSacChiTiet());
                        sizeChiTiet.setSoLuong(Integer.valueOf(request.getSoLuongSize()));
                        sizeChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());
                        sizeChiTiets.add(sizeChiTiet);
                    }
                } else {
                    // Thiết lập thông tin size  chi tiết mới
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
        }
        return this.sizeChiTietReponsitory.saveAll(sizeChiTiets);
    }

    // Lưu danh sách image  vào cơ sở dữ liệu
    public void saveAllImage(List<SanPhamChiTietRequest> sanPhamChiTietRequests, List<SanPhamChiTiet> savedSanPhamChiTiets) {
        List<Image> imageList = new ArrayList<>();
        for (SanPhamChiTietRequest request : sanPhamChiTietRequests) {
            int index = sanPhamChiTietRequests.indexOf(request);
            SanPhamChiTiet sanPhamChiTiet = savedSanPhamChiTiets.get(index);
            Optional<Image> optional = imageReponsitory.findBySanPhamChiTietId(Integer.valueOf(request.getSanPham()));
            for (String images : request.getImages()) {
                if (optional.isPresent()) {
                    // Cập nhật thông tin image
                    Image image = optional.get();
                    image.setSanPhamChiTiet(sanPhamChiTiet);
                    image.setTrangThai(1);
                    image.setNgaySua(DatetimeUtil.getCurrentDate());
                    image.setAnh(images);
                    imageList.add(image);
                } else {
                    // Thiết lập thông tin image mới
                    Image image = new Image();
                    image.setSanPhamChiTiet(sanPhamChiTiet);
                    image.setTrangThai(1);
                    image.setNgayTao(DatetimeUtil.getCurrentDate());
                    image.setAnh(images);
                    imageList.add(image);
                }

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
        List<SanPhamChiTietRequest> sanPhamChiTietLists = new ArrayList<>();
        List<SanPhamChiTietRequest> sanPhamChiTietList = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(3);

            String tempDirPath = "D:\\imgDATN";
            File tempDir = new File(tempDirPath);
            if (!tempDir.exists()) {
                if (tempDir.mkdirs()) {
                    System.out.println("Thư mục tạm đã được tạo thành công.");
                } else {
                    System.out.println("Không thể tạo thư mục tạm.");
                }
            }
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
//                                case 9 -> sanPhamChiTiet.setMoTaMauSacChiTiet(cell.getStringCellValue());
                                default -> {
                                }
                            }
                            cellIndex++;
                        }
                        sanPhamChiTietList.add(sanPhamChiTiet);
                    }


                    List<String> filePathList = new ArrayList<>(); // danh sách chứa đường dẫn tới tệp tin của các ảnh
                    List<byte[]> uniqueImages = new ArrayList<>(); // Danh sách lưu trữ các ảnh duy nhất
                    List<byte[]> uniqueImagesMauSac = new ArrayList<>(); // Danh sách lưu trữ các ảnh duy nhất
                    List<String> filePathListMauSac = new ArrayList<>();
                    for (XSSFShape shape : drawing.getShapes()) {
                        if (shape instanceof XSSFPicture) {
                            XSSFPicture picture = (XSSFPicture) shape;
                            XSSFClientAnchor anchor = picture.getClientAnchor();

                            int col1 = anchor.getCol1(); // Chỉ số cột bắt đầu của ảnh
                            int col2 = anchor.getCol2();// Chỉ số cột kết thúc của ảnh
                            switch (col1) {
                                case 11:
                                    // Lưu ảnh theo cột 10 (ví dụ: setImageMauSac)
                                    XSSFPictureData pictureData10 = picture.getPictureData();
                                    byte[] imageData10 = pictureData10.getData();
                                    String imageHash10 = Arrays.hashCode(imageData10) + "";
                                    uniqueImagesMauSac.add(imageData10);
                                    break;
                                case 12:
                                    // Lưu ảnh theo cột 11
                                    XSSFPictureData pictureData = ((XSSFPicture) shape).getPictureData();
                                    byte[] imageData = pictureData.getData();
                                    String imageHash = Arrays.hashCode(imageData) + "";
                                    uniqueImages.add(imageData); // Thêm ảnh vào danh sách
                                    break;
                                // Thêm các trường hợp khác nếu cần
                            }


                        }
                    }
                    for (byte[] imageDataMauSac : uniqueImagesMauSac) {
                        // Tạo tên tệp tin duy nhất cho hình ảnh
                        String fileNamee = UUID.randomUUID().toString() + ".jpg";
                        // Đường dẫn tệp tin tạm thời
                        String tempFilePathh = tempDirPath + File.separator + fileNamee;
                        // Lưu hình ảnh tạm thời vào địa chỉ trên đĩa cục bộ
                        File tempFiles = new File(tempFilePathh);
                        try (FileOutputStream fos = new FileOutputStream(tempFiles)) {
                            fos.write(imageDataMauSac);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        filePathListMauSac.add(tempFilePathh);
                    }

                    for (byte[] imageData : uniqueImages) {
                        // Tạo tên tệp tin duy nhất cho hình ảnh
                        String fileName = UUID.randomUUID().toString() + ".jpg";
                        // Đường dẫn tệp tin tạm thời
                        String tempFilePath = tempDirPath + File.separator + fileName;
                        // Lưu hình ảnh tạm thời vào địa chỉ trên đĩa cục bộ
                        File tempFile = new File(tempFilePath);
                        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                            fos.write(imageData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        filePathList.add(tempFilePath);
                    }

//                    sanPhamChiTietList.add(sanPhamChiTiet);
                    for (SanPhamChiTietRequest o : sanPhamChiTietList) {
                        List<String> imgUrlsMauSac = new ArrayList<>();
                        List<String> imgUrls = new ArrayList<>();
                        SanPhamChiTietRequest sanPhamChiTiet = new SanPhamChiTietRequest();
                        sanPhamChiTiet.setSanPham(o.getSanPham());
                        sanPhamChiTiet.setTrangThai(o.getTrangThai());
                        sanPhamChiTiet.setVatLieu(o.getVatLieu());
                        sanPhamChiTiet.setTrongLuong(o.getTrongLuong());
                        sanPhamChiTiet.setGiaBan(o.getGiaBan());
                        sanPhamChiTiet.setGiaNhap(o.getGiaNhap());
                        sanPhamChiTiet.setSoLuongTon(o.getSoLuongTon());
                        sanPhamChiTiet.setIdMauSac(o.getIdMauSac());
                        sanPhamChiTiet.setIdSize(o.getIdSize());
                        sanPhamChiTiet.setSoLuongSize(o.getSoLuongSize());
//                      sanPhamChiTiet.setMoTaMauSacChiTiet(o.getMoTaMauSacChiTiet());

                        // Tải tệp tin từ thư mục tạm thời lên Azure Blob Storage
                        for (String tempFilePath : filePathList) {
//                     Thực hiện quá trình tải lên Azure Blob Storage ở đây
                            String azureImageUrl = imageToAzureUtil.uploadImageToAzure(tempFilePath);
                            imgUrls.add(azureImageUrl);
                        }
                        for (String tempFilePath : filePathListMauSac) {
//                     Thực hiện quá trình tải lên Azure Blob Storage ở đây
                            String azureImageUrl = imageToAzureUtil.uploadImageToAzure(tempFilePath);
                            imgUrlsMauSac.add(azureImageUrl);
                        }
                        sanPhamChiTiet.setImages(imgUrls);
                        sanPhamChiTiet.setImgMauSac(imgUrlsMauSac);
                        sanPhamChiTietLists.add(sanPhamChiTiet);

                        break;
                    }
                } else {
                    System.out.println("sheet ko ton tai.");
                }
            } else {
                System.out.println("Workbook is null. Không thể đọc dữ liệu từ Excel.");
            }

//            if (tempDir.exists()) {
//                deleteDirectory(tempDir);
//            } else System.out.println("thư mục không tồn tại");
        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return sanPhamChiTietLists;
    }

    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }

}
