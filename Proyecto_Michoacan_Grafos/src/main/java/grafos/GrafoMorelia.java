/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grafos;

//Para el proyecto encontré una librería, que es JGraphT
import java.util.HashMap;
import java.util.Map;
import org.openstreetmap.gui.jmapviewer.Coordinate;

/**
 *
 * @author abrilislas
 */
public class GrafoMorelia {

    public static Grafo construirGrafo() {
        Grafo grafo = new Grafo();
        Map<String, Vertice> nodos = new HashMap<>();

        Map<String, Coordinate> coordenadas = Map.ofEntries(
                Map.entry("Morelia", new Coordinate(19.7037, -101.1920)),
                Map.entry("Uruapan", new Coordinate(19.4206, -102.0628)),
                Map.entry("Zamora de Hidalgo", new Coordinate(19.9839, -102.2831)),
                Map.entry("Apatzingán", new Coordinate(19.0872, -102.3522)),
                Map.entry("La Piedad", new Coordinate(20.3425, -102.0210)),
                Map.entry("Heroica Zitácuaro", new Coordinate(19.4316, -100.3541)),
                Map.entry("Ciudad Lázaro Cárdenas", new Coordinate(17.9577, -102.2038)),
                Map.entry("Ciudad Hidalgo", new Coordinate(19.6922, -100.5575)),
                Map.entry("Sahuayo de Morelos", new Coordinate(20.0600, -102.7231)),
                Map.entry("Jacona de Plancarte", new Coordinate(19.9531, -102.2858)),
                Map.entry("Pátzcuaro", new Coordinate(19.5164, -101.6097)),
                Map.entry("Zacapu", new Coordinate(19.8139, -101.8233)),
                Map.entry("Los Reyes de Salgado", new Coordinate(19.5889, -102.4733)),
                Map.entry("Las Guacamayas", new Coordinate(18.0211, -102.2128)),
                Map.entry("Maravatío de Ocampo", new Coordinate(19.8917, -100.4422)),
                Map.entry("Puruándiro", new Coordinate(20.0869, -101.5142)),
                Map.entry("Nueva Italia de Ruiz", new Coordinate(19.0306, -102.0917)),
                Map.entry("Tacámbaro de Codallos", new Coordinate(19.2353, -101.4581)),
                Map.entry("La Orilla", new Coordinate(17.9819, -102.2217)),
                Map.entry("Yurécuaro", new Coordinate(20.3411, -102.2825)),
                Map.entry("Jiquilpan de Juárez", new Coordinate(19.9917, -102.7167)),
                Map.entry("Huetamo de Núñez", new Coordinate(18.6333, -100.8833)),
                Map.entry("Paracho de Verduzco", new Coordinate(19.6472, -102.0483)),
                Map.entry("Ario de Rosales", new Coordinate(19.2000, -101.6667)),
                Map.entry("Peribán de Ramos", new Coordinate(19.5200, -102.4139)),
                Map.entry("Tepalcatepec", new Coordinate(19.1892, -102.8461)),
                Map.entry("Zinapécuaro de Figueroa", new Coordinate(19.8667, -100.8333)),
                Map.entry("Cherán", new Coordinate(19.6450, -101.8750)),
                Map.entry("Santa Clara del Cobre", new Coordinate(19.4083, -101.6458)),
                Map.entry("Nuevo San Juan Parangaricutiro", new Coordinate(19.4167, -102.1294))
        );

        for (Map.Entry<String, Coordinate> entry : coordenadas.entrySet()) {
            Vertice nodo = new Vertice(entry.getKey(), entry.getValue());
            grafo.agregarVertice(nodo);
            nodos.put(entry.getKey(), nodo);
        }

        agregarConexiones(grafo, nodos);

        return grafo;
    }

