package com.example.demo.core.Admin.repository;

import com.example.demo.entity.Size;
import com.example.demo.reponsitory.SizeReponsitory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdSizeReponsitory extends SizeReponsitory {

    List<Size> getAllByTrangThai(Integer trangThai, Sort sort);

}
