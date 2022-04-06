/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.system.structs;

import dbd_lcd.Util;
import dbd_lcd.database.CallableStatement;
import dbd_lcd.database.Database;
import dbd_lcd.database.PreparedStatement;
import dbd_lcd.database.ResultSet;
import dbd_lcd.system.LoginModule;
import java.awt.Color;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import dbd_lcd.Util.ColorPack;

/**
 *
 * @author Lenovo2
 */
public class Peminjaman extends RiwayatPeminjaman{
    public static class Status{
        public static final String AKTIF = "A";
        public static final String SELESAI = "S";
        public static final String BATAL = "B";
        
        public static String[] CODE = new String[]{AKTIF, SELESAI, BATAL};
        public static String[] STRING = new String[]{"Aktif", "Selesai", "Batal"};
        
        public static String[] FILTER_STRING = new String[]{"", STRING[0], STRING[1], STRING[2]};
        
        public static HashMap<String, String> TO_STRING = new HashMap<String, String>(){{
            put(CODE[0], STRING[0]);
            put(CODE[1], STRING[1]);
            put(CODE[2], STRING[2]);
        }};
        public static HashMap<String, String> FROM_STRING = new HashMap<String, String>(){{
            put(STRING[0], CODE[0]);
            put(STRING[1], CODE[1]);
            put(STRING[2], CODE[2]);
        }};
        
        public static void cekKode(String kode){
            if(!TO_STRING.containsKey(kode)) throw new RuntimeException("Kode status peminjaman invalid");
        }
        public static void cekString(String str){
            if(!FROM_STRING.containsKey(str)) throw new RuntimeException("String status peminjaman invalid");
        }
        public static ColorPack getColor(String kode){
            
            switch(kode){
                case Peminjaman.Status.AKTIF:{
                    return Util.ColorPack.WHITE;
                }
                case Peminjaman.Status.SELESAI:{
                    return Util.ColorPack.GREEN;
                }
                case Peminjaman.Status.BATAL:{
                    return Util.ColorPack.RED;
                }
                default:{
                    return Util.ColorPack.YELLOW;
                }
            }
        }
    }
    
    public static class Error{
        public static final int OK = 0;
        public static final int STATUS_SAMA = 1;
        public static final int BUKA_KEMBALI = 2;
        public static final int BELUM_DIKEMBALIKAN =3;
        
        public static int[] CODE = new int[]{OK, STATUS_SAMA, BUKA_KEMBALI, BELUM_DIKEMBALIKAN};
        public static String[] STRING = new String[]{
            "OK", 
            "Status sama dengan sebelumnya.", 
            "Dilarang membuka kembali peminjaman.",
            "Ada barang yang belum dikembalikan."
        };
        
        public static HashMap<Integer, String> TO_STRING = new HashMap<Integer, String>(){{
            put(CODE[0], STRING[0]);
            put(CODE[1], STRING[1]);
            put(CODE[2], STRING[2]);
            put(CODE[3], STRING[3]);
        }};
        public static HashMap<String, Integer> FROM_STRING = new HashMap<String, Integer>(){{
            put(STRING[0], CODE[0]);
            put(STRING[1], CODE[1]);
            put(STRING[2], CODE[2]);
            put(STRING[3], CODE[3]);
        }};
        
    }
    
    public Peminjam peminjam;
    private Barang[] barang;
    private KeperluanPinjam[] keperluanPinjam;
    public String keteranganPeminjaman;
    
