/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.gui.form.peminjaman;

import dbd_lcd.Util;
import dbd_lcd.system.structs.Barang;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JTable;

/**
 *
 * @author user
 */
public class TipeBarangComboBoxModel extends AbstractListModel implements ComboBoxModel {
    BarangComboBoxManager manager;
    JTable table;
    String selection = null;
    
    public TipeBarangComboBoxModel(BarangComboBoxManager manager, JTable table){
        this.manager = manager;
        this.table =table;
    }

    public Object getElementAt(int index) {
        return Barang.Tipe.TO_STRING.get(manager.tipePool[index]);
    }

    public int getSize() {
        return manager.tipePool.length;
    }

    public void setSelectedItem(Object anItem) {
        selection = (String) anItem; // to select and register an
    } // item from the pull-down list

    // Methods implemented from the interface ComboBoxModel
    public Object getSelectedItem() {
        return selection; // to add the selection to the combo box
    }
    
    public String getSelectedCode(){
        return Barang.Tipe.FROM_STRING.get((String)selection);
    }
}
