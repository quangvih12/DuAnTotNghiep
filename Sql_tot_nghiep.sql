create database DuAnTotNghiep
go

use DuAnTotNghiep
go

create table thuong_hieu(
 id int primary key identity,
 ten nvarchar(50),
 code nvarchar(50),
ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int
)

create table loai(
 id int primary key identity,
 ten nvarchar(50),
 ma nvarchar(50),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int
)

create table san_pham(
 id int primary key identity,
 ten nvarchar(50),
 ma nvarchar(50),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int,
 mo_ta nvarchar(max),
 dem_lot nvarchar(50),
 quai_deo nvarchar(50),
 id_thuong_hieu int not null foreign key references thuong_hieu (id) ,
 id_loai int not null foreign key references loai (id) 
)
create table trong_luong(
 id int primary key identity,
 don_vi nvarchar(50),
 ma nvarchar(50),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 mo_ta nvarchar(max),
 trang_thai int,
 value int
)

create table vat_lieu(
 id int primary key identity,
 ten nvarchar(50),
 ma nvarchar(50),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 mo_ta nvarchar(max),
 trang_thai int
)

create table khuyen_mai(
 id int primary key identity,
 ten nvarchar(50),
 ma nvarchar(50), 
 mo_ta nvarchar(max),
 kieu_dien int,
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int,
 thoi_gian_bat_dau date,
 thoi_gian_ket_thuc date,
 so_luong int,
 giam_gia int
)


create table mau_sac(
 id int primary key identity,
 ten nvarchar(50),
 ma nvarchar(50),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
  mo_ta nvarchar(max),
 trang_thai int
)

create table size(
 id int primary key identity,
 ten nvarchar(50),
 ma nvarchar(50),
 mo_ta nvarchar(max),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int
)

create table san_pham_chi_tiet(
 id int primary key identity,
 so_luong_ton int,
 gia_nhap decimal(20,0),
 gia_ban decimal(20,0),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int,
 id_san_pham int not null foreign key references san_pham (id) ,
 id_khuyen_mai int  foreign key references khuyen_mai (id) ,
 id_vat_lieu int not null foreign key references vat_lieu (id) ,
 id_trong_luong int not null foreign key references trong_luong (id) 
)

create table size_ctsp(
 id int primary key identity,
 mo_ta nvarchar(max),
 so_luong int,
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int,
 id_size int not null foreign key references size (id) ,
 id_ctsp int not null foreign key references san_pham_chi_tiet (id) 
)

create table mau_sac_ctsp(
	id int primary key identity,
	anh nvarchar(max),
	mo_ta nvarchar(max),
	ngay_sua varchar(50), 
	ngay_tao varchar(50),
	trang_thai int,
	id_mau_sac int not null foreign key references mau_sac (id) ,
	id_ctsp int not null foreign key references san_pham_chi_tiet (id) 
)
create table [image](
 id int primary key identity,
 ma nvarchar(50),
 anh nvarchar(max),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int,
 id_san_pham_chi_tiet int not null foreign key references san_pham_chi_tiet (id) 
)

create table chuc_vu(
 id int primary key identity,
 ten nvarchar(50),
 ma nvarchar(50),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int
)


create table [user](
 id int primary key identity,
 ten nvarchar(50),
 ma nvarchar(50),
 [user_name] nvarchar(50),
 [password] nvarchar(50),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 ngay_sinh varchar(50),
 trang_thai int,
 anh nvarchar(max),
 email nvarchar(max),
 gioi_tinh int,
 sdt nvarchar(15),
 [role] nvarchar(10),	
 id_chuc_vu int foreign key references chuc_vu (id) 
)
create table comment(
 id int primary key identity,
 noi_dung nvarchar(max),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int ,
 id_san_pham_chi_tiet int not null foreign key references san_pham_chi_tiet (id) ,
 id_user int not null foreign key references [user] (id) 
)
create table token(
 id int primary key identity,
 expired bit,
 revoked bit,
 token varchar(225),
 token_type varchar(225),
 id_user int not null foreign key references [user] (id) 
)

create table phuong_thuc_thanh_toan(
id int primary key identity,
 ma nvarchar(50),
 ten nvarchar(50),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int
)



create table dia_chi(
 id int primary key identity,
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int,
 dia_chi nvarchar(max),
 loai_dia_chi nvarchar(50),
 id_user int not null foreign key references [user] (id) 
)

