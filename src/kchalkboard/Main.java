/*
 * Main.java
 *
 * Created on 4 de febrero de 2007, 1:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package kchalkboard;

import kchalkboard.view.ViewedKChalk;

/**
 *
 * @author eduardo
 */
public class Main {
    
    static String ipServidor;
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        if(args != null) {
            
            try {
                   ipServidor = args[0];
            } catch(Exception e) {
                
                ipServidor = "kfussion.com";
            }
        }
        
        ViewedKChalk kc = new ViewedKChalk();
        kc.setIpServidor(ipServidor);
        kc.setVisible(true);
    }
    
}
