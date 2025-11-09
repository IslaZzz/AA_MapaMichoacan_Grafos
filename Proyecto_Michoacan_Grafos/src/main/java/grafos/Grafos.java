/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grafos;

//Para el proyecto encontré una librería, que es JGraphT
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

/**
 *
 * @author abrilislas
 */
public class Grafos{
    
        // Creamos un grafo simple no dirigido
        Graph<String, DefaultEdge> grafo = new SimpleGraph<>(DefaultEdge.class);

    /* Agregar vértices
        grafo.addVertex("Morelia");

        // Agregar aristas (conexiones)
        grafo.addEdge("Morelia", "Uruapan");

        // Mostrar el contenido del grafo
        System.out.println("Vértices: " + grafo.vertexSet());
        System.out.println("Aristas: " + grafo.edgeSet());
    */
}
