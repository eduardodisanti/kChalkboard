/*
 * ViewedKChalk.java
 *
 * Created on 4 de febrero de 2007, 1:18
 */

package kchalkboard.view;

import kchalkboard.controller.ChalkCanvas;
import kchalkboard.comm.Cola;
import kchalkboard.comm.ChalkTransmiter;
import kchalkboard.comm.ChalkClient;
import javax.swing.*;
import java.awt.*;
import kchalkboard.comm.ChalkServer;
import kchalkboard.model.Tool;

/**
 *
 * @author  eduardo
 */
public class ViewedKChalk extends javax.swing.JFrame {
    
    Cola colaIN, colaOUT;
    
    boolean conectado;
    ChalkCanvas c;
    ChalkServer cs;
    ChalkClient cc;
    ChalkTransmiter ct;
    String ipServidor;
    int currentFondo;
    
    /**
     * Creates new form ViewedKChalk
     */
    public ViewedKChalk() {
        initComponents();
        
        c = new ChalkCanvas();
        
        this.add(c);
        
        cs = new ChalkServer();
        
        colaIN = new Cola();
        colaOUT = new Cola();

        cs.setColaIN(colaIN);
        cs.setColaOUT(colaOUT);
        
        c.setColaIN(colaIN);
        c.setColaOUT(colaOUT);
        
        //cc.setColaOUT(colaOUT);

        //cs.setCliente(cc);
        cs.setCallBack(this);
        c.setCallBack(this);
        new Thread(cs).start();
        new Thread(c).start();
        //new Thread(cc).start();
        
        this.setTitle("kChalkboard 0.5 - "+cs.devolverIP());
        
        conectado = false;
        
        ipServidor = "192.168.1.4";
    }
    
    private void cambiarFondo() {
       
        DialogoFondo df = new DialogoFondo(this, true);
        
        df.setValorFondo(currentFondo);
        df.setVisible(true);
        if(df.ok()) {
            
            currentFondo = df.getFondo();
            
            dibujarFondo();
        }
    }
    
    private void dibujarFondo() {
    
         c.setFondo(currentFondo);
         
         if(currentFondo==4 || currentFondo == 2)
             colorBlanco.setBackground(new Color(0,0,0));
         else
             colorBlanco.setBackground(new Color(255,255,255));
    }
    
    public void setCurrentFondo(int f) {
        
        currentFondo = f;
    }
    
    public void cambiarBotonConectar(String s, boolean b) {
        
        jButton1.setText(s);
        jButton1.setEnabled(b);
    }
    
    public void setIpServidor(String unaIp) {
    
        ipServidor = unaIp;
    }
    
    private void elegirColor(Color elcolor) {
        
        c.setCurrentColor(elcolor);
        colorActual.setBackground(elcolor);
    }
    
    private void conectar() {
    
        if(cc == null) {
            cc = new ChalkClient();
            ct = new ChalkTransmiter();
        } 
        if (!cc.conectado()) {
            
            DialogoConectar dg = new DialogoConectar(this, true);
            dg.setIp(ipServidor);
            dg.setVisible(true);
            if(dg.ok) {
                cc.setColaIn(colaIN);
                cc.conectar(dg.ip, 60111);
                conectado = true;
                jButton1.setText("Desconectar");
                new Thread(cc).start();
                ct.setColaOUT(colaOUT);
                ct.setCliente(cc.getCliente());
                new Thread(ct).start();
                this.setTitle("kChalkboard 0.5 conectado con : "+ipServidor);
            }
            dg.dispose();
        }               
    }
    
    public void elegirColor() {
    
        Color colorete;
        
        JColorChooser jcc = new JColorChooser();
        jcc.setVisible(true);
        colorete = JColorChooser.showDialog(this,"Colores disponibles", Color.WHITE);
        
        //colorete = jcc.getColor();
        if(colorete != null)
            elegirColor(colorete); 
    }
    
    public void colorLed(Color c) {
        
    
          jLed1.setBackground(c);
    }
    
    public void redimensionar(Dimension d) {
        
        this.setSize(d);
    }
    
