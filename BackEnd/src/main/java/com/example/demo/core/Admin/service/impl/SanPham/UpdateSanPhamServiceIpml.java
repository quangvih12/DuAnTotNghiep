package com.example.demo.core.Admin.service.impl.SanPham;

import com.example.demo.core.Admin.model.request.AdminSanPhamChiTietRequest;
import com.example.demo.core.Admin.model.request.AdminSanPhamRequest;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
import com.example.demo.core.Admin.repository.AdChiTietSanPhamReponsitory;
import com.example.demo.core.Admin.repository.AdImageReponsitory;
import com.example.demo.core.Admin.repository.AdSizeChiTietReponsitory;
import com.example.demo.core.Admin.service.AdSanPhamChiTietService;
import com.example.demo.entity.*;
import com.example.demo.infrastructure.status.ChiTietSanPhamStatus;
import com.example.demo.reponsitory.MauSacChiTietReponsitory;
import com.example.demo.reponsitory.SanPhamReponsitory;
import com.example.demo.util.DatetimeUtil;
import com.microsoft.azure.storage.StorageException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Service
public class UpdateSanPhamServiceIpml implements AdSanPhamChiTietService {

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

    @Override
    public Page<SanPhamChiTiet> getAll(Integer page, String upAndDown, Integer trangThai) {
        return null;
    }

    @Override
    public SanPhamChiTiet getOne(Integer id) {
        return null;
    }

    @Override
    public AdminSanPhamChiTietResponse add(AdminSanPhamChiTietRequest dto) {
        return null;
    }

    @Override
    public AdminSanPhamChiTietResponse update(AdminSanPhamChiTietRequest dto, Integer id) {
        // Lấy sản phẩm chi tiết từ kho dự trữ
        Optional<SanPhamChiTiet> optionalSanPhamChiTiet = chiTietSanPhamReponsitory.findById(id);

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
            sanPhamChiTiet.setVatLieu(VatLieu.builder().id(Integer.valueOf(dto.getVatLieu())).build());
            sanPhamChiTiet.setTrongLuong(TrongLuong.builder().id(dto.getTrongLuong()).build());

            // Lưu sản phẩm chi tiết đã cập nhật
            SanPhamChiTiet save = chiTietSanPhamReponsitory.save(sanPhamChiTiet);
            this.mutitheard(save, dto);
            return  this.chiTietSanPhamReponsitory.get(save.getId());
        }

