/*
 * Cola.java
 *
 * Created on 1 de febrero de 2006, 10:49 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author eduardo
 */
package kchalkboard.comm;

import java.util.Vector;
public class Cola extends Vector{
    
    /** Creates a new instance of Cola */
    public Cola() {
       
    }
    
    public synchronized void agregar(Object o) {
        
        super.addElement(o);
    }
    
    public synchronized Object primero() {
        
        return(super.firstElement());
    }
    
    public synchronized void eliminar(int pointer) {
        
        super.removeElementAt(pointer);
    }
    
    public synchronized Object pop() {
        
        Object o;
        
        o=null;
        if(super.size()!=0)
        {
           o = primero();
           super.removeElementAt(0);
        }
        return(o);
    }

    public synchronized void push(Object o) {

        super.addElement(o);
    }    
}
