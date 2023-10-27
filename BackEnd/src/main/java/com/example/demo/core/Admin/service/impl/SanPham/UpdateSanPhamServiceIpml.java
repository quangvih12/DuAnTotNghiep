package com.example.demo.core.Admin.service.impl.SanPham;

import com.example.demo.core.Admin.model.request.AdminSanPhamChiTietRequest;
import com.example.demo.core.Admin.model.request.AdminSanPhamRequest;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
import com.example.demo.core.Admin.repository.AdChiTietSanPhamReponsitory;
import com.example.demo.core.Admin.repository.AdImageReponsitory;
import com.example.demo.core.Admin.repository.AdMauSacChiTietReponsitory;
import com.example.demo.core.Admin.repository.AdSizeChiTietReponsitory;
import com.example.demo.core.Admin.service.AdSanPhamService.AdUpdateSanPhamService;
import com.example.demo.entity.*;
import com.example.demo.infrastructure.status.ChiTietSanPhamStatus;
import com.example.demo.reponsitory.MauSacChiTietReponsitory;
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
    private AdSizeChiTietReponsitory sizeChiTietReponsitory;

    @Autowired
    private MauSacChiTietReponsitory mauSacChiTietReponsitory;

    @Autowired
    private SanPhamReponsitory sanPhamReponsitory;

    @Autowired
    ImageToAzureUtil getImageToAzureUtil;

    @Autowired
    private AdMauSacChiTietReponsitory adMauSacChiTietReponsitory;


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
            sanPhamChiTiet.setVatLieu(VatLieu.builder().id(Integer.valueOf(dto.getVatLieu())).build());
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

        Future<List<SizeChiTiet>> sizeFuture = executor.submit(() -> {
            if (request.getIdSize() != null && !request.getIdSize().isEmpty()) {
                return updateSizeChiTiet(sanPhamChiTiet, request);
            } else {
                return Collections.emptyList();
            }
        });

        List<SizeChiTiet> sizes = sizeFuture.get();
        Future<Void> mauSacFuture = executor.submit(() -> {
            try {

                updateMauSacChiTiet(sizes, sanPhamChiTiet, request);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        });

        Future<Void> imageFuture = executor.submit(() -> {
            try {
                updateImage(sanPhamChiTiet, request);
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
    public List<SizeChiTiet> updateSizeChiTiet(SanPhamChiTiet sanPhamChiTiet, AdminSanPhamChiTietRequest dto) {

        // Lấy danh sách SizeChiTiet hiện tại của sản phẩm
        List<SizeChiTiet> currentSizeChiTietList = sizeChiTietReponsitory.findSizeChiTietBySanPhamChiTiet(sanPhamChiTiet.getId());

        // nếu id size == null thì set lại idSizeChiTiet trong mauSacChiTiet và delete nó đi
        if (dto.getIdSize() == null || dto.getIdSize().isEmpty()) {
            List<SizeChiTiet> sizeChiTiets = sizeChiTietReponsitory.findSizeChiTietBySanPhamChiTiet(sanPhamChiTiet.getId());
            for (SizeChiTiet sizeChiTiet : sizeChiTiets) {
                List<MauSacChiTiet> mauSacChiTiet = mauSacChiTietReponsitory.findMauSacChiTietBySizeChiTiet(sizeChiTiet.getId());
                for (MauSacChiTiet mauSac : mauSacChiTiet) {
                    mauSac.setSizeChiTiet(null);
                    mauSacChiTietReponsitory.save(mauSac);
                }

                sizeChiTietReponsitory.deleteById(sizeChiTiet.getId());
            }
            return null;
        }

        List<String> idSize = dto.getIdSize();
        List<String> soLuongSize = dto.getSoLuongSize();

        // Lấy danh sách idSize hiện có
        List<Integer> currentSizeIds = currentSizeChiTietList.stream()
                .map(sizeChiTiet -> sizeChiTiet.getSize().getId())
                .collect(Collectors.toList());

        // Xóa các SizeChiTiet không còn trong danh sách mới
        List<SizeChiTiet> sizesToDelete = currentSizeChiTietList.stream()
                .filter(sizeChiTiet -> !idSize.contains(String.valueOf(sizeChiTiet.getSize().getId())))
                .collect(Collectors.toList());

        for (SizeChiTiet sizeChiTiet : sizesToDelete) {
            List<MauSacChiTiet> mauSacChiTiet = mauSacChiTietReponsitory.findMauSacChiTietBySizeChiTiet(sizeChiTiet.getId());
            mauSacChiTietReponsitory.deleteAll(mauSacChiTiet);
            sizeChiTietReponsitory.deleteById(sizeChiTiet.getId());
        }

        // Cập nhật hoặc tạo mới SizeChiTiet
        List<SizeChiTiet> updatedSizeChiTietList = new ArrayList<>();

        for (int i = 0; i < idSize.size(); i++) {
            int sizeId = Integer.parseInt(idSize.get(i));
            //    int soLuong = Integer.parseInt(soLuongSize.get(i));
            SizeChiTiet sizeChiTiet = currentSizeChiTietList.stream()
                    .filter(s -> s.getSize().getId() == sizeId)
                    .findFirst()
                    .orElse(null);

            if (sizeChiTiet == null) {
                sizeChiTiet = new SizeChiTiet();
                sizeChiTiet.setSize(Size.builder().id(sizeId).build());
                sizeChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                sizeChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
                currentSizeChiTietList.add(sizeChiTiet);
            }

            //    sizeChiTiet.setSoLuong(soLuong);
            sizeChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());

            updatedSizeChiTietList.add(sizeChiTiet);
        }

        // Lưu danh sách SizeChiTiet đã cập nhật
        List<SizeChiTiet> newSize = sizeChiTietReponsitory.saveAll(updatedSizeChiTietList);

        // Cập nhật MauSacChiTiet
        List<MauSacChiTiet> mauSacChiTiet = mauSacChiTietReponsitory.findMauSacChiTietBySanPhamChiTietAndSize(sanPhamChiTiet.getId());

        for (MauSacChiTiet mauSac : mauSacChiTiet) {
            for (SizeChiTiet sizeChiTiet : newSize) {
                mauSac.setSizeChiTiet(sizeChiTiet);
            }
        }
        mauSacChiTietReponsitory.saveAll(mauSacChiTiet);

        return newSize;
    }

    @Override
    public List<MauSacChiTiet> updateMauSacChiTiet(List<SizeChiTiet> sizes, SanPhamChiTiet sanPhamChiTiet, AdminSanPhamChiTietRequest dto) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
        List<String> idMauSac = new ArrayList<>();
        List<String> idMauSac1 = new ArrayList<>();
        List<String> idMauSac2 = dto.getIdMauSac();
        List<String> imgMauSac = dto.getImgMauSac();
        List<String> soLuong = dto.getSoLuongSize();
        List<MauSacChiTiet> updatedMauSacChiTietList = new ArrayList<>();

        // nếu size null thì thêm mới ngược lại xóa hết màu sắc và size của sản phẩm
        if (sizes == null || sizes.isEmpty()) {
            List<SizeChiTiet> sizeChiTiets = sizeChiTietReponsitory.findSizeChiTietBySanPhamChiTiet(sanPhamChiTiet.getId());
            if (!sizeChiTiets.isEmpty()) {
                for (SizeChiTiet sizeChiTiet : sizeChiTiets) {
                    List<MauSacChiTiet> mauSacChiTiet = mauSacChiTietReponsitory.findMauSacChiTietBySizeChiTiet(sizeChiTiet.getId());
                    mauSacChiTietReponsitory.deleteAll(mauSacChiTiet);
                    sizeChiTietReponsitory.deleteById(sizeChiTiet.getId());
                }
                return null;
            } else {
                for (int i = 0; i < idMauSac2.size(); i++) {
                    String mauSac = idMauSac2.get(i);
                    List<MauSacChiTiet> mauSacChiTietList = adMauSacChiTietReponsitory.findBySanPhamChiTietIdAndMauSacId(sanPhamChiTiet.getId(), Integer.valueOf(mauSac));
                    if (!mauSacChiTietList.isEmpty()) {
                        for (MauSacChiTiet mauSacChiTiet : mauSacChiTietList) {
                            mauSacChiTiet.setMauSac(MauSac.builder().id(Integer.valueOf(mauSac)).build());
                            mauSacChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                            mauSacChiTiet.setTrangThai(1);

                            // Kiểm tra xem có ảnh tương ứng không
                            if (!imgMauSac.get(i).matches("https://imagedatn.blob.core.windows.net/imagecontainer/D:" + ".*")) {
                                String linkAnh = getImageToAzureUtil.uploadImageToAzure(imgMauSac.get(i));
                                mauSacChiTiet.setAnh(linkAnh);
                            } else {
                                mauSacChiTiet.setAnh(imgMauSac.get(i));
                            }
                            if (i < soLuong.size()) {
                                mauSacChiTiet.setSoLuong(Integer.valueOf(soLuong.get(i)));
                            }
                            mauSacChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
                            updatedMauSacChiTietList.add(mauSacChiTiet);
                        }
                    } else {
                        MauSacChiTiet mauSacChiTiet = new MauSacChiTiet();
                        mauSacChiTiet.setMauSac(MauSac.builder().id(Integer.valueOf(mauSac)).build());
                        mauSacChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                        mauSacChiTiet.setTrangThai(1);

                        // Kiểm tra xem có ảnh tương ứng không
                        String linkAnh = getImageToAzureUtil.uploadImageToAzure(imgMauSac.get(i));
                        mauSacChiTiet.setAnh(linkAnh);
                        mauSacChiTiet.setSoLuong(Integer.valueOf(soLuong.get(i)));
                        mauSacChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
                        updatedMauSacChiTietList.add(mauSacChiTiet);
                    }
                }
                return mauSacChiTietReponsitory.saveAll(updatedMauSacChiTietList);
            }
        }


        for (SizeChiTiet sizeChiTiet : sizes) {
            List<MauSacChiTiet> mauSacChiTiet1 = mauSacChiTietReponsitory.findMauSacChiTietBySizeChiTiet(sizeChiTiet.getId());
            for (MauSacChiTiet o : mauSacChiTiet1) {
                idMauSac1.add(String.valueOf(o.getMauSac().getId()));
            }
        }

        // loại bỏ những dữ liệu trùng nhau trong list idMauSac2
        Set<Integer> uniqueIds = new HashSet<>();
        List<Integer> uniqueIdMauSacChiTietList = new ArrayList<>();
        for (String id : idMauSac1) {
            if (uniqueIds.add(Integer.valueOf(id))) {
                uniqueIdMauSacChiTietList.add(Integer.valueOf(id));
            }
        }
        // check nếu list mauSacChiTiet nếu null mình tạo mới theo size vừa thêm mới
        for (SizeChiTiet sizeChiTiet : sizes) {
            List<MauSacChiTiet> mauSacChiTiet1 = mauSacChiTietReponsitory.findMauSacChiTietBySizeChiTiet(sizeChiTiet.getId());

            for (int i = 0; i < uniqueIdMauSacChiTietList.size(); i++) {
                Integer id_Mau = uniqueIdMauSacChiTietList.get(i);

                if (mauSacChiTiet1.isEmpty()) {
                    if (sizeChiTiet.getSanPhamChiTiet().getId() == sanPhamChiTiet.getId()) {
                        MauSacChiTiet mauSacChiTiet = new MauSacChiTiet();
                        mauSacChiTiet.setMauSac(MauSac.builder().id(Integer.valueOf(id_Mau)).build());
                        mauSacChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                        mauSacChiTiet.setTrangThai(1);
                        mauSacChiTiet.setSizeChiTiet(sizeChiTiet);

                        // Kiểm tra xem có ảnh tương ứng không
                        if (!imgMauSac.get(i).matches("https://imagedatn.blob.core.windows.net/imagecontainer/D:" + ".*")) {
                            String linkAnh = getImageToAzureUtil.uploadImageToAzure(imgMauSac.get(i));
                            mauSacChiTiet.setAnh(linkAnh);
                        } else {
                            mauSacChiTiet.setAnh(imgMauSac.get(i));
                        }
                        if (i < soLuong.size()) {
                            mauSacChiTiet.setSoLuong(Integer.valueOf(soLuong.get(i)));
                        }
                        mauSacChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
                        updatedMauSacChiTietList.add(mauSacChiTiet);
                    }
                }
            }

            for (MauSacChiTiet o : mauSacChiTiet1) {
                idMauSac.add(String.valueOf(o.getId()));
            }
        }

        for (int i = 0; i < idMauSac2.size(); i++) {
            String id_Mau = idMauSac2.get(i);
            List<SizeChiTiet> applicableSizes = sizes.isEmpty() ? Collections.singletonList(null) : sizes;
            for (SizeChiTiet sizeChiTiet : applicableSizes) {
                List<MauSacChiTiet> mauSacChiTiet1 = mauSacChiTietReponsitory.findMauSacChiTietBySanPhamChiTietAndMauSac(sanPhamChiTiet.getId(), Integer.valueOf(id_Mau));

                if (mauSacChiTiet1.isEmpty()) {

                    if (sizeChiTiet.getSanPhamChiTiet().getId() == sanPhamChiTiet.getId()) {
                        MauSacChiTiet mauSacChiTiet = new MauSacChiTiet();
                        mauSacChiTiet.setMauSac(MauSac.builder().id(Integer.valueOf(id_Mau)).build());
                        mauSacChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                        mauSacChiTiet.setTrangThai(1);
                        mauSacChiTiet.setSizeChiTiet(sizeChiTiet);
                        // Kiểm tra xem có ảnh tương ứng không
                        if (!imgMauSac.get(i).matches("https://imagedatn.blob.core.windows.net/imagecontainer/D:" + ".*")) {
                            String linkAnh = getImageToAzureUtil.uploadImageToAzure(imgMauSac.get(i));
                            mauSacChiTiet.setAnh(linkAnh);
                        } else {
                            mauSacChiTiet.setAnh(imgMauSac.get(i));
                        }
                        if (i < soLuong.size()) {
                            mauSacChiTiet.setSoLuong(Integer.valueOf(soLuong.get(i)));
                        }
                        mauSacChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
                        updatedMauSacChiTietList.add(mauSacChiTiet);
                    }
                }
            }

        }


        // update lại màu sắc chi tiết theo id mau sắc
        for (int i = 0; i < idMauSac.size(); i++) {
            String mauSac = idMauSac.get(i);
            //    System.out.println(idMauSac.size() + " - " + imgMauSac.size());
            MauSacChiTiet mauSacChiTietList = adMauSacChiTietReponsitory.findById(Integer.valueOf(mauSac)).get();
            if (mauSacChiTietList != null) {
                mauSacChiTietList.setSanPhamChiTiet(sanPhamChiTiet);

                if (!imgMauSac.get(i).matches("https://imagedatn.blob.core.windows.net/imagecontainer/D:" + ".*")) {
                    String linkAnh = getImageToAzureUtil.uploadImageToAzure(imgMauSac.get(i));
                    mauSacChiTietList.setAnh(linkAnh);
                } else {
                    mauSacChiTietList.setAnh(imgMauSac.get(i));
                }
                mauSacChiTietList.setSoLuong(Integer.valueOf(soLuong.get(i)));
                updatedMauSacChiTietList.add(mauSacChiTietList);
            }
        }
        return mauSacChiTietReponsitory.saveAll(updatedMauSacChiTietList);
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
    public void deleteSize(Integer idSp, Integer idSize) {
        SizeChiTiet size = sizeChiTietReponsitory.findByIdSanPhamAndIdSize(idSp, idSize);
        sizeChiTietReponsitory.delete(size);
    }

    @Override
    public void deleteMauSac( Integer idMau) {
        MauSacChiTiet mauSac = mauSacChiTietReponsitory.findById(idMau).get();
        mauSacChiTietReponsitory.delete(mauSac);
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
