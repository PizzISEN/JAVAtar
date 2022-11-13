import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
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
import java.awt.BasicStroke;
import java.awt.Image;
import java.awt.geom.Line2D;

import javax.swing.JButton;
import java.awt.event.*;  

import java.security.SecureRandom;

public class printGrid 
    {
    
        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI(); 
                }
            });
        }

        //Préparation de l'interface graphique et affichage
        private static void createAndShowGUI() {
            JFrame f = new JFrame("Javatar");
            int[] s= {15,15};
            Carte c = new Carte(s);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setLocationRelativeTo(null);
            f.setResizable(false);
            f.add(new MyPanel(c));
            f.pack();
            f.setVisible(true);
        }
    }

class MyPanel extends JPanel {
    private Carte c;
    private volatile int posX=0,posY=0;
    private int widthSize=800,heightSize=800;
    private int offset=80;
    private int nRows=0,nColumns=0;
    private int fps=60;
    private int lineThickness=5;
    private int newWidth=widthSize-2*offset,newHeight=heightSize-2*offset;// On définit newWidth et newHeight qui sont les nouvelles largeur et hauteur du carré sur lequel on va vraiment dessiner le terrain(sans les offset)
    private SecureRandom rdm= new SecureRandom();
  
    private volatile int[][] grid=new int[nRows][nColumns];

    //Création des références pour les images liés à chacune des tribus
    private BufferedImage image=null;
    private Image fire_tribe_resultingImage=null;
    private Image water_tribe_resultingImage=null;
    private Image air_tribe_resultingImage=null;
    private Image earth_tribe_resultingImage=null;
    private Image stone_resultingImage=null;

    
    public MyPanel(Carte carte) {

        //Configuration et création de la fenêtre dans l'interface graphique 
        this.c=carte;
        nRows=c.size[0];
        nColumns=c.size[1];
        setBorder(BorderFactory.createLineBorder(Color.black));
        personnalSetup();
        //setupGrid();

        //Démarrage de la boucle d'affichage 
        processing.start();
        //Démarrage de la boucle de logique de la simulation
        //gameLoop.start();

        
    }

    //Fonction qui permet de configurer la simulation si l'utilisateur le veut . Sinon on prend les paramètres par défauts
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
        //Une fois que le jeu a été configuré , on peut charger les images relativement à la taille réelle de la fenêtre
        try {
            image=ImageIO.read(new File("sprites/fire_tribe.png"));
            fire_tribe_resultingImage= image.getScaledInstance((newWidth*4)/(nColumns*5), (newHeight*4)/(nRows*5), Image.SCALE_DEFAULT);
    
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            image=ImageIO.read(new File("sprites/earth_tribe.png"));
            earth_tribe_resultingImage= image.getScaledInstance((newWidth*4)/(nColumns*5), (newHeight*4)/(nRows*5), Image.SCALE_DEFAULT);
    
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            image=ImageIO.read(new File("sprites/water_tribe.png"));
            water_tribe_resultingImage= image.getScaledInstance((newWidth*4)/(nColumns*5), (newHeight*4)/(nRows*5), Image.SCALE_DEFAULT);
    
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            image=ImageIO.read(new File("sprites/air_tribe.png"));
            air_tribe_resultingImage= image.getScaledInstance((newWidth*4)/(nColumns*5), (newHeight*4)/(nRows*5), Image.SCALE_DEFAULT);
    
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            image=ImageIO.read(new File("sprites/stone.png"));
            stone_resultingImage= image.getScaledInstance((newWidth*4)/(nColumns*5), (newHeight*4)/(nRows*5), Image.SCALE_DEFAULT);
    
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    };
    //Initialise toutes les valeurs de grid à 0 
    private void setupGrid(){
        for (int i=0;i<nRows;i++){
            for (int j=0;j<nColumns;j++){
                grid[i][j]=0;
            }
        }

    }

    
    
    
    public Dimension getPreferredSize() {
        return new Dimension(widthSize,heightSize);
    }

