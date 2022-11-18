import java.util.ArrayList;
import java.util.stream.IntStream;

import Sentences.Sentences;

import java.lang.Math;
import java.security.SecureRandom;
import java.util.*;


public class Carte {
    public int[] size;
    public ArrayList<String> sentenceUse = new ArrayList<String>();
    public String[] dataSentences = Sentences.getInstance().messages;
    public ArrayList<ArrayList<Case>>carte;
    public ArrayList<Humain> tabPerso;
    
    private int dist[];
    private Set<Integer> settled;
    private PriorityQueue<Node> pq;
    // Number of vertices
    private int V;
    List<List<Node> > adj;

    Carte(int[] s){
        this.size = s;
        carte = new ArrayList<ArrayList<Case>>();   //tableau double entrée permettant la création de la carte de jeu
        tabPerso = new ArrayList<Humain>();     //Liste de Personnage permettant le choix de l'unité à jouer ensuite, rassemble l'ensemble des personnages en jeu


        for (int i = 0; i < s[0]; i++) {        //Création du tableau de case
            carte.add(new ArrayList<Case>());
            for (int y = 0; y < s[1]; y++) {
                carte.get(i).add(0, new Case("0", i, y, i));
            }
        }

        //1: Définir longueur largeur max de chacunes des aires (si impair laisser au moins 1 case/ si pair laisser 2 cases libres) (paire: (size -2)/2 , impair: (size-1)/2)
        //2: Incrémenter graduellement la largeur et la longueur des carrés puis regarder si l'aire de l'ensemble des rectangles dépasse le seuil fixé 
        //   Si l'Aire cumulée des rectangles est supérieure au seuil alors garder la largeur / longueur précédente ou si on a atteint la longueur et la largeur max que chacune des aires peut prendre
        //3: Une fois que les tailles sont définies il suffit de placer les zones ( Haut-gauche: (X:0->taille_x, Y:0->taille_y),Haut-Droite : (X: tailleMax_x-taille_x->tailleMax,Y:0->taille_y),
        //   Bas-gauche: (X:0->taille_x,Y: tailleMax_Y-taille_Y->taille_Y), Bas-Droite: (X: tailleMax_x-taille_x->tailleMax_X,Y: tailleMax_Y-taille_Y->taille_Y))
        
        //Début de la pose des Obstacles
        int nbCase = size[0] * size[1];
        double nbObstacle = Math.floor(nbCase * 0.12);
        for (int o=0; o<nbObstacle; o++){
            int randX = new SecureRandom().nextInt(size[0]);
            int randY = new SecureRandom().nextInt(size[1]);
            carte.get(randX).get(randY).setType("O");
        }

        //                                                                  CREATION DES SAFEZONES 


        //définition nombres de cases pour le seuil
        double seuil =0.4;
        double seuil_SZ = Math.floor((size[0] * size[1]) * seuil);

        //Définition longueur et largeur max pour laisser au moins 1 case d'écart ( en largeur et longeur)
        double taille_SZ_X_Max;
        double taille_SZ_Y_Max;
        if(size[0]%2==0){
            taille_SZ_X_Max=(size[0]-2)/2;
        }
        else{
            taille_SZ_X_Max=(size[0]-1)/2;
        }
        if(size[1]%2==0){
            taille_SZ_Y_Max=(size[1]-2)/2;
        }
        else{
            taille_SZ_Y_Max=(size[1]-1)/2;
        }
        
        Boolean tailleXEstMax= false;
        Boolean tailleYEstMax= false;
        int taille_X=1;
        int taille_Y=1;
        int derniere_taille_X=0;
        int derniere_taille_Y=0;

        String prochainAIncrementer="X";
        while(!(tailleXEstMax && tailleYEstMax)){

            if(seuil_SZ>=taille_X*taille_Y*4){
                derniere_taille_X=taille_X;
                derniere_taille_Y=taille_Y;

                if (prochainAIncrementer=="X"){
                    if(taille_X<taille_SZ_X_Max){
                        taille_X++;
                        
                        if(!tailleYEstMax){
                            prochainAIncrementer="Y";
                        }
                    }
                    else{
                        tailleXEstMax=true;
                        prochainAIncrementer="Y";
                    }
                }
                else{
                    if(taille_Y<taille_SZ_Y_Max){
                        taille_Y++;
                        if(!tailleXEstMax){
                            prochainAIncrementer="X";
                        }
                    }
                    else{
                        tailleYEstMax=true;
                        prochainAIncrementer="X";
                    }
                     
                }
                
               
            }
            else{
                int minCheck = Math.min(derniere_taille_X,derniere_taille_Y);
                if(size[0] == size[1]){
                    taille_X=minCheck;
                    taille_Y=minCheck;
                }
                else{
                    taille_X = derniere_taille_X;
                    taille_Y = derniere_taille_Y;
                }

                break;
            }
        }

        //  Placement des zones apres avoir calculé le nombre max de cases par SZ

        //Haut-gauche: (X:0->tailleTrouvée_x, Y:0->tailleTrouvée_y) 1 et Air
        for(int i=0; i < taille_X; i++){
            for (int j = 0; j < taille_Y; j++) {
                carte.get(i).get(j).setType("A");
            }
        }

        //Haut-Droite : (X: tailleTotal-tailleTrouvée_x -> tailleTotal_x ,Y:0->tailleTrouvée_y) 2 et Eau
        for(int i=0; i<taille_X; i++){
            for (int j = size[1] - taille_Y; j < size[1]; j++) {
                carte.get(i).get(j).setType("E");
            }
        }
        
        //Bas-gauche: (X:0->taille_x,Y: tailleTotale_Y-taille_Y->tailleTotale_Y)  3 et Terre
        for(int i=size[0] - taille_X; i< size[0]; i++){
            for(int j=0;j< taille_Y; j++){
                carte.get(i).get(j).setType("T");
            }
        }
        //Bas-Droite: (X: tailleTotale_x-taille_x->tailleTotale_X,Y: tailleTotale_Y-taille_Y->tailleTotale_Y))  4 et feu
        for(int i=size[0]-taille_X;i<size[0];i++){
            for(int j=size[1]-taille_Y;j<size[1];j++){
                carte.get(i).get(j).setType("F");
            }
        }


        //                                              CREATION DES PERSONNAGES ET PLACEMENT, MAIS AUSSI SINGLETON
        double nbPersosEquipe = Math.floor(nbCase*0.04);
        //Placement despositions dans Javatar
        Javatar_air.getInstance().setPos(0, 0);
        Javatar_eau.getInstance().setPos(0, s[1]-1);
        Javatar_feu.getInstance().setPos(s[0]-1, s[1]-1);
        Javatar_terre.getInstance().setPos(s[0]-1, 0);

        Javatar_air.getInstance().SetNbOfMessages((int)nbPersosEquipe*4);
        Javatar_eau.getInstance().SetNbOfMessages((int)nbPersosEquipe*4);
        Javatar_feu.getInstance().SetNbOfMessages((int)nbPersosEquipe*4);
        Javatar_terre.getInstance().SetNbOfMessages((int)nbPersosEquipe*4);

        //Placement des positions dans la carte
        carte.get(0).get(0).setType("Ja");
        carte.get(0).get(size[1]-1).setType("Je");
        carte.get(size[0]-1).get(0).setType("Jt");
        carte.get(size[0]-1).get(size[1]-1).setType("Jf");
        

        for (int l=0; l<nbPersosEquipe; l++){
            tabPerso.add(new Nation_du_feu(0, 0));
            tabPerso.add(new Nomades_de_l_air(0, 0));
            tabPerso.add(new Tribus_de_l_eau(0, 0));
            tabPerso.add(new Royaume_de_la_terre(0, 0));
        }

        placementPersos(tabPerso, taille_X, taille_Y);
        System.out.println(tabPerso.get(1).getMessages());

    }
    public String donnePhrase(){
        String msg="";
        int i = new SecureRandom().nextInt(dataSentences.length);
        msg = dataSentences[i];
        while(sentenceUse.contains(msg)){
            i = new SecureRandom().nextInt(dataSentences.length);
            msg = dataSentences[i];
        }
        sentenceUse.add(msg);
        return msg;
    }
    
