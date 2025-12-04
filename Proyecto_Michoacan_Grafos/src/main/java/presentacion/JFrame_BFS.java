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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
 * @author abrilislas
 */
public class JFrame_BFS extends JFrame_Padre implements Observador {

    private final JMapViewer mapViewer;
    private final Grafo grafo;
    private final JComboBox<Vertice> cbOrigen;
    private final JButton btnContinuar;
    private final BotonRegresar btnRegresar;
    private final AlgoritmosBusqueda aB;

    public JFrame_BFS() {
        super();
        setTitle("Seleccionar Origen - Grafo Michoacán");
        setLayout(new BorderLayout());
        aB = new AlgoritmosBusqueda();
        aB.subscribirObservador(this);
      
        this.grafo = GrafoMorelia.construirGrafo();
        mapViewer = new JMapViewer();
        mapViewer.setZoomControlsVisible(true);
        
        mapViewer.setDisplayPosition(new Coordinate(19.7037, -101.1920), 8);

        
        JPanel panelTop = new JPanel(new FlowLayout());
        cbOrigen = new JComboBox<>();
        btnContinuar = new JButton("CONFIRMAR ORIGEN");
        btnRegresar = new BotonRegresar("Exit", this);

        
        btnContinuar.setBackground(new Color(0, 102, 204)); 
        btnContinuar.setForeground(Color.WHITE);

        panelTop.add(new JLabel("Selecciona el Municipio de Origen:"));
        panelTop.add(cbOrigen);
        panelTop.add(btnContinuar);
        panelTop.add(btnRegresar);

        
        for (Vertice v : grafo.getVertices()) {
            cbOrigen.addItem(v);
        }

        
        add(panelTop, BorderLayout.NORTH);
        add(mapViewer, BorderLayout.CENTER);

        
        dibujarGrafoEstadoActual();
        
        cbOrigen.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                resaltarVerticeSeleccionado((Vertice) cbOrigen.getSelectedItem());
            }
        });
        
        btnContinuar.addActionListener(e -> confirmarSeleccion());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        if (cbOrigen.getItemCount() > 0) {
            resaltarVerticeSeleccionado((Vertice) cbOrigen.getItemAt(0));
        }
    }

    private void confirmarSeleccion() {
        Vertice origen = (Vertice) cbOrigen.getSelectedItem();

        if (origen != null) {
            JOptionPane.showMessageDialog(this, "Has seleccionado: " + origen.getNombre()
                    + "\nListo para iniciar algoritmo.");
            new Thread(() -> {
                aB.BFS(grafo, origen);
            }).start();

        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un origen válido.");
        }
    }

    private void dibujarGrafoEstadoActual() {
        mapViewer.removeAllMapMarkers();
        mapViewer.removeAllMapPolygons();

        List<MapMarker> markers = new ArrayList<>();
        List<MapPolygon> lines = new ArrayList<>();

        for (Vertice v : grafo.getVertices()) {

            Color colorDinamico = v.getColor();

            if (colorDinamico == null) {
                colorDinamico = Color.RED; // Color por defecto si es null
            }

            // Usamos ese color para el marcador
            MapMarkerDot marcador = new MapMarkerDot(colorDinamico, v.getCoordenada().getLat(), v.getCoordenada().getLon());
            marcador.setBackColor(colorDinamico);

            markers.add(marcador);

            // Aristas (Las dejamos rojas o negras, según prefieras)
            for (Arista a : grafo.getVecinos(v)) {
                Vertice u = a.getDestino();
                MapPolygonImpl linea = new MapPolygonImpl(v.getCoordenada(), u.getCoordenada(), v.getCoordenada());
                linea.setColor(Color.BLACK); // O el color que gustes para las líneas
                lines.add(linea);
            }
        }

        mapViewer.setMapMarkerList(markers);
        mapViewer.setMapPolygonList(lines);
    }

    /**
     * Redibuja el grafo pero pinta el vértice seleccionado de AZUL (CYAN) para
     * que el usuario sepa dónde está ubicado.
     */
    private void resaltarVerticeSeleccionado(Vertice seleccionado) {
        if (seleccionado == null) {
            return;
        }

        mapViewer.removeAllMapMarkers();
        // No borramos polígonos (líneas) para no procesar tanto, solo cambiamos los puntos.

        List<MapMarker> markers = new ArrayList<>();

        for (Vertice v : grafo.getVertices()) {
            if (v.equals(seleccionado)) {
                // El seleccionado es AZUL y más grande (si la librería lo permite, si no, solo color)
                MapMarkerDot dot = new MapMarkerDot(seleccionado.getColor(), v.getCoordenada().getLat(), v.getCoordenada().getLon());
                dot.setBackColor(Color.RED);
                markers.add(dot);

                // Opcional: Centrar mapa en el seleccionado
                mapViewer.setDisplayPosition(v.getCoordenada(), mapViewer.getZoom());
            } else {
                // Los demás siguen rojos
                markers.add(new MapMarkerDot(Color.RED, v.getCoordenada().getLat(), v.getCoordenada().getLon()));
            }
        }
        mapViewer.setMapMarkerList(markers);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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

    @Override
    public void actualizar() {
        SwingUtilities.invokeLater(() -> {
            dibujarGrafoEstadoActual();
            mapViewer.repaint();
        });

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