create table hoa_don(
 id int primary key identity,
 ma nvarchar(50),
 tong_tien decimal(20,0),
 ten_nguoi_nhan nvarchar(max),
 ngay_nhan varchar(50),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 ngay_thanh_toan varchar(50), 
 ngay_ship varchar(50),
 hinh_thuc_giao_hang int,
 trang_thai int,
 tien_ship decimal(20,0),
 tien_sau_khi_giam_gia decimal(20,0),
id_user int not null foreign key references [user] (id) ,
id_phuong_thuc_thanh_toan int not null foreign key references phuong_thuc_thanh_toan (id) ,
id_dia_chi_sdt int not null foreign key references dia_chi (id)
)

create table gio_hang(
 id int primary key identity,
 ma nvarchar(50),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int,
 id_user int not null foreign key references [user] (id) 
)

create table gio_hang_chi_tiet(
 id int primary key identity,
 ten nvarchar(50),
 ma nvarchar(50),
 don_gia decimal(20,0),
 so_luong int,
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int,
 id_san_pham_chi_tiet int not null foreign key references san_pham_chi_tiet (id) ,
 id_gio_hang int not null foreign key references gio_hang (id) 
)
create table hoa_don_chi_tiet(
 id int primary key identity,
 ten nvarchar(50),
 ma nvarchar(50),
 so_luong int,
 don_gia decimal(20,0),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int,
 id_san_pham_chi_tiet int not null foreign key references san_pham_chi_tiet (id),
 id_hoa_don int not null foreign key references hoa_don (id) 
 )
create table danh_sach_yeu_thich(
 id int primary key identity,
 ten nvarchar(50),
 ma nvarchar(50),
 don_gia decimal(20,0),
 so_luong int,
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int,
 id_san_pham_chi_tiet int not null foreign key references san_pham_chi_tiet (id) ,
 id_user int not null foreign key references [user] (id) 
)

insert into thuong_hieu(ten,code,ngay_sua,ngay_tao,trang_thai)
values
('Amoro','1001','2023-09-10','2023-09-05',1),
('Protec','1002','2023-09-15','2023-09-10',1),
('HSL','1003','2023-09-23','2023-09-19',0),
('Hitech','1004','2023-09-24','2023-09-20',0),
('Andes','1005','2023-08-10','2023-07-25',1),
('Honda','1006','2023-07-19','2023-07-15',1),
('Sankyo','1007','2023-05-20','2023-05-15',0),
('Avex','1008','2023-10-10','2023-10-05',0),
('Royal','1009','2023-07-10','2023-07-01',0);

insert into loai(ten,ma,ngay_sua,ngay_tao,trang_thai)
values
(N'3/4 đầu','L1','2023-09-10','2023-09-05',1),
(N'Fullrace','L2','2023-09-15','2023-09-10',1),
(N'1/2 đầu','L3','2023-09-23','2023-09-19',0),
(N'Xe đạp','L4','2023-09-24','2023-09-20',0),
(N'Trẻ em','L5','2023-08-10','2023-07-25',1),
(N'Lật cằm','L6','2023-07-19','2023-07-15',1);

