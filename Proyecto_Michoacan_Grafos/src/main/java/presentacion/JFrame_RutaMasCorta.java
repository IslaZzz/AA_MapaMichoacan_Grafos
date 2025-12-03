/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion;

import Algoritmos.AlgoritmosBusqueda;
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
import java.util.Map;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
public class JFrame_RutaMasCorta extends JFrame_Padre {

    private final JMapViewer mapViewer;
    private final Grafo grafo;
    private final JComboBox<Vertice> cbOrigen;
    private final JLabel lblDistancia;

    public JFrame_RutaMasCorta() {
        super();
        setTitle("Calculadora de Rutas (Dijkstra) - Grafo Michoacan");
        setLayout(new BorderLayout());

        this.grafo = GrafoMorelia.construirGrafo();
        mapViewer = new JMapViewer();
        mapViewer.setZoomControlsVisible(true);
        mapViewer.setDisplayPosition(new Coordinate(19.7037, -101.1920), 8);

        JPanel panelTop = new JPanel(new FlowLayout());
        cbOrigen = new JComboBox<>();
        JButton btnCalcular = new JButton("CALCULAR RUTA");

        btnCalcular.setBackground(new Color(0, 102, 102));
        btnCalcular.setForeground(Color.WHITE);

        panelTop.add(new JLabel("Origen:"));
        panelTop.add(cbOrigen);
        panelTop.add(btnCalcular);

        for (Vertice v : grafo.getVertices()) {
            cbOrigen.addItem(v);
        }

        JPanel panelBottom = new JPanel(new FlowLayout());
        lblDistancia = new JLabel("Selecciona puntos y calcula la ruta.");
        lblDistancia.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        panelBottom.add(lblDistancia);

        add(panelTop, BorderLayout.NORTH);
        add(mapViewer, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);

        dibujarGrafoCompleto();

        btnCalcular.addActionListener(e -> ejecutarDijkstra());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void ejecutarDijkstra() {
        Vertice origen = (Vertice) cbOrigen.getSelectedItem();

        if (origen == null) {
            return;
        }

        AlgoritmosBusqueda algoritmos = new AlgoritmosBusqueda();

        Map<String, Object> resultados = algoritmos.dijkstra(grafo, origen);

        Map<Vertice, Vertice> predecesores = (Map<Vertice, Vertice>) resultados.get("predecesores");

        lblDistancia.setText("Mostrando rutas mas cortas desde: " + origen.getNombre());

        resaltarArbolDeRutas(predecesores, origen);
    }

    private void dibujarGrafoCompleto() {
        mapViewer.removeAllMapMarkers();
        mapViewer.removeAllMapPolygons();

        List<MapMarker> markers = new ArrayList<>();
        List<MapPolygon> lines = new ArrayList<>();
        Set<String> dibujados = new HashSet<>();

        for (Vertice v : grafo.getVertices()) {
            markers.add(new MapMarkerDot(v.getNombre(), v.getCoordenada()));

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

    private void resaltarArbolDeRutas(Map<Vertice, Vertice> predecesores, Vertice origenSeleccionado) {
        dibujarGrafoCompleto();

        List<MapPolygon> poligonos = new ArrayList<>(mapViewer.getMapPolygonList());
        List<MapMarker> marcadores = new ArrayList<>(mapViewer.getMapMarkerList());

        for (Map.Entry<Vertice, Vertice> entry : predecesores.entrySet()) {
            Vertice destino = entry.getKey();
            Vertice padre = entry.getValue();

            if (padre != null) {
                MapPolygonImpl lineaRuta = new MapPolygonImpl(
                        padre.getCoordenada(),
                        destino.getCoordenada(),
                        padre.getCoordenada()
                );

                lineaRuta.setColor(Color.GREEN);
                lineaRuta.setStroke(new BasicStroke(4));
                poligonos.add(lineaRuta);
            }
        }

        mapViewer.removeAllMapMarkers();
        for (Vertice v : grafo.getVertices()) {
            MapMarkerDot dot;
            if (v.equals(origenSeleccionado)) {
                dot = new MapMarkerDot(Color.BLUE, v.getCoordenada().getLat(), v.getCoordenada().getLon());
                dot.setBackColor(Color.BLUE);
            } else {
                dot = new MapMarkerDot(Color.RED, v.getCoordenada().getLat(), v.getCoordenada().getLon());
            }
            marcadores.add(dot);
        }

        mapViewer.setMapMarkerList(marcadores);
        mapViewer.setMapPolygonList(poligonos);
        mapViewer.repaint();
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
