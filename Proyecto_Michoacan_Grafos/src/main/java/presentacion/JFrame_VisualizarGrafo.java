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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JFrame;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;

/**
 *
 * @author PC Gamer
 */
public class JFrame_VisualizarGrafo extends JFrame_Padre {

    private final JMapViewer mapViewer;
    private final Grafo grafo;

    public JFrame_VisualizarGrafo() {
        super();
        setTitle("Visualización del Grafo de Michoacán");

        setLayout(new BorderLayout());

        this.grafo = GrafoMorelia.construirGrafo();

        mapViewer = new JMapViewer();
        mapViewer.setZoomControlsVisible(true);
        mapViewer.setScrollWrapEnabled(true);

        org.openstreetmap.gui.jmapviewer.Coordinate centro
                = new org.openstreetmap.gui.jmapviewer.Coordinate(19.7037, -101.1920);
        mapViewer.setDisplayPosition(centro, 8);

        dibujarGrafo();

        add(mapViewer, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void dibujarGrafo() {
        List<MapMarker> markers = new ArrayList<>();
        List<MapPolygon> lines = new ArrayList<>();

        // Para evitar dibujar aristas duplicadas (A->B y B->A)
        Set<String> aristasDibujadas = new HashSet<>();

        // 1. Dibujar Vértices (Nodos)
        for (Vertice v : grafo.getVertices()) {
            MapMarkerDot marker = new MapMarkerDot(
                    v.getNombre(), // Texto que aparece al pasar el mouse
                    v.getCoordenada()
            );
            marker.setBackColor(Color.BLUE); // Color del marcador
            marker.setColor(Color.WHITE);    // Color del borde
            markers.add(marker);

            // 2. Dibujar Aristas (Líneas)
            for (Arista a : grafo.getVecinos(v)) {
                Vertice destino = a.getDestino();

                // Clave para evitar duplicados
                String idArista1 = v.getNombre() + "-" + destino.getNombre();
                String idArista2 = destino.getNombre() + "-" + v.getNombre();

                if (!aristasDibujadas.contains(idArista1) && !aristasDibujadas.contains(idArista2)) {

                    MapPolygonImpl line = new MapPolygonImpl(
                            v.getCoordenada(),
                            destino.getCoordenada(),
                            v.getCoordenada() // Se repite el primero para cerrar (aunque JMapViewer lo maneja)
                    );

                    // Estilo de la línea
                    line.setColor(Color.RED);
                    line.setStroke(new java.awt.BasicStroke(2)); // Grosor de 2px

                    lines.add(line);
                    aristasDibujadas.add(idArista1); // Marcar como dibujada
                }
            }
        }

        // Añadir todo al mapa
        mapViewer.setMapMarkerList(markers);
        mapViewer.setMapPolygonList(lines);
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

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