    public void placementPersos(ArrayList<Humain> tab, int tX, int tY){
            for (int i = 0; i<tab.size(); i++){
                tab.get(i).ajouterMessage(donnePhrase());
                switch(tab.get(i).getClass().getSimpleName()){
                    case "Nation_du_feu":
                        int feuX;
                        int feuY;
                        
                        do {
                            feuX = new SecureRandom().nextInt(size[0] - (size[0]-tX)) + (size[0]-tX);
                            feuY = new SecureRandom().nextInt(size[1] - (size[1]-tY)) + (size[1]-tY);
                        } while (carte.get(feuX).get(feuY).personnage != null || carte.get(feuX).get(feuY).type == "Jf");
                        tab.get(i).pos.setCoord(feuX,feuY);
                        carte.get(feuX).get(feuY).personnage = tabPerso.get(i);

                        break;
                    case "Nomades_de_l_air":
                        int airX;
                        int airY;

                        do {
                            airX = new SecureRandom().nextInt(tX);
                            airY = new SecureRandom().nextInt(tY);
                        } while (carte.get(airX).get(airY).personnage != null || carte.get(airX).get(airY).type == "Ja");
                        tab.get(i).pos.setCoord(airX,airY);
                        carte.get(airX).get(airY).personnage = tabPerso.get(i);
                        
                        break;
                    case "Royaume_de_la_terre":
                        int terreX;
                        int terreY;

                        do {
                            terreX = new SecureRandom().nextInt(size[0] - (size[0]-tX)) + (size[0]-tX);
                            terreY = new SecureRandom().nextInt(tY);
                        } while (carte.get(terreX).get(terreY).personnage != null || carte.get(terreX).get(terreY).type == "Jt");
                        tab.get(i).pos.setCoord(terreX, terreY);
                        carte.get(terreX).get(terreY).personnage = tabPerso.get(i);
                        
                        break;
                    case "Tribus_de_l_eau":
                        int eauX;
                        int eauY;

                        do {
                            eauX = new SecureRandom().nextInt(tX);
                            eauY = new SecureRandom().nextInt(size[0] - (size[0]-tY)) + (size[0]-tY);
                        } while (carte.get(eauX).get(eauY).personnage != null || carte.get(eauX).get(eauY).type == "Je");
                        tab.get(i).pos.setCoord(eauX, eauY);
                        carte.get(eauX).get(eauY).personnage = tabPerso.get(i);
                        break;
                }
                
            }
    }        

