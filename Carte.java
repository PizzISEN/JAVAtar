import java.util.ArrayList;
import java.lang.Math;
public class Carte {
    public int[] size;
    public ArrayList<ArrayList<Case>>carte;

    Carte(int[] s){
        this.size = s;
        carte = new ArrayList<ArrayList<Case>>();
        for (int i = 0; i < s[0]; i++) {
            carte.add(new ArrayList<Case>());
            for (int y = 0; y < s[1]; y++) {
                carte.get(i).add(0,new Case("0", i, y, i));
            }
        }

        //1: Définir longueur largeur max de chacunes des aires (si impair laisser au moins 1 case/ si pair laisser 2 cases libres) (paire: (size -2)/2 , impair: (size-1)/2)
        //2: Incrémenter graduellement la largeur et la longueur des carrés puis regarder si l'aire de l'ensemble des rectangles dépasse le seuil fixé 
        //   Si l'Aire cumulée des rectangles est supérieure au seuil alors garder la largeur / longueur précédente ou si on a atteint la longueur et la largeur max que chacune des aires peut prendre
        //3: Une fois que les tailles sont définies il suffit de placer les zones ( Haut-gauche: (X:0->taille_x, Y:0->taille_y),Haut-Droite : (X: tailleMax_x-taille_x->tailleMax,Y:0->taille_y),
        //   Bas-gauche: (X:0->taille_x,Y: tailleMax_Y-taille_Y->taille_Y), Bas-Droite: (X: tailleMax_x-taille_x->tailleMax_X,Y: tailleMax_Y-taille_Y->taille_Y))
        
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
        System.out.println("Seuil_SZ: " + seuil_SZ +"\n");
        System.out.println("La taille optimale donnée par l'algo est en X : "+taille_X+" et en Y: "+taille_Y);

        //  Placement des zones apres avoir calculé le nombre max de cases par SZ

        //Haut-gauche: (X:0->tailleTrouvée_x, Y:0->tailleTrouvée_y) 1 et Air
        for(int i=0; i < taille_X; i++){
            for (int j = 0; j < taille_Y; j++) {
                carte.get(i).get(j).setType("A");
            }
        }

        //Haut-Droite : (X: tailleTotal-tailleTrouvée_x -> tailleTotal_x ,Y:0->tailleTrouvée_y) 2 et Eau
        System.out.println(taille_X + " " + taille_Y);
        for(int i=0; i<taille_X; i++){
            for (int j = size[1] - taille_Y; j < size[1]; j++) {
                carte.get(i).get(j).setType("E");
            }
        }
        
        //Bas-gauche: (X:0->taille_x,Y: tailleTotale_Y-taille_Y->tailleTotale_Y)  3 et Terre
        System.out.println(taille_X + " " + taille_Y);
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
        System.out.print("\n");
        System.out.print("\n");
        for(int i=0;i<size[0];i++){
            for(int j=0;j<size[1];j++){
                System.out.print(carte.get(i).get(j).getType()+" ");
            }
            System.out.print("\n");
        }
    }

    public ArrayList<Coord> caseDispo(int x, int y){        //Fonctionnelle
        Coord coord=new Coord(x,y);                         //Renvoi les coord des cases dispo
        ArrayList<Coord> caseDispos = new ArrayList<Coord>();
        if(coord.x<size[0] && carte.get(coord.x+1).get(coord.y).type=="0"){
            caseDispos.add(new Coord(coord.x+1,coord.y));
        }
        if(coord.x<size[0] && coord.y<size[1] && carte.get(coord.x+1).get(coord.y+1).type=="0"){
            caseDispos.add(new Coord(coord.x+1,coord.y+1));
        }
        if(coord.x>0 && carte.get(coord.x-1).get(coord.y).type=="0"){
            caseDispos.add(new Coord(coord.x-1,coord.y));
        }
        if(coord.x>0 &&  coord.y<size[1] && carte.get(coord.x-1).get(coord.y+1).type=="0"){
            caseDispos.add(new Coord(coord.x-1,coord.y+1));
        }
        if(coord.y<size[1] && carte.get(coord.x).get(coord.y+1).type=="0"){
            caseDispos.add(new Coord(coord.x,coord.y+1));
        }
        if(coord.y>0 && coord.y<size[0] && carte.get(coord.x+1).get(coord.y-1).type=="0"){
            caseDispos.add(new Coord(coord.x+1,coord.y-1));
        }
        if(coord.y>0 && carte.get(coord.x).get(coord.y-1).type=="0"){
            caseDispos.add(new Coord(coord.x,coord.y-1));
        }
        if(coord.y>0 && coord.x>0 && carte.get(coord.x-1).get(coord.y-1).type=="0"){
            caseDispos.add(new Coord(coord.x-1,coord.y-1));
        }
        return caseDispos;
    }



    public ArrayList<Coord> aStarSearch(Coord start,Coord goal){
 
        
        return null;
    }
    
}
