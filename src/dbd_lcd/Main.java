/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd;

import dbd_lcd.gui.MyFrame;
import dbd_lcd.gui.form.DialogKonfirmasi;
import dbd_lcd.gui.form.TampilanUtama;
import dbd_lcd.gui.form.install.SetupFrameExisting;
import dbd_lcd.gui.form.install.SetupFrameNew;
import dbd_lcd.system.structs.Barang;
import dbd_lcd.system.structs.IdBarang;
import java.sql.Timestamp;
import java.util.HashSet;

/**
 *
 * @author user
 */
public class Main {
    
    public static void main(String args[]) {
        Config.init();
        if("0".equals(Config.get("FIRST_RUN"))){
            DialogKonfirmasi dk = new DialogKonfirmasi();
            dk.setTitle("First run");
            dk.setQuestion("Program mendeteksi ini sebagai penggunaan pertama. Apa ini benar-benar penggunaan pertama, database belum dibuat, dan Anda ingin membuat database baru?");
            dk.setOnDispose(new PopupRunnable(){
                @Override
                public boolean run(boolean success){
                    MyFrame popup = null;
                    if(success){
                        popup = new SetupFrameNew();
                    }else{
                        popup = new SetupFrameExisting();
                    }
                    popup.setOnDispose(new PopupRunnable(){
                       @Override
                       public boolean run(boolean success){
                           if(success){
                               dk.setOnDispose(null);
                               dk.dispose();
                               main(args);
                           }
                           return true;
                       }
                    });
                    dk.setPopup(popup);
                    return false;
                }
            });
            dk.setLocationRelativeTo(null);
            dk.setVisible(true);
            return;
        }
        TampilanUtama.main(args);
    }
}
