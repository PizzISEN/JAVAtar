 
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
 
/**
 * This program demonstrates how to draw lines using Graphics2D object.
 * @author www.codejava.net
 *
 */
public class LinesDrawingExample extends JFrame {
    private int i=0;
    private int widthSize=480,heightSize=480;
    private int offset=50;
    private int nRows=10,nColumns=10;
    public LinesDrawingExample() {
        super("Lines Drawing Demo");
 
        setSize(widthSize, heightSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.gameLoop.start();
        
    }
    private Thread gameLoop = new Thread(() -> {
        while (true) {
            //this.i++;
            this.repaint();
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException ex) {
            }
        }
    });
    void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int x1,x2,y1,y2;
        int newWidth=widthSize-2*offset,newHeight=heightSize-2*offset;// On définit newWidth et newHeight qui sont est la nouvelle largeur et hauteur du carré sur lequel on va vraiment dessiner le terrain(sans les offset)
    

        for (int i =0;i<=nRows+1;i++){
            x1=offset;
            y1=offset+newHeight*i/(nRows+1);
            x2=offset+newWidth;
            y2=offset+newHeight*i/(nRows+1);
            g2d.drawLine(x1,y1,x2,y2);
            
        }
        for(int j=0;j<=nColumns+1;j++){
            x1=offset+newWidth*j/(nColumns+1);
            y1=offset;
            x2=offset+newWidth*j/(nColumns+1);
            y2=offset+newHeight;
            g2d.drawLine(x1,y1,x2,y2);
        }
        g2d.drawOval(offset+newWidth/2*nColumns,offset+newHeight/2*nRows,newWidth/nColumns,newHeight/nRows);
        //System.println(offset+newWidth/2*nColumns,offset+newHeight/2*nRows,newWidth/nColumns,newHeight/nRows)
        //g2d.drawOval(150,150,150,150);
      
      //faire un tableau contenant les coordonnées de chaque cases
      //afficher le tableau relativement à la grille que l'on a créé
      //essayer de faire bouger un objet pour s'assurer que ça fonctionne bien
 
    }
 
    public void paint(Graphics g) {
        super.paint(g);
        drawLines(g);
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LinesDrawingExample().setVisible(true);
            }
        });
    }
}
