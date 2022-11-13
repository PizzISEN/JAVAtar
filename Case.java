public class Case {
    public String type;
    private Coord coord = new Coord();
    public int id;
    public Humain personnage;   // Va servir de référence pour le personnage situé sur la case

    //Type des cases: 0 - air(A) - eau(E) - terre(T) - feu(F) - obstacle(O) - Personnage (P)

    public Case (String t, int x, int y, int id){
        this.coord.setCoord(x, y);
        this.type = t;
        this.id = id;
        this.personnage = null;
    }
    
    public Coord getCoord(){
        return coord;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String t){
        this.type = t;
    }

    public void setPersonnage(Humain p){
        this.personnage = p;
    }
}
