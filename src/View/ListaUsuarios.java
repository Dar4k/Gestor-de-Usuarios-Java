package View;

import Controller.UsuarioController;
import Model.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.io.File;
import java.awt.*;

public class ListaUsuarios extends javax.swing.JFrame {

    
    
    private DefaultTableModel modeloTabla;
    private UsuarioController usuarioController;
    private Usuario usuarioActual;

    
    
    public ListaUsuarios() {
        initComponents();
        this.setTitle("Lista de Usuarios");
        setLocationRelativeTo(null);
        usuarioController = new UsuarioController();
        configurarComponentes();
        
    }
    public ListaUsuarios(Usuario usuario) {
        this(); // Llama al constructor sin argumentos para inicializar componentes (initComponents(), etc.)
        this.usuarioActual = usuario; // Guarda el usuario que te abrio la ventana
    }

    
    private void configurarComponentes() {

        String[] columnas = {"ID", "Identificación", "Nombre", "Correo", "Categoría", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblUsuarios.setModel(modeloTabla);
        

        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);

        agregarListeners();
    }
    

    private void agregarListeners() {

        btnListar.addActionListener(e -> cargarUsuarios());

        btnAgregar.addActionListener(e -> abrirRegistroUsuario());

        btnEditar.addActionListener(e -> editarUsuarioSeleccionado());

        btnEliminar.addActionListener(e -> eliminarUsuarioSeleccionado());

        btnExportar.addActionListener(e -> exportarAPDF());

        tblUsuarios.getSelectionModel().addListSelectionListener(e -> {
            boolean haySeleccion = tblUsuarios.getSelectedRow() != -1;
            btnEditar.setEnabled(haySeleccion);
            btnEliminar.setEnabled(haySeleccion);
        });
    }

    private void cargarUsuarios() {
        try {
            modeloTabla.setRowCount(0);
            List<Usuario> usuarios = usuarioController.obtenerTodosUsuarios();

            for (Usuario usuario : usuarios) {
                Object[] fila = {
                    usuario.getId(),
                    usuario.getIdentificacion(),
                    usuario.getNombre(),
                    usuario.getCorreo(),
                    usuario.getcategoria(),
                    usuario.getEstadoTexto()
                };
                modeloTabla.addRow(fila);
            }

            JOptionPane.showMessageDialog(this, "Usuarios cargados: " + usuarios.size());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void abrirRegistroUsuario() {
        JOptionPane.showMessageDialog(this, "Aquí se abrirá el formulario de registro");
        // Por ahora temporal - luego: new Registro().setVisible(true);
    }

    private void editarUsuarioSeleccionado() {
        int filaSeleccionada = tblUsuarios.getSelectedRow();
        if (filaSeleccionada != -1) {
            try {
                int identificacion = (int) modeloTabla.getValueAt(filaSeleccionada, 1);
                Usuario usuarioAEditar = usuarioController.buscarUsuarioPorId(identificacion);

                if (usuarioAEditar != null) {
                    EditarUsuarioDesdeTabla ventanaEdicion = new EditarUsuarioDesdeTabla(usuarioAEditar, this::cargarUsuarios);
                    ventanaEdicion.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el usuario seleccionado");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void eliminarUsuarioSeleccionado() {
        int filaSeleccionada = tblUsuarios.getSelectedRow();
        if (filaSeleccionada != -1) {
            int identificacion = (int) modeloTabla.getValueAt(filaSeleccionada, 1);

            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de eliminar este usuario?", "Confirmar",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean eliminado = usuarioController.eliminarUsuario(identificacion);
                if (eliminado) {
                    JOptionPane.showMessageDialog(this, "Usuario eliminado");
                    cargarUsuarios();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar usuario");
                }
            }
        }
    }

    private void exportarAPDF() {
        try {

            // Diálogo para elegir donde guardar
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar lista de usuarios");
            fileChooser.setSelectedFile(new File("lista_usuarios.txt"));

            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();
                String ruta = archivo.getAbsolutePath();

                // Crear el contenido del archivo
                StringBuilder contenido = new StringBuilder();

                // Título
                contenido.append("=================================\n");
                contenido.append("      LISTADO DE USUARIOS\n");
                contenido.append("=================================\n\n");

                // Encabezados de la tabla
                contenido.append(String.format("%-5s %-15s %-20s %-25s %-15s %-10s\n",
                        "ID", "Identificación", "Nombre", "Correo", "Categoría", "Estado"));
                contenido.append(String.format("%-5s %-15s %-20s %-25s %-15s %-10s\n",
                        "----", "-------------", "--------------------", "-------------------------", "---------------", "----------"));

                // Datos de los usuarios
                int rowCount = modeloTabla.getRowCount();

                for (int fila = 0; fila < rowCount; fila++) {
                    String id = modeloTabla.getValueAt(fila, 0).toString();
                    String identificacion = modeloTabla.getValueAt(fila, 1).toString();
                    String nombre = modeloTabla.getValueAt(fila, 2).toString();
                    String correo = modeloTabla.getValueAt(fila, 3).toString();
                    String categoria = modeloTabla.getValueAt(fila, 4).toString();
                    String estado = modeloTabla.getValueAt(fila, 5).toString();

                    contenido.append(String.format("%-5s %-15s %-20s %-25s %-15s %-15s\n",
                            id, identificacion, nombre, correo, categoria, estado));
                }

                // Pie de página
                contenido.append("\n=================================\n");
                contenido.append("TOTAL DE USUARIOS: ").append(rowCount).append("\n");
                contenido.append("FECHA DE GENERACIÓN: ").append(new java.util.Date()).append("\n");
                contenido.append("=================================\n");

                // Escribir el archivo
                java.io.FileWriter writer = new java.io.FileWriter(archivo);
                writer.write(contenido.toString());
                writer.close();

                JOptionPane.showMessageDialog(this,
                        "✅ Archivo exportado exitosamente!\n\n"
                        + "📁 Ubicación: " + ruta + "\n"
                        + "👥 Usuarios exportados: " + rowCount,
                        "Exportación Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(this, "Exportación cancelada");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "❌ Error al exportar: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        btnListar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        Regresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblUsuarios);

        btnListar.setBackground(new java.awt.Color(0, 102, 255));
        btnListar.setText("Listar");
        btnListar.setToolTipText("");
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        btnAgregar.setBackground(new java.awt.Color(51, 102, 0));
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(153, 102, 0));
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(153, 0, 0));
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnExportar.setBackground(new java.awt.Color(0, 102, 102));
        btnExportar.setText("Exportar");

        Regresar.setText("Menu");
        Regresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnListar)
                        .addGap(63, 63, 63)
                        .addComponent(btnAgregar)
                        .addGap(70, 70, 70)
                        .addComponent(btnEditar)
                        .addGap(291, 291, 291)
                        .addComponent(btnEliminar))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 724, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExportar)
                    .addComponent(Regresar))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(btnExportar)
                        .addGap(87, 87, 87)
                        .addComponent(Regresar))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnListar)
                    .addComponent(btnAgregar)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnListarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        RegistroLista registrarLista = new RegistroLista();
        registrarLista.setVisible(true);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarActionPerformed

    private void RegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegresarActionPerformed
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
    }
        // TODO add your handling code here:
    }//GEN-LAST:event_RegresarActionPerformed

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
            java.util.logging.Logger.getLogger(ListaUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListaUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListaUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListaUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListaUsuarios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Regresar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnListar;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblUsuarios;
    // End of variables declaration//GEN-END:variables
}
