package com.example.demo.core.Admin.service.AdSanPhamService;

import com.example.demo.core.Admin.model.response.AdminExcelAddSanPhamBO;
import com.example.demo.core.Admin.model.response.AdminExcelAddSanPhamResponse;
import com.example.demo.entity.MauSacChiTiet;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.entity.SizeChiTiet;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AdExcelAddSanPhamService {
    AdminExcelAddSanPhamBO previewDataImportExcel(MultipartFile file) throws IOException;

    AdminExcelAddSanPhamResponse processRow(Row row);

    AdminExcelAddSanPhamBO savaData(AdminExcelAddSanPhamBO adminExcelAddSanPhamBO);

    List<SanPhamChiTiet> saveAll(AdminExcelAddSanPhamBO adminExcelAddSanPhamBO);

    void mutitheard(List<SanPhamChiTiet> saveSanPhamChiTiet, AdminExcelAddSanPhamBO adminExcelAddSanPhamBO);

    List<MauSacChiTiet> saveAllMauSacChiTiet(  List<SizeChiTiet> sizes,AdminExcelAddSanPhamBO adminExcelAddSanPhamBO, List<SanPhamChiTiet> savedSanPhamChiTiets);

    List<SizeChiTiet> saveAllSizeChiTiet(AdminExcelAddSanPhamBO adminExcelAddSanPhamBO, List<SanPhamChiTiet> savedSanPhamChiTiets);

    void saveAllImage(AdminExcelAddSanPhamBO adminExcelAddSanPhamBO, List<SanPhamChiTiet> savedSanPhamChiTiets);

    List<String> azureImgProduct(List<String> url);
}
