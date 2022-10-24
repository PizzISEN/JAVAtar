public class Case {
    public int type;
    private Coord coord = new Coord();

    public Case (int t, int x, int y){
        this.coord.x = x;
        this.coord.y = y;
        this.type = t;
    }
    
    public Coord getCoord(){
        return coord;
    }
}
