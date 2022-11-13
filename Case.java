public class Case {
    public int type;
    private Coord coord = new Coord();

    public Case (int t, int x, int y){
        this.coord.setCoord(x, y);;
        this.type = t;
    }
    
    public Coord getCoord(){
        return coord;
    }

    public void setType(int t){
        this.type = t;
    }
}
