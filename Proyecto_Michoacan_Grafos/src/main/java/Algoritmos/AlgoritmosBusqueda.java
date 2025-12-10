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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Abraham Coronel
 */
public class AlgoritmosBusqueda {

    private Observador observador;
    private int tiempo = 0;
    private Vertice verticieInicioBFS;
    
    public void DFS(Grafo grafo, Vertice origen) {
        for (Vertice u : grafo.getVertices()) {
            u.setColor(Color.WHITE);
        }
        tiempo = 0;
        for (Vertice u : grafo.getVertices()) {
            if (u.getColor() == Color.WHITE) {

                DFSVisit(grafo, origen);
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
            Thread.sleep(300l);
        } catch (InterruptedException ex) {
            System.out.println("EASTER EGG");
        }

    }

    
    public Map<String, Object> dijkstra(Grafo grafo, Vertice fuente, Vertice destino) {
        Map<Vertice, Double> distancias = new HashMap<>();
        Map<Vertice, Vertice> predecesores = new HashMap<>();

        for (Vertice v : grafo.getVertices()) {
            distancias.put(v, Double.POSITIVE_INFINITY);
            predecesores.put(v, null);
            v.setAntecesor(null);
        }

        distancias.put(fuente, 0.0);

        Set<Vertice> S = new HashSet<>();
        PriorityQueue<Vertice> Q = new PriorityQueue<>(Comparator.comparingDouble(distancias::get));
        Q.addAll(grafo.getVertices());

        while (!Q.isEmpty()) {
            Vertice u = Q.poll();
            S.add(u);

            u.nodoCompleto();
            notificarObservador();
            pausar(200);
            
            if (u.equals(destino)) {
                break;
        }

            for (Arista arista : grafo.getVecinos(u)) {
                Vertice v = arista.getDestino();
                double peso = arista.getPeso();

                relax(u, v, peso, distancias, predecesores, Q, destino);
            }
        }
        List<Vertice> ruta = getRutaCorta(predecesores, destino);

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("distancias", distancias);
        resultado.put("predecesores", predecesores);
        resultado.put("ruta", ruta);
        return resultado;
    }

    private void relax(Vertice u, Vertice v, double peso, Map<Vertice, Double> distancias, Map<Vertice, Vertice> predecesores, PriorityQueue<Vertice> Q, Vertice destino) {
        double nuevaDist = distancias.get(u) + peso;

        if (distancias.get(v) > nuevaDist) {
            distancias.put(v, nuevaDist);
            predecesores.put(v, u);

            v.setAntecesor(u);
            v.nodoVisitado();
            
            if(v==destino){
                while(destino.getAntecesor()!=u){
                    notificarObservador();
                    pausar(50);
                
                }
            }
            Q.remove(v);
            Q.add(v);
        }
        
    }

    private void pausar(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            System.out.println("Animación interrumpida");
        }
    }

    private void notificarObservador() {
        if (observador != null) {
            observador.actualizar();
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
    
    public void BFS(Grafo grafo, Vertice origen) {
        
        this.verticieInicioBFS = origen;
        
        for (Vertice u : grafo.getVertices()) {
            if (u.getColor() == Color.WHITE) {
                u.setAntecesor(null);
            }
        }
        traverse(grafo);  
    }
    
    private void traverse(Grafo grafo){
        Queue<Vertice> q = new LinkedList<>();
        verticieInicioBFS.nodoVisitado();
        q.add(verticieInicioBFS);
        
        while(!q.isEmpty()){
            
            Vertice verticeActual = q.poll();
            System.out.println(verticeActual);
            
            for (Arista arista : grafo.getVecinos(verticeActual)) {
                Vertice v = arista.getDestino();
                
                if(v.getColor()==Color.WHITE){
                    v.nodoVisitado();
                    v.setAntecesor(verticeActual);
                    q.add(v);
                    
                    notificarObservador();
                    pausar(100);
                }
            }
            verticeActual.nodoCompleto();
        }
        
    }
}
