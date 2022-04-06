/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.system.structs;

import dbd_lcd.Util;
import dbd_lcd.database.CallableStatement;
import dbd_lcd.system.LoginModule;
import java.sql.Timestamp;

/**
 *
 * @author Lenovo2
 */
public class KeperluanPinjam {
    public Integer id;
    public String keperluanPinjam;
    public String namaPenanggungJawab;
    public String ruangPinjam;
    public RangeWaktu durasi;
    
    public KeperluanPinjam(Integer id, String keperluanPinjam, String namaPenanggungJawab, String ruangPinjam, RangeWaktu durasi){
        this.id = id;
        this.keperluanPinjam = keperluanPinjam;
        this.namaPenanggungJawab = namaPenanggungJawab;
        this.ruangPinjam = ruangPinjam;
        this.durasi = durasi;
    }
    public KeperluanPinjam(Integer id, String keperluanPinjam, String namaPenanggungJawab, String ruangPinjam, Timestamp waktuAwal, Timestamp waktuAkhir){
        this(id, keperluanPinjam, namaPenanggungJawab, ruangPinjam, new RangeWaktu(waktuAwal, waktuAkhir));
    }
    public KeperluanPinjam(KeperluanPinjam kp){
        copyFrom(kp);
    }
    
    public void copyFrom(KeperluanPinjam kp){
        this.id = kp.id;
        this.keperluanPinjam = kp.keperluanPinjam;
        this.namaPenanggungJawab = kp.namaPenanggungJawab;
        this.ruangPinjam = kp.ruangPinjam;
        this.durasi = kp.durasi;
    }
    
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null) return false;
        if(!(o instanceof KeperluanPinjam)) return false;
        KeperluanPinjam rhs = (KeperluanPinjam) o;
        return this.id == rhs.id
                && Util.equals(namaPenanggungJawab, rhs.namaPenanggungJawab)
                && Util.equals(keperluanPinjam, rhs.keperluanPinjam)
                && Util.equals(ruangPinjam, rhs.ruangPinjam)
                && Util.equals(durasi, rhs.durasi);
    }
    
    public void insert(CallableStatement stmt2){
        stmt2.setString(1, keperluanPinjam);
        stmt2.setString(2, namaPenanggungJawab);
        stmt2.setString(3, ruangPinjam);
        stmt2.setDateTime(4, durasi.awal);
        stmt2.setDateTime(5, durasi.akhir);
        
        stmt2.setString(7, LoginModule.getSesi());
    
        stmt2.executeUpdate();

        this.id = stmt2.getInt(8);
        LoginModule.setTimeout(stmt2.getDateTime(9));
    }
    
    public void update(CallableStatement stmt2){
        stmt2.setInt(1, id);
        stmt2.setString(2, keperluanPinjam);
        stmt2.setString(3, namaPenanggungJawab);
        stmt2.setString(4, ruangPinjam);
        stmt2.setDateTime(5, durasi.awal);
        stmt2.setDateTime(6, durasi.akhir);


        stmt2.executeUpdate();

        LoginModule.setTimeout(stmt2.getDateTime(8));
    }
}
