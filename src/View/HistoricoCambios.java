package View;

import Service.CambioUsuarioService;
import Model.CambioUsuario;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.util.logging.Logger;
import Model.Usuario;

public class HistoricoCambios extends javax.swing.JFrame {

    private DefaultTableModel modeloHistorial;
    private CambioUsuarioService cambioService;
    private static final Logger logger = Logger.getLogger(HistoricoCambios.class.getName());
    private Usuario usuarioActual;

    public HistoricoCambios() {
        initComponents();
        this.setTitle("Historial de Cambios de Usuarios");
        setLocationRelativeTo(null);
        
        
        cambioService = new CambioUsuarioService();
        
        configurarTabla();
        cargarHistorial();
    }
    
    public HistoricoCambios(Usuario usuario) {
        this(); 
        this.usuarioActual = usuario; 
    }

    private void configurarTabla() {
        String[] columnas = {"ID", "Fecha y Hora", "Acción", "Usuario Sistema", "Detalle"};
        modeloHistorial = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblHistorial.setModel(modeloHistorial);
        
        // CONFIGURAR ANCHO DE COLUMNAS
        configurarAnchoColumnas();
    }

    private void configurarAnchoColumnas() {
        // Ajustar el ancho de las columnas
        javax.swing.table.TableColumnModel columnModel = tblHistorial.getColumnModel();
        
        // ID - Más pequeño
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(0).setMaxWidth(80);
        
        // Fecha y Hora
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(1).setMaxWidth(200);
        
        // Acción
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(2).setMaxWidth(120);
        
        // Usuario Sistema
        columnModel.getColumn(3).setPreferredWidth(120);
        columnModel.getColumn(3).setMaxWidth(150);
        
        // Detalle - Más ancho para mostrar la información completa
        columnModel.getColumn(4).setPreferredWidth(400);
        columnModel.getColumn(4).setMaxWidth(600);
        
        // Hacer que la tabla use todo el espacio disponible
        tblHistorial.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
    }

    public void cargarHistorial() {
        try {
            modeloHistorial.setRowCount(0);
            
            // Obtener la lista de cambios desde el servicio
            List<CambioUsuario> cambios = cambioService.listarCambios();

            for (CambioUsuario cambio : cambios) {
                Object[] fila = {
                    cambio.getId(),
                    cambio.getFechaHora(),
                    cambio.getAccion(),
                    cambio.getUsuarioSistema(),
                    cambio.getDetalle()
                };
                modeloHistorial.addRow(fila);
            }
            
            JOptionPane.showMessageDialog(this, 
                "Cambios cargados: " + cambios.size(), 
                "Historial", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar historial: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tblHistorial = new javax.swing.JTable();
        btnRefrescar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblHistorial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblHistorial);

        btnRefrescar.setForeground(new java.awt.Color(0, 102, 255));
        btnRefrescar.setText("Refrescar");
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });

        jButton1.setText("Volver");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(btnRefrescar, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRefrescar)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
     cargarHistorial();       
    }//GEN-LAST:event_btnRefrescarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
if (usuarioActual != null) {
        if (usuarioActual.getcategoria().equalsIgnoreCase("Gerente")) {
            PerfilGerente perfil = new PerfilGerente(usuarioActual);
            perfil.setVisible(true);
        } else if (usuarioActual.getcategoria().equalsIgnoreCase("Empleado")) {
            PerfilEmpleado perfil = new PerfilEmpleado(usuarioActual);
            perfil.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Categoría desconocida o estado inactivo: " + usuarioActual.getcategoria());
        }
        this.dispose(); // Cierra la ventana actual
    } else {
        JOptionPane.showMessageDialog(this, "No hay usuario en sesión. Regresando al login...");
        Login login = new Login();
        login.setVisible(true);
        this.dispose();
    }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new HistoricoCambios().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblHistorial;
    // End of variables declaration//GEN-END:variables
}
