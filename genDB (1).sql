INSERT INTO khuyen_mai VALUES 
(1,NULL,5,NULL,NULL,'KM1','giảm 5% giá tiền của 1 sản phẩm với tất cả mặt hàng','2023-11-10','2023-10-24',100,'giảm 5%','2023-11-15','2023-11-23',1),
(2,NULL,15,NULL,NULL,'KM2','giảm 15% giá tiền của 1 sản phẩm với tất cả mặt hàng','2023-10-10','2023-09-24',80,'giảm 15%','2023-10-15','2023-10-23',1),
(3,NULL,20,NULL,NULL,'KM2','giảm 20% giá tiền của 1 sản phẩm với tất cả mặt hàng','2023-07-10','2023-06-24',60,'giảm 20%','2023-07-15','2023-07-23',0),
(4,NULL,25,NULL,NULL,'KM4','giảm 25% giá tiền của 1 sản phẩm với tất cả mặt hàng','2023-09-10','2023-08-24',400,'giảm 25%','2023-09-15','2023-09-23',1);

INSERT INTO loai VALUES (1,'L1','2023-09-10','2023-09-05','3/4 đầu',1),
(2,'L2','2023-09-15','2023-09-10','Fullface',1),
(3,'L3','2023-09-23','2023-09-19','1/2 đầu',0),
(4,'L4','2023-09-24','2023-09-20','Xe đạp',0),
(5,'L5','2023-08-10','2023-07-25','Trẻ em',1),
(6,'L6','2023-07-19','2023-07-15','Lật cằm',1);

INSERT INTO mau_sac VALUES (1,'MS1',NULL,'2023-05-05','2023-04-13','Vàng',1),(2,'MS2',NULL,'2023-05-05','2023-04-13','Đen',1),(3,'MS3',NULL,'2023-05-05','2023-04-13','Trắng',1),(4,'MS4',NULL,'2023-05-05','2023-04-13','Xanh',1),(5,'MS5',NULL,'2023-05-05','2023-04-13','Đỏ',1),(6,'MS6',NULL,'2023-05-05','2023-04-13','Hồng',1),(7,'MS7',NULL,'2023-05-05','2023-04-13','Rêu',1),(8,'MS8',NULL,'2023-05-05','2023-04-13','Sữa',1);

INSERT INTO size VALUES (1,'S1','56-57cm','2023-11-10','2023-10-24','L',1),(2,'S2','58-59cm','2023-11-10','2023-10-24','XL',1),(3,'S3','52-54cm','2023-11-10','2023-10-24','S',1),(4,'S4','54-55cm','2023-11-10','2023-10-24','M',1);

INSERT INTO trong_luong VALUES (1,'gam','TL1','2023-09-10','2023-08-20',1,850),(2,'gam','TL2','2023-09-10','2023-08-20',1,1050),(3,'gam','TL3','2023-09-10','2023-08-20',1,1050),(4,'gam','TL4','2023-09-10','2023-08-20',1,752),(5,'gam','TL5','2023-09-10','2023-08-20',1,350);

INSERT INTO thuong_hieu VALUES (1,'1001','2023-09-10','2023-09-05','Amoro',1),(2,'1002','2023-09-15','2023-09-10','Protec',1),(3,'1003','2023-09-23','2023-09-19','HSL',0),(4,'1004','2023-09-24','2023-09-20','Hitech',0),(5,'1005','2023-08-10','2023-07-25','Andes',1),(6,'1006','2023-07-19','2023-07-15','Honda',1),(7,'1007','2023-05-20','2023-05-15','Sankyo',0),(8,'1008','2023-10-10','2023-10-05','Avex',0),(9,'1009','2023-07-10','2023-07-01','Royal',0);

