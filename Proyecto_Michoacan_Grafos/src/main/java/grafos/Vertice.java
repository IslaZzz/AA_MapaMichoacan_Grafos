/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grafos;

import java.awt.Color;
import org.openstreetmap.gui.jmapviewer.Coordinate;

/**
 *
 * @author Abraham Coronel
 */
public class Vertice {

    private final String nombre;
    private final Coordinate coordenada;
    private Color colorNodo;
    private Vertice antecesor;
    private int tiempo;

    public Vertice(String nombre, Coordinate coordenada) {
        this.nombre = nombre;
        this.coordenada = coordenada;
        this.colorNodo = Color.WHITE;
    }
    
    public void nodoVisitado() {
        this.colorNodo = Color.GRAY;
    }
    
    public void nodoCompleto() {
        this.colorNodo = Color.BLACK;
    }
    
    public Color getColor() {
        return colorNodo;
    }
    
    public String getNombre() {
        return nombre;
    }

    public double getLatitud() {
        return coordenada.getLat();
    }

    public double getLongitud() {
        return coordenada.getLon();
    }

    public Vertice getAntecesor() {
        return antecesor;
    }
    
    public void setAntecesor(Vertice v) {
        this.antecesor = v;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Vertice nodo = (Vertice) obj;
        return nombre.equals(nodo.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }

    public Coordinate getCoordenada() {
        return coordenada;
    }

}
