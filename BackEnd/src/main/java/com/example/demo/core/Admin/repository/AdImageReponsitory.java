package com.example.demo.core.Admin.repository;

import com.example.demo.entity.Image;
import com.example.demo.reponsitory.ImageReponsitory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AdImageReponsitory extends ImageReponsitory {

    @Query("select im from  Image im where im.sanPhamChiTiet.id =:id")
    List<Image> findBySanPhamId(Integer id);

    @Query("select im from  Image im where im.sanPhamChiTiet.id =:id")
    List<Image> findBySanPhamIds(Integer id);

    @Query("select im from  Image im where im.sanPhamChiTiet.id =:id and im.anh=:anh")
    Image findBySanPhamIdAndAnh(Integer id,String anh);
}
