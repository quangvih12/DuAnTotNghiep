package com.example.demo.core.Admin.service.impl.ThongKe;

import com.example.demo.core.Admin.model.response.*;
import com.example.demo.core.Admin.repository.AdThongKeResponsitory;
import com.example.demo.core.Admin.service.AdThongKeService.AdThongKeDoanhThuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminThongKeDoanhThuServiceImpl implements AdThongKeDoanhThuService {

    @Autowired
    private AdThongKeResponsitory adThongKeResponsitory;

    @Override
    public AdminThongKeBO getAll() {

        // Lấy dữ liệu từ cơ sở dữ liệu
        Integer tongDoanhThu = adThongKeResponsitory.tongDoanhThu();
        List<AdminThongKeSanPhamCaoResponse> lstSanPhamDoanhThuCao = adThongKeResponsitory.sanPhamDoanhThuCaoNhat();
        List<AdminThongKeSanPhamThapResponse> lstSanPhamDoanhThuThap = adThongKeResponsitory.sanPhamDoanhThuThapNhat();
        List<AdminThongKeLoaiResponse> lstLoai = adThongKeResponsitory.doanhThuTheoLoai();
        List<AdminThongKeThuongHieuResponse> lstThuongHieu = adThongKeResponsitory.doanhThuTheoThuongHieu();
        List<AdminThongKeThangResponse> lstThang = adThongKeResponsitory.doanhThuTheoThang();

        // Tạo đối tượng AdminThongKeBO bằng constructor
        AdminThongKeBO adminThongKeBO = new AdminThongKeBO(
                tongDoanhThu, lstLoai, lstSanPhamDoanhThuCao, lstSanPhamDoanhThuThap, lstThuongHieu, lstThang
        );

        return adminThongKeBO;
    }

    @Override
    public AdminThongKeBO getAllBySanPham(Integer id) {
        // Lấy dữ liệu từ cơ sở dữ liệu
        Integer tongDoanhThu = adThongKeResponsitory.tongDoanhThuBySanPham(id);
        List<AdminThongKeSanPhamCaoResponse> lstSanPhamDoanhThuCao = adThongKeResponsitory.sanPhamDoanhThuCaoNhatBySanPham(id);
        List<AdminThongKeSanPhamThapResponse> lstSanPhamDoanhThuThap = adThongKeResponsitory.sanPhamDoanhThuThapNhatBySanPham(id);
        List<AdminThongKeLoaiResponse> lstLoai = adThongKeResponsitory.doanhThuTheoLoaiBySanPham(id);
        List<AdminThongKeThuongHieuResponse> lstThuongHieu = adThongKeResponsitory.doanhThuTheoThuongHieuBySanPham(id);
        List<AdminThongKeThangResponse> lstThang = adThongKeResponsitory.doanhThuTheoThangBySanPham(id);

        // Tạo đối tượng AdminThongKeBO bằng constructor
        AdminThongKeBO adminThongKeBO = new AdminThongKeBO(
                tongDoanhThu, lstLoai, lstSanPhamDoanhThuCao, lstSanPhamDoanhThuThap, lstThuongHieu, lstThang
        );
        return adminThongKeBO;
    }

    @Override
    public AdminThongKeBO getAllByThuongHieu(Integer id) {
        // Lấy dữ liệu từ cơ sở dữ liệu
        Integer tongDoanhThu = adThongKeResponsitory.tongDoanhThuByThuongHieu(id);
        List<AdminThongKeSanPhamCaoResponse> lstSanPhamDoanhThuCao = adThongKeResponsitory.sanPhamDoanhThuCaoNhatByThuongHieu(id);
        List<AdminThongKeSanPhamThapResponse> lstSanPhamDoanhThuThap = adThongKeResponsitory.sanPhamDoanhThuThapNhatByThuongHieu(id);
        List<AdminThongKeLoaiResponse> lstLoai = adThongKeResponsitory.doanhThuTheoLoaiByThuongHieu(id);
        List<AdminThongKeThuongHieuResponse> lstThuongHieu = adThongKeResponsitory.doanhThuTheoThuongHieuByThuongHieu(id);
        List<AdminThongKeThangResponse> lstThang = adThongKeResponsitory.doanhThuTheoThangByThuongHieu(id);

        // Tạo đối tượng AdminThongKeBO bằng constructor
        AdminThongKeBO adminThongKeBO = new AdminThongKeBO(
                tongDoanhThu, lstLoai, lstSanPhamDoanhThuCao, lstSanPhamDoanhThuThap, lstThuongHieu, lstThang
        );
        return adminThongKeBO;
    }

    @Override
    public AdminThongKeBO getAllByLoai(Integer id) {
        // Lấy dữ liệu từ cơ sở dữ liệu
        Integer tongDoanhThu = adThongKeResponsitory.tongDoanhThuByLoai(id);
        List<AdminThongKeSanPhamCaoResponse> lstSanPhamDoanhThuCao = adThongKeResponsitory.sanPhamDoanhThuCaoNhatByLoai(id);
        List<AdminThongKeSanPhamThapResponse> lstSanPhamDoanhThuThap = adThongKeResponsitory.sanPhamDoanhThuThapNhatByLoai(id);
        List<AdminThongKeLoaiResponse> lstLoai = adThongKeResponsitory.doanhThuTheoLoaiByLoai(id);
        List<AdminThongKeThuongHieuResponse> lstThuongHieu = adThongKeResponsitory.doanhThuTheoThuongHieuByLoai(id);
        List<AdminThongKeThangResponse> lstThang = adThongKeResponsitory.doanhThuTheoThangByLoai(id);

        // Tạo đối tượng AdminThongKeBO bằng constructor
        AdminThongKeBO adminThongKeBO = new AdminThongKeBO(
                tongDoanhThu, lstLoai, lstSanPhamDoanhThuCao, lstSanPhamDoanhThuThap, lstThuongHieu, lstThang
        );
        return adminThongKeBO;
    }

    @Override
    public AdminThongKeBO getAllByYear(String year) {
        // Lấy dữ liệu từ cơ sở dữ liệu
        Integer tongDoanhThu = adThongKeResponsitory.tongDoanhThuByNam(year);
        List<AdminThongKeSanPhamCaoResponse> lstSanPhamDoanhThuCao = adThongKeResponsitory.sanPhamDoanhThuCaoNhatByNam(year);
        List<AdminThongKeSanPhamThapResponse> lstSanPhamDoanhThuThap = adThongKeResponsitory.sanPhamDoanhThuThapNhatByNam(year);
        List<AdminThongKeLoaiResponse> lstLoai = adThongKeResponsitory.doanhThuTheoLoaiByNam(year);
        List<AdminThongKeThuongHieuResponse> lstThuongHieu = adThongKeResponsitory.doanhThuTheoThuongHieuByNam(year);
        List<AdminThongKeThangResponse> lstThang = adThongKeResponsitory.doanhThuTheoThangByNam(year);

        // Tạo đối tượng AdminThongKeBO bằng constructor
        AdminThongKeBO adminThongKeBO = new AdminThongKeBO(
                tongDoanhThu, lstLoai, lstSanPhamDoanhThuCao, lstSanPhamDoanhThuThap, lstThuongHieu, lstThang
        );
        return adminThongKeBO;
    }

    @Override
    public AdminThongKeBO getAllByMonth(Integer startMonth, Integer endMonth) {
        // Lấy dữ liệu từ cơ sở dữ liệu
        Integer tongDoanhThu = adThongKeResponsitory.tongDoanhThuTheoThang(startMonth, endMonth);
        List<AdminThongKeSanPhamCaoResponse> lstSanPhamDoanhThuCao = adThongKeResponsitory.sanPhamDoanhThuCaoNhatByThang(startMonth, endMonth);
        List<AdminThongKeSanPhamThapResponse> lstSanPhamDoanhThuThap = adThongKeResponsitory.sanPhamDoanhThuThapNhatByThang(startMonth, endMonth);
        List<AdminThongKeLoaiResponse> lstLoai = adThongKeResponsitory.doanhThuTheoLoaiByThang(startMonth, endMonth);
        List<AdminThongKeThuongHieuResponse> lstThuongHieu = adThongKeResponsitory.doanhThuTheoThuongHieuByThang(startMonth, endMonth);
        List<AdminThongKeThangResponse> lstThang = adThongKeResponsitory.doanhThuTheoThangByThang(startMonth, endMonth);
//
//        // Tạo đối tượng AdminThongKeBO bằng constructor
        AdminThongKeBO adminThongKeBO = new AdminThongKeBO(
                tongDoanhThu, lstLoai, lstSanPhamDoanhThuCao, lstSanPhamDoanhThuThap, lstThuongHieu, lstThang
        );
        return adminThongKeBO;
    }

}
