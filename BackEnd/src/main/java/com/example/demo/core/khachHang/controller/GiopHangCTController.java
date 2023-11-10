package com.example.demo.core.khachHang.controller;

import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.core.khachHang.model.request.GioHangCTRequest;
import com.example.demo.core.khachHang.model.response.GioHangCTResponse;
import com.example.demo.core.khachHang.model.response.KhVoucherResponse;
import com.example.demo.core.khachHang.repository.KHGioHangChiTietRepository;
import com.example.demo.core.khachHang.service.impl.KHGioHangServiceImpl;
import com.example.demo.core.token.service.TokenService;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.User;
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
    TokenService tokenService;

    @Autowired
    AdUserRepository userRepository;

    @PostMapping("/addGiohang")
    public ResponseEntity<?> addGiohang(@RequestBody GioHangCTRequest ghctrequest, @RequestParam("token") String token
    ) throws URISyntaxException, StorageException, InvalidKeyException, IOException {

        HashMap<String, Object> map = khGioHangService.addCart(ghctrequest, token);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{idghct}")
    public GioHangCTResponse getGHCT(@PathVariable("idghct") Integer idghct, @RequestParam String token) {
        Integer idUser;
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);
        idUser = user.getId();
        return khGHCTRespon.getGHCT(idUser, idghct);
    }


    @GetMapping("/getListGioHang")
    public List<GioHangCTResponse> getList(@RequestParam("token") String token) {
        List<GioHangCTResponse> list = khGioHangService.getListGHCT(token);
        return list;
    }

    @GetMapping("/get-voucher")
    public ResponseEntity<?> getListVoucher(@RequestParam("token") String token) {
        List<KhVoucherResponse> getList = khGioHangService.getListVoucher(token);
        return ResponseEntity.ok(getList);
    }

    @PutMapping("/congSL/{idghct}")
    public ResponseEntity<?> updateCongGHCT(@PathVariable("idghct") Integer idghct, @RequestParam String token) {

        GioHangCTResponse map = khGioHangService.updateCongSoLuong(idghct, token);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/truSL/{idghct}")
    public ResponseEntity<?> updateTruGHCT(@PathVariable("idghct") Integer idghct, @RequestParam String token) {

        GioHangCTResponse map = khGioHangService.updateTruSoLuong(idghct, token);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/updateMauSacSize/{idghct}")
    public ResponseEntity<?> updateMauSacSize(@PathVariable("idghct") Integer idghct, @RequestParam("idSPCT") Integer idSPCT) {
        HashMap<String, Object> map = khGioHangService.updateMauSacSize(idghct, idSPCT);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{idghct}")
    public ResponseEntity<?> deleteGHCT(@PathVariable(value = "idghct") Integer idghct) {
        khGioHangService.deleteGioHangCT(idghct);
        return new ResponseEntity<>("Xoá thành công", HttpStatus.OK);
    }


    @GetMapping("/countGHCT")
    public Integer countGHCTByUser(@RequestParam("userId") Integer userId) {
        return khGHCTRespon.countGHCTByUser(userId);
    }

}