    public ArrayList<Coord> caseDispo(Coord coord,String equipe){        //Fonctionnelle - Renvoi les coord des cases dispo
        ArrayList<Coord> caseDispos = new ArrayList<Coord>();
        if(coord.getX()<size[0]-1 && (carte.get(coord.getX()+1).get(coord.getY()).type=="0" || carte.get(coord.getX()+1).get(coord.getY()).type==equipe) && carte.get(coord.getX()+1).get(coord.getY()).personnage ==null){
            caseDispos.add(new Coord(coord.getX()+1,coord.getY())); //
        }
        if(coord.getX()<size[0]-1 && coord.getY()<size[1]-1 && (carte.get(coord.getX()+1).get(coord.getY()+1).type=="0" || carte.get(coord.getX()+1).get(coord.getY()+1).type==equipe)&& carte.get(coord.getX()+1).get(coord.getY()+1).personnage ==null ){
            caseDispos.add(new Coord(coord.getX()+1,coord.getY()+1)); //
        }
        if(coord.getX()>0 && (carte.get(coord.getX()-1).get(coord.getY()).type=="0"|| carte.get(coord.getX()-1).get(coord.getY()).type==equipe)&& carte.get(coord.getX()-1).get(coord.getY()).personnage ==null){
            caseDispos.add(new Coord(coord.getX()-1,coord.getY())); //
        }
        if(coord.getX()>0 &&  coord.getY()<size[1]-1 && (carte.get(coord.getX()-1).get(coord.getY()+1).type=="0" || carte.get(coord.getX()-1).get(coord.getY()+1).type==equipe )&& carte.get(coord.getX()-1).get(coord.getY()+1).personnage ==null){
            caseDispos.add(new Coord(coord.getX()-1,coord.getY()+1)); //
        }
        if(coord.getY()<size[1]-1 && (carte.get(coord.getX()).get(coord.getY()+1).type=="0"|| carte.get(coord.getX()).get(coord.getY()+1).type==equipe)&& carte.get(coord.getX()).get(coord.getY()+1).personnage ==null){
            caseDispos.add(new Coord(coord.getX(),coord.getY()+1)); //
        }
        if(coord.getY()>0 && coord.getX()<size[0]-1 && (carte.get(coord.getX()+1).get(coord.getY()-1).type=="0" || carte.get(coord.getX()+1).get(coord.getY()-1).type==equipe)&& carte.get(coord.getX()+1).get(coord.getY()-1).personnage ==null){
            caseDispos.add(new Coord(coord.getX()+1,coord.getY()-1)); //
        }
        if(coord.getY()>0 && (carte.get(coord.getX()).get(coord.getY()-1).type=="0" || carte.get(coord.getX()).get(coord.getY()-1).type==equipe)&& carte.get(coord.getX()).get(coord.getY()-1).personnage ==null){
            caseDispos.add(new Coord(coord.getX(),coord.getY()-1));
        }
        if(coord.getY()>0 && coord.getX()>0 && (carte.get(coord.getX()-1).get(coord.getY()-1).type=="0" || carte.get(coord.getX()-1).get(coord.getY()-1).type==equipe)&& carte.get(coord.getX()-1).get(coord.getY()-1).personnage ==null){
            caseDispos.add(new Coord(coord.getX()-1,coord.getY()-1));
        }
        return caseDispos;
    }

