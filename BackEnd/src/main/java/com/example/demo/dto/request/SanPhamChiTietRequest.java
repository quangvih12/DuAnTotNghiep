package com.example.demo.dto.request;

import com.example.demo.adapter.DtoToEntity;
import com.example.demo.entity.KhuyenMai;
import com.example.demo.entity.SanPham;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.entity.TrongLuong;
import com.example.demo.entity.VatLieu;
import com.example.demo.util.DatetimeUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SanPhamChiTietRequest implements DtoToEntity<SanPhamChiTiet> {

    private Integer id;


    private String ngaySua;


    private String ngayTao;

    @NotBlank(message = "Vui lòng chọn trạng thái")
    private String trangThai;

    @Positive(message = "Số lượng là số nguyên lớn hơn 0")
    @Size(max = 5, message = "Số lượng quá lớn")
    private String soLuongTon;

    @NotBlank(message = "Không bỏ trống gia bán")
    @Positive(message = "Số tiền là số nguyên lớn hơn 0")
    @Size(max = 10, message = "Số tiền quá lớn")
    private String giaBan;

    @NotBlank(message = "Không bỏ trống gia bán")
    @Positive(message = "Số tiền là số nguyên lớn hơn 0")
    @Size(max = 10, message = "Số tiền quá lớn")
    private String giaNhap;

    @NotBlank(message = "Không bỏ trống sản phẩm")
    private String sanPham;

    private String khuyenMai;

    @NotBlank(message = "Không bỏ trống vật liệu")
    private String vatLieu;

    @NotBlank(message = "Không bỏ trống trọng lượng")
    private String trongLuong;

    private String moTaMauSacChiTiet;

    @NotBlank(message = "Không bỏ trống số lượng size")
    private String soLuongSize;

    @NotBlank(message = "Không bỏ trống Màu sắc")
    private List<String> idMauSac;

    @NotBlank(message = "Không bỏ trống Size")
    private List<String> idSize;

    private List<String> imgMauSac;

    private List<String> images;

    @Override
    public SanPhamChiTiet dtoToEntity(SanPhamChiTiet sanPhamChiTiet) {
        sanPhamChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
        sanPhamChiTiet.setSanPham(SanPham.builder().id(Integer.valueOf(this.sanPham)).build());
        sanPhamChiTiet.setVatLieu(VatLieu.builder().id(Integer.valueOf(this.vatLieu)).build());
        sanPhamChiTiet.setTrongLuong(TrongLuong.builder().id(Integer.parseInt(this.trongLuong)).build());
        sanPhamChiTiet.setTrangThai(Integer.valueOf(this.trangThai));
        sanPhamChiTiet.setSoLuongTon(Integer.valueOf(this.soLuongTon));
        sanPhamChiTiet.setGiaBan(BigDecimal.valueOf(Long.valueOf(this.giaBan)));
        sanPhamChiTiet.setGiaNhap(BigDecimal.valueOf(Long.valueOf(this.giaNhap)));
        return sanPhamChiTiet;
    }
}
