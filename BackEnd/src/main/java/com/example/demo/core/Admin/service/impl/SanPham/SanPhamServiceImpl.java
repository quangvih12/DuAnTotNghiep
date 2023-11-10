package com.example.demo.core.Admin.service.impl.SanPham;

import com.example.demo.core.Admin.model.request.AdminSanPhamRepuest2;
import com.example.demo.core.Admin.model.request.AdminSanPhamRequest;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTiet2Response;
import com.example.demo.core.Admin.model.response.AdminSanPhamResponse;
import com.example.demo.core.Admin.repository.AdChiTietSanPhamReponsitory;
import com.example.demo.core.Admin.repository.AdImageReponsitory;
import com.example.demo.core.Admin.repository.AdSanPhamReponsitory;
import com.example.demo.core.Admin.service.AdSanPhamService.AdSanPhamService;
import com.example.demo.entity.*;
import com.example.demo.util.DatetimeUtil;
import com.example.demo.util.ImageToAzureUtil;
import com.microsoft.azure.storage.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;


@Service
public class SanPhamServiceImpl implements AdSanPhamService {


    @Autowired
    private AdSanPhamReponsitory sanPhamReponsitory;

    @Autowired
    private AdChiTietSanPhamReponsitory chiTietSanPhamReponsitory;

    @Autowired
    ImageToAzureUtil getImageToAzureUtil;

    @Autowired
    private AdImageReponsitory imageReponsitory;

    @Override
    public List<AdminSanPhamResponse> getAll() {
        return sanPhamReponsitory.getAll();
    }

    @Override
    public AdminSanPhamResponse findByIdSP(Integer id) {
        return sanPhamReponsitory.findByIdSP(id);
    }

    @Override
    public List<AdminSanPhamChiTiet2Response> findBySanPhamCT(Integer id) {
        return sanPhamReponsitory.get(id);
    }

    @Override
    public List<Image> getProductImages(Integer idProduct) {
        return imageReponsitory.findBySanPhamIds(idProduct);
    }

