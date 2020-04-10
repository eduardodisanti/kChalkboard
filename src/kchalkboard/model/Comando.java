/*
 * Comando.java
 *
 * Created on 4 de febrero de 2007, 23:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package kchalkboard.model;

import java.awt.*;
import java.util.*;
/**
 *
 * @author eduardo
 */
public class Comando {
    
    String orden;
    Vector trazo;
    Punto P;
    Color C;
    Dimension D;
    int Fondo;
    int radio;
    long tiempo;
    
    /** Creates a new instance of Comando */
    public Comando() {

        trazo = null;
        C = null;
        P = null;
        D =null;
        orden = null;
    }
    
    public long getTiempo() {
        return(tiempo);
    }

    public void setTiempo(long t) {

        tiempo = t;
    }

    public void setTrazo(Vector t) {
        
        trazo = t;
    }
    
    public void setPunto(Punto p) {
        
        P = p;
    }

    public void setRadio(int r) {
        
        radio = r;
    }
        
    public void setColor(Color c) {
        
        C = c;
    }
        
    public void setDimension(int ancho, int alto) {
        
        D = new Dimension(ancho, alto);
    }
    
    public Vector getTrazo() {
        
        return(trazo);
    }

    public int getRadio() {
        
        return(radio);
    }

    public Punto getPunto() {
        
        return(P);
    }
    
    public Color getColor() {
        
        return(C);
    }

    public Dimension getDimension() {
        
        return(D);
    }
        
    public void setOrden(String o) {
        
        orden = o;
    }
    
    public void setFondo(int f) {
        
        Fondo = f;
    }

    public int getFondo() {
        
        return(Fondo);
    }
    
    public String getOrden() {
        
        return(orden);
    }
}