    public Peminjaman(
            Integer nomor,
            String status, 
            Peminjam peminjam, 
            Barang[] barang, 
            KeperluanPinjam[] keperluanPinjam,
            String keteranganPeminjaman,
            Timestamp waktuPinjam,
            Timestamp waktuHarusKembali,
            Timestamp waktuKembali
        ){
        super(nomor, status, waktuPinjam, waktuHarusKembali, waktuKembali);
        if(peminjam == null) throw new RuntimeException("Peminjam tidak valid");
        this.peminjam = peminjam;
        this.barang = barang;
        this.keperluanPinjam = keperluanPinjam;
        this.keteranganPeminjaman = keteranganPeminjaman;
    }
    public Peminjaman(
            Peminjam peminjam, 
            Barang[] barang, 
            KeperluanPinjam[] keperluanPinjam,
            String keteranganPeminjaman
        ){
        if(peminjam == null) throw new RuntimeException("Peminjam tidak valid");
        if(peminjam.isPeminjamTerblacklist()) throw new RuntimeException("Peminjam terblacklist");
        this.peminjam = peminjam;
        this.statusPeminjaman = Peminjaman.Status.AKTIF;
        setBarang(barang);
        setKeperluanPinjam(keperluanPinjam);
        this.keteranganPeminjaman = keteranganPeminjaman;
        Timestamp ts = keperluanPinjam[0].durasi.akhir;
        for(int i = 1; i < keperluanPinjam.length; ++i){
            Timestamp tsi = keperluanPinjam[i].durasi.akhir;
            if(tsi.after(ts)) ts = tsi;
        }
        ts = Util.addMinute(ts, 30);
        this.waktuHarusKembali = ts;
    }
    @Override
    public String getNamaPeminjam(){
        return peminjam.getNamaPeminjam();
    }
    @Override
    public boolean isPeminjamTerblacklist(){
        return peminjam.isPeminjamTerblacklist();
    }
    public void setStatusPeminjaman(String status){
        Peminjaman.Status.cekKode(status);
        this.statusPeminjaman = status;
    }
    public Peminjaman(Peminjaman p){
        copyFrom(p);
    }
    public Barang getBarang(int i){
        return barang[i];
    }
    public Barang[] getBarang(){
        return barang;
    }
    public int getBarangCount(){
        return barang.length;
    }
    public KeperluanPinjam getKeperluanPinjam(int i){
        return keperluanPinjam[i];
    }
    public int getKeperluanPinjamCount(){
        return keperluanPinjam.length;
    }
    public void setBarang(Barang[] barang){
        this.barang  = initBarang(barang);
    }
    public void setKeperluanPinjam(KeperluanPinjam[] keperluan){
        this.keperluanPinjam = initKeperluanPinjam(keperluan);
    }
    
    public void kembalikan(){
        for(int i = 0; i < barang.length; ++i){
            try{
                barang[i].kembalikan();
            }catch(RuntimeException ex){
                
            }
        }
    }
    public void pinjam(){
        for(int i = 0; i < barang.length; ++i){
            try{
                barang[i].pinjam(nomorPeminjaman);
            }catch(RuntimeException ex){
                
            }
        }
    }
    
    public ColorPack getStatusColor(){
        return Status.getColor(statusPeminjaman);
    }
    public ColorPack getShownWaktuKembaliColor(){
        
        if(isTerlambatKembali()){
            return Util.ColorPack.RED;
        }else if (getStatus().equals(Peminjaman.Status.AKTIF)){
            if(Util.addMinute(Util.now(), 30).after(getShownWaktuKembali())){
                return Util.ColorPack.YELLOW;
            }else{
                return Util.ColorPack.WHITE;
            }
        }else{
            return Util.ColorPack.GREEN;
        }
    }
    public static Barang[] initBarang(Barang[] barang){
        if(barang == null || barang.length < 1) throw new RuntimeException("Barang tidak valid");
        HashSet<IdBarang> ids = new HashSet<>();
        for(int i = 0; i < barang.length; ++i){
            Barang b = barang[i];
            if(b == null) throw new RuntimeException("Barang null");
            if(!ids.add(b.getId())) throw new RuntimeException("Barang terduplikat");
        }
        return barang;
    }
    
