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
public class GrafoMorelia{
    
    // Creamos un grafo simple no dirigido
    Graph<String, DefaultEdge> grafo;

    /**
     * Constructor de la clase, crea todos los vertices
     * correspondientes a las ciudades. 
     */
    public GrafoMorelia(){
        grafo.addVertex("Morelia");
        grafo.addVertex("Uruapan");
        grafo.addVertex("Zamora");
        grafo.addVertex("Zitácuaro"); 
        grafo.addVertex("Apatzingán");
        grafo.addVertex("Hidalgo");
        grafo.addVertex("Tarímbaro");
        grafo.addVertex("La Piedad");
        grafo.addVertex("Pátzcuaro");
        grafo.addVertex("Maravatío");
        grafo.addVertex("Tacámbaro");
        grafo.addVertex("Los Reyes");
        grafo.addVertex("Sahuayo");
        grafo.addVertex("Puruándiro");
        grafo.addVertex("Jacona");
        grafo.addVertex("Jiquilpan");
        grafo.addVertex("Tangancícuaro"); 
        grafo.addVertex("Tacámbaro de Codallos");
        grafo.addVertex("Yurécuaro");
        grafo.addVertex("Nueva Italia de Ruiz Múgica");
        grafo.addVertex("La Orilla (zona de Lázaro Cárdenas)");
        grafo.addVertex("Venustiano Carranza");
        grafo.addVertex("José Sixto Verduzco");
        grafo.addVertex("Tanhuato");
        grafo.addVertex("Angamacutiro");
        grafo.addVertex("Jiménez");
        grafo.addVertex("Ecuandureo");
        
        //llamamos al metodo para crear los vértices
        crearVertices();
        
        
        }
    
    
    public void crearVertices(){
    
    
    }

}
