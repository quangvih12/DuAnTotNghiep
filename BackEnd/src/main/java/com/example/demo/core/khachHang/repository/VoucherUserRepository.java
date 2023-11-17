package com.example.demo.core.khachHang.repository;

import com.example.demo.entity.UserVoucher;
import com.example.demo.reponsitory.UserVoucherRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoucherUserRepository extends UserVoucherRepository {

    @Query("select a from UserVoucher a where a.user.id =:userid and a.voucher.id =:voucherId")
    UserVoucher getVoucherByUser(Integer userid , Integer voucherId);
}
