/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.gui.form.peminjaman;

import dbd_lcd.Util;
import dbd_lcd.system.structs.KeperluanPinjam;
import dbd_lcd.system.structs.RangeWaktu;
import java.sql.Timestamp;

/**
 *
 * @author USER
 */
public class DetailKeperluan extends dbd_lcd.gui.MyFrame {

    /**
     * Creates new form Keperluan_Peminjaman
     */
    KeperluanPinjam keperluan;
    boolean readOnly = false;
    public DetailKeperluan() {
        initComponents();
        setReadOnly(false);
    }
    
    public void setReadOnly(boolean readOnly){
        this.readOnly = readOnly;
        keperluanField.setEnabled(!readOnly);
        tempatField.setEnabled(!readOnly);
        pjField.setEnabled(!readOnly);
        jamMulaiField.setEnabled(!readOnly);
        jamSelesaiField.setEnabled(!readOnly);
        
        cancelButton.setVisible(!readOnly);
        saveButton.setVisible(!readOnly);
        
        doneButton.setVisible(readOnly);
    }
    
    public void load(KeperluanPinjam kp){
        keperluan = kp;
        keperluanField.setText(kp.keperluanPinjam);
        tempatField.setText(kp.ruangPinjam);
        pjField.setText(kp.namaPenanggungJawab);
        jamMulaiField.setText(Util.formatTime(kp.durasi.awal));
        jamSelesaiField.setText(Util.formatTime(kp.durasi.akhir));
        setReadOnly(true);
    }
    public KeperluanPinjam read(){
        String keperluan = Util.nullify(keperluanField.getText());
        String tempat = Util.nullify(tempatField.getText());
        String pj = Util.nullify(pjField.getText());
        String mulaiString = Util.nullify(jamMulaiField.getText());
        String selesaiString = Util.nullify(jamSelesaiField.getText());
        
        if(keperluan == null || keperluan.length() < 3) throw new RuntimeException("Keperluan tidak valid");
        if(tempat == null || tempat.length() < 4) throw new RuntimeException("Tempat tidak valid");
        if(pj == null || pj.length() < 3) throw new RuntimeException("Penanggung jawab tidak valid");
        Timestamp mulai = null;
        Timestamp selesai = null;
        try{
            mulai = Util.parseTime(mulaiString);
            if(mulai == null) throw new RuntimeException();
        }catch(Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("Waktu mulai tidak valid");
        }
        try{
            selesai = Util.parseTime(selesaiString);
            if(selesai == null) throw new RuntimeException();
        }catch(Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("Waktu selesai tidak valid");
        }
        RangeWaktu rangeWaktu = new RangeWaktu(mulai, selesai);
        
        KeperluanPinjam kp = new KeperluanPinjam(null, keperluan, pj, tempat, rangeWaktu);
        return kp;
    }
    
    public void save(){
        
        KeperluanPinjam kp = null;
        try{
            kp = read();
        }catch(Exception ex){
            Util.handleException(this, ex);
            return;
        }
        if(!kp.equals(keperluan)){
            onDispose(true);
        }
        dispose();
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        keperluanField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tempatField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        pjField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jamMulaiField = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jamSelesaiField = new javax.swing.JFormattedTextField();
        jPanel5 = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        doneButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 153, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 236, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.WEST);

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Keperluan");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(keperluanField, gridBagConstraints);
        new dbd_lcd.gui.LimitDocumentFilter(64).attach(keperluanField);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tempat");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel2, gridBagConstraints);

        tempatField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tempatFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(tempatField, gridBagConstraints);
        new dbd_lcd.gui.LimitDocumentFilter(64).attach(tempatField);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("PJ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel5, gridBagConstraints);

        pjField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pjFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(pjField, gridBagConstraints);
        new dbd_lcd.gui.LimitDocumentFilter(64).attach(pjField);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Jam Mulai");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel3, gridBagConstraints);

        jamMulaiField.setFormatterFactory(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jamMulaiField, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Jam Selesai");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel4, gridBagConstraints);

        jamSelesaiField.setFormatterFactory(null);
        jamSelesaiField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jamSelesaiFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jamSelesaiField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jPanel4, gridBagConstraints);

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));

        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/SILANG.png"))); // NOI18N
        cancelButton.setContentAreaFilled(false);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        jPanel5.add(cancelButton);

        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/CENTANG.png"))); // NOI18N
        saveButton.setContentAreaFilled(false);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        jPanel5.add(saveButton);

        doneButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/CENTANG.png"))); // NOI18N
        doneButton.setContentAreaFilled(false);
        doneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doneButtonActionPerformed(evt);
            }
        });
        jPanel5.add(doneButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel3.add(jPanel5, gridBagConstraints);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tempatFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tempatFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tempatFieldActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
        save();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void pjFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pjFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pjFieldActionPerformed

    private void doneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doneButtonActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_doneButtonActionPerformed

    private void jamSelesaiFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jamSelesaiFieldActionPerformed
        // TODO add your handling code here:
        save();
    }//GEN-LAST:event_jamSelesaiFieldActionPerformed

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
            java.util.logging.Logger.getLogger(DetailKeperluan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DetailKeperluan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DetailKeperluan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DetailKeperluan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new DetailKeperluan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton doneButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JFormattedTextField jamMulaiField;
    private javax.swing.JFormattedTextField jamSelesaiField;
    private javax.swing.JTextField keperluanField;
    private javax.swing.JTextField pjField;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField tempatField;
    // End of variables declaration//GEN-END:variables
}