    public static KeperluanPinjam[] initKeperluanPinjam(KeperluanPinjam[] kps){
        if(kps == null || kps.length < 1) throw new RuntimeException("Keperluan harus diisi");
        Arrays.sort(kps, new Comparator<KeperluanPinjam>() {
            public int compare(KeperluanPinjam kp1, KeperluanPinjam kp2) {
                int ret = Long.compare(kp1.durasi.awal.getTime(), kp2.durasi.awal.getTime());
                if(ret != 0) return ret;
                return Long.compare(kp1.durasi.akhir.getTime(), kp2.durasi.akhir.getTime());
            }
        });
        for(int i = 1; i < kps.length; ++i){
            Timestamp first = kps[i-1].durasi.akhir;
            Timestamp next = kps[i].durasi.awal;

            if((next.getTime() - first.getTime()) > 30* 1000 * 60){
                throw new RuntimeException("Ada keperluan yang terpaut terlalu jauh");
            }
        }
        return kps;
    }
    
    public void copyFrom(Peminjaman pm){
        this.nomorPeminjaman = pm.nomorPeminjaman;
        this.statusPeminjaman = pm.statusPeminjaman;
        this.peminjam = new Peminjam(pm.peminjam);
        Barang[] bs = new Barang[pm.barang.length];
        for(int i = 0; i < bs.length; ++i){
            bs[i] = new Barang(pm.barang[i]);
        }
        this.barang = bs;
        KeperluanPinjam[] kps = new KeperluanPinjam[pm.keperluanPinjam.length];
        for(int i = 0; i < kps.length; ++i){
            kps[i] = new KeperluanPinjam(pm.keperluanPinjam[i]);
        }
        this.keperluanPinjam = kps;
        this.keteranganPeminjaman = pm.keteranganPeminjaman;
        this.waktuPinjam = pm.waktuPinjam;
        this.waktuHarusKembali = pm.waktuHarusKembali;
        this.waktuKembali = pm.waktuKembali;
    }
    
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null) return false;
        if(!(o instanceof Peminjaman)) return false;
        
        Peminjaman pm = (Peminjaman) o;
        
        if(barang.length != pm.barang.length) return false;
        if(keperluanPinjam.length != pm.keperluanPinjam.length) return false;
        
        for(int i = 0; i < barang.length; ++i){
            if(!Util.equals(barang[i], pm.barang[i])) return false;
        }
        for(int i = 0; i < keperluanPinjam.length; ++i){
            if(!Util.equals(keperluanPinjam[i], pm.keperluanPinjam[i])) return false;
        }
        return Util.equals(nomorPeminjaman, pm.nomorPeminjaman)
                && Util.equals(statusPeminjaman, pm.statusPeminjaman)
                && Util.equals(peminjam, pm.peminjam)
                && Util.equals(keteranganPeminjaman, pm.keteranganPeminjaman)
                && Util.equals(waktuPinjam, pm.waktuPinjam)
                && Util.equals(waktuHarusKembali, pm.waktuHarusKembali)
                && Util.equals(waktuKembali, pm.waktuKembali);
    }
    
    
    
    public int insert(){
        if(nomorPeminjaman != null) throw new RuntimeException("Peminjaman sudah ada");
        try(CallableStatement stmt = Database.prepareCall(
           "CALL INSERT_PEMINJAMAN(?, ?, ?, ?, ?, ?)"
        )){
            //stmt.setNull(1, java.sql.Types.INTEGER);
            stmt.setString(1, peminjam.getIdPeminjam());
            stmt.setDateTime(2, waktuHarusKembali);
            stmt.setString(3, keteranganPeminjaman);
            stmt.setString(4, LoginModule.getSesi());
            
            stmt.registerOutParameter(5, java.sql.Types.INTEGER);
            stmt.registerOutParameter(6, java.sql.Types.TIMESTAMP);
            
            stmt.executeUpdate();
            
            this.nomorPeminjaman = stmt.getInt(5);
            LoginModule.setTimeout(stmt.getDateTime(6));
            
            //barang
            try(CallableStatement stmt2 = Database.prepareCall(
                    "CALL UPDATE_DETAIL_BARANG(?, ?, ?, ?, ?, ?, ?)"
                )){
                
                stmt2.setString(6, LoginModule.getSesi());

                stmt2.registerOutParameter(7, java.sql.Types.TIMESTAMP);
                
                for(int i = 0; i < barang.length; ++i){
                    Barang b = barang[i];
                    b.setNomorPeminjaman(nomorPeminjaman);
                    b.update(stmt2);
                }
            }
            
            //keperluan
            try(CallableStatement stmt2 = Database.prepareCall(
               "CALL INSERT_KEPERLUAN_PINJAM(?, ?, ?, ?, ?, ?, ?, ?, ?)"
            )){
                stmt2.setInt(6, nomorPeminjaman);
                stmt2.registerOutParameter(8, java.sql.Types.INTEGER);
                stmt2.registerOutParameter(9, java.sql.Types.TIMESTAMP);
                for(int i = 0; i < keperluanPinjam.length; ++i){
                    keperluanPinjam[i].insert(stmt2);
                }
            }
            refresh();
        }
        return nomorPeminjaman;
    }
    
    public void refresh(){
        Peminjaman hasil = get(nomorPeminjaman);
        copyFrom(hasil);
    }
    public void updateBarang(){
        
        if(nomorPeminjaman == null) throw new RuntimeException("Peminjaman belum ada");
        Peminjaman lama = get(nomorPeminjaman);
        try(CallableStatement stmt2 = Database.prepareCall(
                "CALL UPDATE_DETAIL_BARANG(?, ?, ?, ?, ?, ?, ?)"
            )){

            stmt2.setString(6, LoginModule.getSesi());

            stmt2.registerOutParameter(7, java.sql.Types.TIMESTAMP);

            for(int i = 0; i < lama.barang.length; ++i){
                Barang b = barang[i];
                if(Barang.Status.DIPINJAM.equals(b.getStatus())) b.setNomorPeminjaman(nomorPeminjaman);
                if(!b.equals(lama.barang[i])){
                    System.out.println("Update barang");
                    b.update(stmt2);
                }
            }
            for(int i = lama.barang.length; i < barang.length; ++i){
                Barang b = barang[i];
                b.setNomorPeminjaman(nomorPeminjaman);
                b.update(stmt2);
            }
        }
        refresh();
    }
    public void update(){
        if(nomorPeminjaman == null) throw new RuntimeException("Peminjaman belum ada");
        updateBarang();
        Peminjaman lama = get(nomorPeminjaman);
        try(CallableStatement stmt = Database.prepareCall(
           "CALL UPDATE_DETAIL_PEMINJAMAN(?, ?, ?, ?, ?, ?)"
        )){
            
            
            stmt.setInt(1, nomorPeminjaman);
            stmt.setString(2, statusPeminjaman);
            stmt.setDateTime(3, waktuHarusKembali);
            stmt.setString(4, keteranganPeminjaman);
            stmt.setString(5, LoginModule.getSesi());
            
            stmt.registerOutParameter(6, java.sql.Types.TIMESTAMP);
            
            stmt.executeUpdate();
            
            LoginModule.setTimeout(stmt.getDateTime(6));
            
            //keperluan
            if(hasKeperluanPinjamBerubah(lama)){
                int min = Math.min(keperluanPinjam.length, lama.keperluanPinjam.length);
                try(CallableStatement stmt2 = Database.prepareCall(
                        "CALL UPDATE_KEPERLUAN_PINJAM(?, ?, ?, ?, ?, ?, ?, ?)"
                    )){
                    stmt2.setString(7, LoginModule.getSesi());
                    stmt2.registerOutParameter(8, java.sql.Types.TIMESTAMP);
                    for(int i = 0; i < min; ++i){
                        KeperluanPinjam kp = keperluanPinjam[i];
                        kp.id = lama.keperluanPinjam[i].id;
                        kp.update(stmt2);
                    }
                }
                if(keperluanPinjam.length < lama.keperluanPinjam.length){
                    try(CallableStatement stmt2 = Database.prepareCall(
                            "CALL DELETE_KEPERLUAN_PINJAM(?, ?, ?)"
                        )){
                        stmt2.setString(2, LoginModule.getSesi());
                        
                        stmt2.registerOutParameter(3, java.sql.Types.TIMESTAMP);
                        
                        for(int i = min; i < lama.keperluanPinjam.length; ++i){
                            stmt2.setInt(1, lama.keperluanPinjam[i].id);
                            stmt2.executeUpdate();
                            LoginModule.setTimeout(stmt2.getDateTime(3));
                        }
                    }
                }else if (keperluanPinjam.length > lama.keperluanPinjam.length){
                    try(CallableStatement stmt2 = Database.prepareCall(
                       "CALL INSERT_KEPERLUAN_PINJAM(?, ?, ?, ?, ?, ?, ?, ?, ?)"
                    )){
                        stmt2.setInt(6, nomorPeminjaman);
                        stmt2.registerOutParameter(8, java.sql.Types.INTEGER);
                        stmt2.registerOutParameter(9, java.sql.Types.TIMESTAMP);
                        for(int i = min; i < keperluanPinjam.length; ++i){
                            keperluanPinjam[i].insert(stmt2);
                        }
                    }
                }
            }
            refresh();
        }
    }
    public int cekUbahStatus(String status){
        if(statusPeminjaman.equals(status)){
            return Error.STATUS_SAMA;
        }
        if(Status.AKTIF.equals(status)){
            return Error.BUKA_KEMBALI;
        }else{
            for(int i = 0; i < barang.length;++i){
                if(barang[i].getEndTime() == null && Barang.Status.DIPINJAM.equals(barang[i].getStatus())){
                    return Error.BELUM_DIKEMBALIKAN;
                }
            }
        }
        return Error.OK;
    }
    public void ubahStatus(String status, String keterangan){
        cekUbahStatus(status);
        setStatusPeminjaman(status);
        if(!Util.isNullOrEmpty(keterangan)) this.keteranganPeminjaman = keterangan;
        update();
    }
    
    public int simpan(){
        if(nomorPeminjaman == null) return insert();
        update();
        return nomorPeminjaman;
    }
    
    public boolean hasKeperluanPinjamBerubah(Peminjaman lama){
        if(keperluanPinjam.length != lama.keperluanPinjam.length) return true;
        for(int i = 0; i < keperluanPinjam.length; ++i){
            if(!keperluanPinjam[i].equals(lama.keperluanPinjam[i])){
                return true;
            }
        }
        return false;
    }
    
    public static Peminjaman get(int nomorPeminjaman){
        try(PreparedStatement stmt = Database.prepareStatement(
                "SELECT "
                + String.join(", ",
                        new String[]{
                            "P.Status_Peminjaman",
                            "PM.Identitas_Peminjam",
                            "PM.Nama_Peminjam",
                            "PM.Alamat_Peminjam",
                            "PM.Nomor_HP_Peminjam",
                            "PM.Keterangan_Blacklist",
                            "P.Keterangan_Peminjaman",
                            "P.Waktu_Pinjam",
                            "P.Waktu_Harus_Kembali",
                            "P.Waktu_Kembali",
                        }
                    )
                + " FROM Peminjaman P, Peminjam PM"
                + " WHERE P.Nomor_Peminjaman=? AND P.Identitas_Peminjam=PM.Identitas_Peminjam"
        )){
            stmt.setInt(1, nomorPeminjaman);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    String status = rs.getString("Status_Peminjaman");
                    String identitasPeminjam = rs.getString("Identitas_Peminjam");
                    String namaPeminjam = rs.getString("Nama_Peminjam");
                    String alamatPeminjam = rs.getString("Alamat_Peminjam");
                    String nomorHpPeminjam = rs.getString("Nomor_HP_Peminjam");
                    String keteranganBlacklist = rs.getString("Keterangan_Blacklist");
                    String keteranganPeminjaman = rs.getString("Keterangan_Peminjaman");
                    Timestamp waktuPinjam = rs.getDateTime("Waktu_Pinjam");
                    Timestamp waktuHarusKembali = rs.getDateTime("Waktu_Harus_Kembali");
                    Timestamp waktuKembali = rs.getDateTime("Waktu_Kembali");
                    
                    Peminjam peminjam = new Peminjam(identitasPeminjam, namaPeminjam, alamatPeminjam, nomorHpPeminjam, keteranganBlacklist);
                    
                    //barang
                    List<Barang> bs = new LinkedList<Barang>();
                    try(PreparedStatement stmt2 = Database.prepareStatement(
                            //https://stackoverflow.com/questions/1313120/retrieving-the-last-record-in-each-group-mysql
                            "SELECT RB.Nomor_Barang, RB.Tipe_Barang, RB.Status_Barang, RB.Keterangan_Barang, RB.Waktu_Berakhir_Riwayat_Barang "
                                    + "FROM (SELECT * FROM Riwayat_Barang RB0 WHERE RB0.Nomor_Peminjaman=?) RB "
                                        + "LEFT JOIN (SELECT * FROM Riwayat_Barang RB0 WHERE RB0.Nomor_Peminjaman=?) RB2 "
                                        + "ON RB.Nomor_Barang=RB2.Nomor_Barang "
                                            + "AND RB.Tipe_Barang=RB2.Tipe_Barang "
                                            + "AND RB.Waktu_Mulai_Riwayat_Barang<RB2.Waktu_Mulai_Riwayat_Barang "
                                    + "WHERE RB2.Id_Riwayat_Barang IS NULL "
                                    + "ORDER BY RB.Tipe_Barang, RB.Nomor_Barang "
                        )){
                        stmt2.setInt(1, nomorPeminjaman);
                        stmt2.setInt(2, nomorPeminjaman);
                        try(ResultSet rs2 = stmt2.executeQuery()){
                            while(rs2.next()){
                                int nomorBarang = rs2.getInt("Nomor_Barang");
                                String tipeBarang = rs2.getString("Tipe_Barang");
                                String statusBarang = rs2.getString("Status_Barang");
                                String keteranganBarang = rs2.getString("Keterangan_Barang");
                                Timestamp endTime = rs2.getDateTime("Waktu_Berakhir_Riwayat_Barang");
                                Barang b = new Barang(nomorBarang, tipeBarang, statusBarang, nomorPeminjaman, keteranganBarang, endTime);
                                bs.add(b);
                            }
                        }
                    }
                    
                    //kelas pinjam
                    List<KeperluanPinjam> kps = new LinkedList<KeperluanPinjam>();
                    try(PreparedStatement stmt2 = Database.prepareStatement(
                            "SELECT * FROM Keperluan_Pinjam KP WHERE KP.Nomor_Peminjaman=? ORDER BY KP.Waktu_Mulai"
                        )){
                        stmt2.setInt(1, nomorPeminjaman);
                        try(ResultSet rs2 = stmt2.executeQuery()){
                            while(rs2.next()){
                                int idKeperluanPinjam = rs2.getInt("Id_Keperluan_Pinjam");
                                String keperluanPinjam = rs2.getString("Keperluan_Pinjam");
                                String namaPenanggungJawab = rs2.getString("Nama_Penanggung_Jawab");
                                String ruangPinjam = rs2.getString("Ruang_Pinjam");
                                RangeWaktu durasi = new RangeWaktu(
                                        rs2.getDateTime("Waktu_Mulai"),
                                        rs2.getDateTime("Waktu_Selesai")
                                );
                                KeperluanPinjam kp = new KeperluanPinjam(idKeperluanPinjam, keperluanPinjam, namaPenanggungJawab, ruangPinjam, durasi);
                                kps.add(kp);
                            }
                        }
                    }
                    
                    Peminjaman p = new Peminjaman(
                            nomorPeminjaman,
                            status,
                            peminjam,
                            bs.toArray(new Barang[0]),
                            kps.toArray(new KeperluanPinjam[0]),
                            keteranganPeminjaman,
                            waktuPinjam,
                            waktuHarusKembali,
                            waktuKembali
                    );
                    
                    return p;
                }
            }
        }
        throw new RuntimeException("Peminjaman tidak ditemukan");
    }
}
