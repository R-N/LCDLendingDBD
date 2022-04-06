# Init - Root

CALL INSERT_PETUGAS_UNSAFE('admin', 'admin', 'Nama PETUGAS');

INSERT INTO Status_Barang(Status_Barang, Teks_Status_Barang) VALUES('A', 'Ada');
INSERT INTO Status_Barang(Status_Barang, Teks_Status_Barang) VALUES('P', 'Dipinjam');
INSERT INTO Status_Barang(Status_Barang, Teks_Status_Barang) VALUES('R', 'Rusak');

INSERT INTO Tipe_Barang(Tipe_Barang, Teks_Tipe_Barang) VALUES('L', 'LCD');
INSERT INTO Tipe_Barang(Tipe_Barang, Teks_Tipe_Barang) VALUES('K', 'Kabel');

INSERT INTO Status_Peminjaman(Status_Peminjaman, Teks_Status_Peminjaman) VALUES('A', 'Aktif');
INSERT INTO Status_Peminjaman(Status_Peminjaman, Teks_Status_Peminjaman) VALUES('S', 'Selesai');
INSERT INTO Status_Peminjaman(Status_Peminjaman, Teks_Status_Peminjaman) VALUES('B', 'Batal');

# Init - any


CALL LOGIN_PETUGAS('admin', MD5(CONCAT('admin', 'Petugas')), @nama, @sesi, @timeout);
SET @nomor=0;
SET @nomor=@nomor+1;
CALL INSERT_BARANG(@nomor, 'L', NULL, @sesi, @timeout);
CALL INSERT_BARANG(@nomor, 'K', NULL, @sesi, @timeout);
SET @nomor=@nomor+1;
CALL INSERT_BARANG(@nomor, 'L', NULL, @sesi, @timeout);
CALL INSERT_BARANG(@nomor, 'K', NULL, @sesi, @timeout);
SET @nomor=@nomor+1;
CALL INSERT_BARANG(@nomor, 'L', NULL, @sesi, @timeout);
CALL INSERT_BARANG(@nomor, 'K', NULL, @sesi, @timeout);
SET @nomor=@nomor+1;
CALL INSERT_BARANG(@nomor, 'L', NULL, @sesi, @timeout);
CALL INSERT_BARANG(@nomor, 'K', NULL, @sesi, @timeout);
SET @nomor=@nomor+1;
CALL INSERT_BARANG(@nomor, 'L', NULL, @sesi, @timeout);
CALL INSERT_BARANG(@nomor, 'K', NULL, @sesi, @timeout);
SET @nomor=@nomor+1;
CALL INSERT_BARANG(@nomor, 'L', NULL, @sesi, @timeout);
CALL INSERT_BARANG(@nomor, 'K', NULL, @sesi, @timeout);
SET @nomor=@nomor+1;
CALL INSERT_BARANG(@nomor, 'L', NULL, @sesi, @timeout);
CALL INSERT_BARANG(@nomor, 'K', NULL, @sesi, @timeout);
SET @nomor=@nomor+1;
CALL INSERT_BARANG(@nomor, 'L', NULL, @sesi, @timeout);
CALL INSERT_BARANG(@nomor, 'K', NULL, @sesi, @timeout);
SET @nomor=@nomor+1;
CALL INSERT_BARANG(@nomor, 'L', NULL, @sesi, @timeout);
CALL INSERT_BARANG(@nomor, 'K', NULL, @sesi, @timeout);
SET @nomor=@nomor+1;
CALL INSERT_BARANG(@nomor, 'L', NULL, @sesi, @timeout);
CALL INSERT_BARANG(@nomor, 'K', NULL, @sesi, @timeout);

# OK
CALL LOGIN_PETUGAS('admin', MD5(CONCAT('admin', 'Petugas')), @nama, @sesi, @timeout);
CALL UPDATE_DETAIL_BARANG(1, 'L', 'R', NULL, NULL, @sesi, @timeout);

# Error
CALL LOGIN_PETUGAS('admin', MD5(CONCAT('admin', 'Petugas')), @nama, @sesi, @timeout);
CALL UPDATE_DETAIL_BARANG(1, 'K', 'P', NULL, NULL, @sesi, @timeout);

# Error
CALL LOGIN_PETUGAS('admin', MD5(CONCAT('admin', 'Petugas')), @nama, @sesi, @timeout);
CALL UPDATE_DETAIL_BARANG(1, 'K', 'P', 1, NULL, @sesi, @timeout);

# OK
CALL LOGIN_PETUGAS('admin', MD5(CONCAT('admin', 'Petugas')), @nama, @sesi, @timeout);
CALL INSERT_PEMINJAM('H76217063', 'Rizqi', 'Mojo', '083830000200', @sesi, @timeout);