    //Fonction qui affiche l'état de la simulation contenu dans grid 
    public void drawGrid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int x1,x2,y1,y2;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON); 

        //Affichage de la grille
        g2d.setStroke(new BasicStroke(lineThickness));
        for (int i =0;i<=nRows;i++){
            x1=offset;
            y1=offset+newHeight*i/nRows;
            x2=offset+newWidth;
            y2=offset+newHeight*i/nRows;
            g2d.draw(new Line2D.Float(x1,y1,x2,y2));
            
        }
        for(int j=0;j<=nColumns;j++){
            x1=offset +newHeight*j/nRows;
            y1=offset;
            x2=offset+newWidth*j/nColumns;
            y2=offset+newHeight;
            g2d.draw(new Line2D.Float(x1,y1,x2,y2));
        }
        
        //affiche l'état du tableau actuel
        for (int j=0;j<nRows;j++){
            for (int i=0;i<nColumns;i++){
               //Modifier les images avec une couleur translucide
                int alpha = 65; // 50% transparent
                Color myColour ;
                
                if(c.carte.get(j).get(i).getType()=="A"){
                    myColour= new Color(235, 235, 235, alpha);
                    g.setColor(myColour);
                    g.fillRect( offset+(newWidth*i)/nColumns, offset+(newHeight*j)/nRows,newWidth/nColumns, newHeight/nRows);
                    
                }
                if(c.carte.get(j).get(i).getType()=="E"){
                    myColour= new Color(0, 137, 235, alpha);
                    g.setColor(myColour);
                    g.fillRect( offset+(newWidth*i)/nColumns, offset+(newHeight*j)/nRows,newWidth/nColumns, newHeight/nRows);
                   
                }
                if(c.carte.get(j).get(i).getType()=="T"){
                    myColour= new Color(102, 204, 0, alpha);
                    g.setColor(myColour);
                    g.fillRect( offset+(newWidth*i)/nColumns, offset+(newHeight*j)/nRows,newWidth/nColumns, newHeight/nRows);
                   
                }
                if(c.carte.get(j).get(i).getType()=="F"){
                    myColour= new Color(255, 100, 100, alpha);
                    g.setColor(myColour);
                    g.fillRect( offset+(newWidth*i)/nColumns, offset+(newHeight*j)/nRows,newWidth/nColumns, newHeight/nRows);
                  
                }
                
                //Afficher une image lié au type du personnage sur la cage
                if(c.carte.get(j).get(i).getType()=="A"){
                    
                    g.drawImage(air_tribe_resultingImage, offset+newWidth/(nColumns*10)+(newWidth*i)/nColumns, offset+newHeight/(nRows*10)+(newHeight*j)/nRows, null);
                }
                if(c.carte.get(j).get(i).getType()=="E"){
                   
                    g.drawImage(water_tribe_resultingImage, offset+newWidth/(nColumns*10)+(newWidth*i)/nColumns, offset+newHeight/(nRows*10)+(newHeight*j)/nRows, null);
                }
                if(c.carte.get(j).get(i).getType()=="T"){
                    
                    g.drawImage(earth_tribe_resultingImage, offset+newWidth/(nColumns*10)+(newWidth*i)/nColumns, offset+newHeight/(nRows*10)+(newHeight*j)/nRows, null);
                }
                if(c.carte.get(j).get(i).getType()=="F"){
                 
                    g.drawImage(fire_tribe_resultingImage, offset+newWidth/(nColumns*10)+(newWidth*i)/nColumns, offset+newHeight/(nRows*10)+(newHeight*j)/nRows, null);
                }
                if(c.carte.get(j).get(i).getType()=="O"){
                 
                    g.drawImage(stone_resultingImage, offset+newWidth/(nColumns*10)+(newWidth*i)/nColumns, offset+newHeight/(nRows*10)+(newHeight*j)/nRows, null);
                }
            }
        }
 
    }

    //Thread qui s'occupe de modifier aléatoirement la position des cercles dans le tableau 
    private Thread gameLoop = new Thread(()->{
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
            //Modifie aléatoirement chacun des éléments du tableau ( 1 à 5 veut dire qu'il faut afficher une icone , 0 qu'il ne faut rien afficher)
            
            int temp=0;
            for (int i=0;i<nRows;i++){
                for (int j=0;j<nColumns;j++){
                    temp=rdm.nextInt(6);
                    
                    grid[i][j]=temp;
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
            }

        }
    });
   
    //Thread qui s'occupe d'actualiser la fenêtre graphique 
    private Thread processing = new Thread(() -> {//repeint l'écran toutes les 1000/fps ms 
        while (true) {
            
            this.repaint();
            try {
                Thread.sleep(1000/fps);
            } catch (InterruptedException ex) {
            }
        }
    });
   
     
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        try{
            BufferedImage bufferedImage = ImageIO.read(new File("sprites/fond_écran.png"));
            Image resultingImage = bufferedImage.getScaledInstance(widthSize, heightSize, Image.SCALE_DEFAULT);
            
            
            g.drawImage(resultingImage, 0, 0, null);     
            
            /* 
            JButton b=new JButton("Click Here");  
            b.setBounds(50,100,95,30);  
            b.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                          
                    }   });*/
        }
        catch (IOException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
        
        
        
       
     
        
        drawGrid(g);
    } 
}
