package com.example.demo.api.Admin;

import com.example.demo.dto.request.LoaiRequest;
import com.example.demo.dto.request.SanPhamRequest;
import com.example.demo.entity.Loai;
import com.example.demo.entity.SanPham;
import com.example.demo.sevice.Impl.Admin.LoaiServiceImpl;
import com.example.demo.sevice.Impl.Admin.SanPhamServiceImpl;
import com.example.demo.util.DataUltil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/sanPham")
public class SanPhamApi {
    @Autowired
    private SanPhamServiceImpl sanPhamService;

    // getAll loai
    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", value = "pages") Integer pages) {
        Page<SanPham> page = sanPhamService.getAll(pages);
        HashMap<String, Object> map = DataUltil.setData("ok", page);
        return ResponseEntity.ok(map);
    }

    // tim kiếm theo ten hoặc mã
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(defaultValue = "0", value = "pages") Integer pages, @RequestParam(required = false) String keyword) {
        Page<SanPham> page = sanPhamService.search(keyword, pages);
        HashMap<String, Object> map = DataUltil.setData("ok", page);
        return ResponseEntity.ok(map);
    }

    // check validate
    @PostMapping("/validation")
    public ResponseEntity<?> validation(@RequestBody @Valid SanPhamRequest request, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            HashMap<String, Object> map = DataUltil.setData("error", list);
            return ResponseEntity.ok(map);
        } else {
            HashMap<String, Object> map = DataUltil.setData("ok", "");
            return ResponseEntity.ok(map);
        }
    }

    // thêm
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody SanPhamRequest request) {
        HashMap<String, Object> map = sanPhamService.add(request);
        return ResponseEntity.ok(map);
    }

    // sửa
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody SanPhamRequest request) {
        HashMap<String, Object> map = sanPhamService.update(request, id);
        return ResponseEntity.ok(map);
    }

    // xóa (đổi trạng thái về 0)
    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, @RequestBody SanPhamRequest request) {
        HashMap<String, Object> map = sanPhamService.delete(request, id);
        return ResponseEntity.ok(map);
    }

    // thêm bằng file excel
    @PostMapping("/upload")
    public ResponseEntity<?> uploadCustomersData(@RequestParam("file") MultipartFile file) {
        this.sanPhamService.saveExcel(file);
        HashMap<String, Object> map = DataUltil.setData("success", " thêm sản phẩm thành công");
        return ResponseEntity.ok(map);
    }
}
