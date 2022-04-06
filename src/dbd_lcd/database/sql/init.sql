
CREATE USER 'client'@'%' IDENTIFIED BY 'client_password';

GRANT USAGE ON `database_name`.* TO 'client'@'%';

GRANT SELECT ON peminjam TO 'client'@'%';
GRANT SELECT ON barang TO 'client'@'%';
GRANT SELECT ON keperluan_pinjam TO 'client'@'%';
GRANT SELECT ON riwayat_barang TO 'client'@'%';
GRANT SELECT ON peminjaman TO 'client'@'%';
GRANT SELECT ON status_peminjaman TO 'client'@'%';
GRANT SELECT ON status_barang TO 'client'@'%';
GRANT SELECT ON tipe_barang TO 'client'@'%';

GRANT EXECUTE ON PROCEDURE DELETE_KEPERLUAN_PINJAM TO 'client'@'%';
GRANT EXECUTE ON PROCEDURE INSERT_BARANG TO 'client'@'%';
GRANT EXECUTE ON PROCEDURE INSERT_KEPERLUAN_PINJAM TO 'client'@'%';
GRANT EXECUTE ON PROCEDURE INSERT_PEMINJAM TO 'client'@'%';
GRANT EXECUTE ON PROCEDURE INSERT_PEMINJAMAN TO 'client'@'%';
GRANT EXECUTE ON PROCEDURE LOGIN_PETUGAS TO 'client'@'%';
GRANT EXECUTE ON PROCEDURE LOGOUT_PETUGAS TO 'client'@'%';
GRANT EXECUTE ON PROCEDURE PING_SESI TO 'client'@'%';
GRANT EXECUTE ON PROCEDURE UPDATE_DETAIL_BARANG TO 'client'@'%';
GRANT EXECUTE ON PROCEDURE UPDATE_DETAIL_PEMINJAMAN TO 'client'@'%';
GRANT EXECUTE ON PROCEDURE UPDATE_KEPERLUAN_PINJAM TO 'client'@'%';
GRANT EXECUTE ON PROCEDURE UPDATE_PEMINJAM TO 'client'@'%';
GRANT EXECUTE ON FUNCTION GET_NAMA_PETUGAS TO 'client'@'%';

CREATE VIEW Barang_Terkini AS SELECT 
    Nomor_Barang,
    Tipe_Barang,
    Status_Barang,
    Nomor_Peminjaman,
    Keterangan_Barang
FROM Riwayat_Barang
WHERE Waktu_Berakhir_Riwayat_Barang IS NULL;
GRANT SELECT ON Barang_Terkini TO 'client'@'%';


/*CREATE VIEW Peminjaman_Barang_Terakhir AS SELECT 
    RB.Nomor_Barang,
    RB.Tipe_Barang,
    RB.Status_Barang,
    RB.Nomor_Peminjaman,
    RB.Keterangan_Barang
FROM (SELECT * FROM Riwayat_Barang RB0 WHERE RB0.Status_Peminjaman='P') RB
    LEFT JOIN (SELECT * FROM Riwayat_Barang RB0 WHERE RB0.Status_Peminjaman='P') RB2
    ON RB.Nomor_Barang=RB2.Nomor_Barang
        AND RB.Tipe_Barang=RB2.Tipe_Barang
        AND RB.Waktu_Mulai_Riwayat_Barang < RB2.Waktu_Mulai_Riwayat_Barang
WHERE RB2.Id_Riwayat_Barang IS NULL;

GRANT SELECT ON Peminjaman_Barang_Terakhir TO 'client'@'%';
*/


INSERT INTO Status_Barang(Status_Barang, Teks_Status_Barang) VALUES('A', 'Ada');
INSERT INTO Status_Barang(Status_Barang, Teks_Status_Barang) VALUES('P', 'Dipinjam');
INSERT INTO Status_Barang(Status_Barang, Teks_Status_Barang) VALUES('R', 'Rusak');

INSERT INTO Tipe_Barang(Tipe_Barang, Teks_Tipe_Barang) VALUES('L', 'LCD');
INSERT INTO Tipe_Barang(Tipe_Barang, Teks_Tipe_Barang) VALUES('K', 'Kabel');

INSERT INTO Status_Peminjaman(Status_Peminjaman, Teks_Status_Peminjaman) VALUES('A', 'Aktif');
INSERT INTO Status_Peminjaman(Status_Peminjaman, Teks_Status_Peminjaman) VALUES('S', 'Selesai');
INSERT INTO Status_Peminjaman(Status_Peminjaman, Teks_Status_Peminjaman) VALUES('B', 'Batal');

CALL INSERT_PETUGAS_UNSAFE('admin', 'admin_password', 'Nama Admin');