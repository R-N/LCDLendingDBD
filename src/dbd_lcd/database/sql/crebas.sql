/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     04/01/2019 8:14:11                           */
/*==============================================================*/


/*==============================================================*/
/* Table: BARANG                                                */
/*==============================================================*/
create table BARANG
(
   TIPE_BARANG          char(1) not null  comment '',
   NOMOR_BARANG         int not null  comment '',
   primary key (TIPE_BARANG, NOMOR_BARANG)
);

/*==============================================================*/
/* Table: KEPERLUAN_PINJAM                                      */
/*==============================================================*/
create table KEPERLUAN_PINJAM
(
   ID_KEPERLUAN_PINJAM  int not null auto_increment  comment '',
   NOMOR_PEMINJAMAN     int not null  comment '',
   KEPERLUAN_PINJAM     varchar(64) not null  comment '',
   NAMA_PENANGGUNG_JAWAB varchar(64) not null  comment '',
   RUANG_PINJAM         varchar(64) not null  comment '',
   WAKTU_MULAI          datetime not null  comment '',
   WAKTU_SELESAI        datetime not null  comment '',
   primary key (ID_KEPERLUAN_PINJAM)
);

/*==============================================================*/
/* Table: PEMINJAM                                              */
/*==============================================================*/
create table PEMINJAM
(
   IDENTITAS_PEMINJAM   varchar(18) not null  comment '',
   USERNAME_PETUGAS     varchar(64) not null  comment '',
   NAMA_PEMINJAM        varchar(64) not null  comment '',
   ALAMAT_PEMINJAM      varchar(256) not null  comment '',
   NOMOR_HP_PEMINJAM    varchar(14) not null  comment '',
   KETERANGAN_BLACKLIST varchar(256)  comment '',
   WAKTU_ADMIN_PEMINJAM_TERAKHIR datetime not null  comment '',
   primary key (IDENTITAS_PEMINJAM)
);

/*==============================================================*/
/* Table: PEMINJAMAN                                            */
/*==============================================================*/
create table PEMINJAMAN
(
   NOMOR_PEMINJAMAN     int not null auto_increment  comment '',
   USERNAME_PETUGAS     varchar(64)  comment '',
   IDENTITAS_PEMINJAM   varchar(18) not null  comment '',
   STATUS_PEMINJAMAN    char(1) not null  comment '',
   KETERANGAN_PEMINJAMAN varchar(256)  comment '',
   WAKTU_PINJAM         datetime not null  comment '',
   WAKTU_HARUS_KEMBALI  datetime not null  comment '',
   WAKTU_KEMBALI        datetime  comment '',
   WAKTU_ADMIN_PEMINJAMAN_TERAKHIR datetime not null  comment '',
   primary key (NOMOR_PEMINJAMAN)
);

/*==============================================================*/
/* Table: PETUGAS                                               */
/*==============================================================*/
create table PETUGAS
(
   USERNAME_PETUGAS     varchar(64) not null  comment '',
   NAMA_PETUGAS         varchar(64) not null  comment '',
   HASHPASS_PETUGAS     varchar(64) not null  comment '',
   SESI_PETUGAS         varchar(64)  comment '',
   TIMEOUT_SESI_PETUGAS datetime  comment '',
   primary key (USERNAME_PETUGAS)
);

/*==============================================================*/
/* Table: RIWAYAT_BARANG                                        */
/*==============================================================*/
create table RIWAYAT_BARANG
(
   ID_RIWAYAT_BARANG    int not null auto_increment  comment '',
   TIPE_BARANG          char(1) not null  comment '',
   NOMOR_BARANG         int not null  comment '',
   USERNAME_PETUGAS     varchar(64) not null  comment '',
   NOMOR_PEMINJAMAN     int  comment '',
   STATUS_BARANG        char(1) not null  comment '',
   KETERANGAN_BARANG    varchar(256)  comment '',
   WAKTU_MULAI_RIWAYAT_BARANG datetime not null  comment '',
   WAKTU_BERAKHIR_RIWAYAT_BARANG datetime  comment '',
   primary key (ID_RIWAYAT_BARANG)
);

/*==============================================================*/
/* Index: UNIQUE_START_TIMELINE                                 */
/*==============================================================*/
create unique index UNIQUE_START_TIMELINE on RIWAYAT_BARANG
(
   TIPE_BARANG,
   NOMOR_BARANG,
   WAKTU_MULAI_RIWAYAT_BARANG
);

/*==============================================================*/
/* Index: UNIQUE_END_TIMELINE                                   */
/*==============================================================*/
create unique index UNIQUE_END_TIMELINE on RIWAYAT_BARANG
(
   TIPE_BARANG,
   NOMOR_BARANG,
   WAKTU_BERAKHIR_RIWAYAT_BARANG
);

/*==============================================================*/
/* Table: STATUS_BARANG                                         */
/*==============================================================*/
create table STATUS_BARANG
(
   STATUS_BARANG        char(1) not null  comment '',
   TEKS_STATUS_BARANG   varchar(16)  comment '',
   primary key (STATUS_BARANG)
);

