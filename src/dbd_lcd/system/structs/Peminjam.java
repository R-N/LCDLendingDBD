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
import dbd_lcd.system.structs.filter.FilterPeminjam;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author user
 */
public class Peminjam {
    private String idPeminjam;
    private String namaPeminjam;
    private String alamatPeminjam;
    private String nomorHpPeminjam;
    private String keteranganBlacklist;
    
    public Peminjam(String idPeminjam, String namaPeminjam, String alamatPeminjam, String nomorHpPeminjam, String keteranganBlacklist){
        this.idPeminjam = idPeminjam;
        this.namaPeminjam = namaPeminjam;
        this.alamatPeminjam = alamatPeminjam;
        this.nomorHpPeminjam = nomorHpPeminjam;
        this.keteranganBlacklist = Util.nullify(keteranganBlacklist);
    }
    public Peminjam(Peminjam source){
        copyFrom(source);
    }
    
    public void copyFrom(Peminjam pm){
        this.idPeminjam = pm.idPeminjam;
        this.namaPeminjam = pm.namaPeminjam;
        this.alamatPeminjam = pm.alamatPeminjam;
        this.nomorHpPeminjam = pm.nomorHpPeminjam;
        this.keteranganBlacklist = Util.nullify(pm.keteranganBlacklist);
    }
    
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null) return false;
        if(!(o instanceof Peminjam)) return false;
        Peminjam pm = (Peminjam) o;
        return Util.equals(idPeminjam, pm.idPeminjam)
                && Util.equals(namaPeminjam, pm.namaPeminjam)
                && Util.equals(alamatPeminjam, pm.alamatPeminjam)
                && Util.equals(nomorHpPeminjam, pm.nomorHpPeminjam)
                && Util.equals(keteranganBlacklist, pm.keteranganBlacklist);
    }
    
    public static ColorPack getBlacklistColor(boolean blacklist){
        if(blacklist){
            return Util.ColorPack.RED;
        }else{
            return Util.ColorPack.WHITE;
        }
    }
    public ColorPack getBlacklistColor(){
        return getBlacklistColor(isPeminjamTerblacklist());
    }
    public String getIdPeminjam(){
        return idPeminjam;
    }
    
    public String getNamaPeminjam(){
        return namaPeminjam;
    }
    public String getAlamatPeminjam(){
        return alamatPeminjam;
    }
    
    public String getNomorHpPeminjam(){
        return nomorHpPeminjam;
    }
    public String getKeteranganBlacklist(){
        return keteranganBlacklist;
    }
    public boolean isPeminjamTerblacklist(){
        return keteranganBlacklist != null;
    }
    
    public void setKeteranganBlacklist(String ket){
        this.keteranganBlacklist = ket;
    }
    
    public static Peminjam[] fetch(FilterPeminjam filter){
        
        Peminjam[] ret = null;
        String query = "SELECT Identitas_Peminjam, Nama_Peminjam, Alamat_Peminjam, Nomor_HP_Peminjam, Keterangan_Blacklist FROM Peminjam ";
        
        if(!Filter.isNullOrWhereEmpty(filter)){
            List<String> where = new LinkedList<String>();
            
            filter.addWhereClause(where);
            
            if(where.size() > 0){
                query += " WHERE " + String.join(" AND ", where);
            }
        }
        
        query += " ORDER BY Nama_Peminjam";
        
        if(!Filter.isNullOrPagingEmpty(filter)){
            query += filter.getLimitOffset();
        }
        
        List<Peminjam> ret1 = new LinkedList<Peminjam>();
        try(PreparedStatement stmt = Database.prepareStatement(
                query
        )){
            if(filter != null){
                int i = 0;
                filter.applyWhereClause(stmt, i);
            }
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    String id = rs.getString("Identitas_Peminjam");
                    String nama = rs.getString("Nama_Peminjam");
                    String alamat = rs.getString("Alamat_Peminjam");
                    String nomorHp = rs.getString("Nomor_HP_Peminjam");
                    String keterangan = rs.getString("Keterangan_Blacklist");
                    Peminjam pm = new Peminjam(id, nama, alamat, nomorHp, keterangan);
                    ret1.add(pm);
                }
            }
        }
        ret = ret1.toArray(new Peminjam[0]);
        return ret;
    }
    
    public void insert(){
        
        try(CallableStatement stmt = Database.prepareCall(
           "CALL INSERT_PEMINJAM(?, ?, ?, ?, ?, ?)"
        )){
            //stmt.setNull(1, java.sql.Types.INTEGER);
            stmt.setString(1, idPeminjam);
            stmt.setString(2, namaPeminjam);
            stmt.setString(3, alamatPeminjam);
            stmt.setString(4, nomorHpPeminjam);
            stmt.setString(5, LoginModule.getSesi());
            
            stmt.registerOutParameter(6, java.sql.Types.TIMESTAMP);
            
            stmt.executeUpdate();
            
            Timestamp timeout = stmt.getDateTime(6);
            LoginModule.setTimeout(timeout);
        }
        
    }
    
    public void update(){
        try(CallableStatement stmt = Database.prepareCall(
           "CALL UPDATE_PEMINJAM(?, ?, ?, ?, ?, ?, ?)"
        )){
            stmt.setString(1, idPeminjam);
            stmt.setString(2, namaPeminjam);
            stmt.setString(3, alamatPeminjam);
            stmt.setString(4, nomorHpPeminjam);
            stmt.setString(5, keteranganBlacklist);
            stmt.setString(6, LoginModule.getSesi());
            
            stmt.registerOutParameter(7, java.sql.Types.TIMESTAMP);
            
            
            stmt.executeUpdate();
            
            Timestamp timeout = stmt.getDateTime(7);
            LoginModule.setTimeout(timeout);
        }
    }
}
