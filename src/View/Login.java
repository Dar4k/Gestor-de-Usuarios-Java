package View;

import Controller.UsuarioController;
import Model.Usuario;
import Service.UsuarioService;
import javax.swing.JOptionPane;

public class Login extends javax.swing.JFrame {
    UsuarioController usuarioController;
    UsuarioService usuarioService;

    public Login() {
        initComponents();
        setLocationRelativeTo(null);
        this.usuarioService = new UsuarioService();
        this.usuarioController = new UsuarioController(this.usuarioService);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        correoText = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        contraseñaText = new javax.swing.JPasswordField();
        registroBoton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Correo");

        jLabel2.setText("Contraseña");

        correoText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                correoTextActionPerformed(evt);
            }
        });

        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        registroBoton.setText("Registrarse");
        registroBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registroBotonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(registroBoton)
                        .addGap(35, 35, 35)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(correoText, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                            .addComponent(contraseñaText))))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(correoText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(contraseñaText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(registroBoton))
                .addContainerGap(122, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String correo = correoText.getText();
        char[] Contraseña = contraseñaText.getPassword();
        String clave = new String(Contraseña);
        
        if (!correo.isEmpty() && Contraseña != null) {
            try{
                Usuario resultado = usuarioController.loginUsuario(correo, clave);
                if (resultado != null) {
                    if(resultado.getcategoria().equalsIgnoreCase("Gerente") && resultado.isEstado() == true){
                        PerfilGerente perfil = new PerfilGerente(resultado);
                        perfil.setVisible(true);
                    } else if (resultado.getcategoria().equalsIgnoreCase("Empleado") && resultado.isEstado() == true){
                        PerfilEmpleado perfil = new PerfilEmpleado(resultado);
                        perfil.setVisible(true);
                    }else {
                        JOptionPane.showMessageDialog(this, "Categoria desconocida o Estado inactivo:");
                    }
                    this.dispose();
                } else{
                    JOptionPane.showMessageDialog(this, "Correo o contraseña incorrectos");
                }
            } catch(Exception e){
                JOptionPane.showMessageDialog(this, "Error al iniciar sesion");
                System.out.println(e);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Los campos son obligatorios");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void correoTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_correoTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_correoTextActionPerformed

    private void registroBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registroBotonActionPerformed
        Registro registro = new Registro();
        registro.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_registroBotonActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField contraseñaText;
    private javax.swing.JTextField correoText;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton registroBoton;
    // End of variables declaration//GEN-END:variables
}
