/*
 * ChalkCanvas.java
 *
 * Created on 4 de febrero de 2007, 1:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package kchalkboard.controller;

import kchalkboard.comm.Cola;
import kchalkboard.model.Tool;
import kchalkboard.model.Punto;
import kchalkboard.model.Comando;
import kchalkboard.view.ViewedKChalk;
import java.awt.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eduardo
 */
public class ChalkCanvas extends Canvas implements Runnable {
    
    Vector trazos;
    Vector puntos;
    Color currentColor, myCurrentColor;
    Stroke stroke;
    Tool currentTool;
    private Image offimg;
    private Graphics offg; 
    boolean pressed;
    int anchoPantalla, altoPantalla;
    long tiempo;
    ViewedKChalk callback;
    
    Cola colaIN, colaOUT;
    float grosor = (float)1.5;
   
    boolean dibujando, procesarColas;
    int tipoDeFondo;
    
    /** Creates a new instance of ChalkCanvas */
    public ChalkCanvas() {
        
        trazos = new Vector();
        tiempo = 0;

        super.setBackground(new Color(0,0,0));
        dibujando = false;
        procesarColas = true;
        currentColor = new Color(255,255,255);
        myCurrentColor = new Color(255,255,255);
        currentTool = new Tool();

        this.setGrosor(1.5f);

        pressed = false;
        init();
        Cursor cursor = this.getCursor();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        tipoDeFondo = 1;
    }

    public void setGrosor(float unGrosor) {
            grosor = (float) unGrosor;
            stroke = new BasicStroke(grosor);
    }

    public void setCallBack(ViewedKChalk x) {
        
    
        callback = x;
    }
    
    public void setColaIN(Cola c) {
    
         colaIN = c;
    }

    public void setColaOUT(Cola c) {
    
         colaOUT = c;
    }
     
    public void setFondo(int fondo) {

        tipoDeFondo = fondo;
        generarOrdenTipoDeFondo(fondo);
        setFondoAhora(fondo);
    }

    private void generarCallbackFondo() {
    
        callback.setCurrentFondo(tipoDeFondo);
    }
    
    public void setFondoAhora(int fondo) {
            
        Color c;
        
        c = null;
        switch (fondo) {
        
            case 1 : c = new Color(0,0,0);
                     break;
            case 2 : c = new Color(255,255,255);
                     break;
            case 3 : c = new Color(192,192,192);
                     break;
            case 4 : c = new Color(255,255,255);
                     break;
        }
        
        super.setBackground(c);
        tipoDeFondo = fondo;
        vaciarImagenExterna();
        this.repaint();
    }
    
    public void redimensionar(int ancho, int alto) {
    
         redimensionarAhora(ancho, alto);
         generarOrdenRedimensionar(ancho, alto);
    }
    
    public void redimensionarAhora(int ancho, int alto) {
    
         anchoPantalla = ancho;
         altoPantalla = alto;
           
         //this.setSize(d);
		         
         vaciarImagenExterna();
    }
    
    public void setCurrentColor(Color elcolor) {
    
         myCurrentColor = elcolor;
         //generarOrdenCambiarColor();
    }

    public void setHerramienta(Tool t) { // TODO - chancho, arreglar

        int id = t.getId();
         currentTool.setTool(id);
         
         switch(id) {
             
             case 1 : try {
                            this.setCursor(new Cursor(Cursor.HAND_CURSOR));
                            this.setGrosor(t.getGrosor());
                          } catch(Exception e) {
                                                 }
                      break;
                      
             case 2 : try { 
                           this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                           } catch(Exception e) {
                                                 }
                      break;

             case 3 : try {
                           this.setCursor(new Cursor(Cursor.HAND_CURSOR));
                           this.setGrosor(t.getGrosor());
                           } catch(Exception e) {
                                                 }
                      break;
         }
    }

    public void run() {
    
      Comando c;
      while(true) {
       
        if(procesarColas) {
            if(colaIN.size() != 0) {
               c = (Comando) colaIN.pop();
               procesarOrden(c);
            }
        }
            try {
                Thread.sleep(10);
                tiempo++;
            } catch (InterruptedException ex) {
                Logger.getLogger(ChalkCanvas.class.getName()).log(Level.SEVERE, null, ex);
            }
      }
    }
    
