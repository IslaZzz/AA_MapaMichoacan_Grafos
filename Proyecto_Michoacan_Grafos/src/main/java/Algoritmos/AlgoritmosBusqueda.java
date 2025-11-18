/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Algoritmos;

import grafos.Arista;
import grafos.Grafo;
import grafos.Vertice;
import java.util.List;

/**
 *
 * @author Abraham Coronel
 */
public class AlgoritmosBusqueda {
//    public void DFS (Grafo grafo) {
//        for (Vertice u : grafo.getVertices()) {
//            if (u.getColor() == Color.WHITE) { // Línea 6
//                // Línea 7: DFS-VISIT(G, u)
//                dfsVisit(grafo, u);
//            }
//        }
//    }
//    
//    public void DFSVisit(Grafo grafo, Vertice origen) {
//        this.time[0]++; // Línea 1: time = time + 1
//        origen.setD(this.time[0]); // Línea 2: u.d = time
//        origen.setColor(Color.GRAY); // Línea 3: u.color = GRAY
//        
//        System.out.println("Descubierto (d=" + u.getD() + "): " + u.getNombre());
//
//        // Línea 4: for each v ∈ G.Adj[u]
//        List<Arista> adyacentes = grafo.getVecinos(origen);
//        
//        for (Arista arista : adyacentes) {
//            Vertice v = arista.getDestino();
//            
//            if (v.getColor() == Color.WHITE) { // Línea 5
//                v.setPadre(origen); // Línea 6: v.π = u
//                DFSVisit(grafo, v); // Línea 7: DFS-VISIT(G, v)
//            }
//        }
//
//        origen.setColor(Color.BLACK); // Línea 8: u.color = BLACK
//        this.time[0]++; // Línea 9: time = time + 1
//        u.setF(this.time[0]); // Línea 10: u.f = time
//    }
}