insert into san_pham(ten,ma,ngay_sua,ngay_tao,trang_thai,mo_ta,dem_lot,quai_deo,id_thuong_hieu,id_loai)
values
('ROYAL M20D','SP1','2023-09-10','2023-09-05',1,N'Được thiết kế theo phong cách hiện đại với những đường nét bo tròn đầy tinh tế gọn gàng đã khiến ngoại hình của Royal M20C trở nên tinh tế gọn hàng hơn rất nhiều so với những thế hệ mũ bảo hiểm ¾ trước đây. Một nguyên nhân khiến cho mọi người thường chọn mũ bảo hiểm nửa đầu thay vì nón fullface hoặc mũ ¾ chính là vì sự gọn gàng, tiện lợi trong sử dụng, bảo quản, nhưng có thể thấy, với Royal M20C, bạn hoàn toàn yên tâm về sự gọn gàng, tiện lợi trong khi sử dụng',N'Vải nâu đất, có thể tháo rời',N'Có thể tháo rời',9,1),
('ROYAL M139','SP2','2023-09-15','2023-09-10',1,N'Thiết kế kính âm mang tới sự khác biệt của Royal M139 so với các sản phẩm mũ bảo hiểm khác. Khi được kéo lên, lớp kính bảo vệ sẽ ẩn mình vào bên trong nón, biến Royal M139 thành chiếc mũ bảo hiểm  ¾  đầy năng động, phong cách. Khi kéo xuống, lớp kính chắc chắn, trong suốt sẽ bảo vệ bạn khỏi khói bụi, dị vật mà vẫn mang đến cho bạn tầm nhìn thoáng đãng và  phong cách thời trang trên mọi cung đường.',N'Vải nâu đất, có thể tháo rời',N'có thể tháo rời',9,1),
(N'ROYAL M268 TRƠN - 2 KÍNH','SP3','2023-09-23','2023-09-19',0,N'Mũ bảo hiểm Royal M268 siêu phẩm mũ ¾ hai kính mới nhất của Royal Helmet. Giữ nguyên những đặc điểm“đẳng cấp” làm nên thương hiệu Royal, Royal M268 vẫn biết làm nổi bật mình với thiết kế 2 kính độc đáo mang lại những trải nghiệm thú vị, tươi mới cho người đội!',N'Vải đen , có thể tháo rời',N'có thể tháo rời',9,1),
('ROYAL M134C','SP4','2023-09-10','2023-09-05',1,N'Mũ bảo hiểm Royal M134C do công ty Mafa sản xuất. Thương hiệu nón Royal ra đời năm 2008 do ông Mai Văn Thuận sáng lập. Với mục tiêu sản xuất ra những chiếc nón chất lượng nhất, đáp ứng nhu cầu ngày càng cao không chỉ của người dùng ở Việt Nam và cả ở thị trường thế giới.',N'Vải đen , có thể tháo rời',N'có thể tháo rời',9,1),
('Andes Luxury 810B','SP5','2023-07-19','2023-07-15',1,N'Mũ bảo hiểm nguyên đầu Andes Luxury 810B là dòng mũ bảo hiểm fullface cao cấp cải tiến mới của Andes. Nhờ thiết kế nguyên khối bền chắc, Andes Luxury 810B có thể bao bọc toàn bộ phần đầu như mặt, tai, gáy và cả cằm, bền bỉ bảo vệ người đội mũ trong suốt chặng đường dài',N'Vải nâu đất, có thể tháo rời',N'có thể tháo rời',5,2),
('Andes 3S555','SP6','2023-08-10','2023-07-25',0,N'Mũ bảo hiểm nguyên đầu Andes 3S555 là dòng mũ bảo hiểm fullface cao cấp cải tiến mới của Andes. Nhờ thiết kế nguyên khối bền chắc, Andes 3S555 có thể bao bọc toàn bộ phần đầu như mặt, tai, gáy và cả cằm, bền bỉ bảo vệ người đội mũ trong suốt chặng đường dài.',N'Vải đen, có thể tháo rời',N'có thể tháo rời',5,2),
('ROYAL M138B','SP7','2023-09-10','2023-09-05',1,N'Mũ bảo hiểm Royal M138B do công ty Mafa sản xuất. Thương hiệu nón Royal ra đời năm 2008 do ông Mai Văn Thuận sáng lập. Với mục tiêu sản xuất ra những chiếc nón chất lượng nhất, đáp ứng nhu cầu ngày càng cao không chỉ của người dùng ở Việt Nam và cả ở thị trường thế giới.',N'Vải nâu đất, có thể tháo rời',N'có thể tháo rời',9,2),
('ROYAL M141K','SP8','2023-09-10','2023-09-05',0,N'là dòng nón bảo hiểm fullface 1 kính cao cấp mới nhất.Với mức giá rất cạnh tranh và chất lượng cao vượt trội. Đây là mẫu fullface định hướng xuất khẩu nên chất lượng khá tốt',N'Vải nâu đất, có thể tháo rời',N'có thể tháo rời',9,2),
('ROYAL M136','SP9','2023-09-10','2023-09-05',1,N'Một trong những sản phẩm đó là mũ bảo hiểm fullface Royal M136. Là một trong những mũ bảo hiểm fullface phổ biến nhất thị trường hiện nay, M136 chứa trong mình nhiều giá trị để mang lại những giá trị hoàn hảo cho người sở hữu',N'Vải lưới 3 lớp, có thể tháo rời',N'có thể tháo rời',9,2),
('ROYAL M226 DESIGN','SP10','2023-09-15','2023-09-10',1,N'Royal M266 là dòng nón bảo hiểm fullface 2 kính mới ra mắt của nón Royal. Đây là nón fullface có thể nói rẻ nhất Việt Nam',N'Vải lưới 3 lớp, có thể tháo rời',N'có thể tháo rời',9,2),
('Andes 3S126','SP11','2023-09-15','2023-09-10',1,N'Mũ bảo hiểm nửa đầu có kính Andes 3S126 có thiết kế đơn giản, bắt mắt với bộ sưu tập màu, tem hơn 20 mẫu cho cả nhám lẫn bóng. Andes 3S126 còn được gọi là chiếc nón thanh xuân bởi sự đa dạng và trẻ trung ở thiết kế, đây chắc chắn là sản phẩm thú vị dành cho những quý khách hàng yêu thích sự nổi bật.',null,null,5,3),
('ROYAL 152K','SP12','2023-09-15','2023-09-10',1,N'Giữ nguyên những đặc điểm“đẳng cấp” làm nên thương hiệu Royal, Royal 152K vẫn biết làm nổi bật mình với hệ thống thông gió "xịn sò" thiết kế nổi bật với các đường nét góc cạnh, tinh tế được tính toán cẩn thận.',null,null,9,3),
('ROYAL 153LT','SP13','2023-09-15','2023-09-10',0,N'Với vẻ ngoài thời trang, chất lượng hoàn hảo cùng những trải nghiệm thú vị, Mũ bảo hiểm Royal M153LT đang rất được mọi người ưa thích.',null,null,9,3),
('ROYAL 153K','SP14','2023-09-15','2023-09-10',1,N'Với vẻ ngoài thời trang, chất lượng hoàn hảo cùng những trải nghiệm thú vị, Mũ bảo hiểm Royal M153K đang rất được mọi người ưa thích.',null,null,9,3),
('ROYAL M379','SP15','2023-07-19','2023-07-15',1,N'Với vẻ ngoài thời trang, chất lượng hoàn hảo cùng những trải nghiệm thú vị, Mũ bảo hiểm Royal M379 đang rất được mọi người ưa thích.',null,null,9,3),
('ROYAL MD-05','SP16','2023-07-19','2023-07-15',1,N'Royal MD-05 - Dòng mũ bảo hiểm xe đạp thời trang. Lớp đệm Poly Source cao cấp được nén dưới áp suất cao, giúp hấp thu xung động va đập tối ưu cùng với hệ thống khoá vòng đầu cao cấp mang lại cho các vận động viên sự thoải mái, an toàn khi thi đấu với cường độ cao.',null,null,9,4),
('ROYAL MD-15 DESIGN','SP17','2023-07-19','2023-07-15',1,N'Royal MD-15 - Dòng mũ bảo hiểm xe đạp thời trang. Lớp đệm Poly Source cao cấp được nén dưới áp suất cao, giúp hấp thu xung động va đập tối ưu cùng với hệ thống khoá vòng đầu cao cấp mang lại cho các vận động viên sự thoải mái, an toàn khi thi đấu với cường độ cao.',null,null,9,4),
(' ROYAL MD-16 TRƠN','SP18','2023-07-19','2023-07-15',1,N'Với vẻ ngoài thời trang, chất lượng hoàn hảo cùng những trải nghiệm thú vị, Mũ bảo hiểm Royal MD-16 đang rất được mọi người ưa thích.',null,null,9,4),
('ROYAL MD-07','SP19','2023-07-19','2023-07-15',1,N'Royal MD-07 - Dòng mũ bảo hiểm xe đạp thời trang. Lớp đệm Poly Source cao cấp được nén dưới áp suất cao, giúp hấp thu xung động va đập tối ưu cùng với hệ thống khoá vòng đầu cao cấp mang lại cho các vận động viên sự thoải mái, an toàn khi thi đấu với cường độ cao.',null,null,9,4),
('Andes 3S108S','SP20','2023-09-15','2023-09-10',1,N'Mũ bảo hiểm trẻ em nửa đầu Andes 3S108S là dòng nón nửa đầu dành cho trẻ em thông dụng nhất, kiểu dáng của Andes 3S 108S được mô phỏng theo chiếc nón Andes 3S 108M của người lớn. Với thiết kế đơn giản – gọn nhẹ, nón chỉ nặng ~500gr phù hợp với các bé, đảm bảo an toàn cho bé suốt quãng đường mà không hề gây cảm giác khó chịu hay nặng đầu.',null,null,5,5),
('ROYAL M20KS','SP21','2023-09-15','2023-09-10',1,N'Là dòng sản phẩm dành cho trẻ em,  mũ bảo hiểm Royal M20KS Trơn Trẻ em là sản phẩm được đội ngũ nghiên cứu và phát triển sản phẩm của Royal Helmet đặt rất nhiều tâm sức',null,null,9,5),
('ROYAL M139 KID','SP22','2023-09-15','2023-09-10',1,N'Là dòng sản phẩm dành cho trẻ em,  mũ bảo hiểm Royal M139 KID Trẻ em là sản phẩm được đội ngũ nghiên cứu và phát triển sản phẩm của Royal Helmet đặt rất nhiều tâm sức',null,null,9,5),
('ROYAL M108','SP23','2023-09-15','2023-09-10',1,N'Nón bảo hiểm Royal M08 do công ty Mafa sản xuất. Thương hiệu nón Royal ra đời năm 2008 do ông Mai Văn Thuận sáng lập. Với mục tiêu sản xuất ra những chiếc nón chất lượng nhất, đáp ứng nhu cầu ngày càng cao không chỉ của người dùng ở Việt Nam và cả ở thị trường thế giới.',N'Vải đen, có thể tháo rời',N'có thể tháo rời',9,6),
('ROYAL  M08 DESIGN ','SP25','2023-09-15','2023-09-10',1,N'Nón bảo hiểm Royal M08 do công ty Mafa sản xuất. Thương hiệu nón Royal ra đời năm 2008 do ông Mai Văn Thuận sáng lập. Với mục tiêu sản xuất ra những chiếc nón chất lượng nhất, đáp ứng nhu cầu ngày càng cao không chỉ của người dùng ở Việt Nam và cả ở thị trường thế giới.',N'Vải đen, có thể tháo rời',N'có thể tháo rời',9,6),
('ROYAL M79','SP26','2023-09-15','2023-09-10',1,N'Nón bảo hiểm Royal M08 do công ty Mafa sản xuất. Thương hiệu nón Royal ra đời năm 2008 do ông Mai Văn Thuận sáng lập. Với mục tiêu sản xuất ra những chiếc nón chất lượng nhất, đáp ứng nhu cầu ngày càng cao không chỉ của người dùng ở Việt Nam và cả ở thị trường thế giới.',N'Vải đen, có thể tháo rời',N'có thể tháo rời',9,6)