    private void procesarOrden(Comando c) { // TODO - Chancho - Arreglar
    
        String orden;
        Punto p;
        Dimension d;
        
        callback.colorLed(new Color(255,0,0));
        orden = c.getOrden();
        if(orden.equals("P")) {
            
            Punto P = c.getPunto();
            dibujarPunto(P);
        } 
        else
             if(orden.equals("F")) {
                  dibujando = false;
                  pressed = false;
                  puntos = new Vector();
                  agregarTrazo(puntos);
             } else
                   if(orden.equals("C"))
                       currentColor = c.getColor();
                   else
                        if(orden.equals("B"))
                              this.borrarPizarraAhora();
                        else
                            if(orden.equals("X")) {
                                 p = c.getPunto();
                                 this.borrarArea(p.getX(), p.getY(), c.getRadio());
                            }
                            else
                                if(orden.equals("L")) {
                                    d = c.getDimension();
                                    this.redimensionarAhora((int)d.getWidth(), (int)d.getHeight());
                                    callback.redimensionar(d);
                                    callback.setResizable(false);
                                    callback.colorLed(new Color(0,0,255));
                                }
                                 else
                                     if(orden.equals("Z")) {
                                       int f = c.getFondo();
                                       this.setFondoAhora(f);
                                       generarCallbackFondo();
                                     } 
                     }
    
    private void generarOrdenTipoDeFondo(int fondo) {

          Comando cmd = new Comando();
          
          cmd.setOrden("Z");
          cmd.setFondo(fondo);

          encolar(cmd);
    }
    
    private void generarOrdenDibujarPunto(int x, int y, Color c, float ungrosor) {

          Punto P = new Punto(x,y,c, ungrosor);
          Comando cmd = new Comando();
          cmd.setOrden("P");
          cmd.setPunto(new Punto(x,y,c, ungrosor));
          
          encolar(cmd);
    }
    
    private void generarOrdenBorrarArea(int x, int y,int radio) {
        
          Comando cmd = new Comando();
          cmd.setOrden("X");
          
          cmd.setPunto(new Punto(x,y,new Color(0,0,0), grosor));
          cmd.setRadio(radio);
          
          encolar(cmd);
        
    }
    
     private void generarOrdenFinTrazo() {

          Comando cmd = new Comando();
          cmd.setOrden("F");
          
          encolar(cmd);
    }
     
    private void generarOrdenCambiarColor() {

          Comando cmd = new Comando();
          cmd.setOrden("C");
          cmd.setColor(myCurrentColor);
          encolar(cmd);
    }

    private void generarOrdenRedimensionar(int x, int y) {
        
        Comando cmd = new Comando();
        
          cmd.setOrden("L");
          cmd.setDimension(x,y);
          encolar(cmd);
    }
        
    private void canvasMouseMoved(java.awt.event.MouseEvent evt) {
    
        int x = evt.getX();
        int y = evt.getY();
        boolean dibujar;
        
        Color colo = myCurrentColor;
        
        if(dibujando) {
            
           dibujar = !currentTool.getBorra();
           if (dibujar) {
               dibujarPunto(x,y,colo,grosor);
               generarOrdenDibujarPunto(x,y,colo,grosor);
           } else {
             
                    borrarArea(x,y, currentTool.getRadio());
                    generarOrdenBorrarArea(x,y,currentTool.getRadio());
             }
        }
    }
    
    private void borrarArea(int x, int y, int radio) {
        
    
        int i, j;
        int cantPuntos, cantTrazos;
        Vector listapuntos;
        Punto p;
        
        int x1, y1, x2, y2, cx, cy;
        
        x1 = x - radio / 2;
        x2 = x + radio / 2;
        y1 = y - radio / 2;
        y2 = y + radio / 2;
        cantTrazos = trazos.size();
        for(i=0;i<cantTrazos;i++) {
            
            listapuntos = (Vector) trazos.elementAt(i);
            cantPuntos = listapuntos.size();
            
            for(j=0;j<cantPuntos;j++) {
                
                p = (Punto) listapuntos.elementAt(j);
                
                cx = p.getX();
                cy = p.getY();
                
                if(cx >= x1 && cx <= x2 && cy >= y1 && cy <= y2) {
                    quitarPunto(i,j);
                    break;
                }
            }
        }
        vaciarImagenExterna();
        this.repaint();
    }
    
    public void vaciarImagenExterna() {
    
        offimg = this.createImage(anchoPantalla, altoPantalla);
        offg = offimg.getGraphics();
    }
    
    @Override
    public void update(Graphics pg) {
    
        paint(pg);
    }
     
    private void quitarPunto(int trazo, int puntoDelTrazo) {
     
        Vector t1, t2;   // ** Los trazos para dividir
        
        if(puntoDelTrazo==0)
            removerPunto(trazo, puntoDelTrazo);
        else {
            t1 = (Vector) primerTrozo(trazo, puntoDelTrazo);
            t2 = (Vector) ultimoTrozo(trazo, puntoDelTrazo);
            trazos.remove(trazo);
            trazos.add(t1);
            trazos.add(t2);
        }
    }
 
