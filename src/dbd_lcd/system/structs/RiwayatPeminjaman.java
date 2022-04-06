/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.system.structs;

import dbd_lcd.Util;
import dbd_lcd.Util.ColorPack;
import dbd_lcd.database.Database;
import dbd_lcd.database.PreparedStatement;
import dbd_lcd.database.ResultSet;
import dbd_lcd.system.structs.filter.Filter;
import dbd_lcd.system.structs.filter.FilterPeminjaman;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Lenovo2
 */
public class RiwayatPeminjaman {
    protected Integer nomorPeminjaman;
    protected String statusPeminjaman;
    protected String namaPeminjam;
    protected boolean peminjamTerblacklist;
    protected Timestamp waktuPinjam;
    protected Timestamp waktuHarusKembali;
    protected Timestamp waktuKembali;
    
    protected RiwayatPeminjaman(){}
    
    protected RiwayatPeminjaman(Integer nomor, String status, Timestamp waktuPinjam, Timestamp waktuHarusKembali, Timestamp waktuKembali){
        this.nomorPeminjaman = nomor;
        this.statusPeminjaman = status;
        this.waktuPinjam = waktuPinjam;
        this.waktuPinjam = waktuPinjam;
        this.waktuHarusKembali = waktuHarusKembali;
        this.waktuKembali = waktuKembali;
    }
    public RiwayatPeminjaman(int nomor, String status, String namaPeminjam, boolean peminjamTerblacklist, Timestamp waktuPinjam, Timestamp waktuHarusKembali, Timestamp waktuKembali){
        this.nomorPeminjaman = nomor;
        this.statusPeminjaman = status;
        this.waktuPinjam = waktuPinjam;
        this.namaPeminjam = namaPeminjam;
        this.peminjamTerblacklist = peminjamTerblacklist;
        this.waktuPinjam = waktuPinjam;
        this.waktuHarusKembali = waktuHarusKembali;
        this.waktuKembali = waktuKembali;
    }
    
    
    public Integer getNomorPeminjaman(){
        return nomorPeminjaman;
    }
    
    public Peminjaman getPeminjaman(){
        return Peminjaman.get(nomorPeminjaman);
    }
    
    public String getStatus(){
        return statusPeminjaman;
    }
    
    public String getNamaPeminjam(){
        return namaPeminjam;
    }
    
    public boolean isPeminjamTerblacklist(){
        return peminjamTerblacklist;
    }
    
    public Timestamp getWaktuPinjam(){
        return waktuPinjam;
    }
    
    public Timestamp getWaktuHarusKembali(){
        return waktuHarusKembali;
    }
    
    public Timestamp getWaktuKembali(){
        return waktuKembali;
    }
    
    public Timestamp getShownWaktuKembali(){
        if(waktuKembali == null) return waktuHarusKembali;
        return waktuKembali;
    }
    public String getStatusString(){
        return Peminjaman.Status.TO_STRING.get(statusPeminjaman);
    }
    
    public boolean isTerlambatKembali(){
        if(waktuKembali == null) return Util.now().after(waktuHarusKembali);
        return waktuKembali.after(waktuHarusKembali);
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
    
    public ColorPack getBlacklistColor(){
        return Peminjam.getBlacklistColor(isPeminjamTerblacklist());
    }
    
    public static RiwayatPeminjaman[] fetch(FilterPeminjaman filter){
        RiwayatPeminjaman[] ret = null;
        String query = "SELECT "
                + String.join(", ", 
                        new String[]{
                            "P.Nomor_Peminjaman",
                            "P.Status_Peminjaman",
                            "PM.Nama_Peminjam",
                            "(PM.Keterangan_Blacklist IS NOT NULL) AS Peminjam_Terblacklist",
                            "P.Waktu_Pinjam",
                            "P.Waktu_Harus_Kembali",
                            "P.Waktu_Kembali",
                        }
                    )
                + " FROM Peminjaman P, Peminjam PM ";
        
        List<String> where = new LinkedList<String>();
        where.add("P.Identitas_Peminjam = PM.Identitas_Peminjam");
        if(!Filter.isNullOrWhereEmpty(filter)){
            
            filter.addWhereClause(where);
            
            
        }
        if(where.size() > 0){
            query += " WHERE " + String.join(" AND ", where);
        }
        
        query += " ORDER BY P.Waktu_Pinjam DESC ";
        
        if(!Filter.isNullOrPagingEmpty(filter)){
            query += filter.getLimitOffset();
        }
        
        System.out.println("Query: " + query);
        
        List<RiwayatPeminjaman> ret1 = new LinkedList<RiwayatPeminjaman>();
        try(PreparedStatement stmt = Database.prepareStatement(
                query
        )){
            
            if(!Filter.isNullOrWhereEmpty(filter)){
                int i = 0;

                filter.applyWhereClause(stmt, i);
            }
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    RiwayatPeminjaman rp = new RiwayatPeminjaman(
                        rs.getInt("Nomor_Peminjaman"),
                        rs.getString("Status_Peminjaman"),
                        rs.getString("Nama_Peminjam"),
                        rs.getBoolean("Peminjam_Terblacklist"),
                        rs.getDateTime("Waktu_Pinjam"),
                        rs.getDateTime("Waktu_Harus_Kembali"),
                        rs.getDateTime("Waktu_Kembali")
                    );
                    ret1.add(rp);
                }
            }
        }
        ret = ret1.toArray(new RiwayatPeminjaman[0]);
        return ret;
    }
}