INSERT INTO `user` VALUES (1,'levana@gmail.com',1,NULL,'U1','2002-10-07','2023-11-10','2023-10-24','12345','ADMIN','0987678987','Lê Văn A',1,'levana',NULL),(2,'levanb@gmail.com',1,NULL,'U2','2002-11-07','2023-11-10','2023-10-24','12345','NHANVIEN','0987678987','Lê Văn B',1,'levanb',1),(3,'levanc@gmail.com',1,NULL,'U3','2002-12-07','2023-11-10','2023-10-24','12345','USER','0987678987','Lê Văn C',1,'levanc',NULL);

INSERT INTO dia_chi (dia_chi, id_tinh_thanh, ten_tinh_thanh, id_quan_huyen, ten_quan_huyen, id_phuong_xa, ten_phuong_xa, loai_dia_chi, ngay_sua, ngay_tao, trang_thai, id_user) VALUES 
('Số 1, Hàng Than',269,'Lào Cai',2264,'Huyện Si Ma Cai','90816','Thị Trấn Si Ma Cai','Công ty','2023-09-10','2023-04-03',1,2),
('Số 3, Mỹ Đình',268,'Hưng Yên',2194,'Huyện Phù Cừ','220714','Xã Tống Trân','Công ty','2023-10-10','2023-08-23',1,2),
('Thọ Hải, Thọ Xuân',249,'Bắc Ninh',1768,'Yên Phong','190211','Trung Nghĩa','Nhà riêng','2023-07-10','2023-05-13',1,3);


INSERT INTO vat_lieu VALUES (1,'VL1','độ bền cao','2023-11-10','2023-10-24','nhựa ABS nguyên sinh',1),(2,'VL2','êm ái, thông thoáng bảo','2023-11-10','2023-10-24','xốp EPS ',1),(3,'VL3','không gỉ,độ bền cao','2023-11-10','2023-10-24','da simili',1);

INSERT INTO voucher(giam_toi_da, mo_ta, ten, thoi_gian_bat_dau, thoi_gian_ket_thuc, trang_thai) VALUES 
(15000,'ma giam gia zomot','zô mốt','2023/06/23','2023/06/25',1),
(13000,'freeship','freeship','2023/06/23','2023/06/25',1),
(16000,'thời trang','thời trang','2023/06/23','2023/06/25',1),
(15000,'siêu voucher','siêu voucher','2023/06/23','2023/06/25',1),
(11000,'06-06','06-06','2023/06/23','2023/06/25',1);

