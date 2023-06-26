package com.example.demo.api.Admin;

import com.example.demo.dto.request.SanPhamChiTietRequest;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.sevice.Impl.Admin.SanPhamChiTietServiceImpl;
import com.example.demo.util.DataUltil;
import com.microsoft.azure.storage.StorageException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.HashMap;

@RestController
@RequestMapping("/api/ChiTietSp")
public class SanPhamChiTietApi {
    @Autowired
    private SanPhamChiTietServiceImpl sanPhamChiTietService;

    // getAll san pham chi tiet
    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", value = "pages") Integer pages,
                                    @RequestParam(required = false) String upAndDown, @RequestParam(required = false) Integer trangThai) {
        Page<SanPhamChiTiet> page = sanPhamChiTietService.getAll(pages, upAndDown, trangThai);
        HashMap<String, Object> map = DataUltil.setData("ok", page);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietService.getOne(id);
        HashMap<String, Object> map = DataUltil.setData("ok", sanPhamChiTiet);
        return ResponseEntity.ok(map);
    }

    // thêm bằng file excel
    @PostMapping("/upload")
    public ResponseEntity<?> uploadCustomersData(@RequestParam("file") MultipartFile file) throws URISyntaxException, StorageException, InvalidKeyException, IOException {
        this.sanPhamChiTietService.saveExcel(file);
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

    // thêm
    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> add(@RequestPart("request") SanPhamChiTietRequest sanPhamChiTietRequest,
                                 @RequestPart("files") MultipartFile[] files,
                                 @RequestPart("file") MultipartFile file
    ) {
        HashMap<String, Object> map = sanPhamChiTietService.add(sanPhamChiTietRequest, files, file);
        return ResponseEntity.ok(map);
    }
}