insert into trong_luong(don_vi,ma,ngay_sua,ngay_tao,mo_ta,trang_thai,value)
values
('gam','TL1','2023-09-10','2023-08-20',null,1,850),
('gam','TL2','2023-09-10','2023-08-20',null,1,1050),
('gam','TL3','2023-09-10','2023-08-20',null,1,1050),
('gam','TL4','2023-09-10','2023-08-20',null,1,752),
('gam','TL5','2023-09-10','2023-08-20',null,1,350)

insert into khuyen_mai(ten,ma,mo_ta,ngay_sua,ngay_tao,trang_thai,thoi_gian_bat_dau,thoi_gian_ket_thuc,so_luong,giam_gia)
values
(N'giảm 5%','KM1',N'giảm 5% giá tiền của 1 sản phẩm với tất cả mặt hàng','2023-11-10','2023-10-24',1,'2023-11-15','2023-11-23',100,5),
(N'giảm 15%','KM2',N'giảm 15% giá tiền của 1 sản phẩm với tất cả mặt hàng','2023-10-10','2023-09-24',1,'2023-10-15','2023-10-23',80,15),
(N'giảm 20%','KM2',N'giảm 20% giá tiền của 1 sản phẩm với tất cả mặt hàng','2023-07-10','2023-06-24',0,'2023-07-15','2023-07-23',60,20),
(N'giảm 25%','KM4',N'giảm 25% giá tiền của 1 sản phẩm với tất cả mặt hàng','2023-09-10','2023-08-24',1,'2023-09-15','2023-09-23',400,25)

