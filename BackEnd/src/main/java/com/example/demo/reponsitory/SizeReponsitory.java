package com.example.demo.reponsitory;

import com.example.demo.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeReponsitory extends JpaRepository<Size, Integer> {

}
