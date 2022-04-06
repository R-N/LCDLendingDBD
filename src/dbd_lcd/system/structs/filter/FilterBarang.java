/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.system.structs.filter;

import dbd_lcd.Util;
import dbd_lcd.database.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo2
 */
public class FilterBarang extends Filter {
    
    public Integer nomor;
    public String tipe;
    public String status;
    public String keterangan;
    
    public FilterBarang(){}
    public FilterBarang(FilterBarang filter){
        super(filter);
        copyFrom(filter);
    }
    
    public void copyFrom(Filter f){
        if(f instanceof FilterBarang){
            copyFrom((FilterBarang)f);
        }else{
            super.copyFrom(f);
        }
    }
    
    public void copyFrom(FilterBarang f){
        super.copyFrom(f);
        this.nomor = f.nomor;
        this.tipe = f.tipe;
        this.status = f.status;
        this.keterangan = f.keterangan;
    }
    
    @Override
    public boolean isWhereEmpty(){
        return nomor == null && tipe == null && status == null && Util.isNullOrEmpty(keterangan);
    }
    public String getSubquery(){
        if(isWhereEmpty()) return " TRUE ";
        
        List<String> where = new ArrayList<String>();
        where.add("Nomor_Peminjaman=P.Nomor_Peminjaman");
        addWhereClause(where);
        
        int c = where.size();
        for(int i = 0; i < c; ++i){
            where.set(i, "RB." + where.get(i));
        }
            
        return ("EXISTS (SELECT * FROM Riwayat_Barang RB WHERE " + String.join (" AND ", where) + ")");
    }
    
    @Override
    public void addWhereClause(List<String> where){
        if(nomor != null) where.add("Nomor_Barang=?");
        if(tipe != null) where.add("Tipe_Barang=?");
        if(status != null) where.add("Status_Barang=?");
        if(!Util.isNullOrEmpty(keterangan)) where.add("Keterangan_Barang LIKE ?");
    }

    @Override
    public int applyWhereClause(PreparedStatement stmt, int i) {
        if(nomor != null) stmt.setInt(++i, nomor);
        if(tipe != null) stmt.setString(++i, ""+tipe);
        if(status != null) stmt.setString(++i, ""+status);
        if(!Util.isNullOrEmpty(keterangan)) stmt.setString(++i, Util.prepareFilter(keterangan));
        return i;
    }
}
