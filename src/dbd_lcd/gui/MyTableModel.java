/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.gui;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class MyTableModel extends DefaultTableModel{
    protected boolean cellEditable = false;
    @Override
    public boolean isCellEditable(int row, int column){
        return cellEditable;
    }
    public void setCellEditable(boolean cellEditable){
        this.cellEditable = cellEditable;
    }
    public boolean isCellEditable(){
        return cellEditable;
    }
}
