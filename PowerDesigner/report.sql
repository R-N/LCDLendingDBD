SELECT 
	P.Waktu_Pinjam,
	P.Identitas_Peminjam AS Nomor_Induk_Peminjam,
	PM.Nama_Peminjam,
	SP.Teks_Status_Peminjaman AS Status_Peminjaman,
	IF(P.Waktu_Kembali IS NULL, P.Waktu_Harus_Kembali, P.Waktu_Kembali) AS Waktu_Kembali,
	IF(P.Waktu_Kembali IS NULL,
		IF(NOW() > P.Waktu_Harus_Kembali,
			CONCAT(
				DATEDIFF(NOW(), P.Waktu_Harus_Kembali), "d ",
				HOUR(TIMEDIFF(NOW(), P.Waktu_Harus_Kembali)), "h ",
				MINUTE(TIMEDIFF(NOW(), P.Waktu_Harus_Kembali)), "m "
			),
			""
		),
		IF(P.Waktu_Kembali > P.Waktu_Harus_Kembali,
			CONCAT(
				DATEDIFF(P.Waktu_Kembali, P.Waktu_Harus_Kembali), "d ",
				HOUR(TIMEDIFF(P.Waktu_Kembali, P.Waktu_Harus_Kembali)), "h ",
				MINUTE(TIMEDIFF(P.Waktu_Kembali, P.Waktu_Harus_Kembali)), "m "
			),
			""
		)
	) AS Keterlambatan,
	GROUP_CONCAT(DISTINCT CONCAT(RB.Tipe_Barang, RB.Nomor_Barang), ", ") AS Barang_Dipinjam,
	Get_Nama_Petugas(P.Username_Petugas) AS PJ
FROM Peminjaman P, Peminjam PM, Riwayat_Barang RB, Status_Peminjaman SP, Tipe_Barang TP 
WHERE P.Identitas_Peminjam=PM.Identitas_Peminjam 
	AND P.Nomor_Peminjaman=RB.Nomor_Peminjaman
	AND P.Status_Peminjaman=SP.Status_Peminjaman
	AND YEAR(P.Waktu_Pinjam) = $P{REPORT_YEAR}
	AND MONTH(P.Waktu_Pinjam) = $P{REPORT_MONTH}
GROUP BY P.Identitas_Peminjam, PM.Nama_Peminjam, SP.Teks_Status_Peminjaman, P.Waktu_Pinjam, P.Waktu_Kembali
ORDER BY P.Waktu_Pinjam, P.Waktu_Kembali ASC;

SELECT 
	P.Identitas_Peminjam AS Nomor_Induk_Peminjam,
	PM.Nama_Peminjam,
	PM.Nomor_Hp_Peminjam,
	IF(PM.Keterangan_Blacklist IS NULL, "Tidak terblacklist", CONCAT("Terblacklist karena: ", PM.Keterangan_Blacklist)) AS Blacklist,
	P.Waktu_Pinjam,
	SP.Teks_Status_Peminjaman AS Status_Peminjaman,
	IF(P.Waktu_Kembali IS NULL, P.Waktu_Harus_Kembali, P.Waktu_Kembali) AS Waktu_Kembali,
	IF(P.Waktu_Kembali IS NULL,
		IF(NOW() > P.Waktu_Harus_Kembali,
			CONCAT(
				DATEDIFF(NOW(), P.Waktu_Harus_Kembali), "d ",
				HOUR(TIMEDIFF(NOW(), P.Waktu_Harus_Kembali)), "h ",
				MINUTE(TIMEDIFF(NOW(), P.Waktu_Harus_Kembali)), "m "
			),
			""
		),
		IF(P.Waktu_Kembali > P.Waktu_Harus_Kembali,
			CONCAT(
				DATEDIFF(P.Waktu_Kembali, P.Waktu_Harus_Kembali), "d ",
				HOUR(TIMEDIFF(P.Waktu_Kembali, P.Waktu_Harus_Kembali)), "h ",
				MINUTE(TIMEDIFF(P.Waktu_Kembali, P.Waktu_Harus_Kembali)), "m "
			),
			""
		)
	) AS Keterlambatan,
	GROUP_CONCAT(DISTINCT CONCAT(RB.Tipe_Barang, RB.Nomor_Barang), ", ") AS Barang_Dipinjam,
	Get_Nama_Petugas(P.Username_Petugas) AS PJ
FROM Peminjaman P, Peminjam PM, Riwayat_Barang RB, Status_Peminjaman SP, Tipe_Barang TP 
WHERE P.Identitas_Peminjam=PM.Identitas_Peminjam 
	AND P.Nomor_Peminjaman=RB.Nomor_Peminjaman
	AND P.Status_Peminjaman=SP.Status_Peminjaman
	AND YEAR(P.Waktu_Pinjam) = $P{REPORT_YEAR}
	AND MONTH(P.Waktu_Pinjam) = $P{REPORT_MONTH}
GROUP BY P.Identitas_Peminjam, PM.Nama_Peminjam, PM.Nomor_Hp_Peminjam, Blacklist, SP.Teks_Status_Peminjaman, P.Waktu_Pinjam, P.Waktu_Kembali
ORDER BY P.Identitas_Peminjam, P.Waktu_Pinjam, P.Waktu_Kembali ASC;

SELECT 
	TB.Teks_Tipe_Barang,
	RB.Nomor_Barang,
	RB.Waktu_Mulai_Riwayat_Barang,
	RB.Waktu_Berakhir_Riwayat_Barang,
	SB.Teks_Status_Barang,
	PM.Identitas_Peminjam AS Nomor_Induk_Peminjam,
	PM.Nama_Peminjam,
	Get_Nama_Petugas(P.Username_Petugas) AS Petugas
FROM 
	Riwayat_Barang RB
		LEFT JOIN Peminjaman P
		ON P.Nomor_Peminjaman=RB.Nomor_Peminjaman,
	Peminjam PM, Tipe_Barang TB, Status_Barang SB 
WHERE P.Identitas_Peminjam=PM.Identitas_Peminjam
	AND TB.Tipe_Barang=RB.Tipe_Barang 
	AND SB.Status_Barang=RB.Status_Barang
	AND YEAR(P.Waktu_Pinjam) = $P{REPORT_YEAR}
	AND MONTH(P.Waktu_Pinjam) = $P{REPORT_MONTH}
ORDER BY TB.Teks_Tipe_Barang, RB.Nomor_Barang, RB.Waktu_Mulai_Riwayat_Barang ASC;