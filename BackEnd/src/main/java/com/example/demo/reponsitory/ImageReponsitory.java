package com.example.demo.reponsitory;

import com.example.demo.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageReponsitory extends JpaRepository<Image, Integer> {
}