    private void desconectar() {
    
        if (cc.conectado()) {
            
            cc.desconectar();
            conectado = false;
            jButton1.setText("Conectar");
        }
                
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        colorActual = new javax.swing.JLabel();
        colorBlanco = new javax.swing.JLabel();
        colorAmarillo = new javax.swing.JLabel();
        colorOcre = new javax.swing.JLabel();
        colorRojo = new javax.swing.JLabel();
        colorVerde = new javax.swing.JLabel();
        colorAzul = new javax.swing.JLabel();
        colorVioleta = new javax.swing.JLabel();
        btnTiza = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnBorrador = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnPincel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnReproducir = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jButton4 = new javax.swing.JButton();
        jLed1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jToolBar1.setRollover(true);
        jToolBar1.setOpaque(false);
        jToolBar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jToolBar1MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jToolBar1MouseEntered(evt);
            }
        });

        colorActual.setBackground(new java.awt.Color(255, 255, 255));
        colorActual.setText(" Color ");
        colorActual.setToolTipText("Muestra el color actual");
        colorActual.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(0, 0, 0), new java.awt.Color(153, 153, 153), java.awt.Color.darkGray, new java.awt.Color(0, 0, 0)));
        colorActual.setOpaque(true);
        colorActual.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                colorActualMouseDragged(evt);
            }
        });
        colorActual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                colorActualMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                colorActualMouseClicked(evt);
            }
        });
        jToolBar1.add(colorActual);

        colorBlanco.setBackground(new java.awt.Color(255, 255, 255));
        colorBlanco.setText(" * ");
        colorBlanco.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        colorBlanco.setOpaque(true);
        colorBlanco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                colorBlancoMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                colorBlancoMouseClicked(evt);
            }
        });
        jToolBar1.add(colorBlanco);

        colorAmarillo.setBackground(new java.awt.Color(255, 255, 0));
        colorAmarillo.setText(" * ");
        colorAmarillo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        colorAmarillo.setOpaque(true);
        colorAmarillo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                colorAmarilloMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                colorAmarilloMouseClicked(evt);
            }
        });
        jToolBar1.add(colorAmarillo);

        colorOcre.setBackground(new java.awt.Color(204, 102, 0));
        colorOcre.setText(" * ");
        colorOcre.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        colorOcre.setOpaque(true);
        colorOcre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                colorOcreMousePressed(evt);
            }
        });
        jToolBar1.add(colorOcre);

        colorRojo.setBackground(new java.awt.Color(153, 0, 0));
        colorRojo.setForeground(new java.awt.Color(255, 255, 255));
        colorRojo.setText(" * ");
        colorRojo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        colorRojo.setOpaque(true);
        colorRojo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                colorRojoMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                colorRojoMouseClicked(evt);
            }
        });
        jToolBar1.add(colorRojo);

        colorVerde.setBackground(new java.awt.Color(0, 153, 0));
        colorVerde.setForeground(new java.awt.Color(255, 255, 255));
        colorVerde.setText(" * ");
        colorVerde.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        colorVerde.setOpaque(true);
        colorVerde.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                colorVerdeMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                colorVerdeMouseClicked(evt);
            }
        });
        jToolBar1.add(colorVerde);

        colorAzul.setBackground(new java.awt.Color(51, 153, 255));
        colorAzul.setText(" * ");
        colorAzul.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        colorAzul.setOpaque(true);
        colorAzul.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                colorAzulMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                colorAzulMouseClicked(evt);
            }
        });
        jToolBar1.add(colorAzul);

        colorVioleta.setBackground(new java.awt.Color(102, 0, 255));
        colorVioleta.setText(" * ");
        colorVioleta.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        colorVioleta.setOpaque(true);
        colorVioleta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                colorVioletaMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                colorVioletaMouseClicked(evt);
            }
        });
        jToolBar1.add(colorVioleta);

        btnTiza.setText("   Tiza   ");
        btnTiza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTizaActionPerformed(evt);
            }
        });
        jToolBar1.add(btnTiza);

        jLabel3.setText(" ");
        jToolBar1.add(jLabel3);

        btnBorrador.setText("Borrador");
        btnBorrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorradorActionPerformed(evt);
            }
        });
        jToolBar1.add(btnBorrador);

        jLabel2.setText(" ");
        jToolBar1.add(jLabel2);

        btnPincel.setText("Pincel");
        btnPincel.setFocusable(false);
        btnPincel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPincel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPincel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPincelActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPincel);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Fondo");
        jLabel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel1.setOpaque(true);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel1MouseReleased(evt);
            }
        });
        jToolBar1.add(jLabel1);

        btnReproducir.setText("Reproducir");
        btnReproducir.setFocusable(false);
        btnReproducir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReproducir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnReproducir);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jSeparator1.setOpaque(true);
        jToolBar1.add(jSeparator1);

        jButton4.setText("Borrar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        jLed1.setText("   ");
        jLed1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToolBar1.add(jLed1);

        jButton1.setText("Conectar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-720)/2, (screenSize.height-540)/2, 720, 540);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
// TODO add your handling code here:
        
        if(this.isVisible())
            c.redimensionarAhora(this.getWidth(), this.getHeight());
    }//GEN-LAST:event_formComponentResized

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
// TODO add your handling code here:
        if(c!=null) {
             c.redimensionar((int)this.getSize().getWidth(), (int)this.getSize().getHeight());
        }
    }//GEN-LAST:event_formWindowOpened

    private void jLabel1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseReleased
