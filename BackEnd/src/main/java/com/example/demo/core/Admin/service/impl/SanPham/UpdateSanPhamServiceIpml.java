package com.example.demo.core.Admin.service.impl.SanPham;

import com.example.demo.core.Admin.model.request.AdminSanPhamChiTietRequest;
import com.example.demo.core.Admin.model.request.AdminSanPhamRequest;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
import com.example.demo.core.Admin.repository.AdChiTietSanPhamReponsitory;
import com.example.demo.core.Admin.repository.AdImageReponsitory;
import com.example.demo.core.Admin.service.AdSanPhamService.AdUpdateSanPhamService;
import com.example.demo.entity.*;
import com.example.demo.infrastructure.status.ChiTietSanPhamStatus;
import com.example.demo.reponsitory.SanPhamReponsitory;
import com.example.demo.util.DatetimeUtil;
import com.example.demo.util.ImageToAzureUtil;
import com.microsoft.azure.storage.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;


@Service
public class UpdateSanPhamServiceIpml implements AdUpdateSanPhamService {

    @Autowired
    private AdChiTietSanPhamReponsitory chiTietSanPhamReponsitory;

    @Autowired
    private AdImageReponsitory imageReponsitory;


    @Autowired
    private SanPhamReponsitory sanPhamReponsitory;

    @Autowired
    ImageToAzureUtil getImageToAzureUtil;


    @Override
    public AdminSanPhamChiTietResponse update(AdminSanPhamChiTietRequest dto, Integer id) throws URISyntaxException, StorageException, InvalidKeyException, IOException, ExecutionException, InterruptedException {
        // Lấy sản phẩm chi tiết từ kho dự trữ
        Optional<SanPhamChiTiet> optionalSanPhamChiTiet = chiTietSanPhamReponsitory.findById(id);
        //  System.out.println( optionalSanPhamChiTiet.get().getTrangThai());
        if (optionalSanPhamChiTiet.isPresent()) {
            SanPhamChiTiet sanPhamChiTiet = optionalSanPhamChiTiet.get();

            // Cập nhật thông tin sản phẩm
            SanPham sanPham = this.updateSanPham(sanPhamChiTiet, dto);
            sanPhamChiTiet.setSanPham(sanPham);
            sanPhamChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());
            sanPhamChiTiet.setGiaBan(BigDecimal.valueOf(dto.getGiaBan()));
            sanPhamChiTiet.setGiaNhap(BigDecimal.valueOf(dto.getGiaNhap()));
            sanPhamChiTiet.setTrangThai(Integer.valueOf(dto.getTrangThai()));
            sanPhamChiTiet.setSoLuongTon(Integer.valueOf(dto.getSoLuongTon()));
        //    sanPhamChiTiet.setVatLieu(VatLieu.builder().id(Integer.valueOf(dto.getVatLieu())).build());
            sanPhamChiTiet.setTrongLuong(TrongLuong.builder().id(dto.getTrongLuong()).build());


            // Lưu sản phẩm chi tiết đã cập nhật
            SanPhamChiTiet save = chiTietSanPhamReponsitory.save(sanPhamChiTiet);
            this.mutitheard(save, dto);
            return this.chiTietSanPhamReponsitory.get(save.getId());
        }

