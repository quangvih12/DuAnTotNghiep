package com.example.demo.core.Admin.service.impl.SanPham;

import com.example.demo.core.Admin.model.response.AdminExcelAddSanPhamBO;
import com.example.demo.core.Admin.model.response.AdminExcelAddSanPhamResponse;
import com.example.demo.core.Admin.repository.*;
import com.example.demo.core.Admin.service.AdSanPhamService.AdExcelAddSanPhamService;
import com.example.demo.entity.*;
import com.example.demo.util.DataUltil;
import com.example.demo.util.DatetimeUtil;
import com.example.demo.util.ExcelUtils;
import com.example.demo.util.ImageToAzureUtil;
import com.microsoft.azure.storage.StorageException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AdminExcelAddSanPhamSerivecImpl implements AdExcelAddSanPhamService {

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
    private AdMauSacReponsitory adMauSacReponsitory;

    @Autowired
    private AdSizeReponsitory adSizeReponsitory;

    @Autowired
    private AdThuongHieuReponsitory thuongHieuReponsitory;

    @Autowired
    ImageToAzureUtil getImageToAzureUtil;

    @Override
    public AdminExcelAddSanPhamBO previewDataImportExcel(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);

        Sheet sheet = workbook.getSheetAt(0);

        List<AdminExcelAddSanPhamResponse> list = StreamSupport.stream(sheet.spliterator(), false)
                .skip(1) // Bỏ qua 2 dòng đầu tiên
                .filter(row -> !ExcelUtils.checkNullLCells(row, 1))
                .map(row -> processRow(row))
                .collect(Collectors.toList());


        Map<Boolean, Long> importStatusCounts = list.stream()
                .collect(Collectors.groupingBy(AdminExcelAddSanPhamResponse::isError, Collectors.counting()));

        // set tổng số bản ghi lỗi, tổng số bản ghi thành công, tổng số bản ghi
        AdminExcelAddSanPhamBO adminExcelAddSanPhamBO = new AdminExcelAddSanPhamBO();
        adminExcelAddSanPhamBO.setResponseList(list);
        adminExcelAddSanPhamBO.setTotal(Long.parseLong(String.valueOf(list.size())));
        this.savaData(adminExcelAddSanPhamBO);
        adminExcelAddSanPhamBO.setTotalError(importStatusCounts.getOrDefault(true, 0L));
        adminExcelAddSanPhamBO.setTotalSuccess(importStatusCounts.getOrDefault(false, 0L));

        return adminExcelAddSanPhamBO;
    }

    @Override
    public AdminExcelAddSanPhamResponse processRow(Row row) {
        AdminExcelAddSanPhamResponse userDTO = new AdminExcelAddSanPhamResponse();
        //   int errorCount = 0;

        Long stt = ExcelUtils.getCellLong(row.getCell(0));
        String tenSanPham = ExcelUtils.getCellString(row.getCell(1));
        String TenVatLieu = ExcelUtils.getCellString(row.getCell(2));
        String valueTrongLuong = ExcelUtils.getCellString(row.getCell(3));
        Long giaBan = ExcelUtils.getCellLong(row.getCell(4));
        Long giaNhap = ExcelUtils.getCellLong(row.getCell(5));
        Long soLuong = ExcelUtils.getCellLong(row.getCell(6));
        String tenMauSac = ExcelUtils.getCellString(row.getCell(7));
        List<String> idMauSac = new ArrayList<>();
        String tenSize = ExcelUtils.getCellString(row.getCell(8));
        List<String> idSizes = new ArrayList<>();
        String soLuongSize = String.valueOf(ExcelUtils.getCellString(row.getCell(9)));
        String anhMau1 = ExcelUtils.getCellString(row.getCell(10));
        String anhMau2 = ExcelUtils.getCellString(row.getCell(11));
        String anhMau3 = ExcelUtils.getCellString(row.getCell(12));
        String anhChinh = ExcelUtils.getCellString(row.getCell(13));
        String anh1 = ExcelUtils.getCellString(row.getCell(14));
        String anh2 = ExcelUtils.getCellString(row.getCell(15));
        String anh3 = ExcelUtils.getCellString(row.getCell(16));
        //     String soLuongMauSac = String.valueOf(ExcelUtils.getCellString(row.getCell(17)));
        String quaiDeo = ExcelUtils.getCellString(row.getCell(17));
        String demLot = ExcelUtils.getCellString(row.getCell(18));
        String moTa = ExcelUtils.getCellString(row.getCell(19));
        String tenLoai = ExcelUtils.getCellString(row.getCell(20));
        String tenThuongHieu = ExcelUtils.getCellString(row.getCell(21));

        if (DataUltil.isNullObject(tenSanPham)) {
            userDTO.setImportMessageSanPham("Tên Sản phẩm không được để trống tại vị trí: " + stt);
            userDTO.setError(true);

        } else if (DataUltil.isNullObject(TenVatLieu)) {
            userDTO.setImportMessageVatLieu("Tên vật liệu không được để trống tại vị trí: " + stt);
            userDTO.setError(true);

        } else if (giaBan < giaNhap) {
            userDTO.setImportMessageGiaBan("giá bán phải lớn hơn giá nhập tại vị trí: " + stt);
            userDTO.setError(true);

        } else if (DataUltil.isNullObject(valueTrongLuong)) {
            userDTO.setImportMessageTrongLuong("Trọng lượng không được để trống tại vị trí: " + stt);
            userDTO.setError(true);

        } else if (DataUltil.isNullObject(giaBan)) {
            userDTO.setImportMessageGiaBan("Giá bán không được để trống tại vị trí: " + stt);
            userDTO.setError(true);

        } else if (DataUltil.isNullObject(giaNhap)) {
            userDTO.setImportMessageGiaNhap("Giá Nhập không được để trống tại ví trí: " + stt);
            userDTO.setError(true);

        } else if (DataUltil.isNullObject(soLuong)) {
            userDTO.setImportMessageSoLuong("Số lượng không được để trống tại vị trí: " + stt);
            userDTO.setError(true);

        } else if (DataUltil.isNullObject(tenMauSac)) {
            userDTO.setImportMessageMauSac("Tên màu sắc không được để trống tại vị trí: " + stt);
            userDTO.setError(true);

        } else if (DataUltil.isNullObject(anhChinh)) {
            userDTO.setImportMessageAnhChinh("ảnh chính không được để trống vị trí: " + stt);
            userDTO.setError(true);

        } else if (DataUltil.isNullObject(quaiDeo)) {
            userDTO.setImportMessageQuaiDeo("Quai đeo không được để trống tại ví trí: " + stt);
            userDTO.setError(true);

        } else if (DataUltil.isNullObject(demLot)) {
            userDTO.setImportMessageDemLot("Đệm lót không được để trống tại vị trí: " + stt);
            userDTO.setError(true);

        } else if (DataUltil.isNullObject(tenLoai)) {
            userDTO.setImportMessageLoai("Tên loại không được để trống tại vị trí: " + stt);
            userDTO.setError(true);

        } else if (DataUltil.isNullObject(tenThuongHieu)) {
            userDTO.setImportMessageThuongHieu("Tên thương hiệu không được để trống tại vị trí: " + stt);
            userDTO.setError(true);

        } else {
            TrongLuong trongLuong = adTrongLuongRepository.findByTenTrongLuongExcel(Integer.valueOf(valueTrongLuong));
            VatLieu vatLieu = vatLieuReponsitory.findByTenVatLieuExcel(TenVatLieu);
            String[] soLuongSizeArray = soLuongSize.split(",");
            List<String> listSoLuongSize = new ArrayList<>();
            //     String[] soLuongMauArray = soLuongMauSac.split(",");
            //       List<String> listSoLuongMau = new ArrayList<>();
            Loai loai = loaiReponsitory.findByTens(tenLoai);
            ThuongHieu thuongHieu = thuongHieuReponsitory.findByTen(tenThuongHieu);
            String[] mauSacArray = tenMauSac.split(",");
            List<String> listTenMauSac = new ArrayList<>();
            for (String mauSac : mauSacArray) {
                String trimmedMauSac = mauSac.trim(); // Loại bỏ khoảng trắng trước và sau phần tử
                listTenMauSac.add(trimmedMauSac);
            }


            if (DataUltil.isNullObject(vatLieu)) {
                userDTO.setImportMessageVatLieu("Vật liệu không tồn tại vị trí: " + stt);
                userDTO.setError(true);

            } else if (DataUltil.isNullObject(trongLuong)) {
                userDTO.setImportMessageTrongLuong("Trọng lượng không tồn tại vị trí: " + stt);
                userDTO.setError(true);
            } else if (DataUltil.isNullObject(loai)) {
                userDTO.setImportMessageLoai("loại không tồn tại vị trí: " + stt);
                userDTO.setError(true);
            } else if (DataUltil.isNullObject(thuongHieu)) {
                userDTO.setImportMessageThuongHieu("Thương hiệu không tồn tại vị trí: " + stt);
                userDTO.setError(true);
            } else {
                userDTO.setIdTrongLuong(trongLuong.getId());
                userDTO.setValueTrongLuong(Integer.valueOf(valueTrongLuong));
                userDTO.setImportMessageTrongLuong("SUCCESS");
                userDTO.setIdVatLieu(vatLieu.getId());
                userDTO.setTenVatLieu(TenVatLieu);
                userDTO.setImportMessageVatLieu("SUCCESS");
                userDTO.setTenSanPham(tenSanPham);
                userDTO.setImportMessageSanPham("SUCCESS");
                userDTO.setGiaBan(Integer.valueOf(giaBan.toString()));
                userDTO.setImportMessageGiaBan("SUCCESS");
                userDTO.setGiaNhap(Integer.valueOf(giaNhap.toString()));
                userDTO.setImportMessageGiaNhap("SUCCESS");
                userDTO.setSoLuong(Integer.valueOf(soLuong.toString()));
                userDTO.setImportMessageSoLuong("SUCCESS");
                for (String size : soLuongSizeArray) {
                    String trimmedSize = size.trim(); // Loại bỏ khoảng trắng trước và sau phần tử
                    listSoLuongSize.add(trimmedSize);
                }

//                for (String mau : soLuongMauArray) {
//                    String trimmedMauSac = mau.trim(); // Loại bỏ khoảng trắng trước và sau phần tử
//                    listSoLuongMau.add(trimmedMauSac);
//                }
                List<String> listAnhMauSac = new ArrayList<>(Arrays.asList(anhMau1, anhMau2, anhMau3));
                listAnhMauSac.removeIf(anhMau -> anhMau == null);
                List<String> imgMauSacList = azureImgProduct(listAnhMauSac);
                try {
                    String linkAnh = getImageToAzureUtil.uploadImageToAzure(anhChinh);
                    userDTO.setAnhChinh(linkAnh);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                userDTO.setImportMessageAnhChinh("SUCCESS");
                List<String> listAnh = new ArrayList<>(Arrays.asList(anh1, anh2, anh3));
                List<String> imgList = azureImgProduct(listAnh);
                userDTO.setImagesProduct(imgList);
                userDTO.setQuaiDeo(quaiDeo);
                userDTO.setImportMessageQuaiDeo("SUCCESS");
                userDTO.setDemLot(demLot);
                userDTO.setImportMessageDemLot("SUCCESS");
                userDTO.setMoTa(moTa);
                userDTO.setIdLoai(loai.getId());
                userDTO.setTenLoai(tenLoai);
                userDTO.setImportMessageLoai("SUCCESS");
                userDTO.setIdThuongHieu(thuongHieu.getId());
                userDTO.setTenThuongHieu(tenThuongHieu);
                userDTO.setImportMessageThuongHieu("SUCCESS");
                idMauSac = listTenMauSac.stream()
                        .map(tenMau -> {
                            MauSac mauSac = adMauSacReponsitory.findByTenMauSacExcel(tenMau);
                            return mauSac != null ? mauSac.getId().toString() : null;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                if (idMauSac.isEmpty()) {
                    // Xử lý khi không tìm thấy idMauSac hợp lệ
                    userDTO.setImportMessageMauSac("Không tìm thấy màu sắc hợp lệ tại vị trí: " + stt);
                    userDTO.setError(true);
                } else if (idMauSac.size() < imgMauSacList.size()) {
                    userDTO.setImportMessageMauSac("ảnh của màu sắc không tương ứng với màu sắc tại vị trí: " + stt);
                    userDTO.setError(true);
                } else if (idMauSac.size() < imgMauSacList.size()) {
                    userDTO.setImportMessageSoLuongMau("số lượng của màu sắc không tương ứng với màu sắc tại vị trí: " + stt);
                    userDTO.setError(true);
                } else {
                    userDTO.setIdMauSac(idMauSac);
                    userDTO.setTenMau(listTenMauSac);
                    userDTO.setImgMauSac(imgMauSacList);
                    userDTO.setSoLuongMauSac(listSoLuongSize);
                    userDTO.setImportMessageSoLuongMau("SUCCESS");
                    userDTO.setImportMessageMauSac("SUCCESS");
                    userDTO.setError(false);
                }

                if (!DataUltil.isNullObject(tenSize)) {
                    String[] sizeArray = tenSize.split(",");
                    List<String> listTenSize = new ArrayList<>();
                    for (String size : sizeArray) {
                        String trimmedSize = size.trim(); // Loại bỏ khoảng trắng trước và sau phần tử
                        listTenSize.add(trimmedSize);
                    }
                    idSizes = listTenSize.stream()
                            .map(tenSizes -> {
                                Size size = adSizeReponsitory.findByTenSizeExcel(tenSizes);
                                return size != null ? size.getId().toString() : null;
                            })
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    if (idSizes.isEmpty()) {
                        userDTO.setImportMessageSize("Không tìm thấy size hợp lệ tại vị trí: " + stt);
                        userDTO.setError(true);
                    } else {
                        userDTO.setSoLuongSize(listSoLuongSize);
                        userDTO.setImportMessageSoLuongSize("SUCCESS");
                        userDTO.setIdSize(idSizes);
                        userDTO.setTenSize(listTenSize);
                        userDTO.setImportMessageSize("SUCCESS");
                        userDTO.setError(false);
                    }
                }


            }

        }

        return userDTO;
    }

    @Override
    public AdminExcelAddSanPhamBO savaData(AdminExcelAddSanPhamBO adminExcelAddSanPhamBO) {

        try {
            for (AdminExcelAddSanPhamResponse o : adminExcelAddSanPhamBO.getResponseList()) {
                if (o.getTenSanPham() == null || o.getGiaBan() == null || o.getGiaNhap() == null
                        || o.getSoLuong() == null || o.getTenMau() == null || o.getTenLoai() == null
                        || o.getTenThuongHieu() == null || o.getQuaiDeo() == null || o.getDemLot() == null
                        || o.getIdLoai() == null || o.getIdThuongHieu() == null
                        || o.getIdTrongLuong() == null || o.getIdVatLieu() == null) {
                    return null;
                }
            }
            if (adminExcelAddSanPhamBO.getTotalError() != null && Integer.valueOf(adminExcelAddSanPhamBO.getTotalError().toString()) != 0)
                return null;
            List<SanPhamChiTiet> saveSanPhamChiTiet = this.saveAll(adminExcelAddSanPhamBO);
            this.mutitheard(saveSanPhamChiTiet, adminExcelAddSanPhamBO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adminExcelAddSanPhamBO;
    }

    @Override
    public List<SanPhamChiTiet> saveAll(AdminExcelAddSanPhamBO adminExcelAddSanPhamBO) {
        List<SanPhamChiTiet> sanPhamChiTiets = new ArrayList<>();

        adminExcelAddSanPhamBO.getResponseList().forEach(BO -> {

            SanPhamChiTiet chiTiet = chiTietSanPhamReponsitory.findBySanPhamTen(BO.getTenSanPham());

            if (chiTiet == null) {
                SanPham sanPham = SanPham.builder()
                        .ngayTao(DatetimeUtil.getCurrentDate())
                        .quaiDeo(BO.getQuaiDeo())
                        .demLot(BO.getDemLot())
                        .ten(BO.getTenSanPham())
                        .moTa(BO.getMoTa())
                        .anh(BO.getAnhChinh())
                        .loai(Loai.builder().id(Integer.valueOf(BO.getIdLoai())).build())
                        .thuongHieu(ThuongHieu.builder().id(Integer.valueOf(BO.getIdThuongHieu())).build())
                        .trangThai(1)
                        .build();
                SanPham newSanPham = sanPhamReponsitory.save(sanPham);
                newSanPham.setMa("SP " + newSanPham.getId());
                sanPhamReponsitory.save(newSanPham);

                SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
                // Thiết lập thông tin sản phẩm chi tiết mới
                sanPhamChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
                sanPhamChiTiet.setSanPham(newSanPham);
                sanPhamChiTiet.setVatLieu(VatLieu.builder().id(Integer.valueOf(BO.getIdVatLieu())).build());
                sanPhamChiTiet.setTrongLuong(TrongLuong.builder().id(BO.getIdTrongLuong()).build());
                sanPhamChiTiet.setTrangThai(1);
                sanPhamChiTiet.setSoLuongTon(Integer.valueOf(BO.getSoLuong()));
                sanPhamChiTiet.setGiaBan(BigDecimal.valueOf(Long.valueOf(BO.getGiaBan())));
                sanPhamChiTiet.setGiaNhap(BigDecimal.valueOf(Long.valueOf(BO.getGiaNhap())));
                sanPhamChiTiets.add(sanPhamChiTiet);
            } else {
                SanPhamChiTiet existingChiTiet = chiTietSanPhamReponsitory.findById(chiTiet.getId()).get();
                SanPham sanPham = sanPhamReponsitory.findByTenSanPhamExcel(BO.getTenSanPham());
                sanPham.setAnh(BO.getAnhChinh());
                sanPham.setNgaySua(DatetimeUtil.getCurrentDate());
                sanPham.setQuaiDeo(BO.getQuaiDeo());
                sanPham.setDemLot(BO.getDemLot());
                sanPham.setTen(BO.getTenSanPham());
                sanPham.setMoTa(BO.getMoTa());
                sanPham.setLoai(Loai.builder().id(Integer.valueOf(BO.getIdLoai())).build());
                sanPham.setThuongHieu(ThuongHieu.builder().id(Integer.valueOf(BO.getIdThuongHieu())).build());
                existingChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());
                existingChiTiet.setSanPham(sanPham);
                existingChiTiet.setVatLieu(VatLieu.builder().id(Integer.valueOf(BO.getIdVatLieu())).build());
                existingChiTiet.setTrongLuong(TrongLuong.builder().id(BO.getIdTrongLuong()).build());
                existingChiTiet.setTrangThai(1);
                existingChiTiet.setSoLuongTon(Integer.valueOf(BO.getSoLuong()));
                existingChiTiet.setGiaBan(BigDecimal.valueOf(Long.valueOf(BO.getGiaBan())));
                existingChiTiet.setGiaNhap(BigDecimal.valueOf(Long.valueOf(BO.getGiaNhap())));
                sanPhamChiTiets.add(existingChiTiet);
            }

        });
        return chiTietSanPhamReponsitory.saveAll(sanPhamChiTiets);
    }

    @Override
    public void mutitheard(List<SanPhamChiTiet> saveSanPhamChiTiet, AdminExcelAddSanPhamBO adminExcelAddSanPhamBO) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Future<List<SizeChiTiet>> sizeFuture = executor.submit(() -> {
            List<SizeChiTiet> result = new ArrayList<>();
            for (AdminExcelAddSanPhamResponse o : adminExcelAddSanPhamBO.getResponseList()) {
                if (o.getIdSize() != null && !o.getIdSize().isEmpty()) {
                    result.addAll(saveAllSizeChiTiet(adminExcelAddSanPhamBO, saveSanPhamChiTiet));
                    break;
                } else {
                    result.addAll(Collections.emptyList());
                    break;
                }
            }
            return result;
        });


        Future<Void> mauSacFuture = executor.submit(() -> {
            try {
                List<SizeChiTiet> sizes = sizeFuture.get();
                saveAllMauSacChiTiet(sizes, adminExcelAddSanPhamBO, saveSanPhamChiTiet);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        });

        Future<Void> imageFuture = executor.submit(() -> {
            try {
                saveAllImage(adminExcelAddSanPhamBO, saveSanPhamChiTiet);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        });

        try {
            mauSacFuture.get();
            imageFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }

    @Override
    public List<MauSacChiTiet> saveAllMauSacChiTiet(List<SizeChiTiet> sizes, AdminExcelAddSanPhamBO adminExcelAddSanPhamBO, List<SanPhamChiTiet> savedSanPhamChiTiets) {
        List<MauSacChiTiet> mauSacChiTiets = new ArrayList<>();

        adminExcelAddSanPhamBO.getResponseList().forEach(request -> {
            int index = adminExcelAddSanPhamBO.getResponseList().indexOf(request);
            SanPhamChiTiet sanPhamChiTiet = savedSanPhamChiTiets.get(index);

            List<String> idMauSac = request.getIdMauSac();
            List<String> imgMauSac = request.getImgMauSac();
            List<String> soLuong = request.getSoLuongMauSac();

            for (int i = 0; i < idMauSac.size(); i++) {
                String mauSac = idMauSac.get(i);

                List<MauSacChiTiet> mauSacChiTietList = mauSacChiTietReponsitory.findBySanPhamChiTietIdAndMauSacId(sanPhamChiTiet.getId(), Integer.valueOf(mauSac));
                List<SizeChiTiet> applicableSizes = sizes.isEmpty() ? Collections.singletonList(null) : sizes;


                if (mauSacChiTietList.isEmpty()) {
                    // Thiết lập thông tin màu sắc chi tiết mới và thêm vào danh sách
                    for (SizeChiTiet sizeChiTiet : applicableSizes) {
                        if (sizeChiTiet.getSanPhamChiTiet().getId() == sanPhamChiTiet.getId()) {
                            MauSacChiTiet mauSacChiTiet = new MauSacChiTiet();
                            mauSacChiTiet.setMauSac(MauSac.builder().id(Integer.valueOf(mauSac)).build());
                            mauSacChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                            mauSacChiTiet.setTrangThai(1);
                            mauSacChiTiet.setSizeChiTiet(sizeChiTiet);
                            // Kiểm tra xem có ảnh tương ứng không
                            if (i < imgMauSac.size()) {
                                mauSacChiTiet.setAnh(imgMauSac.get(i));
                            }
                            if (i < soLuong.size()) {
                                mauSacChiTiet.setSoLuong(Integer.valueOf(soLuong.get(i)));
                            }
                            mauSacChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
                            mauSacChiTiets.add(mauSacChiTiet);
                        }
                    }
                } else {

                    for (MauSacChiTiet mauSacChiTiet : mauSacChiTietList) {
                        mauSacChiTiet.setMauSac(MauSac.builder().id(Integer.valueOf(mauSac)).build());
                        mauSacChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                        mauSacChiTiet.setTrangThai(1);
                        // Kiểm tra xem có ảnh tương ứng không
                        if (i < imgMauSac.size()) {
                            mauSacChiTiet.setAnh(imgMauSac.get(i));
                        }
                        if (i < soLuong.size()) {
                            mauSacChiTiet.setSoLuong(Integer.valueOf(soLuong.get(i)));
                        }
                        mauSacChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
                        mauSacChiTiets.add(mauSacChiTiet);
                    }

                }
            }
        });

        return this.mauSacChiTietReponsitory.saveAll(mauSacChiTiets);
    }


    @Override
    public List<SizeChiTiet> saveAllSizeChiTiet(AdminExcelAddSanPhamBO adminExcelAddSanPhamBO, List<SanPhamChiTiet> savedSanPhamChiTiets) {
        List<SizeChiTiet> sizeChiTiets = new ArrayList<>();

        adminExcelAddSanPhamBO.getResponseList().forEach(request -> {
            int index = adminExcelAddSanPhamBO.getResponseList().indexOf(request);
            SanPhamChiTiet sanPhamChiTiet = savedSanPhamChiTiets.get(index);


            List<String> sizeId = request.getIdSize();
            for (int sizeIndex = 0; sizeIndex < sizeId.size(); sizeIndex++) {
                int idsize = Integer.valueOf(sizeId.get(sizeIndex));
                SanPhamChiTiet existingChiTiet = chiTietSanPhamReponsitory.findBySanPhamTen(request.getTenSanPham());
                List<SizeChiTiet> sizeChiTietList = sizeChiTietReponsitory.findBySanPhamChiTietIdAndSizeId(existingChiTiet.getId(), Integer.valueOf(request.getIdSize().get(sizeIndex)));

                if (!sizeChiTietList.isEmpty()) {
                    // Cập nhật thông tin size chi tiết cho từng size
                    sizeChiTietList.forEach(sizeChiTiet -> {
                        sizeChiTiet.setSize(Size.builder().id(idsize).build());
                        sizeChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                        sizeChiTiet.setTrangThai(1);
                        sizeChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());

                        sizeChiTiets.add(sizeChiTiet);
                    });
                } else {
                    // Thiết lập thông tin size chi tiết mới cho từng size
                    SizeChiTiet sizeChiTiet = new SizeChiTiet();
                    sizeChiTiet.setSize(Size.builder().id(Integer.valueOf(request.getIdSize().get(sizeIndex))).build());
                    sizeChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                    sizeChiTiet.setTrangThai(1);
                    //       sizeChiTiet.setSoLuong(Integer.valueOf(soLuong));
                    sizeChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());

                    sizeChiTiets.add(sizeChiTiet);
                }
            }
        });

        return this.sizeChiTietReponsitory.saveAll(sizeChiTiets);
    }

    @Override
    public void saveAllImage(AdminExcelAddSanPhamBO adminExcelAddSanPhamBO, List<SanPhamChiTiet> savedSanPhamChiTiets) {
        List<Image> imageList = new ArrayList<>();

        adminExcelAddSanPhamBO.getResponseList().forEach(request -> {
            int index = adminExcelAddSanPhamBO.getResponseList().indexOf(request);
            SanPhamChiTiet sanPhamChiTiet = savedSanPhamChiTiets.get(index);
            SanPhamChiTiet existingChiTiet = chiTietSanPhamReponsitory.findBySanPhamTen(request.getTenSanPham());
            List<Image> list = imageReponsitory.findBySanPhamId(existingChiTiet.getId());

            if (!list.isEmpty()) {
                // Cập nhật thông tin image
                list.forEach(image -> {
                    image.setSanPhamChiTiet(sanPhamChiTiet);
                    image.setTrangThai(1);
                    image.setNgaySua(DatetimeUtil.getCurrentDate());
                });

                // Xóa toàn bộ ảnh và thêm các ảnh mới
                imageReponsitory.deleteAll(list);

                request.getImagesProduct().forEach(images -> {
                    Image image = new Image();
                    image.setSanPhamChiTiet(sanPhamChiTiet);
                    image.setTrangThai(1);
                    image.setNgayTao(DatetimeUtil.getCurrentDate());
                    image.setAnh(images);
                    imageList.add(image);
                });
            } else {
                // Thiết lập thông tin image mới
                request.getImagesProduct().forEach(images -> {
                    Image image = new Image();
                    image.setSanPhamChiTiet(sanPhamChiTiet);
                    image.setTrangThai(1);
                    image.setNgayTao(DatetimeUtil.getCurrentDate());
                    image.setAnh(images);
                    imageList.add(image);
                });
            }
        });

        List<Image> savedImages = imageReponsitory.saveAll(imageList);
        savedImages.forEach(image -> {
            image.setMa("IM" + image.getId());
        });
        imageReponsitory.saveAll(savedImages);
    }

    @Override
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
}
