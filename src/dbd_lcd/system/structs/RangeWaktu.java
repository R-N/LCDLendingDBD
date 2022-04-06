/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.system.structs;

import dbd_lcd.Util;
import java.sql.Timestamp;

/**
 *
 * @author user
 */
public class RangeWaktu {
    public Timestamp awal;
    public Timestamp akhir;
    
    public RangeWaktu(){}
    public RangeWaktu(Timestamp awal, Timestamp akhir){
        this.awal = awal;
        this.akhir = akhir;
        
    }
    
    public boolean isEmpty(){
        return awal == null && akhir == null;
    }
    public static boolean isNullOrEmpty(RangeWaktu rw){
        return rw == null || rw.isEmpty();
    }
    public boolean isValid(){
        return !isEmpty() && (awal == null || akhir == null || awal.before(akhir));
    }
    
    public void cekValid(){
        if(!isValid()){
            throw new RuntimeException("Range waktu tidak valid");
        }
    }
    
    public static RangeWaktu createIfNotNull(Timestamp awal, Timestamp akhir){
        if(awal == null && akhir == null) return null;
        return new RangeWaktu(awal, akhir);
    }
    
    public static boolean isValid(RangeWaktu rw){
        return rw != null && rw.isValid();
    }
    
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null) return false;
        if(!(o instanceof RangeWaktu)) return false;
        RangeWaktu rhs = (RangeWaktu)o;
        return Util.equals(awal, rhs.awal)
                && Util.equals(akhir, rhs.akhir);
    }
    
    public String getAwalTimeString(){
        return Util.formatTime(awal);
    }
    public String getAkhirTimeString(){
        return Util.formatTime(akhir);
    }
}
