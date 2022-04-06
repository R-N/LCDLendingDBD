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
 * @author user
 */
public class FilterKeperluanPinjam extends Filter{
    
    public String keperluanPinjam;
    public String namaPenanggungJawab;
    public String ruangPinjam;
    
    public FilterKeperluanPinjam(){}
    public FilterKeperluanPinjam(FilterKeperluanPinjam filter){
        super(filter);
        copyFrom(filter);
    }
    
    public void copyFrom(Filter f){
        if(f instanceof FilterKeperluanPinjam){
            copyFrom((FilterKeperluanPinjam)f);
        }else{
            super.copyFrom(f);
        }
    }
    
    public void copyFrom(FilterKeperluanPinjam f){
        super.copyFrom(f);
        this.keperluanPinjam = f.keperluanPinjam;
        this.namaPenanggungJawab = f.namaPenanggungJawab;
        this.ruangPinjam = f.ruangPinjam;
    }
    
    @Override
    public boolean isWhereEmpty(){
        return Util.isNullOrEmpty(keperluanPinjam)
               && Util.isNullOrEmpty(namaPenanggungJawab)
               && Util.isNullOrEmpty(ruangPinjam);
    }
    
    public String getSubquery(){
        if(isWhereEmpty()) return " TRUE ";
        
        List<String> where = new ArrayList<String>();
        where.add("Nomor_Peminjaman=P.Nomor_Peminjaman");
        addWhereClause(where);
        
        int c = where.size();
        for(int i = 0; i < c; ++i){
            where.set(i, "KP." + where.get(i));
        }
        
        return ("EXISTS (SELECT * FROM Kelas_Pinjam KP WHERE " + String.join (" AND ", where) + ")");
    }
    
    public void addWhereClause(List<String> where){
        if(!Util.isNullOrEmpty(keperluanPinjam)) where.add("Keperluan_Pinjam LIKE ?");
        if(!Util.isNullOrEmpty(namaPenanggungJawab)) where.add("Nama_Penanggung_Jawab LIKE ?");
        if(!Util.isNullOrEmpty(ruangPinjam)) where.add("Ruang_Pinjam lIKE ?");
    }

    public int applyWhereClause(PreparedStatement stmt, int i) {
        if(!Util.isNullOrEmpty(keperluanPinjam)) stmt.setString(++i, Util.prepareFilter(keperluanPinjam));
        if(!Util.isNullOrEmpty(namaPenanggungJawab)) stmt.setString(++i, Util.prepareFilter(namaPenanggungJawab));
        if(!Util.isNullOrEmpty(ruangPinjam)) stmt.setString(++i, Util.prepareFilter(ruangPinjam));
        return i;
    }
}
