package com.example.demo.core.Admin.service.impl.ThongKe;

import com.example.demo.core.Admin.model.response.*;
import com.example.demo.core.Admin.repository.AdThongKeResponsitory;
import com.example.demo.core.Admin.service.AdThongKeService.AdThongKeDoanhThuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Year;
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
        List<AdminThongKeThangNamTruocResponse> lstThangNam = adThongKeResponsitory.doanhThuTheoThangNamTruoc();

        // Tạo đối tượng AdminThongKeBO bằng constructor
        AdminThongKeBO adminThongKeBO = new AdminThongKeBO(
                tongDoanhThu, lstLoai, lstSanPhamDoanhThuCao, lstSanPhamDoanhThuThap, lstThuongHieu, lstThang, lstThangNam
        );

        return adminThongKeBO;
    }

    public AdminThongKeBO getAllByPhuongThuc(Integer idPhuongThucThanhToan) {

        // Lấy dữ liệu từ cơ sở dữ liệu
        Integer tongDoanhThu = adThongKeResponsitory.tongDoanhThuByPhuongThuc(idPhuongThucThanhToan);
        List<AdminThongKeSanPhamCaoResponse> lstSanPhamDoanhThuCao = adThongKeResponsitory.sanPhamDoanhThuCaoNhatByPhuongThuc(idPhuongThucThanhToan);
        List<AdminThongKeSanPhamThapResponse> lstSanPhamDoanhThuThap = adThongKeResponsitory.sanPhamDoanhThuThapNhatByPhuongThuc(idPhuongThucThanhToan);
        List<AdminThongKeLoaiResponse> lstLoai = adThongKeResponsitory.doanhThuTheoLoaiByPhuongThuc(idPhuongThucThanhToan);
        List<AdminThongKeThuongHieuResponse> lstThuongHieu = adThongKeResponsitory.doanhThuTheoThuongHieuByPhuongThuc(idPhuongThucThanhToan);
        List<AdminThongKeThangResponse> lstThang = adThongKeResponsitory.doanhThuTheoThangByPhuongThuc(idPhuongThucThanhToan);
        List<AdminThongKeThangNamTruocResponse> lstThangNam = adThongKeResponsitory.doanhThuTheoThangNamTruocByPhuongThuc(idPhuongThucThanhToan);

        // Tạo đối tượng AdminThongKeBO bằng constructor
        AdminThongKeBO adminThongKeBO = new AdminThongKeBO(
                tongDoanhThu, lstLoai, lstSanPhamDoanhThuCao, lstSanPhamDoanhThuThap, lstThuongHieu, lstThang, lstThangNam
        );

        return adminThongKeBO;
    }

    @Override
    public AdminThongKeBO getAllBySanPham(Integer id,String year) {
        // Lấy dữ liệu từ cơ sở dữ liệu
        Integer tongDoanhThu = adThongKeResponsitory.tongDoanhThuBySanPham(id,year);
        List<AdminThongKeSanPhamCaoResponse> lstSanPhamDoanhThuCao = adThongKeResponsitory.sanPhamDoanhThuCaoNhatBySanPham(id,year);
        List<AdminThongKeSanPhamThapResponse> lstSanPhamDoanhThuThap = adThongKeResponsitory.sanPhamDoanhThuThapNhatBySanPham(id,year);
        List<AdminThongKeLoaiResponse> lstLoai = adThongKeResponsitory.doanhThuTheoLoaiBySanPham(id,year);
        List<AdminThongKeThuongHieuResponse> lstThuongHieu = adThongKeResponsitory.doanhThuTheoThuongHieuBySanPham(id,year);
        List<AdminThongKeThangResponse> lstThang = adThongKeResponsitory.doanhThuTheoThangBySanPham(id,year);
        List<AdminThongKeThangNamTruocResponse> lstThangNam = adThongKeResponsitory.doanhThuTheoThangNamTruocBySanPham(id);
        // Tạo đối tượng AdminThongKeBO bằng constructor
        AdminThongKeBO adminThongKeBO = new AdminThongKeBO(
                tongDoanhThu, lstLoai, lstSanPhamDoanhThuCao, lstSanPhamDoanhThuThap, lstThuongHieu, lstThang, lstThangNam
        );
        return adminThongKeBO;
    }

    @Override
    public AdminThongKeBO getAllByThuongHieu(Integer id, String year) {
        // Lấy dữ liệu từ cơ sở dữ liệu
        Integer tongDoanhThu = adThongKeResponsitory.tongDoanhThuByThuongHieu(id,year);
        List<AdminThongKeSanPhamCaoResponse> lstSanPhamDoanhThuCao = adThongKeResponsitory.sanPhamDoanhThuCaoNhatByThuongHieu(id,year);
        List<AdminThongKeSanPhamThapResponse> lstSanPhamDoanhThuThap = adThongKeResponsitory.sanPhamDoanhThuThapNhatByThuongHieu(id,year);
        List<AdminThongKeLoaiResponse> lstLoai = adThongKeResponsitory.doanhThuTheoLoaiByThuongHieu(id,year);
        List<AdminThongKeThuongHieuResponse> lstThuongHieu = adThongKeResponsitory.doanhThuTheoThuongHieuByThuongHieu(id,year);
        List<AdminThongKeThangResponse> lstThang = adThongKeResponsitory.doanhThuTheoThangByThuongHieu(id,year);
        List<AdminThongKeThangNamTruocResponse> lstThangNam = adThongKeResponsitory.doanhThuTheoThangNamTruocByThuongHieu(id);
        // Tạo đối tượng AdminThongKeBO bằng constructor
        AdminThongKeBO adminThongKeBO = new AdminThongKeBO(
                tongDoanhThu, lstLoai, lstSanPhamDoanhThuCao, lstSanPhamDoanhThuThap, lstThuongHieu, lstThang, lstThangNam
        );
        return adminThongKeBO;
    }

    @Override
    public AdminThongKeBO getAllByLoai(Integer id,String year) {
        // Lấy dữ liệu từ cơ sở dữ liệu
        Integer tongDoanhThu = adThongKeResponsitory.tongDoanhThuByLoai(id,year);
        List<AdminThongKeSanPhamCaoResponse> lstSanPhamDoanhThuCao = adThongKeResponsitory.sanPhamDoanhThuCaoNhatByLoai(id,year);
        List<AdminThongKeSanPhamThapResponse> lstSanPhamDoanhThuThap = adThongKeResponsitory.sanPhamDoanhThuThapNhatByLoai(id,year);
        List<AdminThongKeLoaiResponse> lstLoai = adThongKeResponsitory.doanhThuTheoLoaiByLoai(id,year);
        List<AdminThongKeThuongHieuResponse> lstThuongHieu = adThongKeResponsitory.doanhThuTheoThuongHieuByLoai(id,year);
        List<AdminThongKeThangResponse> lstThang = adThongKeResponsitory.doanhThuTheoThangByLoai(id,year);
        List<AdminThongKeThangNamTruocResponse> lstThangNam = adThongKeResponsitory.doanhThuTheoThangNamTruocByLoai(id);
        // Tạo đối tượng AdminThongKeBO bằng constructor
        AdminThongKeBO adminThongKeBO = new AdminThongKeBO(
                tongDoanhThu, lstLoai, lstSanPhamDoanhThuCao, lstSanPhamDoanhThuThap, lstThuongHieu, lstThang, lstThangNam
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
        String currentYear = String.valueOf(Year.now().getValue());
        List<AdminThongKeThangNamTruocResponse> lstThangNam = adThongKeResponsitory.doanhThuTheoThangNamHienTaiByNam(currentYear);
        // Tạo đối tượng AdminThongKeBO bằng constructor
        AdminThongKeBO adminThongKeBO = new AdminThongKeBO(
                tongDoanhThu, lstLoai, lstSanPhamDoanhThuCao, lstSanPhamDoanhThuThap, lstThuongHieu, lstThang, lstThangNam
        );
        return adminThongKeBO;
    }

    @Override
    public AdminThongKeBO getAllByMonth(LocalDateTime startDate, LocalDateTime endDate) {
        // Lấy dữ liệu từ cơ sở dữ liệu
        Integer tongDoanhThu = adThongKeResponsitory.tongDoanhThuTheoThang(String.valueOf(startDate), String.valueOf(endDate));
        List<AdminThongKeSanPhamCaoResponse> lstSanPhamDoanhThuCao = adThongKeResponsitory.sanPhamDoanhThuCaoNhatByThang(String.valueOf(startDate), String.valueOf(endDate));
        List<AdminThongKeSanPhamThapResponse> lstSanPhamDoanhThuThap = adThongKeResponsitory.sanPhamDoanhThuThapNhatByThang(String.valueOf(startDate), String.valueOf(endDate));
        List<AdminThongKeLoaiResponse> lstLoai = adThongKeResponsitory.doanhThuTheoLoaiByThang(String.valueOf(startDate), String.valueOf(endDate));
        List<AdminThongKeThuongHieuResponse> lstThuongHieu = adThongKeResponsitory.doanhThuTheoThuongHieuByThang(String.valueOf(startDate), String.valueOf(endDate));

        String startYear = String.valueOf(startDate.getYear());
        String endYear = String.valueOf(endDate.getYear());
        List<AdminThongKeThangResponse> lstThang = adThongKeResponsitory.doanhThuTheoThangByNam(startYear);
        List<AdminThongKeThangNamTruocResponse> lstThangNam = adThongKeResponsitory.doanhThuTheoThangNamHienTaiByNam(endYear);
//        // Tạo đối tượng AdminThongKeBO bằng constructor
        AdminThongKeBO adminThongKeBO = new AdminThongKeBO(
                tongDoanhThu, lstLoai, lstSanPhamDoanhThuCao, lstSanPhamDoanhThuThap, lstThuongHieu, lstThang, lstThangNam
        );
        return adminThongKeBO;
    }

}