/*==============================================================*/
/* Table: STATUS_PEMINJAMAN                                     */
/*==============================================================*/
create table STATUS_PEMINJAMAN
(
   STATUS_PEMINJAMAN    char(1) not null  comment '',
   TEKS_STATUS_PEMINJAMAN varchar(16)  comment '',
   primary key (STATUS_PEMINJAMAN)
);

/*==============================================================*/
/* Table: TIPE_BARANG                                           */
/*==============================================================*/
create table TIPE_BARANG
(
   TIPE_BARANG          char(1) not null  comment '',
   TEKS_TIPE_BARANG     varchar(16)  comment '',
   primary key (TIPE_BARANG)
);

alter table BARANG add constraint FK_BARANG_BERTIPE_TIPE_BAR foreign key (TIPE_BARANG)
      references TIPE_BARANG (TIPE_BARANG) on delete restrict on update cascade;

alter table KEPERLUAN_PINJAM add constraint FK_KEPERLUA_PINJAM_UN_PEMINJAM foreign key (NOMOR_PEMINJAMAN)
      references PEMINJAMAN (NOMOR_PEMINJAMAN) on delete restrict on update restrict;

alter table PEMINJAM add constraint FK_PEMINJAM_ADMINISTR_PETUGAS foreign key (USERNAME_PETUGAS)
      references PETUGAS (USERNAME_PETUGAS) on delete restrict on update restrict;

alter table PEMINJAMAN add constraint FK_PEMINJAMAN_ADMINISTR_PETUGAS foreign key (USERNAME_PETUGAS)
      references PETUGAS (USERNAME_PETUGAS) on delete restrict on update restrict;

alter table PEMINJAMAN add constraint FK_PEMINJAM_MELAKUKAN_PEMINJAM foreign key (IDENTITAS_PEMINJAM)
      references PEMINJAM (IDENTITAS_PEMINJAM) on delete restrict on update cascade;

alter table PEMINJAMAN add constraint FK_PEMINJAM_PEMINJAMA_STATUS_P foreign key (STATUS_PEMINJAMAN)
      references STATUS_PEMINJAMAN (STATUS_PEMINJAMAN) on delete restrict on update cascade;

alter table RIWAYAT_BARANG add constraint FK_RIWAYAT__ADMINISTR_PETUGAS foreign key (USERNAME_PETUGAS)
      references PETUGAS (USERNAME_PETUGAS) on delete restrict on update restrict;

alter table RIWAYAT_BARANG add constraint FK_RIWAYAT__BARANG_BE_STATUS_B foreign key (STATUS_BARANG)
      references STATUS_BARANG (STATUS_BARANG) on delete restrict on update cascade;

alter table RIWAYAT_BARANG add constraint FK_RIWAYAT__BARANG_DI_PEMINJAM foreign key (NOMOR_PEMINJAMAN)
      references PEMINJAMAN (NOMOR_PEMINJAMAN) on delete restrict on update restrict;

alter table RIWAYAT_BARANG add constraint FK_RIWAYAT__MEMILIKI__BARANG foreign key (TIPE_BARANG, NOMOR_BARANG)
      references BARANG (TIPE_BARANG, NOMOR_BARANG) on delete restrict on update cascade;


DELIMITER //
create DEFINER = 'root'@'localhost' procedure PING_SESI(
    IN Sesi CHAR(32), 
    OUT Username VARCHAR(16),
    OUT Timeout DATETIME
)
SQL SECURITY DEFINER
BEGIN
    DECLARE datetime_now DATETIME;
    
    SET datetime_now = NOW();
    SELECT Username_Petugas INTO Username FROM Petugas WHERE Sesi_Petugas=Sesi AND Timeout_Sesi_Petugas > datetime_now;
    IF (SELECT ROW_COUNT()) = 0 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Sesi tidak ada atau sudah hangus';
    END IF;
    
    SET Timeout = datetime_now + INTERVAL 5 MINUTE;
    UPDATE Petugas SET Timeout_Sesi_Petugas=Timeout WHERE Username_Petugas=Username;
    
    COMMIT;
    
END//
DELIMITER ;


DELIMITER //
create DEFINER = 'root'@'localhost' procedure DELETE_KEPERLUAN_PINJAM(
    IN Id INTEGER, 
    IN Sesi CHAR(32), 
    OUT Timeout DATETIME
)
SQL SECURITY DEFINER
BEGIN
    DECLARE username VARCHAR(16);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    CALL Ping_Sesi(Sesi, username, Timeout);
    
    START TRANSACTION;
    
    DELETE FROM Keperluan_Pinjam
    WHERE Id_Keperluan_Pinjam=Id;
    
    IF Row_Count() = 0 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Keperluan pinjam tidak ditemukan';
    END IF;
    
    UPDATE Peminjaman P
    SET P.Username_Petugas=username,
        P.Waktu_Admin_Peminjaman_Terakhir=NOW()
    WHERE P.Nomor_Peminjaman=(
        SELECT KP.Nomor_Peminjaman
        FROM Keperluan_Pinjam KP
        WHERE KP.Id_Keperluan_Pinjam=Id_Keperluan_Pinjam
        LIMIT 1
    );
    
    COMMIT;
