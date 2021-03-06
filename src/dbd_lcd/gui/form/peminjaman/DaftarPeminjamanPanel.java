/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.gui.form.peminjaman;

import dbd_lcd.gui.MyFrame;
import dbd_lcd.gui.MyPanel;
import dbd_lcd.gui.MyTableModel;
import dbd_lcd.PopupRunnable;
import dbd_lcd.RequireLoginRunnable;
import dbd_lcd.Util;
import dbd_lcd.gui.form.PeriodPicker;
import dbd_lcd.report.MyJasperViewer;
import dbd_lcd.system.structs.Peminjaman;
import dbd_lcd.system.structs.RiwayatPeminjaman;
import dbd_lcd.system.structs.filter.FilterPeminjaman;
import java.awt.Component;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author user
 */
public class DaftarPeminjamanPanel extends MyPanel {
    public static class Mode{
        public static final int MAIN = 0;
        public static final int SUB = 1;
    }
    
    public int mode = 0;
    /**
     * Creates new form DaftarPeminjamanPanel
     */
    FilterPeminjaman filter = new FilterPeminjaman();
    RiwayatPeminjaman[] data;
    public DaftarPeminjamanPanel() {
        super();
        initComponents();
        setMode(Mode.MAIN);
    }
    
    public void setMode(int mode){
        switch(mode){
            case Mode.MAIN:{
                addButton.setVisible(true);
                
                doneButton.setVisible(false);
                break;
            }
            case Mode.SUB:{
                doneButton.setVisible(true);
                
                addButton.setVisible(false);
                break;
            }
        }
    }

    public void setFilter(FilterPeminjaman filter){
        this.filter = filter;
        refresh();
    }
    
    public FilterPeminjaman getFilter(){
        return filter;
    }
    
    public void refresh(){
        RiwayatPeminjaman[] rps = RiwayatPeminjaman.fetch(filter);
        
        
        MyTableModel dtm = new MyTableModel();
        dtm.addColumn("Tanggal");
        dtm.addColumn("Peminjam");
        dtm.addColumn("Status");
        dtm.addColumn("Waktu Kembali");
        
        for(int i = 0; i < rps.length; ++i){
            RiwayatPeminjaman rp = rps[i];
            
            dtm.addRow(new Object[]{
                Util.formatDate(rp.getWaktuPinjam()),
                rp.getNamaPeminjam(),
                rp.getStatusString(),
                Util.formatDateTime(rp.getShownWaktuKembali())
            });
        }
        
        data = rps;
        peminjamanTable.setModel(dtm);
    }
    
    public TambahPeminjaman tambahPeminjaman(){
        TambahPeminjaman tp = new TambahPeminjaman();
        tp.setOnDispose(new RequireLoginRunnable(){
            @Override
            public void onLogin(){
                Peminjaman p = tp.read();
                try{
                    p.simpan();
                    refresh();
                    tp.dispose();
                }catch(Exception ex){
                    Util.handleException(tp, ex);
                }
            }
        });
        return tp;
    }
    
    public void determineCellColor(Component comp, int row, int col, boolean selected){
        if(data == null) return;
        switch(col){
            case 1:{
                comp.setBackground(data[row].getBlacklistColor().get(selected));
                break;
            }
            case 2:{
                comp.setBackground(Peminjaman.Status.getColor(data[row].getStatus()).get(selected));
                break;
            }
            case 3:{
                comp.setBackground(data[row].getShownWaktuKembaliColor().get(selected));
                break;
            }
            default:{
                comp.setBackground(Util.ColorPack.WHITE.get(selected));
                break;
            }
        }
    }
    
    public void lihatPeminjaman(){
        
        int row = peminjamanTable.getSelectedRow();
        if(row < 0) return;
        
        lihatPeminjaman(row);
    }
    
    public void lihatPeminjaman(int index){
        DetailPeminjaman dp = new DetailPeminjaman();
        dp.load(data[index].getPeminjaman());
        dp.setOnDispose(new PopupRunnable(){
            @Override
            public boolean run(boolean success){
                if(success){
                    refresh();
                }
                return true;
            }
        });
        setPopup(dp);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        peminjamanTable = new javax.swing.JTable(){
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component comp = super.prepareRenderer(renderer, row, col);
                determineCellColor(comp, convertRowIndexToModel(row), convertColumnIndexToModel(col), Util.contains(getSelectedRows(), row));
                return comp;
            }
        };
        jPanel2 = new javax.swing.JPanel();
        filterButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        viewButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        doneButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 102, 102));
        setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setBackground(new java.awt.Color(0, 102, 102));
        jScrollPane1.setOpaque(false);

        peminjamanTable.setAutoCreateRowSorter(true);
        peminjamanTable.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        peminjamanTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tanggal", "Peminjam", "Status", "Waktu Kembali"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        peminjamanTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                peminjamanTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(peminjamanTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jScrollPane1, gridBagConstraints);

        jPanel2.setOpaque(false);

        filterButton.setBackground(new java.awt.Color(255, 153, 0));
        filterButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        filterButton.setText("Filter");
        filterButton.setOpaque(false);
        filterButton.setSelected(true);
        filterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterButtonActionPerformed(evt);
            }
        });
        jPanel2.add(filterButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(jPanel2, gridBagConstraints);

        jPanel1.setOpaque(false);

        viewButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/KC PMBESAR.png"))); // NOI18N
        viewButton.setContentAreaFilled(false);
        viewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewButtonActionPerformed(evt);
            }
        });
        jPanel1.add(viewButton);

        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/TAMBAH.png"))); // NOI18N
        addButton.setContentAreaFilled(false);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        jPanel1.add(addButton);

        doneButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/CENTANG.png"))); // NOI18N
        doneButton.setContentAreaFilled(false);
        doneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doneButtonActionPerformed(evt);
            }
        });
        jPanel1.add(doneButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        getFrame().setPopup(tambahPeminjaman());
    }//GEN-LAST:event_addButtonActionPerformed

    private void doneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doneButtonActionPerformed
        // TODO add your handling code here:
        getFrame().dispose();
    }//GEN-LAST:event_doneButtonActionPerformed

    private void viewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewButtonActionPerformed
        // TODO add your handling code here:
        lihatPeminjaman();
    }//GEN-LAST:event_viewButtonActionPerformed

    private void peminjamanTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_peminjamanTableMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() != 2) return;
        
        lihatPeminjaman();
        
    }//GEN-LAST:event_peminjamanTableMouseClicked

    private void filterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterButtonActionPerformed
        // TODO add your handling code here:
        FilterPeminjamanFrame fpf = new FilterPeminjamanFrame();
        fpf.load(filter);
        fpf.setOnDispose(new PopupRunnable(){
            @Override
            public boolean run(boolean success){
                if(success){
                    filter = fpf.read();
                    refresh();
                }
                return true;
            }
        });
        setPopup(fpf);
    }//GEN-LAST:event_filterButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton doneButton;
    private javax.swing.JButton filterButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable peminjamanTable;
    private javax.swing.JButton viewButton;
    // End of variables declaration//GEN-END:variables
}
