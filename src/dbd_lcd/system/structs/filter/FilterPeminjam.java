/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.system.structs.filter;

import dbd_lcd.Util;
import dbd_lcd.database.PreparedStatement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Lenovo2
 */
public class FilterPeminjam extends Filter{
    public String idPeminjam;
    public String namaPeminjam;
    public String alamatPeminjam;
    public String nomorHpPeminjam;
    public Boolean peminjamTerblacklist;
    
    public FilterPeminjam(){}
    public FilterPeminjam(FilterPeminjam filter){
        super(filter);
        copyFrom(filter);
    }
    
    public void copyFrom(Filter f){
        if(f instanceof FilterPeminjam){
            copyFrom((FilterPeminjam)f);
        }else{
            super.copyFrom(f);
        }
    }
    
    
    public void copyFrom(FilterPeminjam f){
        super.copyFrom(f);
        this.idPeminjam = f.idPeminjam;
        this.namaPeminjam = f.namaPeminjam;
        this.alamatPeminjam = f.alamatPeminjam;
        this.nomorHpPeminjam = f.nomorHpPeminjam;
        this.peminjamTerblacklist = f.peminjamTerblacklist;
    }
    
    @Override
    public boolean isWhereEmpty(){
        return Util.isNullOrEmpty(idPeminjam)
               && Util.isNullOrEmpty(namaPeminjam)
               && Util.isNullOrEmpty(alamatPeminjam)
               && Util.isNullOrEmpty(nomorHpPeminjam)
               && peminjamTerblacklist == null;
    }
    public String getSubquery(){
        if(isWhereEmpty()) return " TRUE ";
        
        List<String> where = new ArrayList<String>();
        where.add("Identitas_Peminjam=P.Identitas_Peminjam");
        addWhereClause(where);
        
        int c = where.size();
        for(int i = 0; i < c; ++i){
            where.set(i, "PM." + where.get(i));
        }
            
        return ("EXISTS (SELECT * FROM Peminjam PM WHERE " + String.join (" AND ", where) + ")");
    }
    
    public void addWhereClause(List<String> where){
        if(!Util.isNullOrEmpty(idPeminjam)) where.add("Identitas_Peminjam=?");
        if(!Util.isNullOrEmpty(namaPeminjam)) where.add("Nama_Peminjam LIKE ?");
        if(!Util.isNullOrEmpty(alamatPeminjam)) where.add("Alamat_Peminjam LIKE ?");
        if(!Util.isNullOrEmpty(nomorHpPeminjam)) where.add("Nomor_Hp_Peminjam LIKE ?");
        if(peminjamTerblacklist != null){
            if(peminjamTerblacklist == true) where.add("Keterangan_Blacklist IS NOT NULL");
            else where.add("Keterangan_Blacklist IS NULL");
        }
    }

    public int applyWhereClause(PreparedStatement stmt, int i) {
        if(!Util.isNullOrEmpty(idPeminjam)){
            stmt.setString(++i, idPeminjam);
            //stmt.setString(++i, Util.prepareFilter(idPeminjam));
            //System.out.println("Applied idpeminjam '" + Util.prepareFilter(idPeminjam) + "' at i=" + i);
        }
        if(!Util.isNullOrEmpty(namaPeminjam)) stmt.setString(++i, Util.prepareFilter(namaPeminjam));
        if(!Util.isNullOrEmpty(alamatPeminjam)) stmt.setString(++i, Util.prepareFilter(alamatPeminjam));
        if(!Util.isNullOrEmpty(nomorHpPeminjam)) stmt.setString(++i, Util.prepareFilter(nomorHpPeminjam));
        return i;
    }
}
