/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.gui.form.peminjaman;

import dbd_lcd.gui.MyTableModel;
import dbd_lcd.Util;
import dbd_lcd.system.structs.Barang;
import dbd_lcd.system.structs.Peminjaman;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author user
 */
public class BarangTableModel extends MyTableModel{
    boolean[] borrowables = new boolean[0];
    Barang[] source;
    
    public void setSource(Barang[] source){
        this.source = source;
    }
    
    public void setBorrowables(boolean[] borrowables){
        this.borrowables = borrowables;
    }
    
    @Override
    public boolean isCellEditable(int row, int column){
        if(!cellEditable) return false;
        if(column == 2){
            if (row >= borrowables.length) return false;
            return borrowables[row];
        }
        if(row < borrowables.length) return false;
        if(column == 1){
            String tipeString = (String)getValueAt(row, 0);
            if(Util.isNullOrEmpty(tipeString)) return false;
            try{
                Barang.Tipe.FROM_STRING.get(tipeString);
                return true;
            }catch(Exception ex){
                return false;
            } 
        }
        return true;
    }
    
    @Override
    public void setValueAt(Object value, int row, int col){
        if(col == 2 && row < source.length){
            Object real = source[row].getEndTime();
            if(value instanceof Boolean && real != null && Boolean.TRUE.equals(value)){
                super.setValueAt(real, row, col);
            }
        }
        super.setValueAt(value, row, col);
    }
}
