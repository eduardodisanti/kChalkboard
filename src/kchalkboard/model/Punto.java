/*
 * Punto.java
 *
 * Created on 4 de febrero de 2007, 1:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package kchalkboard.model;

import java.awt.*;
/**
 *
 * @author eduardo
 */
public class Punto {
    
    int x,y;
    Color c;
    float grosor;

    /** Creates a new instance of Punto */
    public Punto(int xx, int yy, Color cc, float grosor) {
        
           x = xx;
           y = yy;
           c = cc;
           this.grosor = grosor;
    }

    public float getGrosor() {
        return(grosor);
    }
    
    public int getX() {
        
        return(x);
    }
    
    public int getY() {
        
        return(y);
    }    
    
    public Color getColor() {
        
        return(c);
    }
    
    public void setColor(Color cco) {
        
        c = cco;
    }
}
