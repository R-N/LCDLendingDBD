/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.gui;

import dbd_lcd.PopupRunnable;
import dbd_lcd.report.MyJasperViewer;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


/**
 *
 * @author user
 */
public class MyFrame extends javax.swing.JFrame {
    public static MyFrame top;
    public MyFrame parent;
    private PopupRunnable onDispose;
    public boolean changed;
    
    protected JTabbedPane tabbedPane;
    
    @Override
    public void setVisible(boolean visible){
        super.setVisible(visible);
        if(parent != null){
            parent.setEnabled(!visible);
            if(!visible){
                parent.requestFocus();
                top = parent;
            }
        }
    }
    
    public void setPopup(MyFrame frame){
        frame.parent = this;
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
        top = frame;
    }
    public void setPopup(MyJasperViewer frame){
        frame.parent = this;
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
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
        dispose(changed);
    }
    
    public void dispose(boolean success){
        if(!onDispose(success)) return;
        top = parent;
        if(parent != null) parent.setEnabled(true);
        super.dispose();
    }
    
    public void setTabbedPane(JTabbedPane tabbedPane){
        this.tabbedPane = tabbedPane;
    }
    
    private JPanel lockedTab;
    public void lockTab(JPanel panel){
        if(tabbedPane == null) return;
        if(lockedTab == panel) return;
        if(lockedTab!= null && panel != null && lockedTab != panel){
            throw new RuntimeException("Tabbedpane already has other tab locked");
        }
        int tc = tabbedPane.getTabCount();
        if(panel != null){
            int i = tabbedPane.indexOfComponent(panel);
            System.out.println("Disable except for tab: " + i);
            for(int j = 0; j < tc; ++j){
                tabbedPane.setEnabledAt(j, i == j);
            }
        }else{
            for(int j = 0; j < tc; ++j){
                tabbedPane.setEnabledAt(j, true);
            }
        }
    }
}
