import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


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
        JFrame f = new JFrame("Javatar");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
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
    public static final AffineTransform IDENTITY = new AffineTransform();
    private volatile int[][] grid=new int[nRows][nColumns];
    public MyPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        personnalSetup();
        setupGrid();
        gameLoop.start();
        processing.start();
    }
    private void personnalSetup(){
        System.out.println("Voulez-vous configurer la fenêtre de la simulation? (Y)es/(N)o");
        String tmp=System.console().readLine();
        while(!tmp.equals("Yes")&&!tmp.equals("No")&&!tmp.equals("Y")&&!tmp.equals("N")&&!tmp.equals(""))
        {
            System.out.println("Voulez-vous configurer la fenêtre de la simulation? (Y)es/(N)o");
            tmp=System.console().readLine();
            
        }
        if (tmp.equals("Yes")||tmp.equals("Y")){
            System.out.println("Nombre de lignes?");
            nRows=Integer.parseInt(System.console().readLine());
            System.out.println("Nombre de Colonnes?");
            nColumns=Integer.parseInt(System.console().readLine());
            grid=new int[nRows][nColumns];

            System.out.println("Longueur de la fenêtre (en px)?");
            widthSize=Integer.parseInt(System.console().readLine());
            System.out.println("Hauteur de la fenêtre (en px)?");
            heightSize=Integer.parseInt(System.console().readLine());
            System.out.println("offset (en px)?");
            offset=Integer.parseInt(System.console().readLine());
        }

    };
    

    public static void drawSprite(Graphics2D gr, BufferedImage tilesheet,
            Rectangle source, float x, float y, float width, float height) {
        int halfWidth = (int) (width * 0.5);
        int halfHeight = (int) (height * 0.5);

        gr.translate(x, y);//from   ww  w.java2  s .co m
        gr.drawImage(tilesheet, 0, 0, (int)width,
                (int)height, source.x, source.y, source.x + source.width,
                source.y + source.height, null);
        gr.setTransform(IDENTITY);
    }

    public static void drawSprite(Graphics2D gr, BufferedImage tilesheet,
            Rectangle source, float x, float y, float width, float height,
            float radians) {
        int halfWidth = (int) (width * 0.5);
        int halfHeight = (int) (height * 0.5);

        gr.translate(x, y);
        gr.rotate(radians);
        gr.drawImage(tilesheet, -halfWidth, -halfHeight, halfWidth,
                halfHeight, source.x, source.y, source.x + source.width,
                source.y + source.height, null);
        gr.setTransform(IDENTITY);
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
        try{
            BufferedImage bufferedImage = ImageIO.read(new File("sprites/symbole_air.jpg"));
            Image resultingImage = bufferedImage.getScaledInstance(widthSize, heightSize, Image.SCALE_DEFAULT);
            BufferedImage outputImage = new BufferedImage(widthSize, heightSize, BufferedImage.TYPE_INT_RGB);
            outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
            
            drawSprite((Graphics2D)g, outputImage, getBounds(), 0, 0, widthSize, heightSize);     
        }
        catch (IOException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
        drawGrid(g);
    }  
}