insert into vat_lieu(ten,ma,ngay_sua,ngay_tao,mo_ta,trang_thai)
values
(N'nhựa ABS nguyên sinh','VL1','2023-11-10','2023-10-24',N'độ bền cao',1),
(N'xốp EPS ','VL2','2023-11-10','2023-10-24',N'êm ái, thông thoáng bảo',1),
(N'da simili','VL3','2023-11-10','2023-10-24',N'không gỉ,độ bền cao',1)

insert into size(ten,ma,mo_ta,ngay_sua,ngay_tao,trang_thai)
values
('L','S1','56-57cm','2023-11-10','2023-10-24',1),
('XL','S2','58-59cm','2023-11-10','2023-10-24',1),
('S','S3','52-54cm','2023-11-10','2023-10-24',1),
('M','S4','54-55cm','2023-11-10','2023-10-24',1)



insert into chuc_vu(ten,ma,ngay_sua,ngay_tao,trang_thai)
values
(N'Nhân viên','CV1','2023-11-10','2023-10-24',1),
(N'Giám đốc','CV3','2023-11-10','2023-10-24',1),
(N'Nhân viên Maketing','CV4','2023-11-10','2023-10-24',1)

insert into [user](ten,ma,[user_name],[password],ngay_sua,ngay_tao,ngay_sinh,trang_thai,anh,email,gioi_tinh,sdt,[role],id_chuc_vu)
values
(N'Lê Văn A','U1','levana','12345','2023-11-10','2023-10-24','2002-10-07',1,null,'levana@gmail.com',1,'0987678987','ADMIN',null),
(N'Lê Văn B','U2','levanb','12345','2023-11-10','2023-10-24','2002-11-07',1,null,'levanb@gmail.com',1,'0987678987','NHANVIEN',1),
(N'Lê Văn C','U3','levanc','12345','2023-11-10','2023-10-24','2002-12-07',1,null,'levanc@gmail.com',1,'0987678987','USER',null)