    public ArrayList<Humain> voisins(Coord coord) {
        ArrayList<Humain> h = new ArrayList<Humain>();
        int x = coord.getX();
        int y = coord.getY();

        if (x<size[0]-1 && carte.get(x+1).get(y).type!="O" && carte.get(x+1).get(y).type!="0") {
            h.add(carte.get(x+1).get(y).personnage);
        }
        if (x<size[0]-1 && y<size[1]-1 && carte.get(x+1).get(y+1).type!="O" && carte.get(x+1).get(y+1).type!="0") {
            h.add(carte.get(x+1).get(y+1).personnage);
        }
        if (x>0 && carte.get(x-1).get(y).type!="O" && carte.get(x-1).get(y).type!="0") {
            h.add(carte.get(x-1).get(y).personnage);
        }
        if (x>0 && y<size[1]-1 && carte.get(x-1).get(y+1).type!="O" && carte.get(x-1).get(y+1).type!="0") {
            h.add(carte.get(x-1).get(y+1).personnage);
        }
        if (y<size[1]-1 && carte.get(x).get(y+1).type!="O" && carte.get(x).get(y+1).type!="0") {
            h.add(carte.get(x).get(y+1).personnage);
        }
        if (y>0 && x<size[0]-1 && carte.get(x+1).get(y-1).type!="O" && carte.get(x+1).get(y-1).type!="0") {
            h.add(carte.get(x+1).get(y-1).personnage);
        }
        if (y>0 && carte.get(x).get(y-1).type!="O" && carte.get(x).get(y-1).type!="0") {
            h.add(carte.get(x).get(y-1).personnage);
        }
        if (y>0 && x>0 && carte.get(x-1).get(y-1).type!="O" && carte.get(x-1).get(y-1).type!="0") {
            h.add(carte.get(x-1).get(y-1).personnage);
        }

        return h;
    }

    class Node implements Comparator<Node> {
    
        // Member variables of this class
        public int node;
        public int cost;
    
        // Constructors of this class
    
        // Constructor 1
        public Node() {}
    
        // Constructor 2
        public Node(int node, int cost)
        {
    
            // This keyword refers to current instance itself
            this.node = node;
            this.cost = cost;
        }
    