    private Vector primerTrozo(int trazo, int puntoDelTrazo) {
    
        int i;
        Vector tr;
        
            tr = new Vector();
            
            for(i=0;i<puntoDelTrazo;i++) {
                
              try {
                  tr.add(((Vector)trazos.elementAt(trazo)).elementAt(i));
              } catch(Exception e) {
                  
                  break;
              }
            }
        
        return(tr);
    }

    private Vector ultimoTrozo(int trazo, int puntoDelTrazo) {
    
        int i;
        Vector tr;
        
        tr = new Vector();
            
        for(i=puntoDelTrazo;i<((Vector)trazos.elementAt(trazo)).size();i++)
            tr.add(((Vector)trazos.elementAt(trazo)).elementAt(i));
   
        return(tr);
    }
    private void removerPunto(int trazo, int puntoDelTrazo) {
        
        ((Vector) trazos.elementAt(trazo)).remove(puntoDelTrazo);
    }
    
    private void canvasMouseReleased(java.awt.event.MouseEvent evt) {
    
        dibujando = false;
        generarOrdenFinTrazo();
        pressed = false;
        puntos = new Vector();
        agregarTrazo(puntos);
    }    

    private void canvasMousePressed(java.awt.event.MouseEvent evt) {
    
        dibujando = true;
        
        if(!pressed) {
        if(!currentTool.getBorra()) {
                puntos = new Vector();
        
                agregarTrazo(puntos);
                pressed = true;
          }
        }
    }
    
    public void agregarTrazo(Vector pts) {
    
           trazos.add(pts);
    }
    
    private void init() {
    
        
      addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
         public void mousePressed(java.awt.event.MouseEvent evt) {
                canvasMousePressed(evt);
             
             //dibujando = true;
            }
            @Override
         public void mouseReleased(java.awt.event.MouseEvent evt) {
                canvasMouseReleased(evt);
            }
      });
      
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                canvasMouseMoved(evt);
            }
            
            @Override
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                canvasMouseMoved(evt);
            }
        });
    }
    
    public void dibujarPunto(Punto p) {
        
        if(puntos == null) {
            puntos = new Vector();
            agregarTrazo(puntos);
        }
        
        //p.setColor(p.getColor());
        puntos.add(p);
        this.repaint();
    }
    
    public void dibujarPunto(int x, int y, Color c, float ungrosor) {
        
        puntos.add(new Punto(x,y,c, ungrosor));
        this.repaint();
    }
    
    public void borrarPizarra() {
    
          Comando cmd = new Comando();

          cmd.setOrden("B");
          encolar(cmd);

         borrarPizarraAhora();
    }
    
    public void borrarPizarraAhora() {
    
          vaciarImagenExterna();
          trazos.removeAllElements();
          puntos = null;
          this.repaint();
    }
    
    public void redibujar() {
        
        this.repaint();
    }
    
    @Override
    public void paint(Graphics g) {
        
        
        int cant, i, j, ct;
        boolean first = true;
        Punto p1, p2, p3;
        Vector lospuntos;
        float grosorant = -1;
        
        Graphics2D g2 = (Graphics2D) offg;
        
        if(tipoDeFondo == 4)
            dibujarCuaderno(g2);

        ct = trazos.size();
        p1 = p2 = null;
            
        for(j=0;j!=ct;j++) {
            lospuntos = (Vector) trazos.elementAt(j);
            cant = lospuntos.size();
            first = true;
            for(i=0;i!=cant;i++)
                {
               
                    if(first) {
                        first = false;  
                        p1    = (Punto) lospuntos.elementAt(i);
                        p2    = p1;
                   }
                    else {
                          p2    = (Punto) lospuntos.elementAt(i);
                         // first = true;
                    }

                    grosor = p2.getGrosor();
                    if(grosor != grosorant) {

                        this.setGrosor(grosor);
                        g2.setStroke(stroke);
                        grosorant = grosor;
                    }
                    g2.setColor(p1.getColor());
                    g2.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                   
                    p1 = p2;
                }
            }
        
        
        g.drawImage(offimg, 0, 0, this);
       }
    
    private void dibujarCuaderno(Graphics2D g) {
        
        int i, alto, ancho;
        Dimension d;
        
        BasicStroke strok = new BasicStroke((float)0.5);
        
        g.setStroke(strok);
        d = super.getSize();
        ancho = (int) d.getWidth();
        alto  = (int)d.getHeight();
        
        g.setColor(new Color(128,0,0));
        
        for(i = 0; i <= alto; i+=15) {
            
            g.drawLine(30,i,ancho,i);
        }
        g.setColor(new Color(0,0,128));
        g.drawLine(30,0,30,alto);
    }
    
    private void encolar(Comando cmd) {

        cmd.setTiempo(tiempo);
        colaOUT.add(cmd);
    }
}
