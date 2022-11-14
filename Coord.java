public class Coord {
    private int x;
    private int y;

    public Coord() {
        this.x = 0;
        this.y = 0;
    }

    public Coord(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public void setCoord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
