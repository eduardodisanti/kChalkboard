/*
 * ChalkServer.java
 *
 * Created on 4 de febrero de 2007, 15:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package kchalkboard.comm;

import kchalkboard.view.ViewedKChalk;
import java.net.*;
import java.io.*;

public class ChalkServer implements Runnable {
    
    ServerSocket s = null;
    BufferedReader sIn;
    PrintStream sOut;
    Socket cliente = null;
    String texto;
    Boolean conectado;
    ChalkClient CkC;
    ChalkTransmiter CkT;
    Cola colaIN, colaOUT;
    ViewedKChalk callback;
    
    int puerto;
        
    public ChalkServer() {
        
        
        puerto = 60111;
        
        colaIN = null;
    }
    
        
    public void setCallBack(ViewedKChalk u) {
        
        callback = u;
    }
    
    public String devolverIP() {
        
        if(s!=null)
           return(s.getInetAddress().toString());
        else
            return("Esperando");
    }
    
    public Socket getCliente() {
    
        
        return(cliente);
    }
    
    public void setCliente(ChalkClient cc) {
    
        
        CkC = cc;
    }
        
    public void setColaIN(Cola c)  {
        
        colaIN = c;
    }

     public void setColaOUT(Cola c)  {
        
        colaOUT = c;
    }   
    
    public boolean getConectado()  {
        
        return(conectado);
    }
        
    public void run() {
      
      String ipCliente;
      while(true) {
        try {
            s = new ServerSocket( puerto );
        } catch( IOException e ) {
            }

        // Creamos el objeto desde el cual atenderemos y aceptaremos
        // las conexiones de los clientes y abrimos los canales de  
        // comunicaci—n de entrada y salida
        try {
            cliente = s.accept();
            
            CkC = new ChalkClient();
            CkC.setCliente(cliente);
            CkC.setColaIn(colaIN);
            CkC.setConectado(true);
            conectado = true;
            new Thread(CkC).start();
            
            CkT = new ChalkTransmiter();
            CkT.setColaOUT(colaOUT);
            CkT.setCliente(CkC.getCliente());
            new Thread(CkT).start();
            
            callback.setTitle("kChalkBoard 0.5" + devolverIP());
            callback.cambiarBotonConectar("Ya conectado", false); // TODO - RECONTRAMEGACHANCHO ARREGLAR
            
        } catch( IOException e ) {
            conectado = false;
            System.out.println( e );
            }
        }
    }
  }