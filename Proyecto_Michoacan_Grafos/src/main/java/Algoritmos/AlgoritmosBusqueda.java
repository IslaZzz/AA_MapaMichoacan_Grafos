/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Algoritmos;

import grafos.Arista;
import grafos.Grafo;
import grafos.Vertice;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author Abraham Coronel
 */
public class AlgoritmosBusqueda {

    private Observador observador;
    private int tiempo = 0;

    public void DFS(Grafo grafo) {

        for (Vertice u : grafo.getVertices()) {
            if (u.getColor() == Color.WHITE) {

                DFSVisit(grafo, u);
            }
        }
    }

    public void DFSVisit(Grafo grafo, Vertice origen) {
        this.tiempo++;
        origen.setTiempo(tiempo);
        origen.nodoVisitado();
        observador.actualizar();

        System.out.println("Descubierto (d=" + origen.getTiempo() + "): " + origen.getNombre());

        List<Arista> adyacentes = grafo.getVecinos(origen);

        for (Arista arista : adyacentes) {
            Vertice v = arista.getDestino();

            if (v.getColor() == Color.WHITE) {
                v.setAntecesor(origen);
                DFSVisit(grafo, v);
                observador.actualizar();
            }
        }

        origen.nodoCompleto();
        this.tiempo++;
        System.out.println("Completo (d=" + origen.getTiempo() + "/" + tiempo + "): " + origen.getNombre());
        origen.setTiempo(this.tiempo);

        try {
            Thread.sleep(500l);
        } catch (InterruptedException ex) {
            System.out.println("EASTER EGG");
        }

    }

    public Map<String, Object> dijkstra(Grafo grafo, Vertice fuente) {
        Map<Vertice, Double> distancias = new HashMap<>();
        Map<Vertice, Vertice> predecesores = new HashMap<>();

        for (Vertice v : grafo.getVertices()) {
            distancias.put(v, Double.POSITIVE_INFINITY);
            predecesores.put(v, null);
        }
        distancias.put(fuente, 0.0);

        Set<Vertice> S = new HashSet<>();

        PriorityQueue<Vertice> Q = new PriorityQueue<>(Comparator.comparingDouble(distancias::get));
        Q.addAll(grafo.getVertices());

        while (!Q.isEmpty()) {

            Vertice u = Q.poll();

            S.add(u);

            for (Arista arista : grafo.getVecinos(u)) {
                Vertice v = arista.getDestino();
                double peso = arista.getPeso(); // w(u, v)

                relax(u, v, peso, distancias, predecesores, Q);
            }
        }

        // Devolvemos ambos mapas para que el usuario pueda usarlos
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("distancias", distancias);
        resultado.put("predecesores", predecesores);
        return resultado;

    }

    private void relax(Vertice u, Vertice v, double peso, Map<Vertice, Double> distancias, Map<Vertice, Vertice> predecesores, PriorityQueue<Vertice> Q) {
        double nuevaDist = distancias.get(u) + peso;
        if (distancias.get(v) > nuevaDist) {

            distancias.put(v, nuevaDist);

            predecesores.put(v, u);

            Q.remove(v);
            Q.add(v);
        }
    }

    public static List<Vertice> getRutaCorta(Map<Vertice, Vertice> predecesores, Vertice destino) {
        List<Vertice> ruta = new ArrayList<>();

        for (Vertice v = destino; v != null; v = predecesores.get(v)) {
            ruta.add(v);
        }

        Collections.reverse(ruta);

        if (ruta.isEmpty() || predecesores.get(ruta.get(0)) != null) {
            return new ArrayList<>();
        }

        return ruta;
    }

    public void subscribirObservador(Observador o) {
        this.observador = o;
    }
}
