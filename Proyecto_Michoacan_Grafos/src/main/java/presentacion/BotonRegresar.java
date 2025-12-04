/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author abrilislas
 */
public class BotonRegresar extends JButton{
    
    public BotonRegresar(String textoBoton, JFrame ventanaActual){
        super();
        setBackground(new Color(204,255,204));
        setForeground(new Color(0,51,51));
        this.setText(textoBoton);
        
        addActionListener(e -> {
        ventanaActual.dispose(); 
        new JFrame_MenuPrincipal().setVisible(true); 
        });
    }
}
