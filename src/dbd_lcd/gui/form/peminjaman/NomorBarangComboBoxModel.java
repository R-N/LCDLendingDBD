/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.gui.form.peminjaman;

import dbd_lcd.system.structs.Barang;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JTable;

/**
 *
 * @author user
 */
public class NomorBarangComboBoxModel extends AbstractListModel implements ComboBoxModel {
    BarangComboBoxManager manager;
    JTable table;
    Integer selection = null;

    public NomorBarangComboBoxModel(BarangComboBoxManager manager, JTable table){
        this.manager = manager;
        this.table =table;
    }
    public Object getElementAt(int index) {
        String tipe = getTipe();
        if(tipe == null) return null;
        return manager.nomorPool.get(tipe)[index];
    }
    
    private String getTipe(){
        int row = table.getEditingRow();
        if(row < 0) return null;
        String tipeString = (String)table.getValueAt(row, 0);
        if(tipeString == null) return null;
        return Barang.Tipe.FROM_STRING.get(tipeString);
    }

    public int getSize() {
        String tipe = getTipe();
        if(tipe == null) return 0;
        return manager.nomorPool.get(tipe).length;
    }

    public void setSelectedItem(Object anItem) {
        selection = (Integer) anItem; // to select and register an
    } // item from the pull-down list

    // Methods implemented from the interface ComboBoxModel
    public Object getSelectedItem() {
        return selection; // to add the selection to the combo box
    }
}