/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grafos;

/**
 *
 * @author abrilislas
 */
/**
 * Clase creada para ser utilizada en kruskal,
 * ya que necesita aristas completas.
 */
public class AristaCompleta {
    
    private Vertice origen;
    private Vertice destino;
    private double peso;

    public AristaCompleta(Vertice o, Vertice d, double p) {
        this.origen = o;
        this.destino = d;
        this.peso = p;
    }

    public Vertice getOrigen() {
        return origen;
    }

    public void setOrigen(Vertice origen) {
        this.origen = origen;
    }

    public Vertice getDestino() {
        return destino;
    }

    public void setDestino(Vertice destino) {
        this.destino = destino;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
    
}
