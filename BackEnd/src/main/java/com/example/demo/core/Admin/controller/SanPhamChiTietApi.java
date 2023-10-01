package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.model.request.AdminSanPhamChiTietRequest;
import com.example.demo.core.Admin.model.request.AdminSearchRequest;
import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
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

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = {"*"})
public class SanPhamChiTietApi {
    @Autowired
    private SanPhamChiTietServiceImpl sanPhamChiTietService;

    @Autowired
    private CreateExcelSanPhamServiceImpl createExcelSanPhamService;

    @Autowired
    private UpdateSanPhamServiceIpml updateSanPhamServiceIpml;

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
        List<String> imageUrls = sanPhamChiTietService.getProductImages(productId);
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
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietService.getOne(id);
        HashMap<String, Object> map = DataUltil.setData("ok", sanPhamChiTiet);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/check/{ten}")
    public ResponseEntity<?> check(@PathVariable String ten) {
        return ResponseEntity.ok(sanPhamChiTietService.findBySanPhamTen(ten));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getOneVL(@PathVariable String id) {
        SanPham vatLieu = createExcelSanPhamService.getSp(id);
        HashMap<String, Object> map = DataUltil.setData("ok", vatLieu);
        return ResponseEntity.ok(map);
    }

    // thêm bằng file excel
    @PostMapping("/upload")
    public ResponseEntity<?> uploadCustomersData(@RequestParam("file") MultipartFile file) throws URISyntaxException, StorageException, InvalidKeyException, IOException {
        this.createExcelSanPhamService.saveExcel(file);
        HashMap<String, Object> map = DataUltil.setData("success", " thêm sản phẩm thành công");
        return ResponseEntity.ok(map);
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
            , BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(DataUltil.setData("error", list));
        } else {
            return ResponseEntity.ok(sanPhamChiTietService.add(sanPhamChiTietRequest));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody AdminSanPhamChiTietRequest sanPhamChiTietRequest, @PathVariable Integer id
            , BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(DataUltil.setData("error", list));
        } else {
            return ResponseEntity.ok(updateSanPhamServiceIpml.update(sanPhamChiTietRequest, id));
        }
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<?> delete( @RequestBody AdminSanPhamChiTietRequest sanPhamChiTietRequest, @PathVariable Integer id) {
            return ResponseEntity.ok(updateSanPhamServiceIpml.delete(sanPhamChiTietRequest, id));
    }

}
