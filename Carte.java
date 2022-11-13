import java.util.ArrayList;

public class Carte {                        // Fonctionnelle
    public int[] size;
    public ArrayList<ArrayList<Case>>carte;

    Carte(int[] s){
        this.size = s;
        carte = new ArrayList<ArrayList<Case>>();
        for (int i = 0; i < s[0]; i++) {
            carte.add(new ArrayList<Case>());
            for (int y = 0; y < s[1]; y++) {
                carte.get(i).add(0,new Case(0, i, y));
            }
        }
        carte.get(0).get(0).setType(1);         //Air
        carte.get(s[0]).get(0).setType(2);      //Eau
        carte.get(0).get(s[1]).setType(3);      //Terre
        carte.get(s[0]).get(s[1]).setType(4);   //Feu
        System.out.println(carte);
    }

    public ArrayList<Coord> caseDispo(int x, int y){        //Fonctionnelle
        Coord coord=new Coord(x,y);                         //Renvoi les coord des cases dispo
        ArrayList<Coord> caseDispos = new ArrayList<Coord>();
        if(coord.getX()<size[0] && carte.get(coord.getX()+1).get(coord.getY()).type==0){
            caseDispos.add(new Coord(coord.getX()+1,coord.getY()));
        }
        if(coord.getX()<size[0] && coord.getY()<size[1] && carte.get(coord.getX()+1).get(coord.getY()+1).type==0){
            caseDispos.add(new Coord(coord.getX()+1,coord.getY()+1));
        }
        if(coord.getX()>0 && carte.get(coord.getX()-1).get(coord.getY()).type==0){
            caseDispos.add(new Coord(coord.getX()-1,coord.getY()));
        }
        if(coord.getX()>0 &&  coord.getY()<size[1] && carte.get(coord.getX()-1).get(coord.getY()+1).type==0){
            caseDispos.add(new Coord(coord.getX()-1,coord.getY()+1));
        }
        if(coord.getY()<size[1] && carte.get(coord.getX()).get(coord.getY()+1).type==0){
            caseDispos.add(new Coord(coord.getX(),coord.getY()+1));
        }
        if(coord.getY()>0 && coord.getY()<size[0] && carte.get(coord.getX()+1).get(coord.getY()-1).type==0){
            caseDispos.add(new Coord(coord.getX()+1,coord.getY()-1));
        }
        if(coord.getY()>0 && carte.get(coord.getX()).get(coord.getY()-1).type==0){
            caseDispos.add(new Coord(coord.getX(),coord.getY()-1));
        }
        if(coord.getY()>0 && coord.getX()>0 && carte.get(coord.getX()-1).get(coord.getY()-1).type==0){
            caseDispos.add(new Coord(coord.getX()-1,coord.getY()-1));
        }
        return caseDispos;
    }



    public ArrayList<Coord> aStarSearch(Coord start,Coord goal){
 
        
        return null;
    }
    
}
