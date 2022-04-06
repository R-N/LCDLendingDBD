/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.system.structs;

import dbd_lcd.Util;
import java.util.Objects;

/**
 *
 * @author user
 */
public class IdBarang {
    public int nomor;
    public String tipe;
    
    public IdBarang(int nomor, String tipe){
        setNomor(nomor);
        setTipe(tipe);
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
                return true;
        if (obj == null)
                return false;
        if (!(obj instanceof IdBarang))
                return false;
        IdBarang id = (IdBarang)obj;
        return nomor == id.nomor
                && Util.equals(tipe, id.tipe);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.nomor;
        hash = 97 * hash + Objects.hashCode(this.tipe);
        return hash;
    }
    
    @Override
    public String toString(){
        return tipe + nomor;
    }
    public String getTipeString(){
        return Barang.Tipe.TO_STRING.get(tipe);
    }
    
    public void setNomor(int nomor){
        if(nomor < 1) throw new RuntimeException("Invalid nomor");
        this.nomor = nomor;
    }
    
    public void setTipe(String tipe){
        Barang.Tipe.cekKode(tipe);
        this.tipe = tipe;
    }
    
}
