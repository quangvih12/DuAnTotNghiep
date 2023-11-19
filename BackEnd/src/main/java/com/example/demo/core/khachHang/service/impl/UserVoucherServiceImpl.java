package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.khachHang.repository.VoucherUserRepository;
import com.example.demo.core.khachHang.service.UserVoucherService;
import com.example.demo.entity.UserVoucher;
import com.example.demo.reponsitory.UserVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserVoucherServiceImpl implements UserVoucherService {

    @Autowired
    VoucherUserRepository voucherUserRepo;

    @Override
    public UserVoucher getVoucherByUser(Integer userId, Integer voucherId) {
        return voucherUserRepo.getVoucherByUser(userId, voucherId);
    }
}
