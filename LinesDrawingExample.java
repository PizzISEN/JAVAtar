 
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
    private int widthSize=480,heightSize=200;
    private int nRows=10,nColumns=10;
    public LinesDrawingExample() {
        super("Lines Drawing Demo");
 
        setSize(widthSize, heightSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(new Point(0, 0));
        setLocationRelativeTo(null);
        this.gameLoop.start();
        
    }
    private Thread gameLoop = new Thread(() -> {
        while (true) {
            this.i++;
            this.repaint();
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException ex) {
            }
        }
    });
    void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
 
        //g2d.drawLine(120+i, 50-i, 360+i, 50-i);
        for (int i =0;i<=nRows;i++){
            
            g2d.drawLine(widthSize*i/(nRows+1),0,widthSize*i/(nRows+1),heightSize);
        }
        for(int j=0;j<=nColumns;j++){
            g2d.drawLine(0,heightSize*j/(nColumns+1),widthSize,heightSize*j/(nColumns+1));
        }
        
        //g2d.draw(new Line2D.Double(59.2d, 99.8d, 419.1d, 99.8d));
 
        //g2d.draw(new Line2D.Float(21.50f, 132.50f, 459.50f, 132.50f));
 
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
