package com.example.demo.core.Admin.service.impl.SanPham;

import com.example.demo.core.Admin.model.request.AdminSanPhamChiTietRequest;
import com.example.demo.core.Admin.model.request.AdminSanPhamRequest;
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
    public SanPhamChiTiet add(AdminSanPhamChiTietRequest dto) {
        return null;
    }

    @Override
    public SanPhamChiTiet update(AdminSanPhamChiTietRequest dto, Integer id) {
        SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamReponsitory.findById(id).get();
        if (sanPhamChiTiet != null) {
            SanPham sanPham = this.updateSanPham(sanPhamChiTiet, dto);
            sanPhamChiTiet.setSanPham(sanPham);
            sanPhamChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());
            sanPhamChiTiet.setGiaBan(BigDecimal.valueOf(dto.getGiaBan()));
            sanPhamChiTiet.setGiaNhap(BigDecimal.valueOf(dto.getGiaNhap()));
            sanPhamChiTiet.setTrangThai(Integer.valueOf(dto.getTrangThai()));
            sanPhamChiTiet.setSoLuongTon(Integer.valueOf(dto.getSoLuongTon()));
            sanPhamChiTiet.setVatLieu(VatLieu.builder().id(Integer.valueOf(dto.getVatLieu())).build());
            sanPhamChiTiet.setTrongLuong(TrongLuong.builder().id(dto.getTrongLuong()).build());
            mutitheard(sanPhamChiTiet, dto);
            return chiTietSanPhamReponsitory.save(sanPhamChiTiet);
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
        List<Image> img = this.imageReponsitory.findBySanPhamId(sanPhamChiTiet.getId());

        if (!img.isEmpty()) {
            for (Image imgs : img) {
                for (String i : dto.getImgMauSac()) {
                    imgs.setAnh(i);
                    imgs.setNgaySua(DatetimeUtil.getCurrentDate());
                }
            }
            return this.imageReponsitory.saveAll(img);
        } else {
            List<Image> imageList = new ArrayList<>();
            for (String imgs : dto.getImgMauSac()) {
                Image image = new Image();
                image.setAnh(imgs);
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


    public List<SizeChiTiet> updateSizeChiTiet(SanPhamChiTiet sanPhamChiTiet, AdminSanPhamChiTietRequest dto) {
        // Tìm tất cả các SizeChiTiet của sản phẩm được đưa vào
        List<SizeChiTiet> sizeList = sizeChiTietReponsitory.findSizeChiTietBySanPhamChiTiet(sanPhamChiTiet.getId());

        if (sizeList.isEmpty()) {

            List<SizeChiTiet> sizeChiTietList = new ArrayList<>();

            // Đảm bảo rằng số lượng idSize và soLuongSize là giống nhau
            if (dto.getIdSize().size() != dto.getSoLuongSize().size())
                throw new IllegalArgumentException("Số lượng idSize và soLuongSize không khớp");

            for (int i = 0; i < dto.getIdSize().size(); i++) {
                String sizeId = dto.getIdSize().get(i);
                String soLuongSizeValue = dto.getSoLuongSize().get(i);

                SizeChiTiet sizeChiTiet = new SizeChiTiet();
                sizeChiTiet.setSize(Size.builder().id(Integer.valueOf(sizeId)).build());
                sizeChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                sizeChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
                sizeChiTiet.setSoLuong(Integer.valueOf(soLuongSizeValue));

                sizeChiTietList.add(sizeChiTiet);
            }

            return this.sizeChiTietReponsitory.saveAll(sizeChiTietList);
        } else {
            for (SizeChiTiet sizeChiTiet : sizeList) {
                for (int i = 0; i < dto.getIdSize().size(); i++) {
                    String sizeId = dto.getIdSize().get(i);
                    String soLuongSizeValue = dto.getSoLuongSize().get(i);
                    sizeChiTiet.setSize(Size.builder().id(Integer.valueOf(sizeId)).build());
                    sizeChiTiet.setSoLuong(Integer.valueOf(soLuongSizeValue));
                    sizeChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());
                }
            }
            return sizeChiTietReponsitory.saveAll(sizeList);
        }
    }

    public List<MauSacChiTiet> updateMauSacChiTiet(SanPhamChiTiet sanPhamChiTiet, AdminSanPhamChiTietRequest dto) {
        // Tìm tất cả các SizeChiTiet của sản phẩm được đưa vào
        List<MauSacChiTiet> mauSacList = mauSacChiTietReponsitory.findMauSacChiTietBySanPhamChiTiet(sanPhamChiTiet.getId());

        if (mauSacList.isEmpty()) {

            List<MauSacChiTiet> mauSacChiTietList = new ArrayList<>();

            // Đảm bảo rằng số lượng idMauSac và imgMauSac là giống nhau
            if (dto.getIdMauSac().size() != dto.getImgMauSac().size())
                throw new IllegalArgumentException("Số lượng idMauSac và imgMauSac không khớp");

            for (int i = 0; i < dto.getIdMauSac().size(); i++) {
                String mauSacId = dto.getIdMauSac().get(i);
                String imgMauSacValue = dto.getIdMauSac().get(i);

                MauSacChiTiet mauSacChiTiet = new MauSacChiTiet();
                mauSacChiTiet.setMauSac(MauSac.builder().id(Integer.valueOf(mauSacId)).build());
                mauSacChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                mauSacChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
                mauSacChiTiet.setAnh(imgMauSacValue);
                mauSacChiTietList.add(mauSacChiTiet);
            }
            return this.mauSacChiTietReponsitory.saveAll(mauSacChiTietList);
        } else {
            for (MauSacChiTiet mauSacChiTiet : mauSacList) {
                for (int i = 0; i < dto.getIdMauSac().size(); i++) {
                    String mauSacId = dto.getIdMauSac().get(i);
                    String imgMauSacValue = dto.getIdMauSac().get(i);
                    mauSacChiTiet.setMauSac(MauSac.builder().id(Integer.valueOf(mauSacId)).build());
                    mauSacChiTiet.setAnh(imgMauSacValue);
                    mauSacChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());
                }
            }
            return this.mauSacChiTietReponsitory.saveAll(mauSacList);
        }
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
            sanPham.setQuaiDeo(dto.getQuaiDeo());
            return sanPhamReponsitory.save(sanPham);
        } else {
            AdminSanPhamRequest sanPhamRequest = AdminSanPhamRequest.builder()
                    .loai(dto.getLoai())
                    .thuongHieu(dto.getThuongHieu())
                    .demLot(dto.getDemLot())
                    .moTa(dto.getMoTa())
                    .ten(dto.getTen())
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
    public SanPhamChiTiet delete(AdminSanPhamChiTietRequest dto, Integer id) {
          SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamReponsitory.findById(id).get();
        if (sanPhamChiTiet != null) {
            sanPhamChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());
            sanPhamChiTiet.setGiaBan(BigDecimal.valueOf(dto.getGiaBan()));
            sanPhamChiTiet.setGiaNhap(BigDecimal.valueOf(dto.getGiaNhap()));
            sanPhamChiTiet.setTrangThai(ChiTietSanPhamStatus.XOA);
            sanPhamChiTiet.setSoLuongTon(Integer.valueOf(dto.getSoLuongTon()));
            sanPhamChiTiet.setVatLieu(VatLieu.builder().id(Integer.valueOf(dto.getVatLieu())).build());
            sanPhamChiTiet.setTrongLuong(TrongLuong.builder().id(dto.getTrongLuong()).build());
            mutitheard(sanPhamChiTiet, dto);
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
}
