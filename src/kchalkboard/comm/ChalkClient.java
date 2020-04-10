/*
 * ChalkClient.java
 *
 * Created on 5 de febrero de 2007, 1:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package kchalkboard.comm;

import kchalkboard.model.Punto;
import kchalkboard.model.Comando;
import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.*;

/**
 *
 * @author eduardo
 */
public class ChalkClient implements Runnable {
    
    /**
     * Creates a new instance of ChalkClient
     */
    
    Cola colaIN;
    PrintStream sOut;
    BufferedReader sIn;
    Socket cliente;
    boolean conectado;
    
    public ChalkClient() {
        
        conectado = false;
    }
    
    public void conectar(String ip, int puerto) {
    
        try {
              cliente = new Socket(ip, puerto);
              sOut = new PrintStream( cliente.getOutputStream() );
              sIn = new BufferedReader( new InputStreamReader(cliente.getInputStream()) );
              conectado = true;
              
        } catch(Exception e) {
            
            System.out.println(e);
            conectado = false;
        }
    }
    
    public void desconectar() {
    
        try {
              cliente.close();
              conectado = false;
              
        } catch(Exception e) {
            
            System.out.println(e);
            conectado = false;
        }
    }    
    
    public void setConectado(boolean b) {
        
        conectado = b;
    }
    
    public void setColaIn(Cola o) {
        
       colaIN = o; 
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
    
    public void run() {
        
        Comando cmd;
        String texto;
        
        while(true) {
            
            conectado = true;
            while( conectado ) {
                try {
                texto = sIn.readLine();
                //sOut.println( texto );
                if(texto != null)
                       agregarCola(decodificar(texto));
                else
                       conectado = false;
                Thread.sleep(10);
                } catch(Exception e) { }
             }        
        }
    }
    
    private Comando decodificar(String cadena) {

        Comando comando = new Comando();
        Punto p;
        int b, x, y, c;
        String z;
        String cmd;
        Color cc;
        float grosor = 0;
        long t = 0;
        
        StringTokenizer st = new StringTokenizer(cadena, "|");
        
        x = y = c = 0;
        b = 0;
        cmd ="";
        
        cc = new Color(255,255,255);
        
        while(st.hasMoreTokens()) {
           ++b;
            z = st.nextToken();
            switch(b) {
                
                case 1:
                        cmd = z;
                        break;
                        
                case 2:
                        x = Integer.parseInt(z);
                        break;

                case 3:
                        y = Integer.parseInt(z);
                        break;

                case 4:
                        c = Integer.parseInt(z);
                        cc = new Color(c);
                        break;
                case 5 :
                        grosor = Float.parseFloat(z);
                        b = 0;
                        break;
                case 6:
                        t = Integer.parseInt(z);
                        b = 0;
                        break;
            }
            
            if(b == 0) {
                comando.setOrden(cmd);
                if(cmd.equals("P")) {
                    p = new Punto(x,y,cc,grosor);
                    comando.setPunto(p);
                }
                else 
                    if(cmd.equals("C")) {
                       cc = new Color(x,y,c,grosor);
                       comando.setColor(cc);   
                  }  
                  else 
                       if(cmd.equals("X")) {
                            p = new Punto(x,y,cc,grosor);
                            comando.setPunto(p);
                            comando.setRadio(c);
                       }
                       else
                            if(cmd.equals("L")) {
                                comando.setDimension(x,y);
                                
                            }
                            else
                                if(cmd.equals("Z")) {
                                    comando.setFondo(x);
                                } else
                                    if(cmd.equals("F")) {
                                        // Nothing
                                     }
                    }
          }
       comando.setTiempo(t);
       return(comando);
    }
    
    public Socket getCliente() {
        
         return(cliente);
        
    }
    
    private void agregarCola(Object o) {
        
    
          colaIN.push(o);
    }
}