INSERT INTO san_pham (anh, dem_lot, ma, mo_ta, ngay_sua, ngay_tao, quai_deo, ten, trang_thai,id_loai,id_vat_lieu,id_thuong_hieu) VALUES
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/n6M957MD-07-(5).jpg', 'Vải', 'SP1', 'Mũ bảo hiểm fullface, có kính chắn gió, đệm lót có thể tháo rời, quai đeo có thể điều chỉnh.', '2023-11-08', '2023-11-08', 'Vải', 'Mũ bảo hiểm fullface', 1,1,1,9),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/6QMwPiVn-09.jpg', 'Vải', 'SP2', 'Mũ bảo hiểm nửa đầu, có kính chắn gió, đệm lót có thể tháo rời, quai đeo có thể điều chỉnh.', '2023-11-08', '2023-11-08', 'Vải', 'Mũ bảo hiểm nửa đầu', 1,2,2,9),
('https://mubaohiemandes.vn/wp-content/uploads/2019/08/LUXURY-810B-Tem-Nham-J30-Den-Vang-1.jpg', 'Vải', 'SP3', 'Mũ bảo hiểm cào cào, có kính chắn gió, đệm lót có thể tháo rời, quai đeo có thể điều chỉnh.', '2023-11-08', '2023-11-08', 'Vải', 'Mũ bảo hiểm cào cào', 1,3,3,8),
('https://mubaohiemandes.vn/wp-content/uploads/2019/08/3S555-Tem-Bong-A25-Trang-Xanh-1.jpg', 'Vải', 'SP4', 'Mũ bảo hiểm trẻ em, có kính chắn gió, đệm lót có thể tháo rời, quai đeo có thể điều chỉnh.', '2023-11-08', '2023-11-08', 'Vải', 'Mũ bảo hiểm trẻ em', 1,4,3,7),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/HQ0S39IMG_9737.jpg', 'Da', 'SP5', 'Mũ bảo hiểm fullface, có kính chắn gió, đệm lót có thể tháo rời, quai đeo có thể điều chỉnh.', '2023-11-08', '2023-11-08', 'Da', 'Mũ bảo hiểm fullface', 1,5,2,6),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/gTzVfEroyal-kl-08.jpg', 'Da', 'SP6', 'Mũ bảo hiểm nửa đầu, có kính chắn gió, đệm lót có thể tháo rời, quai đeo có thể điều chỉnh.', '2023-11-08', '2023-11-08', 'Da', 'Mũ bảo hiểm nửa đầu', 1,2,1,5),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/naBX9iIMG_1424.jpg', 'Da', 'SP7', 'Mũ bảo hiểm cào cào, có kính chắn gió, đệm lót có thể tháo rời, quai đeo có thể điều chỉnh.', '2023-11-08', '2023-11-08', 'Da', 'Mũ bảo hiểm cào cào', 1,6,2,4),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/2e3Vnam08-vang.jpg', 'Da', 'SP8', 'Mũ bảo hiểm trẻ em, có kính chắn gió, đệm lót có thể tháo rời, quai đeo có thể điều chỉnh.', '2023-11-08', '2023-11-08', 'Da', 'Mũ bảo hiểm trẻ em', 1,3,1,3),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/iWjG6PIMG_4967.jpg', 'Polycarbonate', 'SP9', 'Mũ bảo hiểm fullface, có kính chắn gió, đệm lót có thể tháo rời, quai đeo có thể điều chỉnh.', '2023-11-08', '2023-11-08', 'Polycarbonate', 'Mũ bảo hiểm fullface', 1,5,2,2),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/5GrbgJIMG_8994.jpg', 'Polycarbonate', 'SP10', 'Mũ bảo hiểm nửa đầu, có kính chắn gió, đệm lót có thể tháo rời, quai đeo có thể điều chỉnh.', '2023-11-08', '2023-11-08', 'Polycarbonate', 'Mũ bảo hiểm nửa đầu', 1,4,3,1);

