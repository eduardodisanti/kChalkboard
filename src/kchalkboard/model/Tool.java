/*
 * Tool.java
 *
 * Created on 15 de febrero de 2007, 20:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package kchalkboard.model;

/**
 *
 * @author eduardo
 */
public class Tool {
    
    private int id;
    private int radio;
    private boolean borra;
    private float grosor;
    
    /** Creates a new instance of Tool */
    public Tool() {
        
        id = 1;
        radio = 30;
        borra = false;
    }

    public Tool(int i) {

        this.setTool(i);

    }

    public int getId() {

        return(id);
    }
    
    public int getRadio() {
    
          return(radio);
    }
    
    public void setTool(int unaid) {
        
        id = unaid;
        
        switch(id) {
            
            case 1 : setDibujar();
                     break;
                     
            case 2 : setBorrar();
                     break;

            case 3 : setPincel();
                     break;
            
        }
                
    }
    
    private void setDibujar() {

        grosor = (float) 1.5;
        borra = false;
    }
    
    private void setBorrar() {
        
        borra = true;
    }
    
    public boolean getBorra() {
        
        return(borra);
    }

    public float getGrosor() {

        return(grosor);
    }

    private void setPincel() {

        borra = false;
        grosor = 10;
    }
}