END//
DELIMITER ;


DELIMITER //
create DEFINER = 'root'@'localhost' function GET_NAMA_PETUGAS(Username VARCHAR(16))
RETURNS VARCHAR(64)
SQL SECURITY DEFINER
BEGIN
    DECLARE Nama VARCHAR(64);
    SELECT P.Nama_Petugas INTO Nama FROM Petugas P WHERE P.Username_Petugas=Username;
    RETURN Nama;
END //
DELIMITER ;


DELIMITER //
create DEFINER = 'root'@'localhost' procedure HITUNG_WAKTU_HARUS_KEMBALI(
    IN Nomor_Peminjaman INTEGER
)
SQL SECURITY DEFINER
BEGIN
    DECLARE err VARCHAR(64);
    DECLARE waktu_harus_kembali DATETIME;
    DECLARE username VARCHAR(16);
    
    
    
    SET waktu_harus_kembali=(
        SELECT MAX(KP.Waktu_Selesai)+INTERVAL 30 MINUTE
        FROM Keperluan_Pinjam KP
        WHERE KP.Nomor_Peminjaman=Nomor_Peminjaman
    );
    
    
    
    UPDATE Peminjaman P
    SET
        P.Waktu_Harus_Kembali=waktu_harus_kembali,
        P.Waktu_Admin_Peminjaman_Terakhir=NOW()
    WHERE P.Nomor_Peminjaman=Nomor_Peminjaman;
    
    
END//
DELIMITER ;


DELIMITER //
create DEFINER = 'root'@'localhost' procedure INSERT_BARANG(
    IN Nomor_Barang INTEGER,
    IN Tipe_Barang CHAR(1),
    IN Keterangan_Barang VARCHAR(256), 
    IN Sesi CHAR(32), 
    OUT Timeout DATETIME
)
SQL SECURITY DEFINER
BEGIN
    DECLARE username VARCHAR(16);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    CALL Ping_Sesi(Sesi, username, Timeout);
    
    START TRANSACTION;
    
    IF EXISTS (SELECT * FROM Barang B WHERE B.Nomor_Barang=Nomor_Barang AND B.Tipe_Barang=Tipe_Barang) THEN
        IF EXISTS (SELECT * FROM Riwayat_Barang RB WHERE RB.Nomor_Barang=Nomor_Barang AND RB.Tipe_Barang=Tipe_Barang AND RB.Waktu_Berakhir_Riwayat_Barang IS NULL) THEN
            SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Barang sudah ada';
        END IF;
    ELSE
        INSERT INTO Barang(Nomor_Barang, Tipe_Barang) VALUES(Nomor_Barang, Tipe_Barang);
    END IF;
    
    INSERT INTO Riwayat_Barang(
        Tipe_Barang,
        Nomor_Barang,
        Username_Petugas,
        Status_Barang,
        Keterangan_Barang,
        Waktu_Mulai_Riwayat_Barang
    ) VALUES (
        Tipe_Barang,
        Nomor_Barang,
        username,
        'A',
        Keterangan_Barang,
        NOW()
    );
    
    COMMIT;
    
END//
DELIMITER ;


DELIMITER //
create DEFINER = 'root'@'localhost' procedure INSERT_KEPERLUAN_PINJAM (
    IN Keperluan_Pinjam VARCHAR(64),
    IN Nama_Penanggung_Jawab VARCHAR(64),
    IN Ruang_Pinjam VARCHAR(16),
    IN Waktu_Mulai DATETIME,
    IN Waktu_Selesai DATETIME,
    IN Nomor_Peminjaman INTEGER,
    IN Sesi CHAR(32),
    OUT Id_Keperluan_Pinjam INTEGER,
    OUT Timeout DATETIME
)
SQL SECURITY DEFINER
BEGIN
    DECLARE username VARCHAR(16);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    
    START TRANSACTION;
    
    CALL Ping_Sesi(Sesi, username, Timeout);
    
    INSERT INTO Keperluan_Pinjam(
        Nomor_Peminjaman,
        Keperluan_Pinjam,
        Nama_Penanggung_Jawab,
        Ruang_Pinjam,
        Waktu_Mulai,
        Waktu_Selesai
    ) VALUES (
        Nomor_Peminjaman,
        Keperluan_Pinjam,
        Nama_Penanggung_Jawab,
        Ruang_Pinjam,
        Waktu_Mulai,
        Waktu_Selesai
    );
    SET Id_Keperluan_Pinjam = LAST_INSERT_ID();
    
    UPDATE Peminjaman P
    SET P.Username_Petugas=username,
        P.Waktu_Admin_Peminjaman_Terakhir=NOW()
    WHERE P.Nomor_Peminjaman=(
        SELECT KP.Nomor_Peminjaman
        FROM Keperluan_Pinjam KP
        WHERE KP.Id_Keperluan_Pinjam=Id_Keperluan_Pinjam
        LIMIT 1
    );
    
    COMMIT;