INSERT INTO san_pham_chi_tiet (anh, gia_ban, gia_nhap, ngay_sua, ngay_tao, so_luong_ton, trang_thai, id_san_pham, id_mau_sac, id_size, id_trong_luong)
VALUES 
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/Zt7RKI_MG_3349.jpg', 1500000, 1200000, '2023-11-10', '2023-11-08', 50, 1, 1, 1, 1, 1),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/vJqbjnroyal-m139-v4.jpg', 1300000, 1100000, '2023-11-10', '2023-11-08', 40, 1, 2, 2, 2, 2),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/hPjwo1_MG_1431.jpg', 1800000, 1500000, '2023-11-10', '2023-11-08', 30, 1, 3, 3, 3, 3),
('https://mubaohiemandes.vn/wp-content/uploads/2019/08/3S555-Tem-Bong-A25-Bac-Xanh.jpg', 1200000, 1000000, '2023-11-10', '2023-11-08', 20, 1, 4, 4, 4, 4),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/qVu6iRIMG_3350-royal.jpg', 2000000, 1800000, '2023-11-10', '2023-11-08', 15, 1, 5, 5, 1, 5),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/YJ9rG8IMG_2412.jpg', 1600000, 1400000, '2023-11-10', '2023-11-08', 25, 1, 6, 6, 2, 1),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/UgxaR0_MG_8621.jpg', 1700000, 1500000, '2023-11-10', '2023-11-08', 35, 1, 7, 7, 3, 2),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/8MzEPqIMG_5454.jpg', 1400000, 1200000, '2023-11-10', '2023-11-08', 30, 1, 8, 8, 4, 3),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/eKrhU4IMG_0597.jpg', 1900000, 1700000, '2023-11-10', '2023-11-08', 18, 1, 9, 1, 1, 4),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/UX95KJsX3lM5IMG_5275.jpg', 1700000, 1500000, '2023-11-10', '2023-11-08', 22, 1, 1, 2, 2, 5),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/X136Mp_MG_2229.jpg', 1200000, 1000000, '2023-11-10', '2023-11-08', 25, 1, 1, 2, 3, 2),
('https://mubaohiemandes.vn/wp-content/uploads/2019/08/3S555-Tem-Bong-A25-Bac-Xanh.jpg', 1300000, 1100000, '2023-11-10', '2023-11-08', 30, 1, 2, 3, 4, 3),
('https://mubaohiemandes.vn/wp-content/uploads/2019/08/LUXURY-810B-Tem-Nham-J30-Den-Vang-1.jpg', 1400000, 1200000, '2023-11-10', '2023-11-08', 22, 1, 3, 4, 1, 4),
('https://mubaohiemandes.vn/wp-content/uploads/2019/08/3S555-Tem-Bong-A25-Bac-Do.jpg', 1500000, 1300000, '2023-11-10', '2023-11-08', 18, 1, 4, 5, 2, 5),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/adguIvroyal-kl-12.jpg', 1600000, 1400000, '2023-11-10', '2023-11-08', 20, 1, 5, 6, 3, 1),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/UgxaR0_MG_8621.jpg', 1700000, 1500000, '2023-11-10', '2023-11-08', 15, 1, 6, 7, 4, 2),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/CLdlNoIMG_0616-copy.jpg', 1800000, 1600000, '2023-11-10', '2023-11-08', 25, 1, 7, 1, 1, 3),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/7mPt1b_MG_9810.jpg', 1900000, 1700000, '2023-11-10', '2023-11-08', 30, 1, 8, 2, 2, 4),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/9th8nxIMG_4967.jpg', 2000000, 1800000, '2023-11-10', '2023-11-08', 28, 1, 9, 3, 3, 5),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/k75KYuIMG_9020.jpg', 2100000, 1900000, '2023-11-10', '2023-11-08', 32, 1, 10, 4, 4, 1);