# OK
CALL LOGIN_PETUGAS('admin', MD5(CONCAT('admin', 'Petugas')), @nama, @sesi, @timeout);
CALL UPDATE_PEMINJAM('H76217063', 'M. Rizqi', 'Mojo Kidul', '083830000100', NULL, @sesi, @timeout);

# {
# OK 
CALL LOGIN_PETUGAS('admin', MD5(CONCAT('admin', 'Petugas')), @nama, @sesi, @timeout);
CALL INSERT_PEMINJAMAN('H76217063', NOW(), NULL, @sesi, @nomor, @timeout);

# ERROR
CALL UPDATE_DETAIL_BARANG(1, 'L', 'P', @nomor, NULL, @sesi,@timeout);

# OK
CALL UPDATE_DETAIL_BARANG(2, 'L', 'P', @nomor, NULL, @sesi, @timeout);

# OK
CALL INSERT_KEPERLUAN_PINJAM('DBD', 'Pak Khalid', 'LabKom 1', NOW(), NOW() + INTERVAL 1 HOUR, @nomor, @sesi, @id, @timeout);
# }

# OK
CALL LOGIN_PETUGAS('admin', MD5(CONCAT('admin', 'Petugas')), @nama, @sesi, @timeout);
CALL UPDATE_PEMINJAMAN(@nomor, NULL, NOW(), 'halo', @sesi, @timeout);
CALL INSERT_KEPERLUAN_PINJAM('PBO', 'Pak Teguh', 'LabKom 3', NOW(), NOW() + INTERVAL 1 HOUR, @nomor, @sesi, @id, @timeout);
CALL UPDATE_KEPERLUAN_PINJAM(@id, 'PBO2', 'Pak Yusuf', 'LabKom 1', NOW(), NOW() + INTERVAL 1 HOUR, @sesi, @timeout);
CALL DELETE_KEPERLUAN_PINJAM(@id, @sesi, @timeout);

# ERROR
CALL LOGIN_PETUGAS('admin', MD5(CONCAT('admin', 'Petugas')), @nama, @sesi, @timeout);
CALL UPDATE_DETAIL_PEMINJAMAN(@nomor, 'S', NOW(), NULL, @sesi, @timeout);

# OK
CALL LOGIN_PETUGAS('admin', MD5(CONCAT('admin', 'Petugas')), @nama, @sesi, @timeout);
CALL UPDATE_PEMINJAM('H76217063', NULL, NULL, '083830000200', 'Tidak bisa dihubungi' , @sesi, @timeout);

# ERROR
CALL LOGIN_PETUGAS('admin', MD5(CONCAT('admin', 'Petugas')), @nama, @sesi, @timeout);
CALL INSERT_PEMINJAMAN('H76217063', NOW(), NULL, @sesi, @nomor2, @timeout);

# OK
CALL LOGIN_PETUGAS('admin', MD5(CONCAT('admin', 'Petugas')), @nama, @sesi, @timeout);
CALL UPDATE_PEMINJAM('H76217063', NULL, NULL, NULL, NULL , @sesi, @timeout);


# {
# OK
CALL LOGIN_PETUGAS('admin', MD5(CONCAT('admin', 'Petugas')), @nama, @sesi, @timeout);
CALL INSERT_PEMINJAMAN('H76217063', NOW(), NULL, @sesi, @nomor2, @timeout);

# ERROR
CALL UPDATE_DETAIL_BARANG(2, 'L', 'P', @nomor2, NULL, @sesi, @timeout);
# }

# OK
CALL LOGIN_PETUGAS('admin', MD5(CONCAT('admin', 'Petugas')), @nama, @sesi, @timeout);
CALL UPDATE_DETAIL_PEMINJAMAN(@nomor2, 'B', NOW(), NULL, @sesi, @timeout);

# OK
CALL LOGIN_PETUGAS('admin', MD5(CONCAT('admin', 'Petugas')), @nama, @sesi, @timeout);
CALL UPDATE_DETAIL_BARANG(2, 'L', 'A', NULL, NULL, @sesi, @timeout);
CALL UPDATE_DETAIL_PEMINJAMAN(@nomor, 'S', NOW(), NULL, @sesi, @timeout);


# ERROR
CALL LOGIN_PETUGAS('admin', MD5(CONCAT('admin', 'Petugas')), @nama, @sesi, @timeout);
CALL UPDATE_DETAIL_BARANG(2, 'L', 'P', @nomor, NULL, @sesi, @timeout);