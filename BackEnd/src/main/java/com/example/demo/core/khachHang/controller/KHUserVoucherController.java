package com.example.demo.core.khachHang.controller;

import com.example.demo.core.khachHang.model.response.VoucherResponse;
import com.example.demo.core.khachHang.service.UserVoucherService;
import com.example.demo.entity.UserVoucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/khach-hang/user-voucher")
public class KHUserVoucherController {

    @Autowired
    UserVoucherService userVoucherService;
    @GetMapping()
    public UserVoucher getVoucherByUser(@RequestParam Integer userid, @RequestParam Integer voucherId){
        return userVoucherService.getVoucherByUser(userid, voucherId);
    }
}