        // Method 1
        @Override public int compare(Node node1, Node node2)
        {
    
            if (node1.cost < node2.cost)
                return -1;
    
            if (node1.cost > node2.cost)
                return 1;
    
            return 0;
        }
    }

    // Method 1
    // Dijkstra's Algorithm
    public void dijkstra(List<List<Node> > adj, int src)
    {
        this.adj = adj;
 
        for (int i = 0; i < V; i++)
            dist[i] = Integer.MAX_VALUE;
 
        // Add source node to the priority queue
        pq.add(new Node(src, 0));
 
        // Distance to the source is 0
        dist[src] = 0;
 
        while (settled.size() != V) {
 
            // Terminating condition check when
            // the priority queue is empty, return
            if (pq.isEmpty())
                return;
 
            // Removing the minimum distance node
            // from the priority queue
            int u = pq.remove().node;
 
            // Adding the node whose distance is
            // finalized
            if (settled.contains(u))
 
                // Continue keyword skips execution for
                // following check
                continue;
 
            // We don't have to call e_Neighbors(u)
            // if u is already present in the settled set.
            settled.add(u);
 
            e_Neighbours(u);
        }
    }
 
    // Method 2
    // To process all the neighbours
    // of the passed node
    private void e_Neighbours(int u)
    {
 
        int edgeDistance = -1;
        int newDistance = -1;
 
        // All the neighbors of v
        for (int i = 0; i < adj.get(u).size(); i++) {
            Node v = adj.get(u).get(i);
 
            // If current node hasn't already been processed
            if (!settled.contains(v.node)) {
                edgeDistance = v.cost;
                newDistance = dist[u] + edgeDistance;
 
                // If new distance is cheaper in cost
                if (newDistance < dist[v.node])
                    dist[v.node] = newDistance;
 
                // Add the current node to the queue
                pq.add(new Node(v.node, dist[v.node]));
            }
        }
    }
 
    public Coord matrice(Coord depart,Coord dest)
    {
        List<List<Node> > adj = new ArrayList<List<Node> >();
        ArrayList<Coord> caseDispos = new ArrayList<Coord>();    
        int dep=0;
        int des=0;
        int it=0;
        int itNode=0;
       this.adj
            = new ArrayList<List<Node> >();
        for (int i = 0; i < size[0]; i++) {
            for (int y = 0; y < size[1]; y++) {
                if(depart.getX()==i && depart.getY()==y){
                    dep=it;
                }
                if(dest.getX()==i && dest.getY()==y){
                    des=it;
                }
                if(carte.get(i).get(y).type== "0"){
                    adj.add(new ArrayList<Node>());
                    caseDispos=caseDispo(new Coord(i, y),carte.get(i).get(y).type);
                    for (int j = 0; j < caseDispos.size(); j++) {
                        adj.get(itNode).add(new Node(caseDispos.get(j).getX()+(caseDispos.get(j).getY()*size[0]),1));
                    }
                    itNode++;
                }
                else{
                    adj.add(new ArrayList<Node>());
                    caseDispos=caseDispo(new Coord(i, y),carte.get(i).get(y).type);
                    for (int j = 0; j < caseDispos.size(); j++) {
                        adj.get(itNode).add(new Node(caseDispos.get(j).getX()+(caseDispos.get(j).getY()*size[0]),99));
                    }
                    itNode++;
                }
                it++;
            }
        }
        this.V = itNode;
        this.dist = new int[V];
        int distCompare = -1;
        Coord sortie = new Coord();
        this.settled = new HashSet<Integer>();
        this.pq = new PriorityQueue<Node>(V, new Node());
        caseDispos=caseDispo(depart,carte.get(depart.getX()).get(depart.getY()).type);
        for (int j = 0; j < caseDispos.size(); j++) {
            dijkstra(adj,caseDispos.get(j).getX()+(caseDispos.get(j).getY()*size[0]));
            if(distCompare<0 || dist[des]<distCompare ){
                distCompare=dist[des];
                sortie=new Coord(caseDispos.get(j).getX(),(caseDispos.get(j).getY()));
            }
        }
        return sortie;

    }
}