END//
DELIMITER ;


DELIMITER //
create DEFINER = 'root'@'localhost' procedure INSERT_PEMINJAM(
    IN Identitas_Peminjam VARCHAR(18),
    IN Nama_Peminjam VARCHAR(64),
    IN Alamat_Peminjam VARCHAR(256),
    IN Nomor_HP_Peminjam VARCHAR(14),
    IN Sesi CHAR(32), 
    OUT Timeout DATETIME
)
SQL SECURITY DEFINER
BEGIN
    DECLARE username VARCHAR(16);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    CALL Ping_Sesi(Sesi, username, Timeout);
    
    START TRANSACTION;
    
    INSERT INTO Peminjam  (
        Identitas_Peminjam,
        Nama_Peminjam,
        Alamat_Peminjam,
        Nomor_HP_Peminjam,
        Username_Petugas,
        Waktu_Admin_Peminjam_Terakhir
    ) VALUES (
        Identitas_Peminjam,
        Nama_Peminjam,
        Alamat_Peminjam,
        Nomor_HP_Peminjam,
        username,
        NOW()
    );
    
    COMMIT;
    
END//
DELIMITER ;


DELIMITER //
create DEFINER = 'root'@'localhost' procedure INSERT_PEMINJAMAN(
    IN Identitas_Peminjam VARCHAR(18),
    IN Waktu_Harus_Kembali DATETIME,
    IN Keterangan_Peminjaman VARCHAR(256),
    IN Sesi_Petugas CHAR(32),
    OUT Nomor_Peminjaman INTEGER,
    OUT Timeout DATETIME
)
SQL SECURITY DEFINER
BEGIN
    DECLARE username VARCHAR(16);
    DECLARE waktu DATETIME;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    CALL PING_SESI(Sesi_Petugas, username, Timeout);
    
    SET waktu=NOW();
    
    START TRANSACTION;
    
    
    INSERT INTO Peminjaman(
        Username_Petugas,
        Identitas_Peminjam,
        Status_Peminjaman,
        Keterangan_Peminjaman,
        Waktu_Pinjam,
        Waktu_Harus_Kembali,
        Waktu_Admin_Peminjaman_Terakhir
    ) VALUES (
        username,
        Identitas_Peminjam,
        'A',
        Keterangan_Peminjaman,
        waktu,
        Waktu_Harus_Kembali,
        waktu
    );
    SET Nomor_Peminjaman = LAST_INSERT_ID();
    
    COMMIT;
    
END//
DELIMITER ;


DELIMITER //
CREATE DEFINER = 'root'@'localhost' PROCEDURE INSERT_PETUGAS_UNSAFE (
    IN Username VARCHAR(16), 
    IN Password VARCHAR(16),
    IN Nama VARCHAR(64)
)
SQL SECURITY INVOKER
BEGIN
    IF Password IS NULL OR Password = ' ' OR CHAR_LENGTH(Password) < 4 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Password tidak valid';
    END IF;

    INSERT INTO Petugas(
        Username_Petugas, 
        Hashpass_Petugas, 
        Nama_Petugas
    ) VALUES(
        Username, 
        MD5(CONCAT(MD5(CONCAT(Password, 'Petugas')), 'Admin')),
        Nama
    );
    
END//
DELIMITER ;


DELIMITER //
CREATE DEFINER = 'root'@'localhost' PROCEDURE LOGIN_PETUGAS (IN Username VARCHAR(16), IN Hashpass CHAR(32), OUT Nama VARCHAR(64), OUT Sesi CHAR(32), OUT Timeout DATETIME)
SQL SECURITY DEFINER
BEGIN
    DECLARE datetime_now DATETIME;
    
    IF Username IS NULL OR Username = ' ' OR CHAR_LENGTH(Username) < 4 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Username tidak valid';
    END IF;
    IF Hashpass IS NULL OR Hashpass = ' ' OR CHAR_LENGTH(Hashpass) < 32 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Hashpass tidak valid';
    END IF;
    IF NOT EXISTS (SELECT * FROM Petugas WHERE Petugas.Username_Petugas = Username AND Petugas.Hashpass_Petugas = MD5(CONCAT(Hashpass, 'Admin'))) THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Username atau password salah';
    END IF;
    SET datetime_now = NOW();
    SET Sesi = MD5(CONCAT(datetime_now, Username));
    WHILE EXISTS (SELECT * FROM Petugas WHERE Sesi_Petugas=Sesi) DO
        SET datetime_now = NOW();
        SET Sesi = MD5(CONCAT(datetime_now, Username));
    END WHILE;
    SET Timeout = datetime_now + INTERVAL 5 MINUTE;
    
    UPDATE Petugas SET Sesi_Petugas = Sesi, Timeout_Sesi_Petugas=Timeout WHERE Username_Petugas=Username;
    SELECT Nama_Petugas INTO Nama FROM Petugas WHERE Sesi_Petugas=Sesi;
END//
DELIMITER ;


