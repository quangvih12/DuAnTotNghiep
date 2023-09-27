package com.example.demo.core.Admin;

import com.example.demo.dto.request.ChucVuRequest;
import com.example.demo.entity.ChucVu;
import com.example.demo.sevice.ChucVuService;
import com.example.demo.util.DataUltil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/chuc-vu")
public class ChucVuApi {

    @Autowired
    private ChucVuService service;

    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", value = "pages") Integer pages) {
        Page<ChucVu> page = service.getAll(pages);
        HashMap<String, Object> map = DataUltil.setData("ok", page);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(defaultValue = "0", value = "pages") Integer pages, @RequestParam(required = false) String keyword) {
        Page<ChucVu> page = service.search(keyword, pages);
        HashMap<String, Object> map = DataUltil.setData("ok", page);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/validation")
    public ResponseEntity<?> validation(@RequestBody @Valid ChucVuRequest request, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            HashMap<String, Object> map = DataUltil.setData("error", list);
            return ResponseEntity.ok(map);
        } else {
            HashMap<String, Object> map = DataUltil.setData("ok", "");
            return ResponseEntity.ok(map);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ChucVuRequest request) {
        HashMap<String, Object> map = service.add(request);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody ChucVuRequest request) {
        HashMap<String, Object> map = service.update(request, id);
        return ResponseEntity.ok(map);
    }

    // xóa (đổi trạng thái về 0)
    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, @RequestBody ChucVuRequest request) {
        HashMap<String, Object> map = service.delete(request, id);
        return ResponseEntity.ok(map);
    }

    // thêm bằng file excel
    @PostMapping("/upload")
    public ResponseEntity<?> uploadCustomersData(@RequestParam("file") MultipartFile file) {
        this.service.saveExcel(file);
        HashMap<String, Object> map = DataUltil.setData("success", " thêm sản phẩm thành công");
        return ResponseEntity.ok(map);
    }

}
