/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.system.structs;

import dbd_lcd.Util;
import dbd_lcd.Util.ColorPack;
import dbd_lcd.database.CallableStatement;
import dbd_lcd.database.Database;
import dbd_lcd.database.PreparedStatement;
import dbd_lcd.database.ResultSet;
import dbd_lcd.system.LoginModule;
import dbd_lcd.system.structs.filter.Filter;
import dbd_lcd.system.structs.filter.FilterBarang;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Lenovo2
 */
public class Barang {
    public static class Status{
        public static final String ADA = "A";
        public static final String DIPINJAM = "P";
        public static final String RUSAK = "R";
        
        public static String[] CODE = new String[]{ADA, DIPINJAM, RUSAK};
        public static String[] STRING = new String[]{"Ada", "Dipinjam", "Rusak"};
        
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
        
        public static String[] STRING_AVAILABLE = new String[]{STRING[0], STRING[2]};
        
        public static void cekKode(String kode){
            if(!TO_STRING.containsKey(kode)) throw new RuntimeException("Kode status invalid");
        }
        public static void cekString(String str){
            if(!FROM_STRING.containsKey(str)) throw new RuntimeException("String status invalid");
        }
        
        public static ColorPack getColor(String kode){
            
            switch(kode){
                case Barang.Status.DIPINJAM:{
                    return Util.ColorPack.YELLOW;
                }
                case Barang.Status.ADA:{
                    return Util.ColorPack.GREEN;
                }
                case Barang.Status.RUSAK:{
                    return Util.ColorPack.RED;
                }
                default:{
                    return Util.ColorPack.WHITE;
                }
            }
        }
    }
    public static class Tipe{
        public static final String LCD = "L";
        public static final String KABEL = "K";
        
        public static String[] CODE = new String[]{LCD, KABEL};
        public static String[] STRING = new String[]{"Proyektor", "Kabel"};
        
        public static String[] FILTER_STRING = new String[]{"", STRING[0], STRING[1]};
        
        public static HashMap<String, String> TO_STRING = new HashMap<String, String>(){{
            put(CODE[0], STRING[0]);
            put(CODE[1], STRING[1]);
        }};
        public static HashMap<String, String> FROM_STRING = new HashMap<String, String>(){{
            put(STRING[0], CODE[0]);
            put(STRING[1], CODE[1]);
        }};
        
        public static void cekKode(String kode){
            if(!TO_STRING.containsKey(kode)) throw new RuntimeException("Kode tipe barang invalid");
        }
        public static void cekString(String str){
            if(!FROM_STRING.containsKey(str)) throw new RuntimeException("String tipe barang invalid");
        }
    }
    private IdBarang id;
    private String status;
    private Integer nomorPeminjaman;
    private String keterangan;
    private Timestamp endTime;
    
    public Barang(int nomor, String tipe, String status, Integer nomorPeminjaman, String keterangan, Timestamp endTime){
        this(new IdBarang(nomor, tipe), status, nomorPeminjaman, keterangan, endTime);
    }
    public Barang(IdBarang id, String status, Integer nomorPeminjaman, String keterangan, Timestamp endTime){
        setId(id);
        this.status = status;
        this.nomorPeminjaman = nomorPeminjaman;
        this.keterangan = keterangan;
        this.endTime = endTime;
    }
    public Barang(int nomor, String tipe, String status, Integer nomorPeminjaman, String keterangan){
        this(new IdBarang(nomor, tipe), status, nomorPeminjaman, keterangan, null);
    }
    public Barang(IdBarang id, String status, Integer nomorPeminjaman, String keterangan){
        this(id, status, nomorPeminjaman, keterangan, null);
    }
    public Barang(Barang b){
        copyFrom(b);
    }
    
