package com.example.demo.core.Admin.service.impl.SanPham;

import com.example.demo.core.Admin.model.request.AdminSanPhamChiTietRequest;
import com.example.demo.core.Admin.model.request.AdminSanPhamRequest;
import com.example.demo.core.Admin.model.request.AdminSearchRequest;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
import com.example.demo.core.Admin.repository.AdChiTietSanPhamReponsitory;
import com.example.demo.core.Admin.repository.AdImageReponsitory;
import com.example.demo.core.Admin.repository.AdKhuyenMaiReponsitory;
import com.example.demo.core.Admin.repository.AdSizeChiTietReponsitory;
import com.example.demo.entity.*;
import com.example.demo.reponsitory.*;
import com.example.demo.core.Admin.service.AdSanPhamChiTietService;
import com.example.demo.util.DataUltil;
import com.example.demo.util.DatetimeUtil;
import com.example.demo.util.ExcelExportUtils;
import com.microsoft.azure.storage.StorageException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.*;

@Service
public class SanPhamChiTietServiceImpl implements AdSanPhamChiTietService {

    @Autowired
    private AdChiTietSanPhamReponsitory chiTietSanPhamReponsitory;

    @Autowired
    private AdImageReponsitory imageReponsitory;

    @Autowired
    private AdSizeChiTietReponsitory sizeChiTietReponsitory;

    @Autowired
    private MauSacChiTietReponsitory mauSacChiTietReponsitory;

    @Autowired
    private SanPhamReponsitory sanPhamReponsitory;

    @Autowired
    private AdKhuyenMaiReponsitory khuyenMaiReponsitory;


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


    public List<AdminSanPhamChiTietResponse> getList(AdminSearchRequest request) {
        return chiTietSanPhamReponsitory.getAll(request);
    }

    public List<SanPhamChiTiet> getAlls() {
        return chiTietSanPhamReponsitory.findAll();
    }

    public List<Image> getProductImages(Integer idProduct) {
        return imageReponsitory.findBySanPhamIds(idProduct);
    }

    public List<Image> getProductImages() {
        return imageReponsitory.findAll();
    }

    public List<SizeChiTiet> getProductSize(Integer idProduct) {
        return sizeChiTietReponsitory.findSizeChiTiet(idProduct);
    }

    public List<MauSacChiTiet> getProductMauSac(Integer idProduct) {
        return mauSacChiTietReponsitory.findMauSacChiTiet(idProduct);
    }

