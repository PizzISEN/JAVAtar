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

import java.util.Collections.*;


import java.util.List;
import java.security.SecureRandom;
import java.util.ArrayList;
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
            
            // c.map[0][0] = Javatar_air.getInstance();
            // c.map[0][0] = Javatar_eau.getInstance();
            // c.map[0][0] = Javatar_feu.getInstance();
            // c.map[0][0] = Javatar_terre.getInstance();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setLocationRelativeTo(null);
            f.setResizable(false);
            f.add(new MyPanel());
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
    private Image fire_master_resultingImage=null;
    private Image water_master_resultingImage=null;
    private Image air_master_resultingImage=null;
    private Image earth_master_resultingImage=null;
    private Image stone_resultingImage=null;

    
    public MyPanel() {

        //Configuration et création de la fenêtre dans l'interface graphique 
        
        int[] s =  personnalSetup();
        c = new Carte(s);
        Javatar_air.getInstance().setPos(0, 0);
        Javatar_eau.getInstance().setPos(0, s[1]-1);
        Javatar_feu.getInstance().setPos(s[0]-1, s[1]-1);
        Javatar_terre.getInstance().setPos(s[0]-1, 0);
        
        setBorder(BorderFactory.createLineBorder(Color.black));
       
        //setupGrid();

        //Démarrage de la boucle d'affichage 
        processing.start();
        //Démarrage de la boucle de logique de la simulation
        gameLoop.start();
        //c.matrice(new Coord(5,5),new Coord(8,9));

        
    }

    //Fonction qui permet de configurer la simulation si l'utilisateur le veut . Sinon on prend les paramètres par défauts
    private int[] personnalSetup(){
        int[] size={10,10};
        System.out.println("Voulez-vous configurer la fenêtre de la simulation? (Y)es/(N)o");
        String tmp=System.console().readLine();
        while(!tmp.equals("Yes")&&!tmp.equals("No")&&!tmp.equals("Y")&&!tmp.equals("N")&&!tmp.equals(""))
        {
            System.out.println("Voulez-vous configurer la fenêtre de la simulation? (Y)es/(N)o");
            tmp=System.console().readLine();
            
        }
        if (tmp.equals("Yes")||tmp.equals("Y")){
            System.out.println("Nombre de lignes?");
            String res=System.console().readLine();
            
            if(res.length()!=0){
                size[0]=Integer.parseInt(res);
            }
            
            System.out.println("Nombre de Colonnes?");
            res=System.console().readLine();
            if(res.length()!=0){
                size[1]=Integer.parseInt(res);
            }
            
            System.out.println("Longueur de la fenêtre (en px)?");
            res=System.console().readLine();
            System.out.println(res);
            if(res.length()!=0){
                widthSize=Integer.parseInt(res);
            }
            System.out.println("Hauteur de la fenêtre (en px)?");
            res=System.console().readLine();
            if(res.length()!=0){
                heightSize=Integer.parseInt(res);
            }
            System.out.println("offset (en px)?");
            res=System.console().readLine();
            if(res.length()!=0){
                offset=Integer.parseInt(res);
            }
            
        }
        nRows=size[0];
        nColumns=size[1];
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
            image=ImageIO.read(new File("sprites/fire_master.png"));
            fire_master_resultingImage= image.getScaledInstance((newWidth*4)/(nColumns*5), (newHeight*4)/(nRows*5), Image.SCALE_DEFAULT);
    
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            image=ImageIO.read(new File("sprites/earth_master.png"));
            earth_master_resultingImage= image.getScaledInstance((newWidth*4)/(nColumns*5), (newHeight*4)/(nRows*5), Image.SCALE_DEFAULT);
    
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            image=ImageIO.read(new File("sprites/water_master.png"));
            water_master_resultingImage= image.getScaledInstance((newWidth*4)/(nColumns*5), (newHeight*4)/(nRows*5), Image.SCALE_DEFAULT);
    
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            image=ImageIO.read(new File("sprites/air_master.png"));
            air_master_resultingImage= image.getScaledInstance((newWidth*4)/(nColumns*5), (newHeight*4)/(nRows*5), Image.SCALE_DEFAULT);
    
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
        return(size);
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
            x1=offset +newWidth*j/nColumns;
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
                if(c.carte.get(j).get(i).getType()=="O"){
                     
                    g.drawImage(stone_resultingImage, offset+newWidth/(nColumns*10)+(newWidth*i)/nColumns, offset+newHeight/(nRows*10)+(newHeight*j)/nRows, null);
                }
                
                //Afficher une image lié au type du personnage sur la cage
                if(c.carte.get(j).get(i).personnage!=null){
                    if(c.carte.get(j).get(i).personnage.getEquipe()=="A"){
                        
                        g.drawImage(air_tribe_resultingImage, offset+newWidth/(nColumns*10)+(newWidth*i)/nColumns, offset+newHeight/(nRows*10)+(newHeight*j)/nRows, null);
                        
                    }
                    if(c.carte.get(j).get(i).personnage.getEquipe()=="E"){
                       
                        g.drawImage(water_tribe_resultingImage, offset+newWidth/(nColumns*10)+(newWidth*i)/nColumns, offset+newHeight/(nRows*10)+(newHeight*j)/nRows, null);
                    }
                    if(c.carte.get(j).get(i).personnage.getEquipe()=="T"){
                        
                        g.drawImage(earth_tribe_resultingImage, offset+newWidth/(nColumns*10)+(newWidth*i)/nColumns, offset+newHeight/(nRows*10)+(newHeight*j)/nRows, null);
                    }
                    if(c.carte.get(j).get(i).personnage.getEquipe()=="F"){
                     
                        g.drawImage(fire_tribe_resultingImage, offset+newWidth/(nColumns*10)+(newWidth*i)/nColumns, offset+newHeight/(nRows*10)+(newHeight*j)/nRows, null);
                    }
                   
                }
                
               
                if(c.carte.get(j).get(i).getType()=="Ja"){
                    myColour= new Color(235, 235, 235, alpha);
                    g.setColor(myColour);
                    g.fillRect( offset+(newWidth*i)/nColumns, offset+(newHeight*j)/nRows,newWidth/nColumns, newHeight/nRows);
                    g.drawImage(air_master_resultingImage, offset+newWidth/(nColumns*10)+(newWidth*i)/nColumns, offset+newHeight/(nRows*10)+(newHeight*j)/nRows, null);
                }
                if(c.carte.get(j).get(i).getType()=="Je"){
                    myColour= new Color(0, 137, 235, alpha);
                    g.setColor(myColour);
                    g.fillRect( offset+(newWidth*i)/nColumns, offset+(newHeight*j)/nRows,newWidth/nColumns, newHeight/nRows);
                    g.drawImage(water_master_resultingImage, offset+newWidth/(nColumns*10)+(newWidth*i)/nColumns, offset+newHeight/(nRows*10)+(newHeight*j)/nRows, null);
                }
                if(c.carte.get(j).get(i).getType()=="Jt"){
                    myColour= new Color(102, 204, 0, alpha);
                    g.setColor(myColour);
                    g.fillRect( offset+(newWidth*i)/nColumns, offset+(newHeight*j)/nRows,newWidth/nColumns, newHeight/nRows);
                    g.drawImage(earth_master_resultingImage, offset+newWidth/(nColumns*10)+(newWidth*i)/nColumns, offset+newHeight/(nRows*10)+(newHeight*j)/nRows, null);
                }
                if(c.carte.get(j).get(i).getType()=="Jf"){
                    myColour= new Color(255, 100, 100, alpha);
                    g.setColor(myColour);
                    g.fillRect( offset+(newWidth*i)/nColumns, offset+(newHeight*j)/nRows,newWidth/nColumns, newHeight/nRows);
                    g.drawImage(fire_master_resultingImage, offset+newWidth/(nColumns*10)+(newWidth*i)/nColumns, offset+newHeight/(nRows*10)+(newHeight*j)/nRows, null);
                }
                
                 
            }
        }
 
    }

    //Thread qui s'occupe de modifier aléatoirement la position des cercles dans le tableau 
    private Thread gameLoop = new Thread(()->{
        while (true){
            ArrayList<Integer> tabIndex=new ArrayList<Integer>();
            if(Javatar_air.getInstance().win()){
                System.out.println("Bravo la tribu de l'air tu as gagné !");
                break;
            }
            if(Javatar_terre.getInstance().win()){
                System.out.println("Bravo la tribu de la terre tu as gagné !");
                break;
            }
            if(Javatar_eau.getInstance().win()){
                System.out.println("Bravo la tribu de l'eau tu as gagné !");
                break;
            }
            if(Javatar_feu.getInstance().win()){
                System.out.println("Bravo la tribu du feu tu as gagné !");
                break;
            }
            //Tableau d'index aléatoirement mélangé
            for (int i=0;i<c.tabPerso.size();i++){
                if(c.tabPerso.get(i).getEnergie()==0 || c.tabPerso.get(i).estVivant() == false)
                {
                    if( c.tabPerso.get(i).estVivant()){
                        c.carte.get(c.tabPerso.get(i).getPos().getX()).get(c.tabPerso.get(i).getPos().getY()).personnage = null;
                        c.carte.get(c.tabPerso.get(i).getPos().getX()).get(c.tabPerso.get(i).getPos().getY()).type = "O";
                        c.tabPerso.get(i).mort();
                    }
                }
                else{
                    tabIndex.add(i);
                }
            }
            if(tabIndex.size()==0){
                int [] nbMessages = new int [4];
                nbMessages[0]=Javatar_air.getInstance().GetNbMessage();
                nbMessages[1]=Javatar_eau.getInstance().GetNbMessage();
                nbMessages[2]=Javatar_terre.getInstance().GetNbMessage();
                nbMessages[3]=Javatar_feu.getInstance().GetNbMessage();
                // Initialize maximum element
                int max = nbMessages[0];
                int i=0;
                
                // Traverse array elements from second and
                // compare every element with current max
                for (int o = 1; o < nbMessages.length; o++){
                    if (nbMessages[o] > max){
                        max = nbMessages[o];
                        i=o;
                    }
                }

                switch (i){
                    case 0:
                        System.out.println("Bravo la tribu de l'air tu as gagné !");
                         break;
                    case 1:
                        System.out.println("Bravo la tribu de la terre tu as gagné !");
                         break;
                    case 2:
                        System.out.println("Bravo la tribu de l'eau tu as gagné !");    
                         break;
                    case 3:
                        System.out.println("Bravo la tribu du feu tu as gagné !");
                         break;
                }
            }
            java.util.Collections.shuffle(tabIndex,rdm);
            
                
           
            //Prendre chacun des indivdus correspondant aux indexs mélangés
            for (int index : tabIndex){
                Humain h = c.tabPerso.get(index);
                //System.out.println("TAB INDEX SIZE: "+ tabIndex.size());
                //System.out.println("INDEX: "+ index);
                //System.out.println("\n");
                System.out.println("HUMAIN QUI JOUE: " + h);

                //Si PE <=20%
                if(h.getEnergie()<20) 
                {
                    ArrayList<Coord> deplacement=new ArrayList<Coord>();
                    //appel de la fonction move vers la safezone
                    switch (c.tabPerso.get(index).getEquipe()){
                        case "A":
                             deplacement.add(c.matrice(h.getPos(),new Coord(0,0)));
                             break;
                        case "E":
                             deplacement.add(c.matrice(h.getPos(),new Coord(c.size[0]-1,0)));
                             break;
                        case "T":
                             deplacement.add(c.matrice(h.getPos(),new Coord(0,c.size[1]-1)));
                             break;
                        case "F":
                             deplacement.add(c.matrice(h.getPos(),new Coord(c.size[0]-1,c.size[1]-1)));
                             break;
                    }
                    c.carte.get(h.getPos().getX()).get(h.getPos().getY()).personnage = null;
                    h.seDeplacer(deplacement);
                    c.carte.get(deplacement.get(0).getX()).get(deplacement.get(0).getY()).personnage=h;
                } 
                else 
                {
                    //Si dans sa SafeZone
                    if(c.carte.get(h.getPos().getX()).get(h.getPos().getY()).type == h.getEquipe()){
                        System.out.println("Dans la safe zone");
                        h.setEnergie(100);
                        switch (c.tabPerso.get(index).getEquipe()){
                            case "A":
                                 Javatar_air.getInstance().SetMessage(h.getMessages());
                                 break;
                            case "E":
                                Javatar_eau.getInstance().SetMessage(h.getMessages());
                                 break;
                            case "T":
                                Javatar_terre.getInstance().SetMessage(h.getMessages());
                                 break;
                            case "F":
                                Javatar_feu.getInstance().SetMessage(h.getMessages());
                                 break;
                        }
                        
                    }

                    //Début du processus de jeu
                    System.out.println(" jen ai pl1");
                    // appel de la fonction move aléatoire
                    c.carte.get(h.getPos().getX()).get(h.getPos().getY()).personnage = null; // enlève de l'ancienne pos 
                    System.out.println("je vais me déplacer");
                    
                    Coord newCoords = h.seDeplacer(c.caseDispo(h.getPos(),c.carte.get(h.getPos().getX()).get(h.getPos().getY()).type)); // ici h.gePos() == newCoords
                    System.out.println("je me suis déplacé");
                
                    c.carte.get(newCoords.getX()).get(newCoords.getY()).personnage = h;
                    System.out.println("ENERGIE FIN DE TOUR: " + h.getEnergie());
                    ArrayList<Humain> neighborTab = new ArrayList<Humain>();
                    neighborTab=c.caseRencontre(newCoords);
                    System.out.println("NEIGHBORTAB: " + neighborTab);
                    for(int i=0; i< neighborTab.size(); i++){
                        int test =h.rencontre(neighborTab.get(i));
                        if(test==0){    //La méthode rencontre renvoi 1 si l'objet appelant la méthod gagne le combat 0 s'il perd et meurt
                            c.carte.get(h.getPos().getX()).get(h.getPos().getY()).personnage = null;
                            c.carte.get(h.getPos().getX()).get(h.getPos().getY()).type = "O";
                        }
                        else if(test==1){
                            c.carte.get(neighborTab.get(i).getPos().getX()).get(neighborTab.get(i).getPos().getY()).personnage = null;
                            c.carte.get(neighborTab.get(i).getPos().getX()).get(neighborTab.get(i).getPos().getY()).type = "O";
                        }
                    }
                    
                }
                

                // ArrayList<Humain> voisinsListe = c.voisins(h.getPos());
                
                // for (Humain v : voisinsListe) {
                //     if (h.estVivant()) {
                //         h.rencontre(v);
                //     }
                // }

                //si l'individu est en dehors de la safezone , il pert des PE
                // il est dans la safezone hop hop hop l'érj
                
                //Si PE<=20%
                    
                //Si un individu est bloqué
                    //si c'est un obstacle
                        //perd autant de PE que de pas qu'il aurait dû effectuer
                    //si c'est un individu 
                        //déclenche rencontre
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                    }
        
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
