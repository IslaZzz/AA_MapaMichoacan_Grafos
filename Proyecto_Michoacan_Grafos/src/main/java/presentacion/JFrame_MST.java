package presentacion;

import Algoritmos.AlgoritmosMST;
import Algoritmos.Observador;
import grafos.Grafo;
import grafos.GrafoMorelia;
import grafos.Vertice;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
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
 * JFrame para mostrar Kruskal
 *
 * @author abrilislas
 */
public class JFrame_MST extends JFrame_Padre implements Observador {

    private final JMapViewer mapViewer;
    private final Grafo grafo;
    private final JButton btnCalcular;
    private final AlgoritmosMST algoritmoMST;
    private final BotonRegresar btnRegresar;

    public JFrame_MST() {
        super();
        setTitle("Minimum Spanning Tree - Kruskal");
        setLayout(new BorderLayout());

        grafo = GrafoMorelia.construirGrafo();
        algoritmoMST = new AlgoritmosMST() {
        };
        algoritmoMST.setObservador(this);
        mapViewer = new JMapViewer();
        mapViewer.setZoomControlsVisible(true);
        mapViewer.setDisplayPosition(new Coordinate(19.7037, -101.1920), 8);

        btnCalcular = new JButton("Calcular MST (Kruskal)");
        btnCalcular.setForeground(Color.WHITE);
        btnCalcular.setBackground(new Color(0, 102, 204));
        btnRegresar = new BotonRegresar("Exit", this);

        JPanel top = new JPanel();
        top.add(btnCalcular);
        top.add(btnRegresar);

        add(top, BorderLayout.NORTH);
        add(mapViewer, BorderLayout.CENTER);

        dibujarGrafoOriginal();

        btnCalcular.addActionListener(e -> ejecutarKruskal());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void ejecutarKruskal() {
        new Thread(() -> {
            algoritmoMST.kruskal(grafo);
        }).start();

    }

    private void dibujarGrafoOriginal() {
        mapViewer.removeAllMapMarkers();
        mapViewer.removeAllMapPolygons();

        List<MapMarker> markers = new ArrayList<>();
        List<MapPolygon> lines = new ArrayList<>();

        for (Vertice v : grafo.getVertices()) {

            MapMarkerDot mark = new MapMarkerDot(Color.RED,
                    v.getCoordenada().getLat(),
                    v.getCoordenada().getLon());
            mark.setBackColor(Color.RED);
            markers.add(mark);

            // Aristas
            grafo.getVecinos(v).forEach(a -> {
                Vertice u = a.getDestino();

                MapPolygonImpl linea = new MapPolygonImpl(
                        v.getCoordenada(),
                        u.getCoordenada(),
                        v.getCoordenada()
                );
                linea.setColor(Color.BLACK);
                lines.add(linea);
            });
        }

        mapViewer.setMapMarkerList(markers);
        mapViewer.setMapPolygonList(lines);
        mapViewer.repaint();
    }

    private void dibujarMST(Grafo mst) {
        mapViewer.removeAllMapMarkers();
        mapViewer.removeAllMapPolygons();

        List<MapMarker> markers = new ArrayList<>();
        List<MapPolygon> lines = new ArrayList<>();

        for (Vertice v : mst.getVertices()) {
            MapMarkerDot mark = new MapMarkerDot(Color.BLUE,
                    v.getCoordenada().getLat(),
                    v.getCoordenada().getLon());
            mark.setBackColor(v.getColor());
            markers.add(mark);
        }

        for (Vertice v : mst.getVertices()) {
            mst.getVecinos(v).forEach(a -> {

                Vertice u = a.getDestino();

                MapPolygonImpl line = new MapPolygonImpl(
                        v.getCoordenada(),
                        u.getCoordenada(),
                        v.getCoordenada()
                );
                line.setColor(a.getColor());
                lines.add(line);
            });
        }

        mapViewer.setMapMarkerList(markers);
        mapViewer.setMapPolygonList(lines);
        mapViewer.repaint();
    }

    @Override
    public void actualizar() {
        SwingUtilities.invokeLater(() -> {
            dibujarMST(algoritmoMST.getMST());
            if (algoritmoMST.estadoProceso()) {
                JOptionPane.showMessageDialog(this,
                        "Peso total del MST: " + algoritmoMST.getPesoTotal());
            }
        });
    }

}
