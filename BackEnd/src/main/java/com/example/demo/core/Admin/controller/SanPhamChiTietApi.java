package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.model.request.AdminSanPhamChiTietRequest;
import com.example.demo.core.Admin.model.request.AdminSearchRequest;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
import com.example.demo.core.Admin.service.AdExcelAddSanPhamService;
import com.example.demo.core.Admin.service.impl.SanPham.CreateExcelSanPhamServiceImpl;
import com.example.demo.core.Admin.service.impl.SanPham.UpdateSanPhamServiceIpml;
import com.example.demo.entity.*;
import com.example.demo.core.Admin.service.impl.SanPham.SanPhamChiTietServiceImpl;
import com.example.demo.util.DataUltil;
import com.microsoft.azure.storage.StorageException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/products")

public class SanPhamChiTietApi {
    @Autowired
    private SanPhamChiTietServiceImpl sanPhamChiTietService;

    @Autowired
    private CreateExcelSanPhamServiceImpl createExcelSanPhamService;

    @Autowired
    private UpdateSanPhamServiceIpml updateSanPhamServiceIpml;

    @Autowired
    private AdExcelAddSanPhamService adExcelAddSanPhamService;

    // getAll san pham chi tiet
    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", value = "pages") Integer pages,
                                    @RequestParam(required = false) String upAndDown, @RequestParam(required = false) Integer trangThai) {
        Page<SanPhamChiTiet> page = sanPhamChiTietService.getAll(pages, upAndDown, trangThai);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/lisst")
    public ResponseEntity<?> getList(final AdminSearchRequest request) {
        List<AdminSanPhamChiTietResponse> lisst = sanPhamChiTietService.getList(request);
        return ResponseEntity.ok(lisst);
    }

    @GetMapping("/li")
    public ResponseEntity<?> getList() {
        List<SanPhamChiTiet> lisst = sanPhamChiTietService.getAlls();
        return ResponseEntity.ok(lisst);
    }

    @GetMapping("/{productId}/images")
    public ResponseEntity<?> getProductImages(@PathVariable Integer productId) {
        List<Image> imageUrls = sanPhamChiTietService.getProductImages(productId);
        return ResponseEntity.ok(imageUrls);
    }

    @GetMapping("/images")
    public ResponseEntity<?> getProductImages() {
        List<Image> imageUrls = sanPhamChiTietService.getProductImages();
        return ResponseEntity.ok(imageUrls);
    }

    @GetMapping("/{productId}/size")
    public ResponseEntity<?> getProductSize(@PathVariable Integer productId) {
        List<SizeChiTiet> size = sanPhamChiTietService.getProductSize(productId);
        return ResponseEntity.ok(size);
    }

    @GetMapping("/{productId}/mauSac")
    public ResponseEntity<?> getProductMauSac(@PathVariable Integer productId) {
        List<MauSacChiTiet> mauSac = sanPhamChiTietService.getProductMauSac(productId);
        return ResponseEntity.ok(mauSac);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        AdminSanPhamChiTietResponse sanPhamChiTiet = sanPhamChiTietService.get(id);
        return ResponseEntity.ok(sanPhamChiTiet);
    }

    @GetMapping("/check/{ten}")
    public ResponseEntity<?> check(@PathVariable String ten) {
        return ResponseEntity.ok(sanPhamChiTietService.findBySanPhamTen(ten));
    }

    @DeleteMapping("/deleteSize")
    public void deleteSize(@RequestParam Integer idSP, @RequestParam Integer idSize) {
        updateSanPhamServiceIpml.deleteSize(idSP, idSize);
    }

    @DeleteMapping("/deleteMauSac")
    public void deleteMauSac(@RequestParam Integer idSP, @RequestParam Integer idMau) {
        updateSanPhamServiceIpml.deleteMauSac(idSP, idMau);
    }

    @DeleteMapping("/deleteImg")
    public void deleteImg(@RequestParam Integer idSP, @RequestParam String img) {
        updateSanPhamServiceIpml.deleteImg(idSP, img);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getOneVL(@PathVariable String id) {
        SanPham vatLieu = createExcelSanPhamService.getSp(id);
        HashMap<String, Object> map = DataUltil.setData("ok", vatLieu);
        return ResponseEntity.ok(map);
    }

    // thêm bằng file excel
    @PostMapping("/view-data")
    public ResponseEntity<?> viewDataImportExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(adExcelAddSanPhamService.previewDataImportExcel(file));
    }

    // xuat excel
    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ListSanPham.xlsx";
        response.setHeader(headerKey, headerValue);
        sanPhamChiTietService.exportCustomerToExcel(response);
    }

    @PostMapping()
    public ResponseEntity<?> adds(@Valid @RequestBody AdminSanPhamChiTietRequest sanPhamChiTietRequest
            , BindingResult result) throws URISyntaxException, StorageException, InvalidKeyException, IOException {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(DataUltil.setData("error", list));
        } else {
            return ResponseEntity.ok(sanPhamChiTietService.add(sanPhamChiTietRequest));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody AdminSanPhamChiTietRequest sanPhamChiTietRequest, @PathVariable Integer id
            , BindingResult result) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(DataUltil.setData("error", list));
        } else {
            return ResponseEntity.ok(updateSanPhamServiceIpml.update(sanPhamChiTietRequest, id));
        }
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<?> delete( @PathVariable Integer id) {
        return ResponseEntity.ok(updateSanPhamServiceIpml.delete( id));
    }

}