    private static void agregarConexiones(Grafo grafo, Map<String, Vertice> nodos) {
        grafo.agregarArista(nodos.get("Heroica Zitácuaro"), nodos.get("Ciudad Hidalgo"), 60);
        grafo.agregarArista(nodos.get("Ciudad Hidalgo"), nodos.get("Heroica Zitácuaro"), 60);

        grafo.agregarArista(nodos.get("Ciudad Hidalgo"), nodos.get("Morelia"), 95);
        grafo.agregarArista(nodos.get("Morelia"), nodos.get("Ciudad Hidalgo"), 95);

        grafo.agregarArista(nodos.get("Morelia"), nodos.get("Zacapu"), 80);
        grafo.agregarArista(nodos.get("Zacapu"), nodos.get("Morelia"), 80);

        grafo.agregarArista(nodos.get("Zacapu"), nodos.get("Zamora de Hidalgo"), 75);
        grafo.agregarArista(nodos.get("Zamora de Hidalgo"), nodos.get("Zacapu"), 75);

        grafo.agregarArista(nodos.get("La Piedad"), nodos.get("Puruándiro"), 70);
        grafo.agregarArista(nodos.get("Puruándiro"), nodos.get("La Piedad"), 70);

        grafo.agregarArista(nodos.get("Puruándiro"), nodos.get("Morelia"), 90);
        grafo.agregarArista(nodos.get("Morelia"), nodos.get("Puruándiro"), 90);

        grafo.agregarArista(nodos.get("Morelia"), nodos.get("Uruapan"), 112);
        grafo.agregarArista(nodos.get("Uruapan"), nodos.get("Morelia"), 112);

        grafo.agregarArista(nodos.get("Uruapan"), nodos.get("Nueva Italia de Ruiz"), 70);
        grafo.agregarArista(nodos.get("Nueva Italia de Ruiz"), nodos.get("Uruapan"), 70);

        grafo.agregarArista(nodos.get("Nueva Italia de Ruiz"), nodos.get("Ciudad Lázaro Cárdenas"), 160);
        grafo.agregarArista(nodos.get("Ciudad Lázaro Cárdenas"), nodos.get("Nueva Italia de Ruiz"), 160);

        grafo.agregarArista(nodos.get("Morelia"), nodos.get("Pátzcuaro"), 58);
        grafo.agregarArista(nodos.get("Pátzcuaro"), nodos.get("Morelia"), 58);

        grafo.agregarArista(nodos.get("Morelia"), nodos.get("Zinapécuaro de Figueroa"), 55);
        grafo.agregarArista(nodos.get("Zinapécuaro de Figueroa"), nodos.get("Morelia"), 55);

        grafo.agregarArista(nodos.get("Morelia"), nodos.get("Tacámbaro de Codallos"), 105);
        grafo.agregarArista(nodos.get("Tacámbaro de Codallos"), nodos.get("Morelia"), 105);

        grafo.agregarArista(nodos.get("Pátzcuaro"), nodos.get("Santa Clara del Cobre"), 18);
        grafo.agregarArista(nodos.get("Santa Clara del Cobre"), nodos.get("Pátzcuaro"), 18);

        grafo.agregarArista(nodos.get("Pátzcuaro"), nodos.get("Uruapan"), 55); // Ruta vieja
        grafo.agregarArista(nodos.get("Uruapan"), nodos.get("Pátzcuaro"), 55);

        grafo.agregarArista(nodos.get("Zamora de Hidalgo"), nodos.get("Jacona de Plancarte"), 5);
        grafo.agregarArista(nodos.get("Jacona de Plancarte"), nodos.get("Zamora de Hidalgo"), 5);

        grafo.agregarArista(nodos.get("Zamora de Hidalgo"), nodos.get("Sahuayo de Morelos"), 60);
        grafo.agregarArista(nodos.get("Sahuayo de Morelos"), nodos.get("Zamora de Hidalgo"), 60);

        grafo.agregarArista(nodos.get("Sahuayo de Morelos"), nodos.get("Jiquilpan de Juárez"), 15);
        grafo.agregarArista(nodos.get("Jiquilpan de Juárez"), nodos.get("Sahuayo de Morelos"), 15);

        grafo.agregarArista(nodos.get("Zamora de Hidalgo"), nodos.get("La Piedad"), 75);
        grafo.agregarArista(nodos.get("La Piedad"), nodos.get("Zamora de Hidalgo"), 75);

        grafo.agregarArista(nodos.get("La Piedad"), nodos.get("Yurécuaro"), 30);
        grafo.agregarArista(nodos.get("Yurécuaro"), nodos.get("La Piedad"), 30);

        grafo.agregarArista(nodos.get("Apatzingán"), nodos.get("Nueva Italia de Ruiz"), 25);
        grafo.agregarArista(nodos.get("Nueva Italia de Ruiz"), nodos.get("Apatzingán"), 25);

        grafo.agregarArista(nodos.get("Apatzingán"), nodos.get("Tepalcatepec"), 85);
        grafo.agregarArista(nodos.get("Tepalcatepec"), nodos.get("Apatzingán"), 85);

        grafo.agregarArista(nodos.get("Ciudad Lázaro Cárdenas"), nodos.get("Las Guacamayas"), 8);
        grafo.agregarArista(nodos.get("Las Guacamayas"), nodos.get("Ciudad Lázaro Cárdenas"), 8);

        grafo.agregarArista(nodos.get("Las Guacamayas"), nodos.get("La Orilla"), 3);
        grafo.agregarArista(nodos.get("La Orilla"), nodos.get("Las Guacamayas"), 3);

        grafo.agregarArista(nodos.get("Zinapécuaro de Figueroa"), nodos.get("Maravatío de Ocampo"), 45);
        grafo.agregarArista(nodos.get("Maravatío de Ocampo"), nodos.get("Zinapécuaro de Figueroa"), 45);

        grafo.agregarArista(nodos.get("Tacámbaro de Codallos"), nodos.get("Huetamo de Núñez"), 95);
        grafo.agregarArista(nodos.get("Huetamo de Núñez"), nodos.get("Tacámbaro de Codallos"), 95);

        grafo.agregarArista(nodos.get("Uruapan"), nodos.get("Paracho de Verduzco"), 38);
        grafo.agregarArista(nodos.get("Paracho de Verduzco"), nodos.get("Uruapan"), 38);

        grafo.agregarArista(nodos.get("Paracho de Verduzco"), nodos.get("Cherán"), 12);
        grafo.agregarArista(nodos.get("Cherán"), nodos.get("Paracho de Verduzco"), 12);

        grafo.agregarArista(nodos.get("Cherán"), nodos.get("Zamora de Hidalgo"), 45);
        grafo.agregarArista(nodos.get("Zamora de Hidalgo"), nodos.get("Cherán"), 45);

        grafo.agregarArista(nodos.get("Jacona de Plancarte"), nodos.get("Los Reyes de Salgado"), 55);
        grafo.agregarArista(nodos.get("Los Reyes de Salgado"), nodos.get("Jacona de Plancarte"), 55);

        grafo.agregarArista(nodos.get("Los Reyes de Salgado"), nodos.get("Peribán de Ramos"), 10);
        grafo.agregarArista(nodos.get("Peribán de Ramos"), nodos.get("Los Reyes de Salgado"), 10);

        grafo.agregarArista(nodos.get("Peribán de Ramos"), nodos.get("Uruapan"), 58);
        grafo.agregarArista(nodos.get("Uruapan"), nodos.get("Peribán de Ramos"), 58);

        grafo.agregarArista(nodos.get("Santa Clara del Cobre"), nodos.get("Ario de Rosales"), 25);
        grafo.agregarArista(nodos.get("Ario de Rosales"), nodos.get("Santa Clara del Cobre"), 25);

        grafo.agregarArista(nodos.get("Uruapan"), nodos.get("Nuevo San Juan Parangaricutiro"), 15);
        grafo.agregarArista(nodos.get("Nuevo San Juan Parangaricutiro"), nodos.get("Uruapan"), 15);
    }

}
