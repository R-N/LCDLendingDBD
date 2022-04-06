/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.gui.form.peminjaman;

import dbd_lcd.Util;
import dbd_lcd.system.structs.Barang;
import dbd_lcd.system.structs.IdBarang;
import dbd_lcd.system.structs.filter.FilterBarang;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class BarangComboBoxManager {
    HashMap<String, int[]> source = new HashMap<String, int[]>();
    
    public BarangTableModel dtm;
    
    public HashMap<String, Integer[]> nomorPool = new HashMap<String, Integer[]>();
    public String[] tipePool = new String[0];
    
    public BarangComboBoxManager(BarangTableModel dtm){
        this.dtm = dtm;
        fetch();
    }
    
    public void fetch(){
        FilterBarang filter = new FilterBarang();
        filter.status = Barang.Status.ADA;
        
        for(int i = 0; i < Barang.Tipe.CODE.length; ++i){
            filter.tipe = Barang.Tipe.CODE[i];
            Barang[] bs = Barang.fetch(filter);
            int[] ids = new int[bs.length];
            for(int j = 0; j < bs.length; ++j){
                ids[j] = bs[j].getNomor();
            }
            source.put(filter.tipe, ids);
            if(!nomorPool.containsKey(filter.tipe)) nomorPool.put(filter.tipe, null);
        }
        refresh(-1);
    }
    
    public void refresh(int excludeRow){
        int rc = dtm.getRowCount();
        HashMap<String, HashSet<Integer>> used = new HashMap<String, HashSet<Integer>>();
        for(int i = 0; i < Barang.Tipe.CODE.length; ++i){
            used.put(Barang.Tipe.CODE[i], new HashSet<Integer>());
        }
        for(int i = 0; i < rc; ++i){
            if(i == excludeRow) continue;
            String tipeString = (String)dtm.getValueAt(i, 0);
            if(Util.isNullOrEmpty(tipeString)) continue;
            String nomorString = (String)dtm.getValueAt(i, 1);
            if(Util.isNullOrEmpty(nomorString)) continue;
            String tipe;
            int nomor;
            try{
                tipe = Barang.Tipe.FROM_STRING.get(tipeString);
                nomor = Integer.parseInt(nomorString);
            }catch(Exception ex){
                continue;
            }
            used.get(tipe).add(nomor);
        }
        
        for(int i = 0; i < Barang.Tipe.CODE.length; ++i){
            String tipe = Barang.Tipe.CODE[i];
            List<Integer> pooli = new ArrayList<Integer>();
            int[] sourcei = source.get(tipe);
            HashSet<Integer> usedi = used.get(tipe);
            for(int j = 0; j < sourcei.length; ++j){
                if(!usedi.contains(sourcei[j])){
                    pooli.add(sourcei[j]);
                }
            }
            nomorPool.put(tipe, pooli.toArray(new Integer[0]));
        }
        List<String> tipePool0 = new ArrayList<String>();
        
        for(int i = 0; i < Barang.Tipe.CODE.length; ++i){
            String tipe = Barang.Tipe.CODE[i];
            if(nomorPool.get(tipe).length > 0){
                tipePool0.add(tipe);
            }
        }
        this.tipePool = tipePool0.toArray(new String[0]);
    }
}
