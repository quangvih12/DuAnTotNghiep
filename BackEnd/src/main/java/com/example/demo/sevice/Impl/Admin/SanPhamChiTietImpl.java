package com.example.demo.sevice.Impl.Admin;

import com.example.demo.dto.request.ImageRequest;
import com.example.demo.dto.request.SanPhamChiTietRequest;
import com.example.demo.entity.Image;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.MauSacChiTiet;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.entity.Size;
import com.example.demo.entity.SizeChiTiet;
import com.example.demo.reponsitory.ChiTietSanPhamReponsitory;
import com.example.demo.reponsitory.ImageReponsitory;
import com.example.demo.reponsitory.MauSacChiTietReponsitory;
import com.example.demo.reponsitory.SizeChiTietReponsitory;
import com.example.demo.sevice.SanPhamChiTietService;
import com.example.demo.util.DataUltil;
import com.example.demo.util.ExcelExportUtils;
import com.example.demo.util.FileUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class SanPhamChiTietImpl implements SanPhamChiTietService {

    @Autowired
    private ChiTietSanPhamReponsitory chiTietSanPhamReponsitory;

    @Autowired
    private ImageReponsitory imageReponsitory;

    @Autowired
    private SizeChiTietReponsitory sizeChiTietReponsitory;

    @Autowired
    MauSacChiTietReponsitory mauSacChiTietReponsitory;

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
    public HashMap<String, Object> add(SanPhamChiTietRequest dto, MultipartFile file, Integer idSize, Integer idMauSac) {
        SanPhamChiTiet sanPham = dto.dtoToEntity(new SanPhamChiTiet());
        Image image = new Image();
        List<Image> imageList = new ArrayList<>();
        try {
            SanPhamChiTiet sanPhamChiTiet = this.chiTietSanPhamReponsitory.save(sanPham);

            // gán id size  và id san pham chi tiết vào đối tượng size chi tiết
            SizeChiTiet sizeChiTiet = new SizeChiTiet();
            sizeChiTiet.setSize(Size.builder().id(idSize).build());
            sizeChiTiet.setSanPhamChiTiet(sanPhamChiTiet);

            // gán id màu sắc  và id san pham chi tiết vào đối tượng màu sắc chi tiết
            MauSacChiTiet mauSacChiTiet = new MauSacChiTiet();
            mauSacChiTiet.setMauSac(MauSac.builder().id(idMauSac).build());
            mauSacChiTiet.setSanPhamChiTiet(sanPhamChiTiet);

            System.out.println(file);
            String encode;
//            try {
//                encode = FileUtil.fileToBase64(file);
//                image.setAnh(encode);
//                image.setSanPhamChiTiet(sanPhamChiTiet);
//                System.out.println(image.getAnh());
//                imageList.add(image);
//            } catch (IOException e) {
//                return DataUltil.setData("error", "lỗi tải ảnh lên");
//            }

            this.sizeChiTietReponsitory.save(sizeChiTiet);
            this.mauSacChiTietReponsitory.save(mauSacChiTiet);
            this.imageReponsitory.saveAll(imageList);

            return DataUltil.setData("success", "thêm thành công");
        } catch (Exception e) {
            return DataUltil.setData("error", "error");
        }
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
    public void saveExcel(MultipartFile file) {

    }

    @Override
    public List<SanPhamChiTiet> exportCustomerToExcel(HttpServletResponse response) throws IOException {
        List<SanPhamChiTiet> sanPhamChiTietList = chiTietSanPhamReponsitory.findAll();
        ExcelExportUtils exportUtils = new ExcelExportUtils(sanPhamChiTietList);
        exportUtils.exportDataToExcel(response);
        return sanPhamChiTietList;
    }
}
