/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.report;

import dbd_lcd.PopupRunnable;
import dbd_lcd.gui.MyFrame;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author user
 */
public class MyJasperViewer extends JasperViewer {
    
    public MyFrame parent;
    private PopupRunnable onDispose;
    public MyJasperViewer(JasperPrint jasperPrint) {
        super(jasperPrint, false);
    }
    
    @Override
    public void setVisible(boolean visible){
        super.setVisible(visible);
        if(parent != null){
            parent.setEnabled(!visible);
            if(!visible){
                parent.requestFocus();
            }
        }
    }
    
    
    public boolean hasOnDispose(){
        return onDispose != null;
    }
    
    public boolean onDispose(boolean success){
        if(onDispose != null) {
            boolean ret = onDispose.run(success);
            if(ret){
                onDispose = null;
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }
    
    public void setOnDispose(PopupRunnable onDispose){
        this.onDispose = onDispose;
    }
    
    @Override
    public void dispose(){
        //setVisible(false);
        dispose(false);
    }
    
    public void dispose(boolean success){
        if(!onDispose(success)) return;
        if(parent != null) parent.setEnabled(true);
        super.dispose();
    }
    
}
