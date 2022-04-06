/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd;

import dbd_lcd.gui.MyFrame;
import dbd_lcd.gui.form.FormLogin;
import dbd_lcd.system.structs.Barang;

/**
 *
 * @author user
 */
public abstract class RequireLoginRunnable implements PopupRunnable{
    
    @Override
    public final boolean run(boolean success){
        if(success){
            FormLogin.cekDanMintaSesi(MyFrame.top, new PopupRunnable(){
                @Override
                public boolean run(boolean success){
                    if(success){
                        onLogin();
                    }
                    return true;
                }
            });
            return false;
        }
        return true;
    }
    
    public abstract void onLogin();
}
