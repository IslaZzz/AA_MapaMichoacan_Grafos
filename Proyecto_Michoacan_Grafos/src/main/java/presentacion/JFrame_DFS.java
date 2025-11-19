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
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;

/**
 *
 * @author Ramon Valencia
 */
public class JFrame_DFS extends JFrame_Padre implements Observador{

    private final JMapViewer mapViewer;
    private final Grafo grafo;
    private final JComboBox<Vertice> cbOrigen;
    private final JButton btnContinuar;
    private final AlgoritmosBusqueda aB;

    public JFrame_DFS() {
        super();
        setTitle("Seleccionar Origen - Grafo Michoacán");
        setLayout(new BorderLayout());
        aB = new AlgoritmosBusqueda();
        aB.subscribirObservador(this);
        // 1. Inicializar Grafo y Mapa
        this.grafo = GrafoMorelia.construirGrafo();
        mapViewer = new JMapViewer();
        mapViewer.setZoomControlsVisible(true);
        // Centrar mapa en Morelia aprox
        mapViewer.setDisplayPosition(new Coordinate(19.7037, -101.1920), 8);

        // 2. Panel Superior (Controles)
        JPanel panelTop = new JPanel(new FlowLayout());
        cbOrigen = new JComboBox<>();
        btnContinuar = new JButton("CONFIRMAR ORIGEN");
        
        // Estilo del botón
        btnContinuar.setBackground(new Color(0, 102, 204)); // Un azul para diferenciar
        btnContinuar.setForeground(Color.WHITE);

        panelTop.add(new JLabel("Selecciona el Municipio de Origen:"));
        panelTop.add(cbOrigen);
        panelTop.add(btnContinuar);

        // 3. Llenar el ComboBox con los vértices
        for (Vertice v : grafo.getVertices()) {
            cbOrigen.addItem(v);
        }

        // 4. Añadir componentes a la ventana
        add(panelTop, BorderLayout.NORTH);
        add(mapViewer, BorderLayout.CENTER);

        // Dibujar el grafo base inicial
        dibujarGrafoBase();

        // 5. Eventos
        
        // Evento: Al cambiar la selección del combo, resaltar en el mapa
        cbOrigen.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                resaltarVerticeSeleccionado((Vertice) cbOrigen.getSelectedItem());
            }
        });

        // Evento: Botón Continuar
        btnContinuar.addActionListener(e -> confirmarSeleccion());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Resaltar el primero por defecto al abrir
        if (cbOrigen.getItemCount() > 0) {
            resaltarVerticeSeleccionado((Vertice) cbOrigen.getItemAt(0));
        }
    }

    /**
     * Acción al presionar el botón.
     * Aquí es donde conectarías con la siguiente ventana (ej. BFS/DFS).
     */
    private void confirmarSeleccion() {
        Vertice origen = (Vertice) cbOrigen.getSelectedItem();
        
        if (origen != null) {
            // AQUÍ VA TU LÓGICA SIGUIENTE. 
            // Por ejemplo: abrir la ventana de recorrido BFS pasándole este vértice.
            
            JOptionPane.showMessageDialog(this, "Has seleccionado: " + origen.getNombre() + 
                    "\nListo para iniciar algoritmo.");
            aB.DFSVisit(grafo, origen);
            // Ejemplo:
            // JFrame_VisualizarRecorrido recorrido = new JFrame_VisualizarRecorrido(origen);
            // recorrido.setVisible(true);
            
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un origen válido.");
        }
    }

    /**
     * Dibuja todo el grafo en rojo (estado normal).
     */
    private void dibujarGrafoBase() {
        mapViewer.removeAllMapMarkers();
        mapViewer.removeAllMapPolygons();

        List<MapMarker> markers = new ArrayList<>();
        List<MapPolygon> lines = new ArrayList<>();

        // Dibujar Aristas (Líneas)
        // Usamos un set simple de strings para no dibujar lineas dobles si no es necesario
        // O simplemente iteramos todo si no importa la sobreimpresión
        for (Vertice v : grafo.getVertices()) {
            // Marcador (Punto rojo por defecto)
            markers.add(new MapMarkerDot(v.getColor(), v.getCoordenada().getLat(), v.getCoordenada().getLon()));
            
            // Aristas
            for (Arista a : grafo.getVecinos(v)) {
                Vertice u = a.getDestino();
                MapPolygonImpl linea = new MapPolygonImpl(v.getCoordenada(), u.getCoordenada(), v.getCoordenada());
                linea.setColor(Color.RED);
                lines.add(linea);
            }
        }
        
        mapViewer.setMapMarkerList(markers);
        mapViewer.setMapPolygonList(lines);
    }

    /**
     * Redibuja el grafo pero pinta el vértice seleccionado de AZUL (CYAN)
     * para que el usuario sepa dónde está ubicado.
     */
    private void resaltarVerticeSeleccionado(Vertice seleccionado) {
        if (seleccionado == null) return;

        mapViewer.removeAllMapMarkers();
        // No borramos polígonos (líneas) para no procesar tanto, solo cambiamos los puntos.
        
        List<MapMarker> markers = new ArrayList<>();

        for (Vertice v : grafo.getVertices()) {
            if (v.equals(seleccionado)) {
                // El seleccionado es AZUL y más grande (si la librería lo permite, si no, solo color)
                MapMarkerDot dot = new MapMarkerDot(Color.BLUE, v.getCoordenada().getLat(), v.getCoordenada().getLon());
                dot.setBackColor(Color.CYAN);
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

    @Override
    public void actualizar() {
        dibujarGrafoBase();
        repaint();
        revalidate();
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
