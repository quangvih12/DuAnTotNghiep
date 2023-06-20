package com.example.demo.api.Admin;

import com.example.demo.entity.Loai;
import com.example.demo.dto.request.LoaiRequest;
import com.example.demo.sevice.Impl.Admin.LoaiServiceImpl;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/loai")
public class LoaiApi {

    @Autowired
    private LoaiServiceImpl loaiService;

    // getAll loai
    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", value = "pages") Integer pages) {
        Page<Loai> page = loaiService.getAll(pages);
        HashMap<String, Object> map = DataUltil.setData("ok", page);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(defaultValue = "0", value = "pages") Integer pages, @RequestParam(required = false) String keyword) {
        Page<Loai> page = loaiService.search(keyword, pages);
        HashMap<String, Object> map = DataUltil.setData("ok", page);
        return ResponseEntity.ok(map);
    }

    // check validate
    @PostMapping("/validation")
    public ResponseEntity<?> validation(@RequestBody @Valid LoaiRequest request, BindingResult result) {
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
    public ResponseEntity<?> add(@RequestBody LoaiRequest request) {
        HashMap<String, Object> map = loaiService.add(request);
        return ResponseEntity.ok(map);
    }

    // sửa
    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody LoaiRequest request) {
        HashMap<String, Object> map = loaiService.update(request, id);
        return ResponseEntity.ok(map);
    }

    // xóa (đổi trạng thái về 0)
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, @RequestBody LoaiRequest request) {
        HashMap<String, Object> map = loaiService.delete(request, id);
        return ResponseEntity.ok(map);
    }

}
