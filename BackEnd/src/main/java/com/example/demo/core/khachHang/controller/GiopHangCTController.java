package com.example.demo.core.khachHang.controller;

import com.example.demo.core.khachHang.model.request.GioHangCTRequest;
import com.example.demo.core.khachHang.model.response.GioHangCTResponse;
import com.example.demo.core.khachHang.repository.KHGioHangChiTietRepository;
import com.example.demo.core.khachHang.repository.KHMauSacCTRepository;
import com.example.demo.core.khachHang.repository.KHMauSacRepository;
import com.example.demo.core.khachHang.repository.KHSizeCTRepository;
import com.example.demo.core.khachHang.service.impl.KHGioHangServiceImpl;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.MauSacChiTiet;
import com.example.demo.entity.SizeChiTiet;
import com.microsoft.azure.storage.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/khach-hang/giohang")
public class GiopHangCTController {


    @Autowired
    KHGioHangServiceImpl khGioHangService;

    @Autowired
    KHGioHangChiTietRepository khGHCTRespon;

    @Autowired
    KHMauSacCTRepository msctRepo;

    @Autowired
    KHSizeCTRepository sizectRepo;

    @Autowired
    KHMauSacRepository msRepo;

    @PostMapping("/addGiohang")
    public ResponseEntity<?> addGiohang(@RequestBody GioHangCTRequest ghctrequest, @RequestParam("token") String token
    ) throws URISyntaxException, StorageException, InvalidKeyException, IOException {

        HashMap<String, Object> map = khGioHangService.addCart(ghctrequest,token);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{idghct}")
    public GioHangCTResponse getGHCT(@PathVariable("idghct") Integer idghct){

        return khGHCTRespon.getGHCTByID(idghct);
    }

    @GetMapping("/msct/{idmsct}")
    public MauSacChiTiet getMsctByID(@PathVariable("idmsct") Integer idmsct){

        return msctRepo.findAllById(idmsct);
    }

    @GetMapping("/sizect/{idsizect}")
    public SizeChiTiet getSizeById(@PathVariable("idsizect") Integer idsizect){

        return sizectRepo.findAllById(idsizect);
    }

    @GetMapping("/getListGioHang")
    public List<GioHangCTResponse> getList(){
        List<GioHangCTResponse> list = khGHCTRespon.getListGHCT();
        return  list;
    }

    @PostMapping("/congSL/{idghct}")
    public ResponseEntity<?>  updateCongGHCT(@PathVariable("idghct") Integer idghct){

        HashMap<String, Object> map = khGioHangService.updateCongSoLuong(idghct);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/truSL/{idghct}")
    public ResponseEntity<?>  updateTruGHCT(@PathVariable("idghct") Integer idghct){

        HashMap<String, Object> map = khGioHangService.updateTruSoLuong(idghct);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/updateMauSacSize/{idghct}")
    public ResponseEntity<?>  updateMauSacSize(@PathVariable("idghct") Integer idghct, @RequestParam("tenMauSac") Integer tenMauSac, @RequestParam("tenSize") Integer tenSize ){

        HashMap<String, Object> map = khGioHangService.updateMauSacSize(idghct, tenMauSac, tenSize);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{idghct}")
    public ResponseEntity<?> deleteGHCT(@PathVariable(value = "idghct") Integer idghct){
        khGioHangService.deleteGioHangCT(idghct);
        return new ResponseEntity<>("Xoá thành công", HttpStatus.OK);
    }

    // dùng để lấy ảnh màu sắc chi tiết
    @GetMapping("/msct")
    public List<MauSacChiTiet> getListMSCT(){
        List<MauSacChiTiet> listMSCT = msctRepo.findAll();
        return  listMSCT;
    }

    // dùng để lấy tên màu sắc trong giỏ hàng
    @GetMapping("/ms")
    public List<MauSac> getListMS(){
        List<MauSac> listMSCT = msRepo.findAll();
        return  listMSCT;
    }

    @GetMapping("/sizect")
    public List<SizeChiTiet> getListsizeCT(){
        List<SizeChiTiet> listSizeCT = sizectRepo.findAll();
        return  listSizeCT;
    }

    @GetMapping("/countGHCT")
    public Integer countGHCTByUser(@RequestParam("userId") Integer userId){
        return khGHCTRespon.countGHCTByUser(userId);
    }

}