INSERT INTO image VALUES (1,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/klIZba_MG_3349.jpg','IM1','2023-05-05','2023-04-13',1,1),
(2,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/TLXYEi_MG_3350.jpg','IM2','2023-05-05','2023-04-13',1,1),
(3,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/NOYMt4_MG_3352.jpg','IM3','2023-05-05','2023-04-13',1,1),
(4,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/gqyBwx_MG_3348.jpg','IM4','2023-05-05','2023-04-13',1,1),
(5,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/b4hEP5SwGTU0royal-m139-v7.jpg','IM5','2023-05-05','2023-04-13',1,2),
(6,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/3IqhWcroyal-m139-v7-1.jpg','IM6','2023-05-05','2023-04-13',1,2),
(7,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/hD1uIXroyal-m139-v7-2.jpg','IM7','2023-05-05','2023-04-13',1,2),
(8,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/u50Ujeroyal-m139-v7-3.jpg','IM8','2023-05-05','2023-04-13',1,2),
(9,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/RsOSbzroyal-m139-v7-4.jpg','IM9','2023-05-05','2023-04-13',1,2),
(10,'https://mubaohiemandes.vn/wp-content/uploads/2019/08/3S555-Tem-Bong-A25-Trang-Xanh-1.jpg','IM10','2023-05-05','2023-04-13',1,3),
(11,'https://mubaohiemandes.vn/wp-content/uploads/2019/08/3S555-Tem-Bong-A25-Trang-Xanh-4.jpg','IM11','2023-05-05','2023-04-13',1,3),
(12,'https://mubaohiemandes.vn/wp-content/uploads/2019/08/3S555-Tem-Bong-A25-Trang-Do.jpg','IM12','2023-05-05','2023-04-13',1,3),
(13,'https://mubaohiemandes.vn/wp-content/uploads/2019/08/3S555-Tem-Bong-A25-Trang-Xanh-3.jpg','IM13','2023-05-05','2023-04-13',1,3),
(15,'https://mubaohiemandes.vn/wp-content/uploads/2019/08/3S555-Tem-Bong-A25-Trang-Xanh-1.jpg','IM15','2023-05-05','2023-04-13',1,4),
(16,'https://mubaohiemandes.vn/wp-content/uploads/2019/08/3S555-Tem-Bong-A25-Trang-Xanh-4.jpg','IM16','2023-05-05','2023-04-13',1,4),
(17,'https://mubaohiemandes.vn/wp-content/uploads/2019/08/3S555-Tem-Bong-A25-Trang-Do.jpg','IM17','2023-05-05','2023-04-13',1,4),
(18,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/K9FtBoIMG_8995.jpg','IM17','2023-05-05','2023-04-13',1,10),
(19,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/5GrbgJIMG_8994.jpg','IM17','2023-05-05','2023-04-13',1,10),
(20,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/hKN9sZIMG_8997.jpg','IM17','2023-05-05','2023-04-13',1,10),
(21,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/dNR7H2IMG_8998.jpg','IM17','2023-05-05','2023-04-13',1,10),
(22,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/RAdtZCIMG_8999.jpg','IM17','2023-05-05','2023-04-13',1,10),
(23,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/LR0EtQIMG_0481.jpg','IM17','2023-05-05','2023-04-13',1,9),
(24,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/9th8nxIMG_4967.jpg','IM17','2023-05-05','2023-04-13',1,9),
(25,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/iWjG6PIMG_4967.jpg','IM17','2023-05-05','2023-04-13',1,9),
(26,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/F2UTAOIMG_4968.jpg','IM17','2023-05-05','2023-04-13',1,9),
(27,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/hkrUL2IMG_4971.jpg','IM17','2023-05-05','2023-04-13',1,9),
(28,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/2e3Vnam08-vang.jpg','IM17','2023-05-05','2023-04-13',1,8),
(29,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/EMUFRp_MG_0874.jpg','IM17','2023-05-05','2023-04-13',1,8),
(30,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/Dje90p_MG_0870.jpg','IM17','2023-05-05','2023-04-13',1,8),
(31,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/BOewoV_MG_0880.jpg','IM17','2023-05-05','2023-04-13',1,8),
(32,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/gTzVfEroyal-kl-08.jpg','IM17','2023-05-05','2023-04-13',1,6),
(33,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/edncUZroyal-kl-07.jpg','IM17','2023-05-05','2023-04-13',1,6),
(34,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/MjADSPIMG_3224.jpg','IM17','2023-05-05','2023-04-13',1,6),
(35,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/naBX9iIMG_1424.jpg','IM17','2023-05-05','2023-04-13',1,7),
(36,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/NvXqicIMG_1423.jpg','IM17','2023-05-05','2023-04-13',1,7),
(37,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/5NsT2YIMG_1425.jpg','IM17','2023-05-05','2023-04-13',1,7),
(38,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/sDHolTIMG_1428.jpg','IM17','2023-05-05','2023-04-13',1,7),
(39,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/HQ0S39IMG_9737.jpg','IM17','2023-05-05','2023-04-13',1,5),
(40,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/LkNxVuIMG_9735.jpg','IM17','2023-05-05','2023-04-13',1,5),
(41,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/zFQE51IMG_9739.jpg','IM17','2023-05-05','2023-04-13',1,5),
(42,'https://royalhelmet.com.vn/ckfinder/userfiles/images/products/uv5pERIMG_9742.jpg','IM17','2023-05-05','2023-04-13',1,5);

INSERT INTO phuong_thuc_thanh_toan (ma,ngay_tao,ten,trang_thai)
VALUES ('PTTT1','2023-11-17 12:50:00','online',0),
('PTTT2','2023-11-17 12:50:00','offline',0)