DELIMITER //
CREATE DEFINER = 'root'@'localhost' PROCEDURE LOGIN_PETUGAS_UNSAFE (IN Username VARCHAR(16), IN Password VARCHAR(16), OUT Nama VARCHAR(64), OUT Sesi CHAR(32), OUT Timeout DATETIME)
SQL SECURITY INVOKER
BEGIN
    IF Password IS NULL OR Password = ' ' OR CHAR_LENGTH(Password) < 4 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Password tidak valid';
    END IF;
    CALL Login_Petugas(Username, MD5(CONCAT(Password, 'Petugas')), Nama, Sesi, Timeout);
END//
DELIMITER ;


DELIMITER //
create DEFINER = 'root'@'localhost' procedure LOGOUT_PETUGAS(
    IN Sesi_Petugas CHAR(32)
)
SQL SECURITY DEFINER
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
   
    
    START TRANSACTION;
    
    UPDATE Petugas P
    SET P.Sesi_Petugas=NULL,
        P.Timeout_Sesi_Petugas=NULL
    WHERE P.Sesi_Petugas=Sesi;
    
    
    IF Row_Count() = 0 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Sesi tidak ada';
    END IF;
    COMMIT;
    
END//
DELIMITER ;


DELIMITER //
create DEFINER = 'root'@'localhost' procedure UPDATE_DETAIL_BARANG(
    IN Nomor_Barang INTEGER,
    IN Tipe_Barang CHAR(1),
    IN Status_Barang CHAR(1),
    IN Nomor_Peminjaman INTEGER,
    IN Keterangan_Barang VARCHAR(256),
    IN Sesi CHAR(32), 
    OUT Timeout DATETIME
)
SQL SECURITY DEFINER
proc_label: BEGIN
    DECLARE username VARCHAR(16);
    DECLARE id_riwayat_barang_lama INTEGER;
    DECLARE nomor_peminjaman_lama INTEGER;
    DECLARE status_barang_lama CHAR(1);
    DECLARE keterangan_barang_lama VARCHAR(256);
    DECLARE waktu DATETIME;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    
    CALL Ping_Sesi(Sesi, username, Timeout);
    
    START TRANSACTION;
    
    SELECT 
        RB.Id_Riwayat_Barang,
        RB.Status_Barang, 
        RB.Nomor_Peminjaman, 
        RB.Keterangan_Barang 
    INTO 
        id_riwayat_barang_lama,
        status_barang_lama, 
        nomor_peminjaman_lama, 
        keterangan_barang_lama 
    FROM Riwayat_Barang RB 
    WHERE RB.Nomor_Barang=Nomor_Barang
        AND RB.Tipe_Barang=Tipe_Barang
        AND RB.Waktu_Berakhir_Riwayat_Barang IS NULL;
        
    IF Status_Barang = status_barang_lama
        AND Nomor_Peminjaman = nomor_peminjaman_lama
        AND Keterangan_Barang = keterangan_barang_lama THEN
        
        LEAVE proc_label;
    END IF;
        
    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Barang tidak ada';
    END IF;
    
    IF Status_Barang='P' AND Nomor_Peminjaman IS NULL THEN
        SET Nomor_Peminjaman=nomor_peminjaman_lama;
        IF Nomor_Peminjaman IS NULL THEN 
            SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Nomor peminjaman harus diisi untuk barang berstatus dipinjam';    
        END IF;
    END IF;
    
    IF Status_Barang='P' AND Status_Barang=status_barang_lama THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Barang masih dipinjam.';
    END IF;
    
    IF Status_Barang<>'P' AND Nomor_Peminjaman IS NOT NULL THEN
        SET Nomor_Peminjaman=NULL;
    END IF;
    
    IF Keterangan_Barang IS NULL THEN
        SET Keterangan_Barang=keterangan_barang_lama;
    END IF;
    
    SET waktu=NOW();
    
    
        
        
    IF Status_Barang <> status_barang_lama THEN
        IF Status_Barang = 'P' AND status_barang_lama <> 'A' THEN
            SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Barang tidak bisa dipinjam';
        END IF;
        IF Status_Barang <> 'A' AND status_barang_lama = 'P' THEN
            SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Barang masih dipinjam';
        END IF;
        
        IF Status_Barang='P' THEN
            IF (
                SELECT P.Status_Peminjaman 
                FROM Peminjaman P
                WHERE P.Nomor_Peminjaman=Nomor_Peminjaman
            ) <> 'A' THEN
                SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Peminjaman tidak aktif';
            END IF;
        END IF;
            
    ELSEIF Status_Barang='P' AND Nomor_Peminjaman<>nomor_peminjaman_lama THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Barang masih dipinjam';
    END IF;
    
    UPDATE Riwayat_Barang RB 
    SET RB.Waktu_Berakhir_Riwayat_Barang=waktu 
    WHERE RB.Nomor_Barang=Nomor_Barang
        AND RB.Tipe_Barang=Tipe_Barang
        AND RB.Waktu_Berakhir_Riwayat_Barang IS NULL;
    
    INSERT INTO Riwayat_Barang(
        Tipe_Barang,
        Nomor_Barang,
        Username_Petugas,
        Nomor_Peminjaman,
        Status_Barang,
        Keterangan_Barang,
        Waktu_Mulai_Riwayat_Barang
    ) VALUES (
        Tipe_Barang,
        Nomor_Barang,
        username,
        Nomor_Peminjaman,
        Status_Barang,
        Keterangan_Barang,
        waktu
    );
    
    IF Status_Barang = 'A' AND status_barang_lama = 'P' THEN
        UPDATE Peminjaman P
        SET P.Username_Petugas=username,
            P.Waktu_Admin_Peminjaman_Terakhir=NOW()
        WHERE P.Nomor_Peminjaman=nomor_peminjaman_lama;
    END IF;
    
    IF nomor_peminjaman_lama IS NOT NULL AND NOT EXISTS (SELECT * FROM Barang_Terkini BT WHERE BT.Nomor_Peminjaman=nomor_peminjaman_lama) THEN
        UPDATE Peminjaman P
        SET P.Username_Petugas=username,
            P.Waktu_Admin_Peminjaman_Terakhir=NOW(),
            P.Waktu_Kembali=NOW(),
            P.Status_Peminjaman='S'
        WHERE P.Nomor_Peminjaman=nomor_peminjaman_lama;
    END IF;
    COMMIT;
    
