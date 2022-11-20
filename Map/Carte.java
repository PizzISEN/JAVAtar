package Map;

import java.util.ArrayList;

import Sentences.Sentences;

import java.lang.Math;
import java.security.SecureRandom;
import java.util.*;


import Coordonnees.Coord;
import Personnage.*;

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

    public Carte(int[] s){
        this.size = s;
        carte = new ArrayList<ArrayList<Case>>();   //tableau double entrée permettant la création de la carte de jeu
        tabPerso = new ArrayList<Humain>();     //Liste de Personnage permettant le choix de l'unité à jouer ensuite, rassemble l'ensemble des personnages en jeu


        for (int i = 0; i < s[0]; i++) {        //Création du tableau de case
            carte.add(new ArrayList<Case>());
            for (int y = 0; y < s[1]; y++) {
                carte.get(i).add(0, new Case("0", i, y, i));
            }
        }


        
        //Début de la pose des Obstacles
        int nbCase = size[0] * size[1];
        double nbObstacle = Math.floor(nbCase * 0.05);
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
    
    public void placementPersos(ArrayList<Humain> tab, int tX, int tY){     //Fonction de placement de personnages dans la Safezone (Premier spawn des personnages)
            for (int i = 0; i<tab.size(); i++){

                tab.get(i).ajouterMessage(donnePhrase());   //ajout du message que le personnage aura en connaissance

                switch(tab.get(i).getClass().getSimpleName()){      //Début du Switch case selon les nations

                    case "Nation_du_feu":   
                        int feuX;
                        int feuY;
                        
                        do {
                            feuX = new SecureRandom().nextInt(size[0] - (size[0]-tX)) + (size[0]-tX);           //on prend des random coordonnées dans leur SafeZone
                            feuY = new SecureRandom().nextInt(size[1] - (size[1]-tY)) + (size[1]-tY);           
                        } while (carte.get(feuX).get(feuY).personnage != null || carte.get(feuX).get(feuY).type == "Jf");   // Ne peut pas etre placé sur les coins car c'est la position du Singleton/Javatar
                        tab.get(i).setPos(feuX, feuY);                                                                          // ne peut pas etre placé au dessus d'un autre
                        carte.get(feuX).get(feuY).personnage = tabPerso.get(i);     //Set de la position dans le tableau d'humain qui nous sert à choisir qui va jouer son tour
                                                                                    //Et Set la position dans la map
                        break;                                                      //Nous travaillons avec un système de deux tableaux sur lesquels il faut répercuter constamment les actions sur l'un et l'autre

                    case "Nomades_de_l_air":        //Meme process pour les autres tribus
                        int airX;
                        int airY;

                        do {
                            airX = new SecureRandom().nextInt(tX);
                            airY = new SecureRandom().nextInt(tY);
                        } while (carte.get(airX).get(airY).personnage != null || carte.get(airX).get(airY).type == "Ja");
                        tab.get(i).setPos(airX, airY);
                        carte.get(airX).get(airY).personnage = tabPerso.get(i);
                        
                        break;

                    case "Royaume_de_la_terre":
                        int terreX;
                        int terreY;

                        do {
                            terreX = new SecureRandom().nextInt(size[0] - (size[0]-tX)) + (size[0]-tX);
                            terreY = new SecureRandom().nextInt(tY);
                        } while (carte.get(terreX).get(terreY).personnage != null || carte.get(terreX).get(terreY).type == "Jt");
                        tab.get(i).setPos(terreX, terreY);
                        carte.get(terreX).get(terreY).personnage = tabPerso.get(i);
                        
                        break;

                    case "Tribus_de_l_eau":
                        int eauX;
                        int eauY;

                        do {
                            eauX = new SecureRandom().nextInt(tX);
                            eauY = new SecureRandom().nextInt(size[0] - (size[0]-tY)) + (size[0]-tY);
                        } while (carte.get(eauX).get(eauY).personnage != null || carte.get(eauX).get(eauY).type == "Je");
                        tab.get(i).setPos(eauX, eauY);
                        carte.get(eauX).get(eauY).personnage = tabPerso.get(i);

                        break;
                }
                
            }
    }

    // Ici et apres, fonctions pas marrantes avec gestion des positions afin de nous renvoyer ce que l'on souhaite

    public ArrayList<Coord> caseDispo(Coord coord,String equipe){        //Renvoi les coord des cases dispo autour d'une coordonnée
        ArrayList<Coord> caseDispos = new ArrayList<Coord>();           
        if(coord.getX()<size[0]-1 && (carte.get(coord.getX()+1).get(coord.getY()).type=="0" || carte.get(coord.getX()+1).get(coord.getY()).type==equipe) && carte.get(coord.getX()+1).get(coord.getY()).personnage ==null){
            caseDispos.add(new Coord(coord.getX()+1,coord.getY()));
        }
        if(coord.getX()<size[0]-1 && coord.getY()<size[1]-1 && (carte.get(coord.getX()+1).get(coord.getY()+1).type=="0" || carte.get(coord.getX()+1).get(coord.getY()+1).type==equipe)&& carte.get(coord.getX()+1).get(coord.getY()+1).personnage ==null ){
            caseDispos.add(new Coord(coord.getX()+1,coord.getY()+1));
        }
        if(coord.getX()>0 && (carte.get(coord.getX()-1).get(coord.getY()).type=="0"|| carte.get(coord.getX()-1).get(coord.getY()).type==equipe)&& carte.get(coord.getX()-1).get(coord.getY()).personnage ==null){
            caseDispos.add(new Coord(coord.getX()-1,coord.getY()));
        }
        if(coord.getX()>0 &&  coord.getY()<size[1]-1 && (carte.get(coord.getX()-1).get(coord.getY()+1).type=="0" || carte.get(coord.getX()-1).get(coord.getY()+1).type==equipe )&& carte.get(coord.getX()-1).get(coord.getY()+1).personnage ==null){
            caseDispos.add(new Coord(coord.getX()-1,coord.getY()+1));
        }
        if(coord.getY()<size[1]-1 && (carte.get(coord.getX()).get(coord.getY()+1).type=="0"|| carte.get(coord.getX()).get(coord.getY()+1).type==equipe)&& carte.get(coord.getX()).get(coord.getY()+1).personnage ==null){
            caseDispos.add(new Coord(coord.getX(),coord.getY()+1));
        }
        if(coord.getY()>0 && coord.getX()<size[0]-1 && (carte.get(coord.getX()+1).get(coord.getY()-1).type=="0" || carte.get(coord.getX()+1).get(coord.getY()-1).type==equipe)&& carte.get(coord.getX()+1).get(coord.getY()-1).personnage ==null){
            caseDispos.add(new Coord(coord.getX()+1,coord.getY()-1));
        }
        if(coord.getY()>0 && (carte.get(coord.getX()).get(coord.getY()-1).type=="0" || carte.get(coord.getX()).get(coord.getY()-1).type==equipe)&& carte.get(coord.getX()).get(coord.getY()-1).personnage ==null){
            caseDispos.add(new Coord(coord.getX(),coord.getY()-1));
        }
        if(coord.getY()>0 && coord.getX()>0 && (carte.get(coord.getX()-1).get(coord.getY()-1).type=="0" || carte.get(coord.getX()-1).get(coord.getY()-1).type==equipe)&& carte.get(coord.getX()-1).get(coord.getY()-1).personnage ==null){
            caseDispos.add(new Coord(coord.getX()-1,coord.getY()-1));
        }
        return caseDispos;
    }

    public ArrayList<Humain> caseRencontre(Coord coord,String equipe){        //Renvoi un tableau avec les références des humains autour d'un coordonnée
        ArrayList<Humain> neighborTab = new ArrayList<Humain>();
        if(coord.getX()<size[0]-1 && (carte.get(coord.getX()+1).get(coord.getY()).type=="0" || carte.get(coord.getX()+1).get(coord.getY()).type==equipe) && carte.get(coord.getX()+1).get(coord.getY()).personnage !=null){
            neighborTab.add(carte.get(coord.getX()+1).get(coord.getY()).personnage);
        }
        if(coord.getX()<size[0]-1 && coord.getY()<size[1]-1 && (carte.get(coord.getX()+1).get(coord.getY()+1).type=="0" || carte.get(coord.getX()+1).get(coord.getY()+1).type==equipe) && carte.get(coord.getX()+1).get(coord.getY()+1).personnage !=null ){
            neighborTab.add(carte.get(coord.getX()+1).get(coord.getY()+1).personnage);
        }
        if(coord.getX()>0 && (carte.get(coord.getX()-1).get(coord.getY()).type=="0"|| carte.get(coord.getX()-1).get(coord.getY()).type==equipe) && carte.get(coord.getX()-1).get(coord.getY()).personnage !=null){
            neighborTab.add(carte.get(coord.getX()-1).get(coord.getY()).personnage);
        }
        if(coord.getX()>0 &&  coord.getY()<size[1]-1 && (carte.get(coord.getX()-1).get(coord.getY()+1).type=="0" || carte.get(coord.getX()-1).get(coord.getY()+1).type==equipe ) && coord.getY()<size[1]-1 && carte.get(coord.getX()-1).get(coord.getY()+1).personnage !=null){
            neighborTab.add(carte.get(coord.getX()-1).get(coord.getY()+1).personnage);
        }
        if(coord.getY()<size[1]-1 && (carte.get(coord.getX()).get(coord.getY()+1).type=="0"|| carte.get(coord.getX()).get(coord.getY()+1).type==equipe) && carte.get(coord.getX()).get(coord.getY()+1).personnage !=null){
            neighborTab.add(carte.get(coord.getX()).get(coord.getY()+1).personnage);
        }
        if(coord.getY()>0 && coord.getX()<size[0]-1 && coord.getX()<size[0]-1 && (carte.get(coord.getX()+1).get(coord.getY()-1).type=="0" || carte.get(coord.getX()+1).get(coord.getY()-1).type==equipe) && carte.get(coord.getX()+1).get(coord.getY()-1).personnage !=null){
            neighborTab.add(carte.get(coord.getX()+1).get(coord.getY()-1).personnage);
        }
        if(coord.getY()>0 && (carte.get(coord.getX()).get(coord.getY()-1).type=="0" || carte.get(coord.getX()).get(coord.getY()-1).type==equipe) && carte.get(coord.getX()).get(coord.getY()-1).personnage !=null){
            neighborTab.add(carte.get(coord.getX()).get(coord.getY()-1).personnage);
        }
        if(coord.getY()>0 && coord.getX()>0 && (carte.get(coord.getX()-1).get(coord.getY()-1).type=="0" || carte.get(coord.getX()-1).get(coord.getY()-1).type==equipe) && coord.getX()>0 && carte.get(coord.getX()-1).get(coord.getY()-1).personnage !=null){
            neighborTab.add(carte.get(coord.getX()-1).get(coord.getY()-1).personnage);
        }
        return neighborTab;
    }

    public ArrayList<Coord> caseDispoDjikstra(Coord coord,String equipe){        //Renvoi les coord des cases dispo autour d'une coordonnée, utilisée différemment pour Djikstra
        ArrayList<Coord> caseDispos = new ArrayList<Coord>();
        if(coord.getX()<size[0]-1 && (carte.get(coord.getX()+1).get(coord.getY()).type=="0" || carte.get(coord.getX()+1).get(coord.getY()).type==equipe)){
            caseDispos.add(new Coord(coord.getX()+1,coord.getY())); //
        }
        if(coord.getX()<size[0]-1 && coord.getY()<size[1]-1 && (carte.get(coord.getX()+1).get(coord.getY()+1).type=="0" || carte.get(coord.getX()+1).get(coord.getY()+1).type==equipe)){
            caseDispos.add(new Coord(coord.getX()+1,coord.getY()+1)); //
        }
        if(coord.getX()>0 && (carte.get(coord.getX()-1).get(coord.getY()).type=="0"|| carte.get(coord.getX()-1).get(coord.getY()).type==equipe)){
            caseDispos.add(new Coord(coord.getX()-1,coord.getY())); //
        }
        if(coord.getX()>0 &&  coord.getY()<size[1]-1 && (carte.get(coord.getX()-1).get(coord.getY()+1).type=="0" || carte.get(coord.getX()-1).get(coord.getY()+1).type==equipe )){
            caseDispos.add(new Coord(coord.getX()-1,coord.getY()+1)); //
        }
        if(coord.getY()<size[1]-1 && (carte.get(coord.getX()).get(coord.getY()+1).type=="0"|| carte.get(coord.getX()).get(coord.getY()+1).type==equipe)){
            caseDispos.add(new Coord(coord.getX(),coord.getY()+1)); //
        }
        if(coord.getY()>0 && coord.getX()<size[0]-1 && (carte.get(coord.getX()+1).get(coord.getY()-1).type=="0" || carte.get(coord.getX()+1).get(coord.getY()-1).type==equipe)){
            caseDispos.add(new Coord(coord.getX()+1,coord.getY()-1)); //
        }
        if(coord.getY()>0 && (carte.get(coord.getX()).get(coord.getY()-1).type=="0" || carte.get(coord.getX()).get(coord.getY()-1).type==equipe)){
            caseDispos.add(new Coord(coord.getX(),coord.getY()-1));
        }
        if(coord.getY()>0 && coord.getX()>0 && (carte.get(coord.getX()-1).get(coord.getY()-1).type=="0" || carte.get(coord.getX()-1).get(coord.getY()-1).type==equipe)){
            caseDispos.add(new Coord(coord.getX()-1,coord.getY()-1));
        }
        return caseDispos;
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
 
    public Coord matrice(Coord depart,Coord dest,String equipe)
    {
        List<List<Node> > adj = new ArrayList<List<Node> >();
        ArrayList<Coord> caseDispos = new ArrayList<Coord>();    
        int des=0;
        int it=0;
        int itNode=0;
       this.adj = new ArrayList<List<Node> >();
        for (int i = 0; i < size[0]; i++) { // boucle permettant la création des différents noeuds et différents liens entre ceux-ci avec les coût associés
            for (int y = 0; y < size[1]; y++) {
                if(dest.getY()==i && dest.getX()==y){
                    des=it;
                }

                if(carte.get(i).get(y).type== "0" ||carte.get(i).get(y).type== equipe ||carte.get(i).get(y).personnage != null){ // on donne une distance de 1 pour les cases de safezone vide ou personnage 
                //(on prend le parti que le personnage va bouger et le problème est géré juste après)
                    adj.add(new ArrayList<Node>());
                    caseDispos=caseDispoDjikstra(new Coord(i, y),equipe);
                    for (int j = 0; j < caseDispos.size(); j++) {
                        adj.get(itNode).add(new Node(caseDispos.get(j).getY()+(caseDispos.get(j).getX()*size[0]),1));
                    }
                    itNode++;
                }
                else{ // ici ce sont des obstacles alors la distance est égale à 99
                    adj.add(new ArrayList<Node>());
                    caseDispos=caseDispoDjikstra(new Coord(i, y),equipe);
                    for (int j = 0; j < caseDispos.size(); j++) {
                        adj.get(itNode).add(new Node(caseDispos.get(j).getY()+(caseDispos.get(j).getX()*size[0]),99));
                    }
                    itNode++;
                }
                it++;
            }
        }
        int distCompare = -1;
        Coord sortie = new Coord();
        caseDispos=caseDispo(depart,equipe);


        for (int j = 0; j < caseDispos.size(); j++) { //ici on parcours pour toute les cases alentours de la case actuelle Djikstra et on en ressort la case avec
        //la distance avec la destination la plus faible possible ( ici on gère également l'exception des personnages car caseDispo ne renvoie pas les cases avec des personnages)
            this.V = itNode;
            this.dist = new int[V];
            this.settled = new HashSet<Integer>();
            this.pq = new PriorityQueue<Node>(V, new Node());

            dijkstra(adj,caseDispos.get(j).getY()+(caseDispos.get(j).getX()*size[0]));
            if(distCompare<0 || dist[des]<distCompare ){
                distCompare=dist[des];
                sortie=new Coord(caseDispos.get(j).getX(),(caseDispos.get(j).getY()));
            }
        }
        return sortie;

    }
}
