package com.example.demo.core.Admin.model.request;

import com.example.demo.infrastructure.adapter.DtoToEntity;
import com.example.demo.entity.Image;
import com.example.demo.util.DatetimeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminImageRequest implements DtoToEntity<Image> {

    private Integer id;

//    @NotBlank(message = "không bỏ trống ảnh")
    private MultipartFile[] file;

    private String ma;

    private String ngaySua;

    private String ngayTao;

    private String trangThai;

    private String sanPhamChiTiet;

    @Override
    public Image dtoToEntity(Image image) {
        image.setTrangThai(Integer.valueOf(this.trangThai));
        image.setNgayTao(DatetimeUtil.getCurrentDate());
        return image;
    }
}