insert into dia_chi(ngay_sua,ngay_tao,trang_thai,dia_chi,loai_dia_chi,id_user)
values
('2023-09-10','2023-04-03',1,N'Số 1, Hàng Than',N'Công ty',2),
('2023-10-10','2023-08-23',1,N'Số 3, Mỹ Đình',N'Công ty',2),
('2023-07-10','2023-05-13',1,N'Thọ Hải, Thọ Xuân',N'Nhà riêng',3)

insert into phuong_thuc_thanh_toan(ma,ten,ngay_sua,ngay_tao,trang_thai)
values
('TT1','Online','2022-09-07','2022-08-06',1),
('TT2',N'Tiền mặt','2022-09-07','2022-08-06',1)

insert into san_pham_chi_tiet(gia_nhap,gia_ban,ngay_sua,ngay_tao,so_luong_ton,trang_thai,id_san_pham,id_khuyen_mai,id_vat_lieu,id_trong_luong)
values
('700000','780000','2023-05-05','2023-04-13',50,1,1,null,1,1),
('600000','730000','2023-05-05','2023-04-13',100,1,2,null,1,1),
('500000','6400000','2023-05-05','2023-04-13',25,1,3,null,1,1),
('400000','540000','2023-05-05','2023-04-13',30,1,4,null,1,2),
('600000','750000','2023-05-05','2023-04-13',120,1,5,null,1,1)

insert into size_ctsp(so_luong,ngay_sua,ngay_tao,trang_thai,mo_ta,id_size,id_ctsp)
values
(23,'2023-05-05',null,1,null,1,1),
(10,'2023-05-05',null,1,null,4,1),
(17,'2023-05-05',null,1,null,3,1),
(55,'2023-05-05',null,1,null,1,2),
(2,'2023-05-05',null,1,null,2,2),
(3,'2023-05-05',null,1,null,4,2),
(40,'2023-05-05',null,1,null,3,2),
(20,'2023-05-05',null,1,null,1,3),
(5,'2023-05-05',null,1,null,3,3),
(10,'2023-05-05',null,1,null,1,4),
(10,'2023-05-05',null,1,null,2,4),
(10,'2023-05-05',null,1,null,4,4),
(40,'2023-05-05',null,1,null,1,5),
(20,'2023-05-05',null,1,null,2,5),
(20,'2023-05-05',null,1,null,3,5),
(40,'2023-05-05',null,1,null,4,5)

insert into mau_sac(ten,ma,ngay_sua,ngay_tao,trang_thai)
values
(N'Vàng','MS1','2023-05-05','2023-04-13',1),
(N'Đen','MS2','2023-05-05','2023-04-13',1),
(N'Trắng','MS3','2023-05-05','2023-04-13',1),
(N'Xanh','MS4','2023-05-05','2023-04-13',1),
(N'Đỏ','MS5','2023-05-05','2023-04-13',1),
(N'Hồng','MS6','2023-05-05','2023-04-13',1),
(N'Rêu','MS7','2023-05-05','2023-04-13',1),
(N'Sữa','MS8','2023-05-05','2023-04-13',1)

