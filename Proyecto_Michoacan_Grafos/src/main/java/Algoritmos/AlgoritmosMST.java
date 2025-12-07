/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Algoritmos;

import grafos.Arista;
import grafos.AristaCompleta;
import grafos.Grafo;
import grafos.Vertice;
import java.awt.Color;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

/**
 *
 * @author Abraham Coronel
 */
public abstract class AlgoritmosMST {

    JPanel panelGrafo = new JPanel();
    //Grafo grafo;
    List<AristaCompleta> listaAristas;
    //mapa de referencia para saber cual es el árbol más pequeño y ponerlo debajo del más grande.
    Map<Vertice, Integer> rank = new HashMap<>();
    //mapa de grupos de vertices
    Map<Vertice, Vertice> parent = new HashMap<>();
    //Grafo Minimum spanning tree
    Grafo MST;
    Observador observador;
    boolean terminoProceso;

    private Double pesoTotal;

    /**
     * Método que implementa el algoritmo kruskal, devolviendo el Minimum
     * Spanning tree y el peso total del grafo de entrada.
     *
     * @param grafo Recibe el grafo sobre el cual trabajará
     */
    public void kruskal(Grafo grafo) {
        pesoTotal = 0.0;

        //Obtenemos la lista de aristas y las ordenamos
        listaAristas = grafo.getAristas();
        listaAristas.sort(Comparator.comparingDouble(AristaCompleta::getPeso));

        //agregamos los vertices al MST
        MST = new Grafo();
        for (Vertice v : grafo.getVertices()) {
            MST.agregarVertice(v);
        }
        //agregamos cada vertice a un conjunto
        for (Vertice vertex : grafo.getVertices()) {
            makeSet(vertex);
        }

        //recorremos las aristas ya ordenadas
        for (AristaCompleta arista : listaAristas) {
            Vertice u = arista.getOrigen();
            Vertice v = arista.getDestino();

            //Si pertenecen a conjuntos diferentes, esta arista entra al MST
            if (findSet(u) != findSet(v)) {
                MST.agregarArista(u, v, arista.getPeso());
                pesoTotal += arista.getPeso();
                unionFind(u, v);
            }
            arista.setColor(Color.RED);
            observador.actualizar();
            try {
                Thread.sleep(200l);
            } catch (InterruptedException ex) {
                System.out.println("EASTER EGG");
            }
        }
        terminoElProceso();
        observador.actualizar();
    }

    /**
     * Recibe el vértice y crea un conjunto nuevo dónde es el único elemento.
     *
     * @param vertex vertice (ig)
     */
    private void makeSet(Vertice vertex) {

        parent.put(vertex, vertex); //vertice en su propio conjunto
        rank.put(vertex, 0); //profundidad (algo asi)
    }

    private Vertice findSet(Vertice vertex) {
        if (parent.get(vertex) != vertex) {
            parent.put(vertex, findSet(parent.get(vertex)));
        }
        return parent.get(vertex);
    }

    private void unionFind(Vertice u, Vertice v) {
        //buscamos los conuuntos a los que pertenecen 
        Vertice rootU = findSet(u);
        Vertice rootV = findSet(v);

        if (rootV == rootU) {
            return;
        }
        int rankU = rank.get(rootU);
        int rankV = rank.get(rootV);

        if (rankU < rankV) {
            parent.put(rootU, rootV);
        } else if (rankU > rankV) {
            parent.put(rootV, rootU);
        } else {
            parent.put(rootV, rootU);
            rank.put(rootU, rankU + 1);
        }

    }

    public Grafo getMST() {
        return MST;
    }

    public Double getPesoTotal() {
        return pesoTotal;
    }

    /**
     * Pintamos el grafo en el panel
     */
    private void pintarGrafo() {

    }

    public void setObservador(Observador o) {
        observador = o;
    }
    
    private void terminoElProceso() {
        terminoProceso = true;
    }
    
    public boolean estadoProceso() {
        return terminoProceso;
    }
}
