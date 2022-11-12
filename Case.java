public class Case {
    public String type;
    private Coord coord = new Coord();
    public int id;

    //Type des cases: 0 - air(A) - eau(E) - terre(T) - feu(F) - obstacle(O) - Personnage (P)

    public Case (String t, int x, int y, int id){
        this.coord.x = x;
        this.coord.y = y;
        this.type = t;
        this.id = id;
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
}