    @Override
    public SanPhamChiTiet getOne(Integer id) {
        Optional<SanPhamChiTiet> optional = this.chiTietSanPhamReponsitory.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    public AdminSanPhamChiTietResponse get(Integer id) {
        AdminSanPhamChiTietResponse optional = this.chiTietSanPhamReponsitory.get(id);
        return optional;
    }

    public Boolean findBySanPhamTen(String ten) {
        SanPhamChiTiet chiTiet = chiTietSanPhamReponsitory.findBySanPhamTen(ten);
        if (chiTiet == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public AdminSanPhamChiTietResponse add(AdminSanPhamChiTietRequest request) {
        System.out.println(request);
        // bước 1: lấy các thuộc tính của bảng sản phẩm từ request và lưu và bảng sản phẩm
        AdminSanPhamRequest sanPhamRequest = AdminSanPhamRequest.builder()
                .loai(request.getLoai())
                .thuongHieu(request.getThuongHieu())
                .demLot(request.getDemLot())
                .moTa(request.getMoTa())
                .ten(request.getTen())
                .quaiDeo(request.getQuaiDeo())
                .anh(request.getAnh())
                .build();
        SanPham sanPham = this.saveSanPham(sanPhamRequest);

        // bước 2: lấy id của sản phẩm vừa lưu và các thuộc tính ở dto lưu vào bảng sản phẩm chi tiết
        SanPhamChiTiet sanPhamChiTiet = request.dtoToEntity(new SanPhamChiTiet());
        sanPhamChiTiet.setSanPham(sanPham);
        SanPhamChiTiet chiTietSanPham = chiTietSanPhamReponsitory.save(sanPhamChiTiet);

        // bước 3: sử dụng mutitheard để có thể lưu các bảng hình ảnh, màu sắc chi tiết, size chi tiết,khuyến mại chi tiết
        this.mutitheard(chiTietSanPham, request);

        return this.get(chiTietSanPham.getId());

    }

    public SanPham saveSanPham(AdminSanPhamRequest request) {
        SanPham sanPham = request.dtoToEntity(new SanPham());
        SanPham sanPhamSave = sanPhamReponsitory.save(sanPham);
        // lưu ma theo dạng SP + id vừa tương ứng
        sanPhamSave.setMa("SP" + sanPhamSave.getId());
        return sanPhamReponsitory.save(sanPhamSave);
    }

    public void mutitheard(SanPhamChiTiet sanPhamChiTiet, AdminSanPhamChiTietRequest request) {

        // Tạo các luồng cho các công việc cần thực hiện đồng thời
        Thread mauSacThread = new Thread(() -> saveMauSac(request.getIdMauSac(), request.getImgMauSac(), sanPhamChiTiet));
        Thread sizeThread = new Thread(() -> saveSize(request.getIdSize(), sanPhamChiTiet, request.getSoLuongSize()));
        Thread imageThread = new Thread(() -> saveImage(sanPhamChiTiet, request.getImagesProduct()));

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


    //lưu mau sắc chi tiết
    public Iterable<MauSacChiTiet> saveMauSac(List<String> idMauSac, List<String> imgMauSac, SanPhamChiTiet sanPhamChiTiet) {
        List<MauSacChiTiet> mauSacChiTietList = new ArrayList<>();

        // Đảm bảo rằng số lượng idMauSac và imgMauSac là giống nhau
        if (idMauSac.size() != imgMauSac.size())
            throw new IllegalArgumentException("Số lượng idMauSac và imgMauSac không khớp");

        for (int i = 0; i < idMauSac.size(); i++) {
            String mauSacId = idMauSac.get(i);
            String imgMauSacValue = imgMauSac.get(i);

            MauSacChiTiet mauSacChiTiet = new MauSacChiTiet();
            mauSacChiTiet.setMauSac(MauSac.builder().id(Integer.valueOf(mauSacId)).build());
            mauSacChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
            mauSacChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
            mauSacChiTiet.setAnh(imgMauSacValue);
            mauSacChiTietList.add(mauSacChiTiet);
        }
        return this.mauSacChiTietReponsitory.saveAll(mauSacChiTietList);

    }


    // lưu size chi tiet
    public Iterable<SizeChiTiet> saveSize(List<String> idSize, SanPhamChiTiet sanPhamChiTiet, List<String> soLuongSize) {
        List<SizeChiTiet> sizeChiTietList = new ArrayList<>();

        // Đảm bảo rằng số lượng idSize và soLuongSize là giống nhau
        if (idSize.size() != soLuongSize.size())
            throw new IllegalArgumentException("Số lượng idSize và soLuongSize không khớp");


        for (int i = 0; i < idSize.size(); i++) {
            String sizeId = idSize.get(i);
            String soLuongSizeValue = soLuongSize.get(i);

            SizeChiTiet sizeChiTiet = new SizeChiTiet();
            sizeChiTiet.setSize(Size.builder().id(Integer.valueOf(sizeId)).build());
            sizeChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
            sizeChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
            sizeChiTiet.setSoLuong(Integer.valueOf(soLuongSizeValue));

            sizeChiTietList.add(sizeChiTiet);
        }

        return this.sizeChiTietReponsitory.saveAll(sizeChiTietList);

    }


    // lưu ảnh
    public Iterable<Image> saveImage(SanPhamChiTiet sanPhamChiTiet, List<String> imgSanPham) {
        List<Image> imageList = new ArrayList<>();
        for (String img : imgSanPham) {
            Image image = new Image();
            image.setAnh(img);
            image.setSanPhamChiTiet(sanPhamChiTiet);
            image.setTrangThai(1);
            image.setNgayTao(DatetimeUtil.getCurrentDate());
            imageList.add(image);
        }
        List<Image> images = this.imageReponsitory.saveAll(imageList);
        for (int i = 0; i < images.size(); i++) {
            Image image = images.get(i);
            image.setMa("IM" + images.get(i).getId());
        }
        return this.imageReponsitory.saveAll(imageList);
    }


    @Override
    public SanPhamChiTiet update(AdminSanPhamChiTietRequest dto, Integer id) {
        return null;
    }

    @Override
    public SanPhamChiTiet delete( Integer id) {
        return null;
    }

    @Override
    public void saveExcel(MultipartFile file) throws IOException, StorageException, InvalidKeyException, URISyntaxException {

    }

    @Override
    public List<SanPhamChiTiet> exportCustomerToExcel(HttpServletResponse response) throws IOException {
        List<SanPhamChiTiet> sanPhamChiTietList = chiTietSanPhamReponsitory.findAll();
        ExcelExportUtils exportUtils = new ExcelExportUtils(sanPhamChiTietList);
        exportUtils.exportDataToExcel(response);
        return sanPhamChiTietList;
    }
    // lấy danh sách ctsp có idkm = null hoặc trạng thái khuyến mại không phải là đang diễn ra hoặc chưa bắt đầu
    @Override
    public List<SanPhamChiTiet> getAllSPCTByKhuyenMai() {
       return chiTietSanPhamReponsitory.getAllCTSPByKhuyenMai();

    }

    @Override
    public HashMap<String, Object> updateProductDetail(Integer productId, Integer idkm) {
        // Lấy ctsp theo id
        Optional<SanPhamChiTiet> optionalProductDetail = chiTietSanPhamReponsitory.findById(productId);

        if (optionalProductDetail.isPresent()) {
            SanPhamChiTiet spct = optionalProductDetail.get();
            KhuyenMai km = khuyenMaiReponsitory.getOneById(idkm);
            // Cập nhật idkm cho ctsp
            spct.setKhuyenMai(km);

            // Áp dụng giảm giá theo giá phần trăm
            if(km.getTrangThai() == 0){
                BigDecimal giaBan = spct.getGiaBan();

                BigDecimal phanTram  = new BigDecimal(km.getGiaTriGiam()).divide(new BigDecimal(100));

                BigDecimal giamGia = giaBan.multiply(phanTram);
                BigDecimal giaBanSauGiam = giaBan.subtract(giamGia);

//                BigDecimal phanTramDangThapPhan = new BigDecimal(km.getGiaTriGiam()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
//
//                // Tính giá sau khi giảm
//                BigDecimal giaSauKhiGiam = giaBan.multiply(phanTramDangThapPhan).setScale(2, RoundingMode.HALF_UP);

                spct.setGiaSauGiam(giaBanSauGiam);
            }

            try {
                chiTietSanPhamReponsitory.save(spct);
                return DataUltil.setData("success", chiTietSanPhamReponsitory.save(spct));

            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            throw new RuntimeException("Không tìm thấy chi tiết sản phẩm với ID: " + productId);
        }

    }

    @Scheduled(fixedRate = 20000)
    public void updateGiaCTSP(){
        //Lấy danh sách CTSP theo trạng thái khuyến mại là  bắt đầu
        List<SanPhamChiTiet> listSPCT = chiTietSanPhamReponsitory.getCTSPByTrangThaiKhuyenMai(0);

        // Set lại giá sau giảm khi trạng thái chuyển từ chưa bắt đầu => đang diễn ra
        for(SanPhamChiTiet spct : listSPCT){
            KhuyenMai km = khuyenMaiReponsitory.getOneById(spct.getKhuyenMai().getId());
            BigDecimal giaBan = spct.getGiaBan();
            BigDecimal phanTram = new BigDecimal(km.getGiaTriGiam()/100);
            BigDecimal giamGia = giaBan.multiply(phanTram);
            BigDecimal giaBanSauGiam = giaBan.subtract(giamGia);
            spct.setGiaSauGiam(giaBanSauGiam);

            chiTietSanPhamReponsitory.save(spct);
        }
    }

}
