/*
 * ChalkTransmiter.java
 *
 * Created on 9 de febrero de 2007, 3:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package kchalkboard.comm;

import kchalkboard.model.Punto;
import kchalkboard.model.Comando;
import java.net.*;
import java.io.*;
import java.awt.*;

/**
 *
 * @author eduardo
 */
public class ChalkTransmiter implements Runnable {
    
    Cola colaOUT;
    PrintStream sOut;
    BufferedReader sIn;
    Socket cliente;
    boolean conectado;
    
    /** Creates a new instance of ChalkTransmiter */
    public ChalkTransmiter() {
        
        conectado = false;
    }
    
    public void run() {
        Comando cmd;
        
        while(true) {
           if(colaOUT.size()!=0) {
                cmd = (Comando) colaOUT.pop();
                if(conectado)
                    sOut.println(codificar(cmd));
           }
       }
    }
 
    public void setColaOUT(Cola o) {
        
       colaOUT = o; 
    }
        
 
    
    public void setConectado(boolean b) {
        
        conectado = b;
    }
        
    public boolean conectado() {
    
        return(conectado);
    }
    
    public void setCliente(Socket s) {
        
        cliente = s;
        try {
            sOut = new PrintStream( cliente.getOutputStream() );
            sIn = new BufferedReader( new InputStreamReader(cliente.getInputStream()) );
            conectado = true;
        }  catch( IOException e ) {
            conectado = false;
            System.out.println( e );
          }
    }

    private String codificar(Comando cmd) {
    
        String orden, txt;
        Color c;
        Punto p;
        Dimension d;
        int R,G,B, x, y, r;
        float g;
        
        txt = "";
        
        
        orden = cmd.getOrden();
      /*  if(orden.equals("C")) {
            txt = "C|";
            c   = cmd.getColor();
            R   = c.getRed();
            G   = c.getGreen();
            B   = c.getBlue();
            txt = txt + R + "|" + G + "|" + B+ "|" + 0;
        } */
        
        if(orden.equals("P")) {
            txt = "P|";
            p   = cmd.getPunto();
            x = p.getX();
            y = p.getY();
            g = p.getGrosor();
            c = p.getColor();
            int rgb = c.getRGB();
            txt = txt + x + "|" + y + "|" + rgb + "|" + g;
        }

        if(orden.equals("F")) {
            txt = "F|";
            txt = txt + 0 + "|" + 0 + "|" + 0+ "|" + 0;
        }
        
        if(orden.equals("Z")) {
            txt = "Z|";
            txt = txt + cmd.getFondo() + "|" + 0 + "|" + 0+ "|" + 0;
        }
        
        if(orden.equals("B")) {
            txt = "B|";
            txt = txt + 0 + "|" + 0 + "|" + 0 + "|" + 0;
        }       
        
        if(orden.equals("X")) {
            txt = "X|";
            p   = cmd.getPunto();
            x = p.getX();
            y = p.getY();
            r = cmd.getRadio();
            g = p.getGrosor();
            txt = txt + x + "|" + y + "|" + r + "|" + g;
        }
                
        if(orden.equals("L")) {
            txt = "L|";
            d   = cmd.getDimension();
            x = (int) d.getWidth();
            y = (int) d.getHeight();
            txt = txt + x + "|" + y + "|" + 0+ "|" + 0;
        }

        txt = txt + "|" + cmd.getTiempo();
        return(txt);
    }

}
