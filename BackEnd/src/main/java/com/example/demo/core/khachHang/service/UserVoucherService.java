package com.example.demo.core.khachHang.service;

import com.example.demo.core.khachHang.model.response.VoucherResponse;
import com.example.demo.entity.UserVoucher;

public interface UserVoucherService {

    UserVoucher getVoucherByUser(Integer userId, Integer voucherId);
}
