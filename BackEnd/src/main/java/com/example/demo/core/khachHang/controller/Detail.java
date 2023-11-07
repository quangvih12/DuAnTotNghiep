package com.example.demo.core.khachHang.controller;

import com.example.demo.core.Admin.model.response.AdminSanPhamChiTietResponse;
import com.example.demo.core.khachHang.model.response.DetailSanPhamResponse;
import com.example.demo.core.khachHang.model.response.MauSacResponse;
import com.example.demo.core.khachHang.repository.DetailSPCTTRepository;
import com.example.demo.core.khachHang.service.KHDetailService.DetailSizeService;
import com.example.demo.core.khachHang.service.KHDetailService.ImageServie;
import com.example.demo.core.khachHang.service.KHDetailService.DetaiService;
import com.example.demo.core.khachHang.service.KHDetailService.DetailMauSacService;
import com.example.demo.entity.SanPhamChiTiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/khach-hang/detail")
@CrossOrigin(origins = {"*"})
public class Detail {

    @Autowired
    private DetaiService detaiService;

    @Autowired
    private DetailMauSacService mauSacService;

    @Autowired
    private DetailSizeService sizeService;

    @Autowired
    private ImageServie servie;

    @Autowired
    DetailSPCTTRepository detailRepo;

//    @Autowired
//    KHSizeCTRepository sizeCTRepo;
//
//    @Autowired
//    KHMauSacCTRepository mausacCTRepo;


    @GetMapping("/{idctsp}")
    public DetailSanPhamResponse getDetailCTSP(@PathVariable("idctsp") Integer idctsp) {
        DetailSanPhamResponse sanPhamChiTiet = detailRepo.getDetailCTSP(idctsp);
        return sanPhamChiTiet;
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        AdminSanPhamChiTietResponse sanPhamChiTiet = detaiService.get(id);
        return ResponseEntity.ok(sanPhamChiTiet);
    }

//    @GetMapping("/findByMauSac/{idctsp}")
//    public List<MauSacResponse> listMauSacCT(@PathVariable("idctsp") Integer idctsp){
//        List<MauSacResponse> lisst =  mausacCTRepo.listMsctByIdctsp(idctsp);
//        Set< Integer> set = new HashSet<>();
//        Iterator<MauSacResponse> iterator = lisst.listIterator();
//        while(iterator.hasNext()){
//            MauSacResponse ms = iterator.next();
//            Integer idMauSac = ms.getIdMS();
//            if(!set.add(idMauSac)){
//                iterator.remove();
//            }
//        }
//        return lisst;
//    };
//
//    @GetMapping("/findBySize/{idctsp}")
//    public ResponseEntity<?> findBySize(@PathVariable Integer idctsp){
//        return ResponseEntity.ok(sizeCTRepo.listSizectByIdctsp(idctsp));
//    }

    @GetMapping("/findByImage/{id}")
    public ResponseEntity<?> findByImage(@PathVariable Integer id){
        return ResponseEntity.ok(servie.findByIdCTSP(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getList() {
        List<SanPhamChiTiet> lisst = detaiService.getAlls();
        return ResponseEntity.ok(lisst);
    }

    @GetMapping("/getSLTon/{idctsp}")
    public ResponseEntity<?> getSLTon(@PathVariable("idctsp") Integer idctsp){
        return ResponseEntity.ok(detailRepo.getSLTonTongByIDCT(idctsp));
    }

    @GetMapping("/getSizeByMS/{idctsp}")
    public ResponseEntity<?> gettListSizeByMauSac(@PathVariable("idctsp") Integer idctsp, @RequestParam("idms") Integer idms ){

        return ResponseEntity.ok(detailRepo.getListSizeByMauSac(idctsp, idms));
    }


    @GetMapping("/getMauSacBySize/{idctsp}")
    public ResponseEntity<?> getListMauSacBySize(@PathVariable("idctsp") Integer idctsp, @RequestParam("idsizect") Integer idsizect){

        return ResponseEntity.ok(detailRepo.getListMauSacBySize(idctsp, idsizect));
    }


    @GetMapping("/getSanPhamSelected/{idctsp}")
    public ResponseEntity<?> getSanPhamSelected(@PathVariable("idctsp") Integer idctsp, @RequestParam("idms") Integer idms,@RequestParam("idsizect") Integer idsizect){

        return ResponseEntity.ok(detailRepo.getSanPhamSelected(idctsp, idms,idsizect));
    }
}