END//
DELIMITER ;


DELIMITER //
create DEFINER = 'root'@'localhost' procedure UPDATE_DETAIL_PEMINJAMAN(
    IN Nomor_Peminjaman INTEGER,
    IN Status_Peminjaman CHAR(1),
    IN Waktu_Harus_Kembali DATETIME,
    IN Keterangan_Peminjaman VARCHAR(256),
    IN Sesi_Petugas CHAR(32),
    OUT Timeout DATETIME
)
SQL SECURITY DEFINER
BEGIN
    DECLARE username VARCHAR(16);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    CALL PING_SESI(Sesi_Petugas, username, Timeout);
    
    START TRANSACTION;
    
    UPDATE Peminjaman P
    SET
        P.Status_Peminjaman=Status_Peminjaman,
        P.Keterangan_Peminjaman=Keterangan_Peminjaman,
        P.Waktu_Harus_Kembali=Waktu_Harus_Kembali,
        P.Username_Petugas=username,
        P.Waktu_Admin_Peminjaman_Terakhir=NOW()
    WHERE P.Nomor_Peminjaman=Nomor_Peminjaman;
    
    
    IF Row_Count() = 0 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Peminjaman tidak ditemukan';
    END IF;
    COMMIT;
    
END//
DELIMITER ;


DELIMITER //
create DEFINER = 'root'@'localhost' procedure UPDATE_KEPERLUAN_PINJAM(
    IN Id_Keperluan_Pinjam INTEGER, 
    IN Keperluan_Pinjam VARCHAR(64),
    IN Nama_Penanggung_Jawab VARCHAR(64),
    IN Ruang_Pinjam VARCHAR(16), 
    IN Waktu_Mulai DATETIME,
    IN Waktu_Selesai DATETIME,
    IN Sesi CHAR(32), 
    OUT Timeout DATETIME
)
SQL SECURITY DEFINER
BEGIN
    DECLARE username VARCHAR(16);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    CALL Ping_Sesi(Sesi, username, Timeout);
    
    START TRANSACTION;
    
    IF NOT EXISTS(SELECT * FROM Peminjaman P WHERE P.Nomor_Peminjaman=Nomor_Peminjaman) THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Peminjaman tidak ada';
    END IF;
    
    UPDATE Keperluan_Pinjam KP
    SET KP.Keperluan_Pinjam = Keperluan_Pinjam,
        KP.Nama_Penanggung_Jawab = Nama_Penanggung_Jawab,
        KP.Ruang_Pinjam = Ruang_Pinjam,
        KP.Waktu_Mulai = Waktu_Mulai,
        KP.Waktu_Selesai = Waktu_Selesai
    WHERE KP.Id_Keperluan_Pinjam=Id_Keperluan_Pinjam;
    
    IF Row_Count() = 0 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Keperluan pinjam tidak ditemukan';
    END IF;
    
    UPDATE Peminjaman P
    SET P.Username_Petugas=username,
        P.Waktu_Admin_Peminjaman_Terakhir=NOW()
    WHERE P.Nomor_Peminjaman=(
        SELECT KP.Nomor_Peminjaman
        FROM Keperluan_Pinjam KP
        WHERE KP.Id_Keperluan_Pinjam=Id_Keperluan_Pinjam
        LIMIT 1
    );
    
    COMMIT;
END//
DELIMITER ;


