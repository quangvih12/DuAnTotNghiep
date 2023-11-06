package com.example.demo.core.Admin.service.impl.SanPham;

import com.example.demo.core.Admin.model.request.AdminMauSacChiTietRequest;
import com.example.demo.core.Admin.model.response.AdminMauSacChiTietResponse;
import com.example.demo.core.Admin.repository.AdMauSacChiTietReponsitory;
import com.example.demo.core.Admin.service.AdminMauSacChiTietService;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.MauSacChiTiet;
import com.example.demo.entity.SanPhamChiTiet;
import com.example.demo.entity.SizeChiTiet;
import com.example.demo.reponsitory.MauSacChiTietReponsitory;
import com.example.demo.util.ImageToAzureUtil;
import com.microsoft.azure.storage.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.List;
import java.util.Optional;

@Service
public class AdminMauSacChiTietServiceImpl implements AdminMauSacChiTietService {
    @Autowired
    private AdMauSacChiTietReponsitory adMauSacChiTietReponsitory;

    @Autowired
    private MauSacChiTietReponsitory mauSacChiTietReponsitory;

    @Autowired
    ImageToAzureUtil getImageToAzureUtil;

    @Override
    public AdminMauSacChiTietResponse add(AdminMauSacChiTietRequest request) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
       MauSacChiTiet e = new MauSacChiTiet();
        if(request.getIdSizeChiTiet() == null){
            e.setSizeChiTiet(null);
        }else {
            e.setSizeChiTiet(SizeChiTiet.builder().id(request.getIdSizeChiTiet()).build());
        }
        String linkAnh = getImageToAzureUtil.uploadImageToAzure(request.getAnh());
        e.setAnh(linkAnh);
        e.setSanPhamChiTiet(SanPhamChiTiet.builder().id(request.getIdSanPhamChiTiet()).build());
        e.setMauSac(MauSac.builder().id(request.getIdMauSac()).build());
        e.setSoLuong(request.getSoLuong());
        MauSacChiTiet mauSacChiTiet =  adMauSacChiTietReponsitory.save( e);
        return mauSacChiTietReponsitory.findByMauSacChiTiet(mauSacChiTiet.getId());
    }

    @Override
    public AdminMauSacChiTietResponse update(Integer id, AdminMauSacChiTietRequest request) throws IOException, StorageException, InvalidKeyException, URISyntaxException {
        Optional<MauSacChiTiet> optional = adMauSacChiTietReponsitory.findById(id);
        if (optional.isPresent()) {
            MauSacChiTiet chiTiet = optional.get();
            chiTiet.setSanPhamChiTiet(SanPhamChiTiet.builder().id(request.getIdSanPhamChiTiet()).build());
            chiTiet.setMauSac(MauSac.builder().id(request.getIdMauSac()).build());
            if (request.getIdSizeChiTiet() == null) {
                chiTiet.setSizeChiTiet(null);
            } else {
                chiTiet.setSizeChiTiet(SizeChiTiet.builder().id(request.getIdSizeChiTiet()).build());
            }
            chiTiet.setSoLuong(request.getSoLuong());
            if (!request.getAnh().matches("https://imagedatn.blob.core.windows.net/imagecontainer/D:" + ".*")) {
                String linkAnh = getImageToAzureUtil.uploadImageToAzure(request.getAnh());
                chiTiet.setAnh(linkAnh);
            } else {
                chiTiet.setAnh(request.getAnh());
            }
            MauSacChiTiet chiTiet1 = adMauSacChiTietReponsitory.save(chiTiet);
            return mauSacChiTietReponsitory.findByMauSacChiTiet(chiTiet1.getId());
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        adMauSacChiTietReponsitory.deleteById(id);
    }

    @Override
    public Boolean check(Integer idSP, Integer idMauSac) {
        List<MauSacChiTiet> mauSacChiTiet = mauSacChiTietReponsitory.findMSBySPAndMSs(idSP,idMauSac);
        if (mauSacChiTiet == null || mauSacChiTiet.isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

    public Boolean checks(Integer idSP, Integer idMauSac,Integer idSizeCT) {
        MauSacChiTiet mauSacChiTiet = mauSacChiTietReponsitory.findMSBySPAndMS(idSP,idMauSac,idSizeCT);
        if (mauSacChiTiet == null) {
            return true;
        } else {
            return false;
        }

    }
}