        return null;
    }

    @Override
    public void mutitheard(SanPhamChiTiet sanPhamChiTiet, AdminSanPhamChiTietRequest request) throws ExecutionException, InterruptedException {


        ExecutorService executor = Executors.newFixedThreadPool(3);

//        Future<List<SizeChiTiet>> sizeFuture = executor.submit(() -> {
//            if (request.getIdSize() != null && !request.getIdSize().isEmpty()) {
//                return updateSizeChiTiet(sanPhamChiTiet, request);
//            } else {
//                return Collections.emptyList();
//            }
//        });
//
//        List<SizeChiTiet> sizes = sizeFuture.get();
//        Future<Void> mauSacFuture = executor.submit(() -> {
//            try {
//
//                updateMauSacChiTiet(sizes, sanPhamChiTiet, request);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//            return null;
//        });

        Future<Void> imageFuture = executor.submit(() -> {
            try {
                updateImage(sanPhamChiTiet, request);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        });

        try {
//            mauSacFuture.get();
            imageFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }


    @Override
    public List<Image> updateImage(SanPhamChiTiet sanPhamChiTiet, AdminSanPhamChiTietRequest dto) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
        List<String> imgP = dto.getImagesProduct();
        List<Image> updatedImages = new ArrayList<>();

        for (String imgAnh : imgP) {
            int lastIndexOfSlash = imgAnh.lastIndexOf("\\");

            if (lastIndexOfSlash != -1) {
                String fileName = imgAnh.substring(lastIndexOfSlash + 1);
                Image img = this.imageReponsitory.findBySanPhamIdAndAnh(sanPhamChiTiet.getId(), "%" + fileName + "%");
                // Kiểm tra xem có ảnh tồn tại hay không
                if (img != null) {
                    // Cập nhật thông tin ảnh nếu cần
                    if (img.getAnh().equals(dto.getAnh())) {
                        img.setAnh(dto.getAnh());
                    } else {
                        String linkAnh = getImageToAzureUtil.uploadImageToAzure(dto.getAnh());
                        img.setAnh(linkAnh);
                    }
                    // Lưu lại ảnh đã cập nhật
                    updatedImages.add(img);
                } else {
                    // Tạo ảnh mới nếu ảnh không tồn tại
                    Image newImg = new Image();
                    newImg.setSanPhamChiTiet(sanPhamChiTiet);
                    String linkAnh = getImageToAzureUtil.uploadImageToAzure(imgAnh);
                    newImg.setAnh(linkAnh);
                    // Lưu lại ảnh mới
                    Image savedImage = imageReponsitory.save(newImg);
                    updatedImages.add(savedImage);
                }
            } else {
                //        System.out.println("Không tìm thấy dấu gạch chéo trong URL.");
            }
        }

        return updatedImages;
    }




    @Override
    public SanPham updateSanPham(SanPhamChiTiet sanPhamChiTiet, AdminSanPhamChiTietRequest dto) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
        SanPham sanPham = sanPhamReponsitory.findById(sanPhamChiTiet.getSanPham().getId()).get();

        if (sanPham != null) {
            sanPham.setNgaySua(DatetimeUtil.getCurrentDate());
            sanPham.setTen(dto.getTen());
            sanPham.setLoai(Loai.builder().id(dto.getLoai()).build());
            sanPham.setThuongHieu(ThuongHieu.builder().id(dto.getThuongHieu()).build());
            sanPham.setDemLot(dto.getDemLot());
            sanPham.setMoTa(dto.getMoTa());
            if (sanPham.getAnh().equals(dto.getAnh())) {
                sanPham.setAnh(dto.getAnh());
            } else {
                String linkAnh = getImageToAzureUtil.uploadImageToAzure(dto.getAnh());
                sanPham.setAnh(linkAnh);
            }
            sanPham.setQuaiDeo(dto.getQuaiDeo());
            return sanPhamReponsitory.save(sanPham);
        } else {
            String linkAnh = getImageToAzureUtil.uploadImageToAzure(dto.getAnh());
            AdminSanPhamRequest sanPhamRequest = AdminSanPhamRequest.builder()
                    .loai(dto.getLoai())
                    .thuongHieu(dto.getThuongHieu())
                    .demLot(dto.getDemLot())
                    .moTa(dto.getMoTa())
                    .ten(dto.getTen())
                    .anh(linkAnh)
                    .quaiDeo(dto.getQuaiDeo())
                    .build();
            return this.saveSanPham(sanPhamRequest);
        }
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
    public SanPhamChiTiet delete(Integer id) {
        SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamReponsitory.findById(id).get();
        if (sanPhamChiTiet != null) {
            sanPhamChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());
            sanPhamChiTiet.setTrangThai(ChiTietSanPhamStatus.XOA);
            return chiTietSanPhamReponsitory.save(sanPhamChiTiet);
        }
        return null;
    }


    @Override
    public void deleteImg(Integer idSp, String img) {
        int lastIndexOfSlash = img.lastIndexOf("\\");

        if (lastIndexOfSlash != -1) {
            String fileName = img.substring(lastIndexOfSlash + 1);
            Image image = imageReponsitory.findBySanPhamIdAndAnh(idSp, "%" + fileName + "%");
            // System.out.println(image.getId());
            imageReponsitory.delete(image);
        } else {
            //     System.out.println("Không tìm thấy dấu gạch chéo trong URL.");
        }


        //
    }


}
