/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion;

import grafos.Arista;
import grafos.Grafo;
import grafos.GrafoMorelia;
import grafos.Vertice;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Ramon Valencia
 */
public class JFrame_TablaDatos extends JFrame_Padre {
    
    private final Grafo grafo;
    private JTabbedPane tabbedPane;
    
    public JFrame_TablaDatos() {
        super();
        setTitle("Base de Datos del Grafo - Michoacán");
        setLayout(new BorderLayout());
        setSize(800, 600); // Tamaño inicial más grande

        this.grafo = GrafoMorelia.construirGrafo();

        
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(33, 33, 33));
        panelHeader.setBorder(new EmptyBorder(15, 15, 15, 15));

        int totalV = grafo.getVertices().size();
        int totalA = contarAristasTotales();

        JLabel lblTitulo = new JLabel("Resumen del Grafo");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel lblStats = new JLabel("Municipios: " + totalV + " | Conexiones: " + totalA);
        lblStats.setForeground(new Color(200, 200, 200));
        lblStats.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        panelHeader.add(lblTitulo, BorderLayout.WEST);
        panelHeader.add(lblStats, BorderLayout.EAST);

        add(panelHeader, BorderLayout.NORTH);

        
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        
        tabbedPane.addTab("Municipios (Nodos)", crearPanelVertices());

        
        tabbedPane.addTab("Carreteras (Aristas)", crearPanelAristas());

        add(tabbedPane, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel crearPanelVertices() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columnas = {"Nombre", "Latitud", "Longitud", "Grado (Conexiones)"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (Vertice v : grafo.getVertices()) {
            int grado = grafo.getVecinos(v).size();
            Object[] fila = {
                v.getNombre(),
                v.getCoordenada().getLat(),
                v.getCoordenada().getLon(),
                grado
            };
            modelo.addRow(fila);
        }

        JTable tabla = estilizarTabla(new JTable(modelo));
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelAristas() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnas = {"Origen", "Destino", "Distancia (Peso)"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (Vertice v : grafo.getVertices()) {
            for (Arista a : grafo.getVecinos(v)) {
                Object[] fila = {
                    v.getNombre(),
                    a.getDestino().getNombre(),
                    String.format("%.2f Km", a.getPeso()) // Formato bonito
                };
                modelo.addRow(fila);
            }
        }

        JTable tabla = estilizarTabla(new JTable(modelo));
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    private int contarAristasTotales() {
        int count = 0;
        for (Vertice v : grafo.getVertices()) {
            count += grafo.getVecinos(v).size();
        }
        return count;
    }
    
    private JTable estilizarTabla(JTable tabla) {
        tabla.setRowHeight(25);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.setFillsViewportHeight(true);
        tabla.setShowVerticalLines(false);
        tabla.setGridColor(new Color(230, 230, 230));

        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(0, 102, 102)); 
        header.setForeground(Color.WHITE);
        header.setOpaque(true);

        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                } else {
                    c.setBackground(new Color(173, 216, 230)); // Color de selección suave
                }
                ((JLabel) c).setBorder(new EmptyBorder(0, 5, 0, 0)); // Padding izquierdo
                return c;
            }
        });
        
        return tabla;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
