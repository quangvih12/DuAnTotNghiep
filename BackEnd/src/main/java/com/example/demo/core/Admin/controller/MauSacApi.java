package com.example.demo.core.Admin;

import com.example.demo.dto.request.MauSacRequest;
import com.example.demo.entity.MauSac;
import com.example.demo.sevice.Impl.Admin.MauSacServiceImpl;
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
@RequestMapping("/api/mausac")
public class MauSacApi {
    @Autowired
    private MauSacServiceImpl mauSacService;

    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", value = "pages") Integer pages) {
        Page<MauSac> page = mauSacService.getAll(pages);
        HashMap<String, Object> map = DataUltil.setData("ok", page);
        return ResponseEntity.ok(map);
    }

    // check validate
    @PostMapping("/validation")
    public ResponseEntity<?> validation(@RequestBody @Valid MauSacRequest request, BindingResult result) {
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
    public ResponseEntity<?> add(@RequestBody MauSacRequest request) {
        HashMap<String, Object> map = mauSacService.add(request);
        return ResponseEntity.ok(map);
    }

    // sửa
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody MauSacRequest request) {
        HashMap<String, Object> map = mauSacService.update(request, id);
        return ResponseEntity.ok(map);
    }

    // xóa (đổi trạng thái về 0)
    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, @RequestBody MauSacRequest request) {
        HashMap<String, Object> map = mauSacService.delete(request, id);
        return ResponseEntity.ok(map);
    }

    // thêm bằng file excel
    @PostMapping("/upload")
    public ResponseEntity<?> uploadCustomersData(@RequestParam("file") MultipartFile file) {
        this.mauSacService.saveExcel(file);
        HashMap<String, Object> map = DataUltil.setData("success", " thêm size thành công");
        return ResponseEntity.ok(map);
    }

}
