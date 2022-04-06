/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.gui.form.peminjaman;

import dbd_lcd.gui.MyTableModel;
import dbd_lcd.PopupRunnable;
import dbd_lcd.Util;
import dbd_lcd.gui.form.peminjam.DetailPeminjamFrame;
import dbd_lcd.system.structs.Barang;
import dbd_lcd.system.structs.IdBarang;
import dbd_lcd.system.structs.KeperluanPinjam;
import dbd_lcd.system.structs.Peminjam;
import dbd_lcd.system.structs.Peminjaman;
import dbd_lcd.system.structs.filter.FilterPeminjam;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author admin
 */
public class TambahPeminjaman extends dbd_lcd.gui.MyFrame {

    /**
     * Creates new form FormulirPeminjaman
     */
    BarangTableModel barangTableModel;
    BarangComboBoxManager manager;
    Peminjam peminjam;
    public TambahPeminjaman() {
        initComponents();
        prepareBarangTable();
        findPeminjam();
    }
    
    public void prepareBarangTable(){
        BarangTableModel dtm = new BarangTableModel();
        dtm.setCellEditable(true);
        dtm.addColumn("Tipe Barang");
        dtm.addColumn("Nomor Barang");
        manager = new BarangComboBoxManager(dtm);
        manager.fetch();
        barangTableModel = dtm;
        TabelDetailBarang.setModel(dtm);
        tipeBarangCB.setModel(new TipeBarangComboBoxModel(manager, TabelDetailBarang));
        nomorBarangCB.setModel(new NomorBarangComboBoxModel(manager, TabelDetailBarang));
        
        TabelDetailBarang.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(tipeBarangCB));
        TabelDetailBarang.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(nomorBarangCB));
        
    }
    
    
    public void setIdPeminjam(String idPeminjam){
        idPeminjamField.setText(idPeminjam);
    }
    
    public String getIdPeminjam(){
        String idPeminjam = Util.nullify(Util.preparePhoneNumber(idPeminjamField.getText()));
        return idPeminjam;
    }
    
    public void findPeminjam(){
        String idPeminjam = getIdPeminjam();
        ButtonPlusPeminjam.setVisible(false);
        idPeminjamField.setToolTipText(null);
        try{
            if(idPeminjam == null || idPeminjam.length() < 9){
                throw new RuntimeException("Nomor identitas tidak valid");
            }
            FilterPeminjam filter = new FilterPeminjam();
            filter.idPeminjam = idPeminjam;
            Peminjam[] ps = Peminjam.fetch(filter);
            if(ps.length == 0){
                ButtonPlusPeminjam.setVisible(true);
                throw new RuntimeException("Peminjam tidak ditemukan");
            }
            peminjam = ps[0];
            namaPeminjamLabel.setText(peminjam.getNamaPeminjam());
            idPeminjamField.setBackground(peminjam.getBlacklistColor().get(false));
            if(peminjam.isPeminjamTerblacklist()){
                Util.showError(
                        this, 
                        "Anda Terblacklist", 
                        String.format(
                                "Anda, %s, telah diblacklist dari layanan ini karena: %s",
                                peminjam.getNamaPeminjam(),
                                peminjam.getKeteranganBlacklist()
                        )
                );
                idPeminjamField.setToolTipText(peminjam.getKeteranganBlacklist());
            }
        }catch(Exception ex){
            peminjam = null;
            idPeminjamField.setBackground(Peminjam.getBlacklistColor(true).get(false));
            namaPeminjamLabel.setText(ex.getMessage());
        }
    }
    
    public void tambahPeminjam(){
        setEnabled(false);
        DetailPeminjamFrame tp = new DetailPeminjamFrame();
        tp.setOnDispose(new PopupRunnable(){
            @Override
            public boolean run(boolean success){
                if(success){
                    setIdPeminjam(tp.getIdPeminjam());
                    findPeminjam();
                }
                return true;
            }
        });
        tp.setIdPeminjam(getIdPeminjam());
        setPopup(tp);
    }
    
    public Barang[] readBarang(){
        int rc = TabelDetailBarang.getRowCount();
        Barang[] ret = new Barang[rc];
        for(int i = 0; i < rc; ++i){
            ret[i] = Util.readBarang(TabelDetailBarang, i);
        }
        return ret;
    }
    
    public Peminjaman read(){
        Peminjaman p = new Peminjaman(
                peminjam,
                readBarang(),
                daftarKeperluanPanel.read(),
                Util.nullify(keteranganTA.getText())
        );
        return p;
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

        tipeBarangCB = new javax.swing.JComboBox<>();
        nomorBarangCB = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        idPeminjamField = new javax.swing.JTextField();
        ButtonPlusPeminjam = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        namaPeminjamLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        keteranganTA = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelDetailBarang = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        ButtonMinFP = new javax.swing.JButton();
        ButtonPlusFP = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        daftarKeperluanPanel = new dbd_lcd.gui.form.peminjaman.DaftarKeperluanPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();

        tipeBarangCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipeBarangCBActionPerformed(evt);
            }
        });

        nomorBarangCB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nomorBarangCBFocusLost(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(0, 102, 102));
        getContentPane().setLayout(new java.awt.CardLayout());

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));
        jPanel2.setPreferredSize(new java.awt.Dimension(475, 300));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(255, 153, 0));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setBackground(new java.awt.Color(255, 153, 0));
        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 21)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Formulir Peminjaman");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        jPanel1.add(jLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(jPanel1, gridBagConstraints);

        jTabbedPane1.setBackground(new java.awt.Color(0, 102, 102));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(475, 270));

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("No. Identitas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel2, gridBagConstraints);

        idPeminjamField.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        idPeminjamField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                idPeminjamFieldFocusLost(evt);
            }
        });
        idPeminjamField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idPeminjamFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(idPeminjamField, gridBagConstraints);
        new dbd_lcd.gui.LimitDocumentFilter(18).attach(idPeminjamField);

        ButtonPlusPeminjam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/TAMBAH.png"))); // NOI18N
        ButtonPlusPeminjam.setContentAreaFilled(false);
        ButtonPlusPeminjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonPlusPeminjamActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = -24;
        gridBagConstraints.ipady = -6;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(ButtonPlusPeminjam, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Nama");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel3, gridBagConstraints);

        namaPeminjamLabel.setText("Tidak ditemukan");
        namaPeminjamLabel.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 118;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(namaPeminjamLabel, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Keterangan");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel4, gridBagConstraints);

        keteranganTA.setColumns(20);
        keteranganTA.setRows(5);
        jScrollPane2.setViewportView(keteranganTA);
        new dbd_lcd.gui.LimitDocumentFilter(256).attach(keteranganTA);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jScrollPane2, gridBagConstraints);

        jTabbedPane1.addTab("Peminjaman", jPanel4);

        jPanel5.setBackground(new java.awt.Color(0, 102, 102));
        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel5.setPreferredSize(new java.awt.Dimension(473, 260));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jPanel7.setOpaque(false);
        jPanel7.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setBackground(new java.awt.Color(0, 102, 102));
        jScrollPane1.setOpaque(false);

        TabelDetailBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null}
            },
            new String [] {
                "Barang", "Nomor"
            }
        ));
        TabelDetailBarang.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                TabelDetailBarangPropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(TabelDetailBarang);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel7.add(jScrollPane1, gridBagConstraints);

        jPanel6.setOpaque(false);
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        ButtonMinFP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/MIN.png"))); // NOI18N
        ButtonMinFP.setContentAreaFilled(false);
        ButtonMinFP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonMinFPActionPerformed(evt);
            }
        });
        jPanel6.add(ButtonMinFP);

        ButtonPlusFP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/TAMBAH.png"))); // NOI18N
        ButtonPlusFP.setContentAreaFilled(false);
        ButtonPlusFP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonPlusFPActionPerformed(evt);
            }
        });
        jPanel6.add(ButtonPlusFP);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel7.add(jPanel6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel5.add(jPanel7, gridBagConstraints);

        jTabbedPane1.addTab("Barang", jPanel5);

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel3.setPreferredSize(new java.awt.Dimension(473, 260));
        jPanel3.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(daftarKeperluanPanel, gridBagConstraints);

        jTabbedPane1.addTab("Keperluan", jPanel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jTabbedPane1, gridBagConstraints);

        jPanel8.setBackground(new java.awt.Color(0, 102, 102));
        jPanel8.setOpaque(false);
        jPanel8.setLayout(new java.awt.GridBagLayout());

        jPanel11.setOpaque(false);
        jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/SILANG.png"))); // NOI18N
        cancelButton.setContentAreaFilled(false);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        jPanel11.add(cancelButton);

        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/CENTANG.png"))); // NOI18N
        addButton.setContentAreaFilled(false);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        jPanel11.add(addButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        jPanel8.add(jPanel11, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel2.add(jPanel8, gridBagConstraints);

        getContentPane().add(jPanel2, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        try{
            read();
        }catch(Exception ex){
            ex.printStackTrace();
            Util.handleError(this, ex);
            return;
        }
        
        dispose(true);
    }//GEN-LAST:event_addButtonActionPerformed

    private void idPeminjamFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_idPeminjamFieldFocusLost
        // TODO add your handling code here:
        findPeminjam();
    }//GEN-LAST:event_idPeminjamFieldFocusLost

    private void idPeminjamFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idPeminjamFieldActionPerformed
        // TODO add your handling code here:
        findPeminjam();
    }//GEN-LAST:event_idPeminjamFieldActionPerformed

    private void ButtonPlusPeminjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonPlusPeminjamActionPerformed
        // TODO add your handling code here:
        tambahPeminjam();
    }//GEN-LAST:event_ButtonPlusPeminjamActionPerformed

    private void ButtonPlusFPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonPlusFPActionPerformed
        // TODO add your handling code here:
        barangTableModel.addRow(new Object[]{null, null});
    }//GEN-LAST:event_ButtonPlusFPActionPerformed

    private void TabelDetailBarangPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_TabelDetailBarangPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_TabelDetailBarangPropertyChange

    private void tipeBarangCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipeBarangCBActionPerformed
        // TODO add your handling code here:
        int row = TabelDetailBarang.getSelectedRow();
        if(row < 0) row = TabelDetailBarang.getEditingRow();
        if(row < 0) return;
        nomorBarangCB.setModel(nomorBarangCB.getModel());
        String selection = (String)TabelDetailBarang.getValueAt(row, 0);
        if(selection == null){
            TabelDetailBarang.setValueAt(null, row, 1);
        }else{
            Integer old = (Integer) TabelDetailBarang.getValueAt(row, 1);
            Integer[] pool = manager.nomorPool.get(Barang.Tipe.FROM_STRING.get(selection));
            if(!Util.contains(pool, old)) TabelDetailBarang.setValueAt(pool[0], row, 1);
        }
    }//GEN-LAST:event_tipeBarangCBActionPerformed

    private void ButtonMinFPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonMinFPActionPerformed
        // TODO add your handling code here:
        int[] rows = TabelDetailBarang.getSelectedRows();
        for(int i = rows.length-1; i >= 0; --i){
            barangTableModel.removeRow(rows[i]);
        }
    }//GEN-LAST:event_ButtonMinFPActionPerformed

    private void nomorBarangCBFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nomorBarangCBFocusLost
        // TODO add your handling code here:
        TableCellEditor editor = TabelDetailBarang.getCellEditor();
        if(editor!=null) editor.stopCellEditing();
    }//GEN-LAST:event_nomorBarangCBFocusLost

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TambahPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TambahPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TambahPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TambahPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TambahPeminjaman().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonMinFP;
    private javax.swing.JButton ButtonPlusFP;
    private javax.swing.JButton ButtonPlusPeminjam;
    private javax.swing.JTable TabelDetailBarang;
    private javax.swing.JButton addButton;
    private javax.swing.JButton cancelButton;
    private dbd_lcd.gui.form.peminjaman.DaftarKeperluanPanel daftarKeperluanPanel;
    private javax.swing.JTextField idPeminjamField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea keteranganTA;
    private javax.swing.JLabel namaPeminjamLabel;
    private javax.swing.JComboBox<String> nomorBarangCB;
    private javax.swing.JComboBox<String> tipeBarangCB;
    // End of variables declaration//GEN-END:variables
}
