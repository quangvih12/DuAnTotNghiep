package com.example.demo.core.Admin.controller;


import com.example.demo.core.Admin.model.request.UserVoucherRequest;
import com.example.demo.core.Admin.service.AdUserService;
import com.example.demo.core.Admin.service.AdUserVoucherService;
import com.example.demo.entity.UserVoucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin/user-voucher")
public class UserVoucherController {

    @Autowired
    private AdUserVoucherService userVoucherService;

    @PostMapping("/add")
    public void addUserVoucher(@RequestBody UserVoucherRequest userVoucher) {
        userVoucherService.addUserVoucher(userVoucher.getIdVoucher(), userVoucher.getIdUser());
    }

}
