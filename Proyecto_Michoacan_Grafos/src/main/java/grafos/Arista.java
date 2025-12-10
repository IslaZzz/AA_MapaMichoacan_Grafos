/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grafos;

import java.awt.Color;

/**
 *
 * @author Abraham Coronel
 */
public class Arista {

    private final Vertice destino;
    private final double peso;
    private Color color;

    public Arista(Vertice destino, double peso) {
        this.destino = destino;
        this.peso = peso;
        this.color = Color.BLUE;
    }

    public Vertice getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "→ " + destino.getNombre() + " (" + peso + ")";
    }

}
