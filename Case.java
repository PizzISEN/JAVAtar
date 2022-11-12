public class Case {
    public int type;
    private Coord coord = new Coord();
    public int id;

    public Case (int t, int x, int y, int id){
        this.coord.x = x;
        this.coord.y = y;
        this.type = t;
        this.id = id;
    }
    
    public Coord getCoord(){
        return coord;
    }

    public int getType(){
        return this.type;
    }

    public void setType(int t){
        this.type = t;
    }
}
