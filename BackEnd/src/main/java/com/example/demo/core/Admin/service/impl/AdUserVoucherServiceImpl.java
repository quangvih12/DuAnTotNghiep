package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.repository.AdUserVoucherRepository;
import com.example.demo.core.Admin.service.AdUserVoucherService;
import com.example.demo.entity.User;
import com.example.demo.entity.UserVoucher;
import com.example.demo.entity.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdUserVoucherServiceImpl implements AdUserVoucherService {

    @Autowired
    private AdUserVoucherRepository userVoucherRepo;

    @Override
    public void addUserVoucher(Integer idVoucher, Integer idUser) {
        User u = User.builder().id(idUser).build();
        Voucher voucher = Voucher.builder().id(idVoucher).build();
        UserVoucher userVoucher = UserVoucher.builder().user(u).voucher(voucher).trangThai(0).build();
        userVoucherRepo.save(userVoucher);
    }
}
