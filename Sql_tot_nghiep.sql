create database DuAnTotNghiep
go

use DuAnTotNghiep
go

create table thuong_hieu(
 id int not null  primary key,
 ten nvarchar(50),
 code nvarchar(50),
ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int
)

create table loai(
 id int not null  primary key,
 ten nvarchar(50),
 ma nvarchar(50),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int
)

create table san_pham(
 id int not null  primary key,
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
 id int not null  primary key,
 ten nvarchar(50),
 ma nvarchar(50),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 mo_ta nvarchar(max),
 trang_thai int,
 value int
)

create table vat_lieu(
 id int not null  primary key,
 ten nvarchar(50),
 ma nvarchar(50),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 mo_ta nvarchar(max),
 trang_thai int
)

create table khuyen_mai(
 id int not null  primary key,
 ten nvarchar(50),
 ma nvarchar(50), 
 mo_ta nvarchar(max),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int,
 thoi_gian_bat_dau date,
 thoi_gian_ket_thuc date,
 so_luong int,
 giam_gia int
)


create table mau_sac(
 id int not null  primary key,
 ten nvarchar(50),
 ma nvarchar(50),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 mo_ta nvarchar(max),
 anh nvarchar(max),
 trang_thai int
)

create table size(
 id int not null  primary key,
 ten nvarchar(50),
 ma nvarchar(50),
 mo_ta nvarchar(max),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int
)

create table san_pham_chi_tiet(
 id int not null  primary key,
 ten nvarchar(50),
 ma nvarchar(50),
 gia_nhap decimal(20,0),
 gia_ban decimal(20,0),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int,
 id_san_pham int not null foreign key references san_pham (id) ,
 id_khuyen_mai int not null foreign key references khuyen_mai (id) ,
 id_vat_lieu int not null foreign key references vat_lieu (id) ,
 id_mau_sac int not null foreign key references mau_sac (id) ,
 id_size int not null foreign key references size (id) ,
 id_trong_luong int not null foreign key references trong_luong (id) 
)

create table [image](
 id int not null  primary key,
 ten nvarchar(50),
 ma nvarchar(50),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int,
 id_san_pham_chi_tiet int not null foreign key references san_pham_chi_tiet (id) 
)

create table cua_hang(
 id int not null  primary key,
 ten nvarchar(50),
 ma nvarchar(50),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int
)

create table chuc_vu(
 id int not null  primary key,
 ten nvarchar(50),
 ma nvarchar(50),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int
)



create table [user](
 id int not null  primary key,
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
 id_chuc_vu int not null foreign key references chuc_vu (id) ,
 id_cua_hang int not null foreign key references cua_hang (id) 
)
create table comment(
 id int not null  primary key,
 noi_dung nvarchar(max),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int ,
 id_san_pham_chi_tiet int not null foreign key references san_pham_chi_tiet (id) ,
 id_user int not null foreign key references [user] (id) 
)
create table token(
 id int not null  primary key,
 expired bit,
 revoked bit,
 token varchar(225),
 token_type varchar(225),
 id_user int not null foreign key references [user] (id) 
)

create table phuong_thuc_thanh_toan(
 id int not null  primary key,
 ma nvarchar(50),
 ten nvarchar(50),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int
)

create table hinh_thuc_giao_hang(
 id int not null  primary key,
 ma nvarchar(50),
 ten nvarchar(50),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int
)

create table dia_chi(
 id int not null  primary key,
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int,
 dia_chi nvarchar(max),
 loai_dia_chi nvarchar(50),
 id_user int not null foreign key references [user] (id) 
)

create table hoa_don(
 id int not null  primary key,
 ma nvarchar(50),
 tong_tien decimal(20,0),
 ten_nguoi_nhan nvarchar(max),
 ngay_nhan varchar(50),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int,
 tien_ship decimal(20,0),
 tien_sau_khi_giam_gia decimal(20,0),
id_user int not null foreign key references [user] (id) ,
id_phuong_thuc_thanh_toan int not null foreign key references phuong_thuc_thanh_toan (id) ,
id_hinh_thuc_giao_hang int not null foreign key references hinh_thuc_giao_hang (id) ,
id_dia_chi_sdt int not null foreign key references dia_chi (id) 
)

create table gio_hang(
 id int not null  primary key,
 ma nvarchar(50),
 ngay_sua varchar(50), 
 ngay_tao varchar(50),
 trang_thai int,
 id_user int not null foreign key references [user] (id) 
)

create table gio_hang_chi_tiet(
 id int not null  primary key,
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
 id int not null  primary key,
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
 id int not null  primary key,
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