    public ColorPack getStatusColor(){
        return Status.getColor(getStatus());
    }
    
    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        if(o == this) return true;
        if(!(o instanceof Barang)) return false;
        Barang b = (Barang)o;
        System.out.println("Equality check, status: " + getStatusString() + " " + b.getStatusString());
        return id.equals(b.id)
                && status.equals(b.status)
                && Util.equals(nomorPeminjaman, b.nomorPeminjaman)
                && Util.equals(keterangan, b.keterangan)
                && Util.equals(endTime, b.endTime);
    }

    
    public Timestamp getEndTime(){
        return endTime;
    }
    
    public void setEndTime(Timestamp endTime){
        this.endTime = endTime;
    }
    
    public void setStatus(String status){
        Barang.Status.cekKode(status);
        if(!Barang.Status.DIPINJAM.equals(status)) setNomorPeminjaman(null);
        this.status = status;
    }
    
    public void setKeterangan(String keterangan){
        this.keterangan = keterangan;
    }
    
    public void copyFrom(Barang b){
        this.id = b.id;
        this.status = b.status;
        this.nomorPeminjaman = b.nomorPeminjaman;
        this.keterangan = b.keterangan;
    }
    
    public void setNomor(int nomor){
        this.id.setNomor(nomor);
    }
    
    public void setTipe(String tipe){
        this.id.setTipe(tipe);
    }
    
    public void kembalikan(){
        if(endTime == null){
            if(Barang.Status.DIPINJAM.equals(status)){
                setStatus(Barang.Status.ADA);
                return;
            }
        }
        throw new RuntimeException("Barang sudah dikembalikan");
    }
    public void pinjam(int nomorPmeinjaman){
        if(endTime != null){
            if(Barang.Status.ADA.equals(getCurrent().status)){
                setNomorPeminjaman(nomorPmeinjaman);
                return;
            }
        }
        throw new RuntimeException("Barang tidak bisa dipinjam");
    }
    
    
    public void setId(IdBarang id){
        if(id == null) throw new RuntimeException("Id tidak boleh null");
        this.id = id;
    }
    
    public int getNomor(){
        return id.nomor;
    }
    
    public String getTipe(){
        return id.tipe;
    }
    
    public String getStatus(){
        return status;
    }
    
    public String getKeterangan(){
        return keterangan;
    }
    
    public IdBarang getId(){
        return id;
    }
    
    public String getKode(){
        return id.toString();
    }
    
    public String getStatusString(){
        return Status.TO_STRING.get(status);
    }
    
    public String getTipeString(){
        return id.getTipeString();
    }
    
    public void setNomorPeminjaman(Integer nomorPeminjaman){
        this.nomorPeminjaman = nomorPeminjaman;
        if(nomorPeminjaman != null) setStatus(Barang.Status.DIPINJAM);
    }
    
    public Integer getNomorPeminjaman(){
        return nomorPeminjaman;
    }
    
    public void update(CallableStatement stmt2){
        stmt2.setInt(1, getNomor());
        stmt2.setString(2, getTipe());
        stmt2.setString(3, getStatus());
        stmt2.setInt(4, getNomorPeminjaman());
        stmt2.setString(5, getKeterangan());

        stmt2.executeUpdate();

        LoginModule.setTimeout(stmt2.getDateTime(7));
    }
    
    public void insert(){
        
        try(CallableStatement stmt = Database.prepareCall(
           "CALL INSERT_Barang(?, ?, ?, ?, ?)"
        )){
            //stmt.setNull(1, java.sql.Types.INTEGER);
            stmt.setInt(1, id.nomor);
            stmt.setString(2, id.tipe);
            stmt.setString(3, keterangan);
            stmt.setString(4, LoginModule.getSesi());
            
            stmt.registerOutParameter(5, java.sql.Types.TIMESTAMP);
            
            stmt.executeUpdate();
            
            Timestamp timeout = stmt.getDateTime(5);
            LoginModule.setTimeout(timeout);
        }
        
    }
    
    public void update(){
        try(CallableStatement stmt = Database.prepareCall(
           "CALL UPDATE_DETAIL_BARANG(?, ?, ?, ?, ?, ?, ?)"
        )){
            stmt.setString(6, LoginModule.getSesi());
            
            stmt.registerOutParameter(7, java.sql.Types.TIMESTAMP);
            
            update(stmt);
            
        }
    }
    
    public Barang getCurrent(){
        if(endTime == null) return this;
        FilterBarang filter = new FilterBarang();
        filter.nomor = this.id.nomor;
        filter.tipe = this.id.tipe;
        return fetch(filter)[0];
    }
    
    public static Barang[] fetch(FilterBarang filter){
        Barang[] ret = null;
        String query = "SELECT Nomor_Barang, Tipe_Barang, Status_Barang, Nomor_Peminjaman, Keterangan_Barang FROM Barang_Terkini ";
        
        if(!Filter.isNullOrWhereEmpty(filter)){
            List<String> where = new LinkedList<String>();
            
            filter.addWhereClause(where);
            
            if(where.size() > 0){
                query += " WHERE " + String.join(" AND ", where);
            }
        }
        
        query += " ORDER BY Nomor_Barang";
        
        if(!Filter.isNullOrPagingEmpty(filter)){
            query += filter.getLimitOffset();
        }
        
        List<Barang> ret1 = new LinkedList<Barang>();
        try(PreparedStatement stmt = Database.prepareStatement(
                query
        )){
            if(filter != null){
                int i = 0;
                filter.applyWhereClause(stmt, i);
            }
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    int nomor = rs.getInt("Nomor_Barang");
                    String tipe = rs.getString("Tipe_Barang");
                    String status = rs.getString("Status_Barang");
                    Integer nomorPeminjaman = rs.getInt("Nomor_Peminjaman");
                    String keterangan = rs.getString("Keterangan_Barang");
                    Barang b = new Barang(nomor, tipe, status, nomorPeminjaman, keterangan, null);
                    ret1.add(b);
                }
            }
        }
        ret = ret1.toArray(new Barang[0]);
        return ret;
    }
    
    public static OverallStatusBarang getOverallStatusBarang(){
        OverallStatusBarang ret = new OverallStatusBarang();
        try(PreparedStatement stmt = Database.prepareStatement(
                "SELECT COUNT(*) FROM Barang_Terkini WHERE Tipe_Barang=? AND Status_Barang=?"
        )){
            stmt.setString(1, Barang.Tipe.LCD);
            stmt.setString(2, Barang.Status.ADA);
            try(ResultSet rs = stmt.executeQuery()){
                rs.next();
                ret.availableCount = rs.getInt(1);
            }
        }
        try(PreparedStatement stmt = Database.prepareStatement(
                "SELECT COUNT(*) FROM Peminjaman P WHERE P.Status_Peminjaman=? AND P.Waktu_Harus_Kembali<NOW();"
        )){
            stmt.setString(1, Peminjaman.Status.AKTIF);
            try(ResultSet rs = stmt.executeQuery()){
                rs.next();
                ret.lateCount = rs.getInt(1);
            }
        }
        try(PreparedStatement stmt = Database.prepareStatement(
                "SELECT COUNT(*) FROM Barang_Terkini WHERE Tipe_Barang=? AND Status_Barang=?"
        )){
            stmt.setString(1, Barang.Tipe.LCD);
            stmt.setString(2, Barang.Status.RUSAK);
            try(ResultSet rs = stmt.executeQuery()){
                rs.next();
                ret.unavailableCount = rs.getInt(1);
            }
        }
        return ret;
    }
    
}
