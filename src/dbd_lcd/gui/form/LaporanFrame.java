/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.gui.form;

import dbd_lcd.PopupRunnable;
import dbd_lcd.Util;
import dbd_lcd.report.MyJasperViewer;
import java.util.HashMap;

/**
 *
 * @author user
 */
public class LaporanFrame extends dbd_lcd.gui.MyFrame {

    /**
     * Creates new form Laporan
     */
    public LaporanFrame() {
        initComponents();
    }

    public void showReport(String reportName){
        
        PeriodPicker pp = new PeriodPicker();
        pp.setOnDispose(new PopupRunnable(){
            @Override
            public boolean run(boolean success){
                if(success){
                    MyJasperViewer report = Util.viewReport(reportName, new HashMap<String, Object>(){{
                        put("REPORT_YEAR", pp.readTahun());
                        put("REPORT_MONTH", pp.readBulan());
                    }});
                    pp.setPopup(report);
                }
                return true;
            }
        });
        setPopup(pp);
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
        jLabel1 = new javax.swing.JLabel();
        peminjamanButton = new javax.swing.JButton();
        peminjamButton = new javax.swing.JButton();
        barangButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setText("Laporan");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel1, gridBagConstraints);

        peminjamanButton.setBackground(new java.awt.Color(255, 153, 0));
        peminjamanButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        peminjamanButton.setText("Peminjaman");
        peminjamanButton.setOpaque(false);
        peminjamanButton.setSelected(true);
        peminjamanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                peminjamanButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(peminjamanButton, gridBagConstraints);

        peminjamButton.setBackground(new java.awt.Color(255, 153, 0));
        peminjamButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        peminjamButton.setText("Peminjam");
        peminjamButton.setOpaque(false);
        peminjamButton.setSelected(true);
        peminjamButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                peminjamButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(peminjamButton, gridBagConstraints);

        barangButton.setBackground(new java.awt.Color(255, 153, 0));
        barangButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        barangButton.setText("Barang");
        barangButton.setOpaque(false);
        barangButton.setSelected(true);
        barangButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barangButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(barangButton, gridBagConstraints);

        getContentPane().add(jPanel1, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void peminjamanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_peminjamanButtonActionPerformed
        // TODO add your handling code here:
        showReport("Peminjaman");
    }//GEN-LAST:event_peminjamanButtonActionPerformed

    private void peminjamButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_peminjamButtonActionPerformed
        // TODO add your handling code here:
        showReport("Peminjam");
    }//GEN-LAST:event_peminjamButtonActionPerformed

    private void barangButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barangButtonActionPerformed
        // TODO add your handling code here:
        showReport("Barang");
    }//GEN-LAST:event_barangButtonActionPerformed

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
            java.util.logging.Logger.getLogger(LaporanFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LaporanFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LaporanFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LaporanFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LaporanFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton barangButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton peminjamButton;
    private javax.swing.JButton peminjamanButton;
    // End of variables declaration//GEN-END:variables
}
