/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion;

import Algoritmos.AlgoritmosBusqueda;
import Algoritmos.Observador;
import grafos.Arista;
import grafos.Grafo;
import grafos.GrafoMorelia;
import grafos.Vertice;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;

/**
 *
 * @author Gyzmbin
 */
public class JFrame_RutaMasCorta extends JFrame_Padre implements Observador {

    private final JMapViewer mapViewer;
    private final Grafo grafo;
    private final JComboBox<Vertice> cbOrigen;
    private final JLabel lblDistancia;
    private final AlgoritmosBusqueda algoritmos;

    public JFrame_RutaMasCorta() {
        super();
        setTitle("Dijkstra Animado - Arbol de Rutas");
        setLayout(new BorderLayout());

        this.grafo = GrafoMorelia.construirGrafo();

        this.algoritmos = new AlgoritmosBusqueda();
        this.algoritmos.subscribirObservador(this);

        mapViewer = new JMapViewer();
        mapViewer.setZoomControlsVisible(true);
        mapViewer.setDisplayPosition(new Coordinate(19.7037, -101.1920), 8);

        JPanel panelTop = new JPanel(new FlowLayout());
        cbOrigen = new JComboBox<>();
        JButton btnCalcular = new JButton("INICIAR ANIMACION DIJKSTRA");

        btnCalcular.setBackground(new Color(0, 102, 102));
        btnCalcular.setForeground(Color.WHITE);

        panelTop.add(new JLabel("Selecciona Origen:"));
        panelTop.add(cbOrigen);
        panelTop.add(btnCalcular);

        for (Vertice v : grafo.getVertices()) {
            cbOrigen.addItem(v);
        }

        JPanel panelBottom = new JPanel(new FlowLayout());
        lblDistancia = new JLabel("El algoritmo mostrara como se encuentran las rutas mas cortas a TODOS los municipios.");
        lblDistancia.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        panelBottom.add(lblDistancia);

        add(panelTop, BorderLayout.NORTH);
        add(mapViewer, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);

        dibujarGrafoBase();

        btnCalcular.addActionListener(e -> ejecutarDijkstraAnimado());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void ejecutarDijkstraAnimado() {
        Vertice origen = (Vertice) cbOrigen.getSelectedItem();
        if (origen == null) {
            return;
        }

        lblDistancia.setText("Ejecutando Dijkstra desde: " + origen.getNombre() + "...");

        new Thread(() -> {
            algoritmos.dijkstra(grafo, origen);
            SwingUtilities.invokeLater(() -> lblDistancia.setText("¡Algoritmo Finalizado! Rutas completas."));
        }).start();
    }

    private void dibujarGrafoBase() {
        mapViewer.removeAllMapMarkers();
        mapViewer.removeAllMapPolygons();

        List<MapMarker> markers = new ArrayList<>();
        List<MapPolygon> lines = new ArrayList<>();
        Set<String> dibujados = new HashSet<>();

        for (Vertice v : grafo.getVertices()) {
            MapMarkerDot marker = new MapMarkerDot(v.getNombre(), v.getCoordenada());
            marker.setBackColor(Color.GRAY);
            markers.add(marker);

            for (Arista a : grafo.getVecinos(v)) {
                Vertice u = a.getDestino();
                String key = v.getNombre() + "-" + u.getNombre();
                String keyRev = u.getNombre() + "-" + v.getNombre();

                if (!dibujados.contains(key) && !dibujados.contains(keyRev)) {
                    MapPolygonImpl linea = new MapPolygonImpl(v.getCoordenada(), u.getCoordenada(), v.getCoordenada());
                    linea.setColor(Color.LIGHT_GRAY);
                    lines.add(linea);
                    dibujados.add(key);
                }
            }
        }
        mapViewer.setMapMarkerList(markers);
        mapViewer.setMapPolygonList(lines);
    }

    @Override
    public void actualizar() {
        SwingUtilities.invokeLater(() -> {
            resaltarEstadoActual();
            mapViewer.repaint();
        });
    }

    private void resaltarEstadoActual() {
        List<MapMarker> markers = new ArrayList<>();
        List<MapPolygon> lines = new ArrayList<>();
        Set<String> dibujados = new HashSet<>();

        for (Vertice v : grafo.getVertices()) {
            for (Arista a : grafo.getVecinos(v)) {
                Vertice u = a.getDestino();
                String key = v.getNombre() + "-" + u.getNombre();
                if (!dibujados.contains(key)) {
                    MapPolygonImpl linea = new MapPolygonImpl(v.getCoordenada(), u.getCoordenada(), v.getCoordenada());
                    linea.setColor(Color.LIGHT_GRAY);
                    lines.add(linea);
                    dibujados.add(key);
                    dibujados.add(u.getNombre() + "-" + v.getNombre());
                }
            }
        }

        for (Vertice v : grafo.getVertices()) {

            if (v.getAntecesor() != null) {
                MapPolygonImpl lineaRuta = new MapPolygonImpl(
                        v.getAntecesor().getCoordenada(),
                        v.getCoordenada(),
                        v.getAntecesor().getCoordenada()
                );
                lineaRuta.setColor(Color.GREEN);
                lineaRuta.setStroke(new BasicStroke(4));
                lines.add(lineaRuta);
            }

            Color colorNodo = Color.GRAY;

            if (v.getColor() == Color.BLACK) {
                colorNodo = Color.BLUE;
            } else if (v.getAntecesor() != null) {
                colorNodo = Color.ORANGE;
            }

            if (v.equals(cbOrigen.getSelectedItem())) {
                colorNodo = Color.RED;
            }

            MapMarkerDot dot = new MapMarkerDot(v.getNombre(), v.getCoordenada());
            dot.setBackColor(colorNodo);
            markers.add(dot);
        }

        mapViewer.setMapMarkerList(markers);
        mapViewer.setMapPolygonList(lines);
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
