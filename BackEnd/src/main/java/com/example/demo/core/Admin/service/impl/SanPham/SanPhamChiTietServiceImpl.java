package com.example.demo.core.Admin.service.impl.SanPham;

import com.example.demo.core.Admin.model.request.AdminSanPhamChiTietRequest;
import com.example.demo.core.Admin.model.request.AdminSanPhamRequest;
import com.example.demo.core.Admin.model.request.AdminSearchRequest;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
import com.example.demo.core.Admin.repository.AdChiTietSanPhamReponsitory;
import com.example.demo.core.Admin.repository.AdImageReponsitory;
import com.example.demo.core.Admin.repository.AdSizeChiTietReponsitory;
import com.example.demo.core.Admin.service.AdSanPhamService.AdSanPhamChiTietService;
import com.example.demo.entity.*;
import com.example.demo.reponsitory.MauSacChiTietReponsitory;
import com.example.demo.reponsitory.SanPhamReponsitory;
import com.example.demo.util.DatetimeUtil;
import com.example.demo.util.ImageToAzureUtil;
import com.microsoft.azure.storage.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

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
    ImageToAzureUtil getImageToAzureUtil;

    @Override
    public List<AdminSanPhamChiTietResponse> getList(AdminSearchRequest request) {
        return chiTietSanPhamReponsitory.getAll(request);
    }


    public List<AdminSanPhamChiTietResponse> loc(String comboBoxValue) {
        return chiTietSanPhamReponsitory.loc(comboBoxValue);
    }

    @Override
    public List<Image> getProductImages(Integer idProduct) {
        return imageReponsitory.findBySanPhamIds(idProduct);
    }

    @Override
    public List<Image> getProductImages() {
        return imageReponsitory.findAll();
    }

    @Override
    public List<SizeChiTiet> getProductSize(Integer idProduct) {
        return sizeChiTietReponsitory.findSizeChiTiet(idProduct);
    }

    @Override
    public List<MauSacChiTiet> getProductMauSac(Integer idProduct) {
        return mauSacChiTietReponsitory.findMauSacChiTiet(idProduct);
    }

    @Override
    public AdminSanPhamChiTietResponse get(Integer id) {
        AdminSanPhamChiTietResponse optional = this.chiTietSanPhamReponsitory.get(id);
        return optional;
    }

    @Override
    public Boolean findBySanPhamTen(String ten) {
        SanPhamChiTiet chiTiet = chiTietSanPhamReponsitory.findBySanPhamTenAndTrangThai(ten);
        if (chiTiet == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public AdminSanPhamChiTietResponse add(AdminSanPhamChiTietRequest request) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
        // System.out.println(request);
        // bước 1: lấy các thuộc tính của bảng sản phẩm từ request và lưu và bảng sản phẩm
        String linkAnh = getImageToAzureUtil.uploadImageToAzure(request.getAnh());
        AdminSanPhamRequest sanPhamRequest = AdminSanPhamRequest.builder()
                .loai(request.getLoai())
                .thuongHieu(request.getThuongHieu())
                .demLot(request.getDemLot())
                .moTa(request.getMoTa())
                .ten(request.getTen())
                .quaiDeo(request.getQuaiDeo())
                .anh(linkAnh)
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

    @Override
    public SanPham saveSanPham(AdminSanPhamRequest request) {
        SanPham sanPham = request.dtoToEntity(new SanPham());
        SanPham sanPhamSave = sanPhamReponsitory.save(sanPham);
        // lưu ma theo dạng SP + id vừa tương ứng
        sanPhamSave.setMa("SP" + sanPhamSave.getId());
        return sanPhamReponsitory.save(sanPhamSave);
    }

    @Override
    public void mutitheard(SanPhamChiTiet sanPhamChiTiet, AdminSanPhamChiTietRequest request) {

        // Tạo các luồng cho các công việc cần thực hiện đồng thời
        Thread mauSacThread = new Thread(() -> {
            try {
                saveMauSac(request.getIdMauSac(), request.getImgMauSac(), sanPhamChiTiet);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (StorageException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });
        Thread sizeThread = new Thread(() -> saveSize(request.getIdSize(), sanPhamChiTiet, request.getSoLuongSize()));
        Thread imageThread = new Thread(() -> {
            try {
                saveImage(sanPhamChiTiet, request.getImagesProduct());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (StorageException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });

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

    @Override
    //lưu mau sắc chi tiết
    public Iterable<MauSacChiTiet> saveMauSac(List<String> idMauSac, List<String> imgMauSac, SanPhamChiTiet sanPhamChiTiet) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
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
            String linkAnh = getImageToAzureUtil.uploadImageToAzure(imgMauSacValue);
            mauSacChiTiet.setAnh(linkAnh);
            mauSacChiTietList.add(mauSacChiTiet);
        }
        return this.mauSacChiTietReponsitory.saveAll(mauSacChiTietList);

    }

    @Override
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

    @Override
    // lưu ảnh
    public Iterable<Image> saveImage(SanPhamChiTiet sanPhamChiTiet, List<String> imgSanPham) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
        List<Image> imageList = new ArrayList<>();
        for (String img : imgSanPham) {
            Image image = new Image();
            String linkAnh = getImageToAzureUtil.uploadImageToAzure(img);
            image.setAnh(linkAnh);
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


}
