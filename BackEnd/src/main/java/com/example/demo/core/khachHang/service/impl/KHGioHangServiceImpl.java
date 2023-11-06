package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.core.khachHang.model.request.GioHangCTRequest;
import com.example.demo.core.khachHang.repository.ChiTietSanPhamRepository;
import com.example.demo.core.khachHang.repository.KHGioHangChiTietRepository;
import com.example.demo.core.khachHang.repository.KHGioHangRepository;
import com.example.demo.core.khachHang.service.KHGiohangService;
import com.example.demo.core.token.service.TokenService;
import com.example.demo.entity.GioHang;
import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.entity.User;
import com.example.demo.util.DataUltil;
import com.example.demo.util.DatetimeUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class KHGioHangServiceImpl implements KHGiohangService {
    @Autowired
    KHGioHangChiTietRepository gioHangCTRespon;

    @Autowired
    ChiTietSanPhamRepository productReponstory;

    @Autowired
    KHGioHangRepository giohangRepo;

    @Autowired
    TokenService tokenService;

    @Autowired
    AdUserRepository userRepository;


    public HashMap<String, Object> addCart(GioHangCTRequest ghct, String token) {
        Integer idKh;
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);
        idKh = user.getId();
        GioHangChiTiet map = this.addCartDataBase(ghct,idKh);
        return DataUltil.setData("success", map);
    }

    public GioHangChiTiet addCartDataBase(GioHangCTRequest ghct,Integer idKh) {

        SanPhamChiTiet sanPhamCT = productReponstory.findSanPhamChiTietsById(ghct.getSanPhamChiTiet());
        if (ghct.getSoLuong() > sanPhamCT.getSoLuongTon()) {
//            HashMap<String, Object> map = DataUltil.setData("error", "số lượng sản phẩm không đủ");
//            return map;
            return  null;
        }
        if (ghct.getSoLuong() <= 0) {
            return null;
        }
        User kh = User.builder().id(idKh).build();

        // tìm GHCT theo id user, id ctsp
        List<GioHangChiTiet> list = gioHangCTRespon.findById(kh.getId(), sanPhamCT.getId());

        if(list.size() == 0){
            createNewCart(kh, sanPhamCT, ghct);
        }else{
            boolean createNewCartDetail = false;
            boolean isExistedCartDetail = false;

            for (GioHangChiTiet gioHangChiTiet : list) {

                if (gioHangChiTiet.getTenMauSac().equals(ghct.getTenMauSac()) && gioHangChiTiet.getTenSize().equals(ghct.getTenSize())) {
                    gioHangChiTiet.setSoLuong(ghct.getSoLuong() + gioHangChiTiet.getSoLuong());
                    gioHangChiTiet.setNgaySua(DatetimeUtil.getCurrentDate());
                    gioHangCTRespon.save(gioHangChiTiet);
                    createNewCartDetail = true;
                    break;
                }
//                } else if (!gioHangChiTiet.getTenMauSac().equals(ghct.getTenMauSac()) || !gioHangChiTiet.getTenSize().equals(ghct.getTenSize())) {
//                    createNewCart(kh, sanPhamCT, ghct);
//                    createNewCartDetail = true;
//                    break;
//                }
            }

            if (!createNewCartDetail) {
                // Nếu chưa tạo giỏ hàng chi tiết mới trong vòng lặp, thực hiện tạo ở đây
                createNewCart(kh, sanPhamCT, ghct);
            }
        }
        return null;
    }

    public GioHangChiTiet createNewCart(User kh,SanPhamChiTiet sanPhamCT,GioHangCTRequest  ghct){
        GioHang _gioHang = GioHang.builder().user(kh).build();
        GioHang gh = giohangRepo.save(_gioHang);
        gh.setMa("GH" + gh.getId());
        gh.setNgayTao(DatetimeUtil.getCurrentDate());

        // thêm mới vào GHCT
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;
        GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();

        if(sanPhamCT.getGiaSauGiam() == null){
            gioHangChiTiet.setDonGia(sanPhamCT.getGiaBan());
        }else {
            gioHangChiTiet.setDonGia(sanPhamCT.getGiaSauGiam());
        }

        gioHangChiTiet.setMa("GHCT"+ randomNumber);
        gioHangChiTiet.setSoLuong(ghct.getSoLuong());
        gioHangChiTiet.setTenSize(ghct.getTenSize());
        gioHangChiTiet.setTenMauSac(ghct.getTenMauSac());
        gioHangChiTiet.setGioHang(gh);
        gioHangChiTiet.setSanPhamChiTiet(sanPhamCT);
        gioHangChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());

        gioHangCTRespon.save(gioHangChiTiet);
        HashMap<String, Object> map = DataUltil.setData("success", gioHangCTRespon.save(gioHangChiTiet));
        return gioHangCTRespon.save(gioHangChiTiet);

    }

    @Override
    public List<?> getAll(HttpSession httpSession) {
        return null;
    }

    @Override
    public ResponseEntity<HttpStatus> deleteGioHangCT(Integer id) {
        try {
            GioHangChiTiet gioHangChiTiet = gioHangCTRespon.findById(id).get();
            gioHangCTRespon.deleteById(gioHangChiTiet.getId());
            giohangRepo.deleteById(gioHangChiTiet.getGioHang().getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> deleteAllGioHangCT() {
        try {
            gioHangCTRespon.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public HashMap<String, Object> updateCongSoLuong(Integer id) {
        Optional<GioHangChiTiet> tutorialData = gioHangCTRespon.findById(id);

        if (tutorialData.isPresent()) {
            GioHangChiTiet _gioHangChiTiet = tutorialData.get();

            _gioHangChiTiet.setSoLuong(_gioHangChiTiet.getSoLuong() + 1);
            gioHangCTRespon.save(_gioHangChiTiet);
            HashMap<String, Object> map = DataUltil.setData("warning", gioHangCTRespon.save(_gioHangChiTiet));
            return map;
        } else {
            HashMap<String, Object> map = DataUltil.setData("warning", "lỗi");
            return map;
        }
    }

    @Override
    public HashMap<String, Object> updateTruSoLuong(Integer id) {
        Optional<GioHangChiTiet> tutorialData = gioHangCTRespon.findById(id);

        if (tutorialData.isPresent()) {
            GioHangChiTiet _gioHangChiTiet = tutorialData.get();
            _gioHangChiTiet.setSoLuong(_gioHangChiTiet.getSoLuong() - 1);
            if (_gioHangChiTiet.getSoLuong() <= 0) {
                this.deleteGioHangCT(id);
                HashMap<String, Object> map = DataUltil.setData("warning", "sản phẩm không được nhỏ bằng 0");
                return map;
            } else {
                gioHangCTRespon.save(_gioHangChiTiet);
                HashMap<String, Object> map = DataUltil.setData("warning", gioHangCTRespon.save(_gioHangChiTiet));
                return map;
            }

        } else {
            HashMap<String, Object> map = DataUltil.setData("warning", "lỗi");
            return map;
        }

    }

    @Override
    public HashMap<String, Object> updateMauSacSize(Integer idghct, Integer idMauSacCT, Integer idSizeCT) {
        Optional<GioHangChiTiet> ghct = gioHangCTRespon.findById(idghct);

        if (ghct.isPresent()) {
            GioHangChiTiet _gioHangChiTiet = ghct.get();
            _gioHangChiTiet.setTenMauSac(idMauSacCT);
            _gioHangChiTiet.setTenSize(idSizeCT);

            gioHangCTRespon.save(_gioHangChiTiet);
            HashMap<String, Object> map = DataUltil.setData("warning", gioHangCTRespon.save(_gioHangChiTiet));
            return map;


        } else {
            HashMap<String, Object> map = DataUltil.setData("warning", "lỗi");
            return map;
        }
    }

}
