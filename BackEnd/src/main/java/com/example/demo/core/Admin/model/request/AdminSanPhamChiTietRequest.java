package com.example.demo.core.Admin.model.request;

import com.example.demo.infrastructure.adapter.DtoToEntity;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.entity.TrongLuong;
import com.example.demo.entity.VatLieu;
import com.example.demo.util.DatetimeUtil;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AdminSanPhamChiTietRequest implements DtoToEntity<SanPhamChiTiet> {

    @NotBlank(message = "không bỏ trống tên")
    private String ten;

    @NotBlank(message = "không bỏ trống đệm lót")
    private String demLot;

    @NotBlank(message = "không bỏ trống quai đeo")
    private String quaiDeo;

    private Integer thuongHieu;

    private Integer loai;

    @NotBlank(message = "Vui lòng chọn trạng thái")
    private String trangThai;

    @NotBlank(message = "Vui lòng nhập mô tả")
    private String moTa;

    @NotBlank(message = "Vui lòng nhập ảnh")
    private String anh;

    @Positive(message = "Sai định dạng")
    @Min(value = 1, message = "số lượng phải lớn hơn hoặc bằng 1")
    @Max(value = 1000, message = "Số lượng quá lớn")
    private Integer soLuongTon;

    @Positive(message = "Sai định dạng")
    @Min(value = 50000, message = "giá phải lớn hơn hoặc bằng 50 nghìn")
    @Max(value = 10000000, message = "Giá bán không lớn hơn 10 triệu")
    private Integer giaBan;

    @Positive(message = "Số tiền là số nguyên lớn hơn 0")
    @Max(value = 999999999, message = "Số tiền quá lớn")
    private Integer giaNhap;

    private Integer khuyenMai;

    @Positive(message = "Không bỏ trống vật liệu")
    private Integer vatLieu;

    @Positive(message = "Không bỏ trống trọng lượng")
    private Integer trongLuong;

    private List<String> soLuongSize;

    private List<String> idMauSac;

    private List<String> idSize;

    private List<String> imgMauSac;

    private List<String> imagesProduct;

    @Override
    public SanPhamChiTiet dtoToEntity(SanPhamChiTiet sanPhamChiTiet) {
        sanPhamChiTiet.setNgayTao(DatetimeUtil.getCurrentDate());
        sanPhamChiTiet.setVatLieu(VatLieu.builder().id(Integer.valueOf(this.getVatLieu())).build());
        sanPhamChiTiet.setTrongLuong(TrongLuong.builder().id(this.getTrongLuong()).build());
        sanPhamChiTiet.setTrangThai(Integer.valueOf(this.getTrangThai()));
        sanPhamChiTiet.setSoLuongTon(Integer.valueOf(this.getSoLuongTon()));
        sanPhamChiTiet.setGiaBan(BigDecimal.valueOf(Long.valueOf(this.getGiaBan())));
        sanPhamChiTiet.setGiaNhap(BigDecimal.valueOf(Long.valueOf(this.getGiaNhap())));
        return sanPhamChiTiet;
    }
}
