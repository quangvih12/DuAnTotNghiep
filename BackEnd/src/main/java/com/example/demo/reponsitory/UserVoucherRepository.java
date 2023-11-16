package com.example.demo.reponsitory;

import com.example.demo.entity.UserVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVoucherRepository extends JpaRepository<UserVoucher, Integer> {
}
