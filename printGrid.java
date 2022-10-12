import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

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
    private int posX=0,posY=0;
    private int widthSize=480,heightSize=480;
    private int offset=40;
    private int nRows=10,nColumns=10;
    private int fps=1;
    public MyPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        gameLoop.start();
    }
    private Thread gameLoop = new Thread(() -> {
        while (true) {
            
            this.repaint();
            try {
                Thread.sleep(1000/fps);
            } catch (InterruptedException ex) {
            }
            posX++;
            if(posX>nColumns){
                posY++;
                posX=0;
            }
            if (posY>nRows){
                posY=0;
            }

            
        }
    });
    public Dimension getPreferredSize() {
        return new Dimension(widthSize,heightSize);
    }
    void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int x1,x2,y1,y2;
        int newWidth=widthSize-2*offset,newHeight=heightSize-2*offset;// On définit newWidth et newHeight qui sont est la nouvelle largeur et hauteur du carré sur lequel on va vraiment dessiner le terrain(sans les offset)
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON); 

        for (int i =0;i<=nRows+1;i++){
            x1=offset;
            y1=offset+newHeight*i/(nRows+1);
            x2=offset+newWidth;
            y2=offset+newHeight*i/(nRows+1);
            g2d.drawLine(x1,y1,x2,y2);
            
        }
        for(int j=0;j<=nColumns+1;j++){
            x1=offset +newHeight*j/(nRows+1);
            y1=offset;
            x2=offset+newWidth*j/(nColumns+1);
            y2=offset+newHeight;
            g2d.drawLine(x1,y1,x2,y2);
        }
        g2d.fillOval(offset+newHeight*posX/(nRows+1),offset+newHeight*posY/(nRows+1),newWidth/(nColumns+1),newHeight/(nRows+1));
        //System.println(offset+newWidth/(2*nColumns),offset+newHeight/(2*nRows),newWidth/nColumns,newHeight/nRows)
        //g2d.drawOval(150,150,150,150);
      
      //faire un tableau contenant les coordonnées de chaque cases
      //afficher le tableau relativement à la grille que l'on a créé
      //essayer de faire bouger un objet pour s'assurer que ça fonctionne bien
 
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);      
        drawLines(g);
    }  
}
