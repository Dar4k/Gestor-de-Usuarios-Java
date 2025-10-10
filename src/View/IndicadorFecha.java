package View;

import Controller.UsuarioController;
import Model.Usuario;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;


public class IndicadorFecha extends javax.swing.JFrame {
    
    private UsuarioController usuarioController;
    private Usuario usuarioActual;

    public IndicadorFecha(Usuario usuarioActual) {
        initComponents();
        usuarioController = new UsuarioController();
        this.usuarioActual = usuarioActual;
        setLayout(new BorderLayout());
        mostrarGraficoIniciosPorFecha();
        agregarBotones();
    }
    
    private void mostrarGraficoIniciosPorFecha() {
    try {
        Map<String, Integer> datos = usuarioController.obtenerIniciosPorFecha();
        org.jfree.data.category.DefaultCategoryDataset dataset = new org.jfree.data.category.DefaultCategoryDataset();

        for (Map.Entry<String, Integer> entry : datos.entrySet()) {
            dataset.addValue(entry.getValue(), "Usuarios", entry.getKey());
        }

        JFreeChart chart = org.jfree.chart.ChartFactory.createBarChart(
                "Inicios de Sesión por Fecha",
                "Fecha",
                "Cantidad de Usuarios",
                dataset,
                org.jfree.chart.plot.PlotOrientation.VERTICAL,
                true, true, false
        );

        chart.setBackgroundPaint(new java.awt.Color(245, 245, 245)); 
        chart.getTitle().setPaint(new java.awt.Color(60, 60, 60)); 
        chart.getTitle().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));

        var plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new java.awt.Color(255, 255, 255));
        plot.setDomainGridlinePaint(new java.awt.Color(200, 200, 200));
        plot.setRangeGridlinePaint(new java.awt.Color(200, 200, 200));

        var renderer = (org.jfree.chart.renderer.category.BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new java.awt.Color(66, 135, 245)); 
        renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter()); 

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(700, 450));
        panel.setMouseWheelEnabled(true);

        getContentPane().removeAll();
        add(panel, BorderLayout.CENTER);
        pack();
        repaint();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar gráfico de actividad: " + e.getMessage());
    }
}
    
    private void agregarBotones() {
    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

    JButton btnOtroGrafico = new JButton("Atras");
    JButton btnSalir = new JButton("Salir");

    btnOtroGrafico.addActionListener(e -> {
        dispose();

        View.Indicador indicador = new View.Indicador(this.usuarioActual);
        indicador.setVisible(true);
        indicador.setLocationRelativeTo(null); 
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
