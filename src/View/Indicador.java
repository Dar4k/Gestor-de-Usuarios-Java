package View;

import Controller.UsuarioController;
import Model.Usuario;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;

public class Indicador extends javax.swing.JFrame {
    
    private UsuarioController usuarioController;
    private Usuario usuarioActual;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Indicador.class.getName());

    public Indicador(Usuario usuarioActual) {
        initComponents();
        usuarioController = new UsuarioController();
        this.usuarioActual = usuarioActual;
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        mostrarGraficoUsuariosPorRol();
        agregarBotones();
    }
    
    private void mostrarGraficoUsuariosPorRol() {
        try {
            Map<String, Integer> datos = usuarioController.obtenerIndicadoresActividad(0);

            DefaultPieDataset dataset = new DefaultPieDataset();
            for (Map.Entry<String, Integer> entry : datos.entrySet()) {
                dataset.setValue(entry.getKey(), entry.getValue());
            }

            JFreeChart chart = ChartFactory.createPieChart(
                    "Usuarios por Rol",
                    dataset,
                    true,
                    true,
                    false
            );

            ChartPanel panel = new ChartPanel(chart);
            panel.setPreferredSize(new Dimension(400, 300));

            getContentPane().removeAll();
            getContentPane().add(panel);
            pack();
            repaint();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al generar indicadores", e);
            JOptionPane.showMessageDialog(this, "Error al cargar los indicadores");
        }
    }
    
    private void agregarBotones() {
    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

    JButton btnOtroGrafico = new JButton("Siguiente");
    JButton btnSalir = new JButton("Salir");

    btnOtroGrafico.addActionListener(e -> {
        dispose();

        View.IndicadorFecha indicadorFecha = new View.IndicadorFecha(this.usuarioActual);
        indicadorFecha.setVisible(true);
        indicadorFecha.setLocationRelativeTo(null); 
    });

    btnSalir.addActionListener(e -> {
        dispose(); 
        
        if (usuarioActual.getCategoria().equalsIgnoreCase("Gerente")) {
            View.PerfilGerente gerente = new View.PerfilGerente(this.usuarioActual);
            gerente.setVisible(true);
            gerente.setLocationRelativeTo(null);
        } else if (usuarioActual.getCategoria().equalsIgnoreCase("Empleado")) {
            View.PerfilEmpleado empleado = new View.PerfilEmpleado(this.usuarioActual);
            empleado.setVisible(true);
            empleado.setLocationRelativeTo(null);
        } else {
            JOptionPane.showMessageDialog(null, "No se reconoce la categoría del usuario.");
        }
        
        
    });

    panelBotones.add(btnOtroGrafico);
    panelBotones.add(btnSalir);

    getContentPane().add(panelBotones, BorderLayout.SOUTH);
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
