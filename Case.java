public class Case {
    public int type;
    private int coordX ;
    private int coordY;

    public Case (int t, int x, int y){
        this.coordX = x;
        this.coordY = y;
        this.type = t;
    }
    
    public int[] getCoord(){
        int [] arr = {this.coordX, this.coordY};
        return arr;
    }
}
