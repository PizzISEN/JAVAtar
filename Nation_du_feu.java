public class Nation_du_feu extends Humain {
    private Coord pos = new Coord();
    public boolean vivant;

    public Nation_du_feu(){
        super(0, 0);
    }
    @Override
    public void rencontre(Humain h){}

    @Override
    public void setPos(int x, int y){
        this.pos.x = x;
        this.pos.y = y;
    }

    @Override
    public void setFuturePos(int x, int y){
        this.pos.x = x;
        this.pos.y = y;
    }

    @Override
    public void mort() {
        this.vivant = false;
    }
}