// TODO add your handling code here:
        
        cambiarFondo();
    }//GEN-LAST:event_jLabel1MouseReleased

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

    }//GEN-LAST:event_formWindowActivated

    private void colorActualMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colorActualMouseReleased
// TODO add your handling code here:
        elegirColor();
    }//GEN-LAST:event_colorActualMouseReleased

    private void colorActualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colorActualMouseClicked
// TODO add your handling code here:
        
     //   elegirColor();
    }//GEN-LAST:event_colorActualMouseClicked

    private void colorActualMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colorActualMouseDragged
// TODO add your handling code here:
    }//GEN-LAST:event_colorActualMouseDragged

    private void btnBorradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorradorActionPerformed
// TODO add your handling code here:
        
                   c.setHerramienta(new Tool(2));
    }//GEN-LAST:event_btnBorradorActionPerformed

    private void btnTizaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTizaActionPerformed
// TODO add your handling code here:

           c.setHerramienta(new Tool(1));
    }//GEN-LAST:event_btnTizaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
        if (!conectado) 
           conectar();
        else
           desconectar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void colorBlancoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colorBlancoMousePressed
// TODO add your handling code here:
        elegirColor(colorBlanco.getBackground());
    }//GEN-LAST:event_colorBlancoMousePressed

    private void colorAmarilloMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colorAmarilloMousePressed
// TODO add your handling code here:
        elegirColor(colorAmarillo.getBackground());
    }//GEN-LAST:event_colorAmarilloMousePressed

    private void colorRojoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colorRojoMousePressed
// TODO add your handling code here:
        elegirColor(colorRojo.getBackground());
    }//GEN-LAST:event_colorRojoMousePressed

    private void colorVerdeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colorVerdeMousePressed
// TODO add your handling code here:
        elegirColor(colorVerde.getBackground());
    }//GEN-LAST:event_colorVerdeMousePressed

    private void colorAzulMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colorAzulMousePressed
// TODO add your handling code here:
        elegirColor(colorAzul.getBackground());
    }//GEN-LAST:event_colorAzulMousePressed

    private void colorVioletaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colorVioletaMousePressed
// TODO add your handling code here:
        elegirColor(colorVioleta.getBackground());
    }//GEN-LAST:event_colorVioletaMousePressed

    private void colorVioletaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colorVioletaMouseClicked
// TODO add your handling code here:
        elegirColor(colorVioleta.getBackground());
    }//GEN-LAST:event_colorVioletaMouseClicked

    private void colorOcreMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colorOcreMousePressed
// TODO add your handling code here:
        elegirColor(colorOcre.getBackground());
    }//GEN-LAST:event_colorOcreMousePressed

    private void jToolBar1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToolBar1MouseExited
// TODO add your handling code here:
        
    }//GEN-LAST:event_jToolBar1MouseExited

    private void jToolBar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToolBar1MouseEntered
// TODO add your handling code here:
        
    }//GEN-LAST:event_jToolBar1MouseEntered

    private void colorAmarilloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colorAmarilloMouseClicked
// TODO add your handling code here:
        elegirColor(colorAmarillo.getBackground());
    }//GEN-LAST:event_colorAmarilloMouseClicked

    private void colorAzulMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colorAzulMouseClicked
// TODO add your handling code here:
        elegirColor(colorAzul.getBackground());
    }//GEN-LAST:event_colorAzulMouseClicked

    private void colorBlancoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colorBlancoMouseClicked
// TODO add your handling code here:
        
        elegirColor(colorBlanco.getBackground());
    }//GEN-LAST:event_colorBlancoMouseClicked

    private void colorVerdeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colorVerdeMouseClicked
// TODO add your handling code here:
         elegirColor(colorVerde.getBackground());
    }//GEN-LAST:event_colorVerdeMouseClicked

    private void colorRojoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_colorRojoMouseClicked
// TODO add your handling code here:
        
        elegirColor(colorRojo.getBackground());
    }//GEN-LAST:event_colorRojoMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
// TODO add your handling code here:
        
        c.borrarPizarra();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnPincelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPincelActionPerformed
        // TODO add your handling code here:

        c.setHerramienta(new Tool(3));
    }//GEN-LAST:event_btnPincelActionPerformed
    
    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrador;
    private javax.swing.JButton btnPincel;
    private javax.swing.JButton btnReproducir;
    private javax.swing.JButton btnTiza;
    private javax.swing.JLabel colorActual;
    private javax.swing.JLabel colorAmarillo;
    private javax.swing.JLabel colorAzul;
    private javax.swing.JLabel colorBlanco;
    private javax.swing.JLabel colorOcre;
    private javax.swing.JLabel colorRojo;
    private javax.swing.JLabel colorVerde;
    private javax.swing.JLabel colorVioleta;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLed1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
    
}