    @Override
    public Boolean findBySanPhamTen(String ten) {
        SanPham chiTiet = sanPhamReponsitory.findBySanPhamTenAndTrangThai(ten);
        if (chiTiet == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<AdminSanPhamResponse> loc(String comboBoxValue) {
        return sanPhamReponsitory.loc(comboBoxValue);
    }

    @Override
    public AdminSanPhamResponse save(AdminSanPhamRepuest2 request) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
        String linkAnh = getImageToAzureUtil.uploadImageToAzure(request.getAnh());
        AdminSanPhamRequest sanPhamRequest = AdminSanPhamRequest.builder()
                .loai(request.getLoai())
                .thuongHieu(request.getThuongHieu())
                .demLot(request.getDemLot())
                .moTa(request.getMoTa())
                .ten(request.getTen())
                .vatLieu(request.getVatLieu())
                .quaiDeo(request.getQuaiDeo())
                .anh(linkAnh)
                .build();
        SanPham sanPham = this.saveSanPham(sanPhamRequest);
        if (request.getImagesProduct() == null || request.getImagesProduct().isEmpty()) {
            this.saveImage(sanPham, request.getImgMauSac());
        } else {
            for (String img : request.getImagesProduct()) {
                request.getImgMauSac().add(img);
            }
            this.saveImage(sanPham, request.getImgMauSac());
        }
        List<SanPhamChiTiet> saveSanPhamChiTiet = this.saveSanPhamChiTiet(request, sanPham);
        return this.findByIdSP(sanPham.getId());
    }

    public SanPham saveSanPham(AdminSanPhamRequest request) {
        SanPham sanPham = request.dtoToEntity(new SanPham());
        SanPham sanPhamSave = sanPhamReponsitory.save(sanPham);
        // lưu ma theo dạng SP + id vừa tương ứng
        sanPhamSave.setMa("SP" + sanPhamSave.getId());
        return sanPhamReponsitory.save(sanPhamSave);
    }

    @Override
    public AdminSanPhamResponse update(Integer id) {
        return null;
    }

    @Override
    public List<SanPhamChiTiet> saveSanPhamChiTiet(AdminSanPhamRepuest2 repuest2, SanPham sanPham) throws URISyntaxException, StorageException, InvalidKeyException, IOException {

        List<SanPhamChiTiet> lstsanPhamChiTiet = new ArrayList<>();


        for (int i = 0; i < repuest2.getIdMauSac().size(); i++) {
            Integer idMau = Integer.valueOf(repuest2.getIdMauSac().get(i));
            String imgMauSacValue = repuest2.getImgMauSac().get(i);
            String soluong = repuest2.getSoLuongSize().get(i);
            BigDecimal giaBan = BigDecimal.valueOf(Long.valueOf(repuest2.getGiaBan().get(i)));
            BigDecimal giaNhap = BigDecimal.valueOf(Long.valueOf(repuest2.getGiaNhap().get(i)));
            if (repuest2.getIdSize() == null || repuest2.getIdSize().isEmpty()) {
                SanPhamChiTiet chiTiet = new SanPhamChiTiet();

                chiTiet.setAnh(repuest2.getAnh());
                chiTiet.setSanPham(sanPham);
                chiTiet.setGiaNhap(giaNhap);
                chiTiet.setGiaBan(giaBan);
                chiTiet.setSoLuongTon(Integer.valueOf(soluong));
                String linkAnh = getImageToAzureUtil.uploadImageToAzure(imgMauSacValue);
                chiTiet.setAnh(linkAnh);
                chiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
                chiTiet.setSize(null);
                chiTiet.setMauSac(MauSac.builder().id(idMau).build());
                chiTiet.setTrongLuong(TrongLuong.builder().id(repuest2.getTrongLuong()).build());
                lstsanPhamChiTiet.add(chiTiet);
            } else {
                for (String idSize : repuest2.getIdSize()) {
                    SanPhamChiTiet chiTiet = new SanPhamChiTiet();

                    chiTiet.setAnh(repuest2.getAnh());
                    chiTiet.setSanPham(sanPham);
                    chiTiet.setGiaNhap(giaNhap);
                    chiTiet.setGiaBan(giaBan);
                    chiTiet.setSoLuongTon(Integer.valueOf(soluong));
                    String linkAnh = getImageToAzureUtil.uploadImageToAzure(imgMauSacValue);
                    chiTiet.setAnh(linkAnh);
                    chiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
                    chiTiet.setSize(Size.builder().id(Integer.valueOf(idSize)).build());
                    chiTiet.setMauSac(MauSac.builder().id(idMau).build());
                    chiTiet.setTrongLuong(TrongLuong.builder().id(repuest2.getTrongLuong()).build());
                    lstsanPhamChiTiet.add(chiTiet);
                }
            }
        }

        List<SanPhamChiTiet> lstChiTiet = chiTietSanPhamReponsitory.saveAll(lstsanPhamChiTiet);
        return lstChiTiet;
    }

    @Override
    public AdminSanPhamResponse delete(Integer id) {
        SanPham sanPham = sanPhamReponsitory.findById(id).get();
        if(sanPham != null){
            sanPham.setTrangThai(0);
            sanPhamReponsitory.save(sanPham);
        }
        return this.findByIdSP(sanPham.getId());
    }

    @Override
    public AdminSanPhamResponse khoiPhuc(Integer id) {
        SanPham sanPham = sanPhamReponsitory.findById(id).get();
        if(sanPham != null){
            sanPham.setTrangThai(3);
            sanPhamReponsitory.save(sanPham);
        }
        return this.findByIdSP(sanPham.getId());
    }

    public List<Image> saveImage(SanPham sanPham, List<String> imgSanPham) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
        List<Image> imageList = new ArrayList<>();
        for (String img : imgSanPham) {
            Image image = new Image();
            String linkAnh = getImageToAzureUtil.uploadImageToAzure(img);
            image.setAnh(linkAnh);
            image.setSanPham(sanPham);
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