DELIMITER //
create DEFINER = 'root'@'localhost' procedure UPDATE_PEMINJAM(
    IN Identitas_Peminjam VARCHAR(18),
    IN Nama_Peminjam VARCHAR(64),
    IN Alamat_Peminjam VARCHAR(256),
    IN Nomor_HP_Peminjam VARCHAR(14),
    IN Keterangan_Blacklist VARCHAR(256),
    IN Sesi_Petugas CHAR(32),
    OUT Timeout DATETIME
)
SQL SECURITY DEFINER
BEGIN
    DECLARE username VARCHAR(16);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION, NOT FOUND
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    CALL PING_SESI(Sesi_Petugas, username, Timeout);
    
    START TRANSACTION;
    
    UPDATE Peminjam P
    SET
        P.Nama_Peminjam=Nama_Peminjam,
        P.Alamat_Peminjam=Alamat_Peminjam,
        P.Nomor_HP_Peminjam=Nomor_HP_Peminjam,
        P.Keterangan_Blacklist=Keterangan_Blacklist,
        P.Username_Petugas=username,
        P.Waktu_Admin_Peminjam_Terakhir=NOW()
    WHERE P.Identitas_Peminjam=Identitas_Peminjam;
    
    
    IF Row_Count() = 0 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Peminjam tidak ditemukan';
    END IF;
    COMMIT;
    
END//
DELIMITER ;


DELIMITER //
create trigger AFTER_DELETE_KEPERLUAN_PINJAM after delete
on KEPERLUAN_PINJAM for each row
begin
    CALL HITUNG_WAKTU_HARUS_KEMBALI(OLD.Nomor_Peminjaman);
end//
DELIMITER ;


DELIMITER //
create trigger AFTER_INSERT_KEPERLUAN_PINJAM after insert
on KEPERLUAN_PINJAM for each row
begin
    CALL HITUNG_WAKTU_HARUS_KEMBALI(NEW.Nomor_Peminjaman);
end//
DELIMITER ;


DELIMITER //
create trigger AFTER_UPDATE_KEPERLUAN_PINJAM after update
on KEPERLUAN_PINJAM for each row
begin
    CALL HITUNG_WAKTU_HARUS_KEMBALI(NEW.Nomor_Peminjaman);
end//
DELIMITER ;


DELIMITER //
create trigger BEFORE_INSERT_KEPERLUAN before insert
on KEPERLUAN_PINJAM for each row
begin

    IF NEW.Keperluan_Pinjam IS NULL OR NEW.Keperluan_Pinjam = ' ' THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Keperluan_Pinjam tidak valid';
    END IF;
    IF NEW.Nama_Penanggung_Jawab IS NULL OR NEW.Nama_Penanggung_Jawab = ' ' OR CHAR_LENGTH(NEW.Nama_Penanggung_Jawab) < 3 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Nama_Penanggung_Jawab tidak valid';
    END IF;
    IF NEW.Ruang_Pinjam IS NULL OR NEW.Ruang_Pinjam = ' ' OR CHAR_LENGTH(NEW.Ruang_Pinjam) < 4 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Ruang_Pinjam tidak valid';
    END IF;
    IF NEW.Waktu_Mulai IS NULL THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Waktu_Mulai tidak boleh kosong';
    END IF;
    IF NEW.Waktu_Selesai IS NULL THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Waktu_Selesai tidak boleh kosong';
    END IF;
    
    IF NEW.Waktu_Selesai <= NEW.Waktu_Mulai THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Waktu mulai dan selesai tidak valid';
    END IF;
    
end//
DELIMITER ;


DELIMITER //
create trigger BEFORE_UPDATE_KEPERLUAN before update
on KEPERLUAN_PINJAM for each row
begin
    IF NEW.Nomor_Peminjaman IS NULL THEN
        SET NEW.Nomor_Peminjaman = OLD.Nomor_Peminjaman;
    END IF;
    IF NEW.Keperluan_Pinjam IS NULL THEN
        SET NEW.Keperluan_Pinjam = OLD.Keperluan_Pinjam;
    END IF;
    IF NEW.Nama_Penanggung_Jawab IS NULL THEN
        SET NEW.Nama_Penanggung_Jawab = OLD.Nama_Penanggung_Jawab;
    END IF;
    IF NEW.Ruang_Pinjam IS NULL THEN
        SET NEW.Ruang_Pinjam = OLD.Ruang_Pinjam;
    END IF;
    IF NEW.Waktu_Mulai IS NULL THEN
        SET NEW.Waktu_Mulai = OLD.Waktu_Mulai;
    END IF;
    IF NEW.Waktu_Selesai IS NULL THEN
        SET NEW.Waktu_Selesai = OLD.Waktu_Selesai;
    END IF;
    
    IF NEW.Waktu_Selesai <= NEW.Waktu_Mulai THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Waktu mulai dan selesai tidak valid';
    END IF;
end//
DELIMITER ;


DELIMITER //
create trigger BEFORE_INSERT_PEMINJAM before insert
on PEMINJAM for each row
begin
    
    IF NEW.Nama_Peminjam = ' ' OR CHAR_LENGTH(NEW.Nama_Peminjam) < 3 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Nama peminjam tidak valid';
    END IF;
    IF NEW.Alamat_Peminjam = ' ' OR CHAR_LENGTH(NEW.Alamat_Peminjam) < 3 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Alamat peminjam tidak valid';
    END IF;
    IF NEW.Identitas_Peminjam = ' ' OR CHAR_LENGTH(NEW.Identitas_Peminjam) < 9 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Identitas peminjam  tidak valid';
    END IF;
    IF NEW.Nomor_HP_Peminjam = ' ' OR CHAR_LENGTH(NEW.Nomor_HP_Peminjam) < 9 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Nomor HP peminjam tidak valid';
    END IF;
