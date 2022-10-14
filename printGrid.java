import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.util.List;
import java.util.ArrayList;
import java.security.SecureRandom;

public class printGrid {
   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }

    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
        SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.add(new MyPanel());
        f.pack();
        f.setVisible(true);
    }
}

class MyPanel extends JPanel {
    private volatile int posX=0,posY=0;
    private int widthSize=480,heightSize=480;
    private int offset=40;
    private int nRows=10,nColumns=10;
    private int fps=60;
    private SecureRandom rdm= new SecureRandom();
    private volatile int[][] grid=new int[nRows][nColumns];
    public MyPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        setupGrid();
        gameLoop.start();
        processing.start();
    }
    private void setupGrid(){
        for (int i=0;i<nRows;i++){
            for (int j=0;j<nColumns;j++){
                grid[i][j]=0;
            }
        }

    }
    private Thread processing = new Thread(()->{
        while (true){
            //Bouge la position du cercle toutes les deux secondes
            posX++;
            if(posX==nColumns){
                posY++;
                posX=0;
            }
            if (posY==nRows){
                posY=0;
            }
            //Modifie aléatoirement chacun des éléments du tableau ( 1 veut dire qu'il faut afficher un cercle , 0 qu'il ne faut rien afficher)
            
            int temp=0;
            for (int i=0;i<nRows;i++){
                for (int j=0;j<nColumns;j++){
                    temp=rdm.nextInt(0,2);
                    
                    grid[i][j]=temp;
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
            }

        }
    });
    private Thread gameLoop = new Thread(() -> {//repeint l'écran toutes les 1000/fps ms 
        while (true) {
            
            this.repaint();
            try {
                Thread.sleep(1000/fps);
            } catch (InterruptedException ex) {
            }
           
        
        }
    });
    public Dimension getPreferredSize() {
        return new Dimension(widthSize,heightSize);
    }
    void drawGrid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int x1,x2,y1,y2;
        int newWidth=widthSize-2*offset,newHeight=heightSize-2*offset;// On définit newWidth et newHeight qui sont est la nouvelle largeur et hauteur du carré sur lequel on va vraiment dessiner le terrain(sans les offset)
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON); 

        for (int i =0;i<=nRows;i++){
            x1=offset;
            y1=offset+newHeight*i/nRows;
            x2=offset+newWidth;
            y2=offset+newHeight*i/nRows;
            g2d.drawLine(x1,y1,x2,y2);
            
        }
        for(int j=0;j<=nColumns;j++){
            x1=offset +newHeight*j/nRows;
            y1=offset;
            x2=offset+newWidth*j/nColumns;
            y2=offset+newHeight;
            g2d.drawLine(x1,y1,x2,y2);
        }
        //affiche le cerle
        //g2d.fillOval(offset+newHeight*posX/nRows+2,offset+newHeight*posY/nRows+2,newWidth/nColumns-4,newHeight/nRows-4);
        //affiche l'état du tableau actuel
        for (int j=0;j<nRows;j++){
            for (int i=0;i<nColumns;i++){
                if(grid[i][j]==1){
                    g2d.fillOval(offset+newHeight*j/nColumns+2,offset+newHeight*i/nRows+2,newWidth/nColumns-4,newHeight/nRows-4);
                }
                
            }
        }
      
      //faire un tableau contenant les coordonnées de chaque cases
      //afficher le tableau relativement à la grille que l'on a créé
      //essayer de faire bouger un objet pour s'assurer que ça fonctionne bien
 
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);      
        drawGrid(g);
    }  
}
