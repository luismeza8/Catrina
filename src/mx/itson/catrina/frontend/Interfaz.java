/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package mx.itson.catrina.frontend;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import mx.itson.catrina.backend.entidades.Estado;
import mx.itson.catrina.backend.entidades.Movimiento;
import mx.itson.catrina.backend.enumeradores.Tipo;

/**
 *
 * @author lm
 */
public class Interfaz extends javax.swing.JFrame {

    /**
     * Creates new form Interfaz
     */
    public Interfaz() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        lblNombre = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblContable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblInfo = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPrincipal = new javax.swing.JTable();
        jcbMeses = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(900, 1000));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 20, -1, -1));
        jPanel1.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 130, 210, 30));

        jLabel1.setFont(new java.awt.Font("Cantarell", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Estado de Cuenta");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 850, 40));

        tblContable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Info"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblContable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 140, 300, 120));

        tblInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Info"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblInfo);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 300, 120));

        tblPrincipal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Descripcion", "Deposito", "Retiro", "Subtotal"
            }
        ));
        jScrollPane3.setViewportView(tblPrincipal);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 570, 900, -1));

        jcbMeses.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        jPanel1.add(jcbMeses, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();

                byte[] archivoByte = Files.readAllBytes(archivo.toPath());

                String contenido = new String(archivoByte, StandardCharsets.UTF_8);

                Estado estado = new Estado().deserializar(contenido);

                DefaultTableModel modelInfo = (DefaultTableModel) tblInfo.getModel();
                DefaultTableModel modelContable = (DefaultTableModel) tblContable.getModel();
                
                DefaultTableModel modelPrincipal = (DefaultTableModel) tblPrincipal.getModel();
                
                for (Movimiento m : estado.getMovimientos()) {
                    if(m.getTipo() == Tipo.DEPOSITO) {
                        modelPrincipal.addRow(new Object[] {
                            m.getFecha(),
                            m.getDescripcion(),
                            m.getCantidad(),
                            " ",
                            " "
                        });
                    } else if (m.getTipo() == Tipo.RETIRO) {
                        modelPrincipal.addRow(new Object[] {
                            m.getFecha(),
                            m.getDescripcion(),
                            " ",
                            m.getCantidad(), 
                            " "
                        });
                    }
                }

                for (Object o : estado.getCliente().obtenerLista()) {
                    modelInfo.addRow(new Object[]{o});
                }

                for (Object o : estado.obtenerLista()) {
                    modelContable.addRow(new Object[]{o});
                }
                
                System.out.println(estado.obtenerSaldoInicial(estado.getMovimientos(), 2));
                
            }

        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

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
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JComboBox<String> jcbMeses;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JTable tblContable;
    private javax.swing.JTable tblInfo;
    private javax.swing.JTable tblPrincipal;
    // End of variables declaration//GEN-END:variables
}