end//
DELIMITER ;


DELIMITER //
create trigger BEFORE_UPDATE_PEMINJAM before update
on PEMINJAM for each row
begin
    IF NEW.Nama_Peminjam IS NULL THEN
        SET NEW.Nama_Peminjam=OLD.Nama_Peminjam;
    END IF;
    IF NEW.Alamat_Peminjam IS NULL THEN
        SET NEW.Alamat_Peminjam=OLD.Alamat_Peminjam;
    END IF;
    IF NEW.Identitas_Peminjam IS NULL THEN
        SET NEW.Identitas_Peminjam=OLD.Identitas_Peminjam;
    END IF;
    IF NEW.Nomor_HP_Peminjam IS NULL THEN
        SET NEW.Nomor_HP_Peminjam=OLD.Nomor_HP_Peminjam;
    END IF;
    IF NEW.Nama_Peminjam = ' ' OR CHAR_LENGTH(NEW.Nama_Peminjam) < 3 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Nama peminjam tidak valid';
    END IF;
    IF NEW.Alamat_Peminjam = ' ' OR CHAR_LENGTH(NEW.Alamat_Peminjam) < 3 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Alamat peminjam tidak valid';
    END IF;
    IF NEW.Identitas_Peminjam = ' ' OR CHAR_LENGTH(NEW.Identitas_Peminjam) < 9 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Identitas peminjam tidak valid';
    END IF;
    IF NEW.Nomor_HP_Peminjam = ' ' OR CHAR_LENGTH(NEW.Nomor_HP_Peminjam) < 9 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Nomor HP peminjam tidak valid';
    END IF;
end//
DELIMITER ;


DELIMITER //
create trigger BEFORE_INSERT_PEMINJAMAN before insert
on PEMINJAMAN for each row
begin
    DECLARE keterangan_blacklist VARCHAR(320);
    
    
    SELECT P.Keterangan_Blacklist 
    INTO keterangan_blacklist 
    FROM Peminjam P 
    WHERE P.Identitas_Peminjam=NEW.Identitas_Peminjam;
    
    IF keterangan_blacklist IS NOT NULL THEN
        SET keterangan_blacklist = CONCAT('Peminjam terblacklist. Keterangan: ', keterangan_blacklist);
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT=keterangan_blacklist;
    END IF;
end//
DELIMITER ;


DELIMITER //
create trigger BEFORE_UPDATE_PEMINJAMAN before update
on PEMINJAMAN for each row
begin
    IF NEW.Status_Peminjaman IS NULL THEN
        SET NEW.Status_Peminjaman = OLD.Status_Peminjaman;
    END IF;
    IF NEW.Waktu_Pinjam IS NULL THEN
        SET NEW.Waktu_Pinjam = OLD.Waktu_Pinjam;
    END IF;
    IF NEW.Keterangan_Peminjaman IS NULL THEN
        SET NEW.Keterangan_Peminjaman = OLD.Keterangan_Peminjaman;
    END IF;
    IF NEW.Waktu_Harus_Kembali IS NULL THEN
        SET NEW.Waktu_Harus_Kembali = OLD.Waktu_Harus_Kembali;
    END IF;
    IF NEW.Waktu_Kembali IS NULL AND NEW.Status_Peminjaman > 1 THEN
        SET NEW.Waktu_Kembali = OLD.Waktu_Kembali;
    END IF;
    
    IF NEW.Username_Petugas IS NULL THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Username petugas harus diisi';
    END IF;
    
    
    IF OLD.Status_Peminjaman <> NEW.Status_Peminjaman THEN
        IF NEW.Status_Peminjaman='A' THEN
            SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Anda tidak dapat membuka kembali peminjaman. Silahkan buat peminjaman baru.';
        ELSE
            IF EXISTS (SELECT * FROM Barang_Terkini BR WHERE BR.Nomor_Peminjaman=NEW.Nomor_Peminjaman) THEN
                SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Masih ada barang yang belum dikembalikan';
            END IF;
            SET NEW.Waktu_Kembali=NOW();
        END IF;
    END IF;
end//
DELIMITER ;


DELIMITER //
create trigger BEFORE_INSERT_PETUGAS before insert
on PETUGAS for each row
begin
    IF NEW.Username_Petugas IS NULL OR NEW.Username_Petugas = ' ' OR CHAR_LENGTH(NEW.Username_Petugas) < 4 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Username tidak valid';
    END IF;
    IF NEW.Nama_Petugas IS NULL OR NEW.Nama_Petugas = ' ' OR CHAR_LENGTH(NEW.Nama_Petugas) < 3 THEN
        SIGNAL SQLSTATE '03000' SET MESSAGE_TEXT='Nama tidak valid';
    END IF;
    
end//
DELIMITER ;