        return null;
    }

    public void mutitheard(SanPhamChiTiet sanPhamChiTiet, AdminSanPhamChiTietRequest request) {

        // Tạo các luồng cho các công việc cần thực hiện đồng thời
        Thread mauSacThread = new Thread(() -> updateMauSacChiTiet(sanPhamChiTiet, request));
        Thread sizeThread = new Thread(() -> updateSizeChiTiet(sanPhamChiTiet, request));
        Thread imageThread = new Thread(() -> updateImage(sanPhamChiTiet, request));

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

    public List<Image> updateImage(SanPhamChiTiet sanPhamChiTiet, AdminSanPhamChiTietRequest dto) {
        List<String> imgP = dto.getImagesProduct();
        List<Image> updatedImages = new ArrayList<>();

        for (String imgAnh : imgP) {
            Image img = this.imageReponsitory.findBySanPhamIdAndAnh(sanPhamChiTiet.getId(), imgAnh);

            // Kiểm tra xem có ảnh tồn tại hay không
            if (img != null) {
                // Cập nhật thông tin ảnh nếu cần
                img.setAnh(imgAnh);
                // Lưu lại ảnh đã cập nhật
                updatedImages.add(img);
            } else {
                // Tạo ảnh mới nếu ảnh không tồn tại
                Image newImg = new Image();
                newImg.setSanPhamChiTiet(sanPhamChiTiet);
                newImg.setAnh(imgAnh);
                // Lưu lại ảnh mới
                Image savedImage = imageReponsitory.save(newImg);
                updatedImages.add(savedImage);
            }
        }

        return updatedImages;
    }


    public List<SizeChiTiet> updateSizeChiTiet(SanPhamChiTiet sanPhamChiTiet, AdminSanPhamChiTietRequest dto) {
        List<String> idSize = dto.getIdSize();
        List<String> soLuongSize = dto.getSoLuongSize();
        List<SizeChiTiet> updatedSizeChiTietList = new ArrayList<>();

        // Đảm bảo rằng số lượng idSize và soLuongSize là giống nhau
        if (idSize.size() != soLuongSize.size()) {
            throw new IllegalArgumentException("Số lượng idSize và soLuongSize không khớp");
        }

        for (int i = 0; i < idSize.size(); i++) {
            String sizeId = idSize.get(i);
            String soLuongSizeValue = soLuongSize.get(i);

            // Tìm kiếm SizeChiTiet dựa trên sanPhamChiTiet và idSize
            SizeChiTiet sizeChiTiet = sizeChiTietReponsitory.findSizeChiTietBySanPhamChiTietAndSize(sanPhamChiTiet.getId(), Integer.valueOf(sizeId));

            if (sizeChiTiet == null) {
                // Nếu không tìm thấy, tạo mới SizeChiTiet
                sizeChiTiet = new SizeChiTiet();
                sizeChiTiet.setSize(Size.builder().id(Integer.valueOf(sizeId)).build());
                sizeChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                sizeChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
            }

            // Cập nhật thông tin của SizeChiTiet
            sizeChiTiet.setSoLuong(Integer.valueOf(soLuongSizeValue));
            sizeChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());

            // Thêm SizeChiTiet đã cập nhật vào danh sách
            updatedSizeChiTietList.add(sizeChiTiet);
        }

        // Lưu danh sách SizeChiTiet đã cập nhật
        return sizeChiTietReponsitory.saveAll(updatedSizeChiTietList);
    }


    public List<MauSacChiTiet> updateMauSacChiTiet(SanPhamChiTiet sanPhamChiTiet, AdminSanPhamChiTietRequest dto) {
        List<String> idMau = dto.getIdMauSac();
        List<String> anhMau = dto.getImgMauSac();
        List<MauSacChiTiet> updatedMauSacChiTietList = new ArrayList<>();

        // Lặp qua danh sách idMau và imgMauSac
        for (int i = 0; i < idMau.size(); i++) {
            String mauSacId = idMau.get(i);
            String imgMauSacValue = anhMau.get(i);

            // Tìm MauSacChiTiet dựa trên sanPhamChiTiet và mauSacId
            MauSacChiTiet mauSacChiTiet = mauSacChiTietReponsitory.findMauSacChiTietBySanPhamChiTietAndMauSac(sanPhamChiTiet.getId(), Integer.valueOf(mauSacId));

            if (mauSacChiTiet == null) {
                // Nếu không tìm thấy, tạo mới MauSacChiTiet
                mauSacChiTiet = new MauSacChiTiet();
                mauSacChiTiet.setMauSac(MauSac.builder().id(Integer.valueOf(mauSacId)).build());
                mauSacChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                mauSacChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
            }

            // Cập nhật thông tin của MauSacChiTiet
            mauSacChiTiet.setAnh(imgMauSacValue);
            mauSacChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());

            // Thêm MauSacChiTiet đã cập nhật hoặc tạo mới vào danh sách
            updatedMauSacChiTietList.add(mauSacChiTiet);
        }

        // Lưu danh sách MauSacChiTiet đã cập nhật hoặc tạo mới và trả về
        return mauSacChiTietReponsitory.saveAll(updatedMauSacChiTietList);
    }


    public SanPham updateSanPham(SanPhamChiTiet sanPhamChiTiet, AdminSanPhamChiTietRequest dto) {
        SanPham sanPham = sanPhamReponsitory.findById(sanPhamChiTiet.getSanPham().getId()).get();
        if (sanPham != null) {
            sanPham.setNgaySua(DatetimeUtil.getCurrentDate());
            sanPham.setTen(dto.getTen());
            sanPham.setLoai(Loai.builder().id(dto.getLoai()).build());
            sanPham.setThuongHieu(ThuongHieu.builder().id(dto.getThuongHieu()).build());
            sanPham.setDemLot(dto.getDemLot());
            sanPham.setMoTa(dto.getMoTa());
            sanPham.setAnh(dto.getAnh());
            sanPham.setQuaiDeo(dto.getQuaiDeo());
            return sanPhamReponsitory.save(sanPham);
        } else {
            AdminSanPhamRequest sanPhamRequest = AdminSanPhamRequest.builder()
                    .loai(dto.getLoai())
                    .thuongHieu(dto.getThuongHieu())
                    .demLot(dto.getDemLot())
                    .moTa(dto.getMoTa())
                    .ten(dto.getTen())
                    .anh(dto.getAnh())
                    .quaiDeo(dto.getQuaiDeo())
                    .build();
            return this.saveSanPham(sanPhamRequest);
        }
    }

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
    public void saveExcel(MultipartFile file) throws IOException, StorageException, InvalidKeyException, URISyntaxException {

    }

    @Override
    public List<SanPhamChiTiet> exportCustomerToExcel(HttpServletResponse response) throws IOException {
        return null;
    }

    public void deleteSize(Integer idSp, Integer idSize) {
        SizeChiTiet size = sizeChiTietReponsitory.findByIdSanPhamAndIdSize(idSp, idSize);
        sizeChiTietReponsitory.delete(size);
    }

    public void deleteMauSac(Integer idSp, Integer idMau) {
        MauSacChiTiet mauSac = mauSacChiTietReponsitory.findMSBySPAndMS(idSp, idMau);
        mauSacChiTietReponsitory.delete(mauSac);
    }

    public void deleteImg(Integer idSp, String img) {
        int lastIndexOfSlash = img.lastIndexOf("\\");

        if (lastIndexOfSlash != -1) {
            String fileName = img.substring(lastIndexOfSlash + 1);
            System.out.println(fileName);
            Image image = imageReponsitory.findBySanPhamIdAndAnh(idSp,"%"+fileName+"%");
            System.out.println(image.getId());
            imageReponsitory.delete(image);
        } else {
            System.out.println("Không tìm thấy dấu gạch chéo trong URL.");
        }


       //
    }

}