insert into [image] (ma,anh,ngay_sua,ngay_tao,trang_thai,id_san_pham_chi_tiet)
values
('IM1','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/klIZba_MG_3349.jpg','2023-05-05','2023-04-13',1,1),
('IM2','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/TLXYEi_MG_3350.jpg','2023-05-05','2023-04-13',1,1),
('IM3','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/NOYMt4_MG_3352.jpg','2023-05-05','2023-04-13',1,1),
('IM4','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/gqyBwx_MG_3348.jpg','2023-05-05','2023-04-13',1,1),
('IM5','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/b4hEP5SwGTU0royal-m139-v7.jpg','2023-05-05','2023-04-13',1,2),
('IM6','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/3IqhWcroyal-m139-v7-1.jpg','2023-05-05','2023-04-13',1,2),
('IM7','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/hD1uIXroyal-m139-v7-2.jpg','2023-05-05','2023-04-13',1,2),
('IM8','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/u50Ujeroyal-m139-v7-3.jpg','2023-05-05','2023-04-13',1,2),
('IM9','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/RsOSbzroyal-m139-v7-4.jpg','2023-05-05','2023-04-13',1,2),
('IM10','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/xKHMALIMG_5434.jpg','2023-05-05','2023-04-13',1,3),
('IM11','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/OSknJrIMG_5435.jpg','2023-05-05','2023-04-13',1,3),
('IM12','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/gJnFjBIMG_5433.jpg','2023-05-05','2023-04-13',1,3),
('IM13','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/ikwHUAIMG_5436.jpg','2023-05-05','2023-04-13',1,3),
('IM14','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/cDpyPNIMG_5437.jpg','2023-05-05','2023-04-13',1,3),
('IM15','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/lANmFe_MG_9655.jpg','2023-05-05','2023-04-13',1,4),
('IM16','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/4SNrqJ_MG_9657.jpg','2023-05-05','2023-04-13',1,4),
('IM17','https://royalhelmet.com.vn/ckfinder/userfiles/images/products/qKPAJv_MG_9659.jpg','2023-05-05','2023-04-13',1,4)


insert into mau_sac_ctsp(anh,mo_ta,ngay_sua,ngay_tao,trang_thai,id_mau_sac,id_ctsp)
values
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/Zt7RKI_MG_3349.jpg',null,'2023-05-05','2023-04-13',1,1,1),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/P0jXDB_MG_3354.jpg',null,'2023-05-05','2023-04-13',1,2,1),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/K6uk81_MG_3354-muc.jpg',N'Xanh mực','2023-05-05','2023-04-13',1,4,1),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/0IAa3e_MG_0585.jpg',N'Trắng bóng','2023-05-05','2023-04-13',1,3,1),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/ahK7SV_MG_0593.jpg',N'Đen bóng','2023-05-05','2023-04-13',1,2,1),

('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/FofRWKroyal-m139-v1-5.jpg',N'V1-Đen','2023-05-05','2023-04-13',1,2,2),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/5JoMl9royal-m139-v2-2.jpg',N'V2-Đen','2023-05-05','2023-04-13',1,2,2),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/sN4zd1Royal-m139-v10.jpg',N'Trắng bóng','2023-05-05','2023-04-13',1,3,2),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/4eOVB1royal-m139-v9.jpg',N'V1-Trắng','2023-05-05','2023-04-13',1,3,2),

('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/xKHMALIMG_5434.jpg',N'Xanh ngọc','2023-05-05','2023-04-13',1,4,3),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/AfmhwnIMG_5283.jpg',N'Đỏ đô','2023-05-05','2023-04-13',1,5,3),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/8MzEPqIMG_5454.jpg',N'Sữa bóng','2023-05-05','2023-04-13',1,8,3),
('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/PeCI1GIMG_3358.jpg',N'Đen mờ','2023-05-05','2023-04-13',1,2,3),

('https://royalhelmet.com.vn/ckfinder/userfiles/images/products/lANmFe_MG_9655.jpg',N'Đen ','2023-05-05','2023-04-13',1,2,4)
