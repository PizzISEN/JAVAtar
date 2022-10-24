public abstract class Humain {
    private Coord pos = new Coord();
    private String[] messages;
    private Coord futurePos = new Coord();

    public boolean vivant;

    public Humain(int x, int y) {
        this.pos.x = x;
        this.pos.y = y;
        this.vivant = true;
    }

    public String[] getMessages() {
        return this.messages;
    }

    public Coord getPosition() {
        return this.pos;
    }

    public void changerPosition(int x, int y) {
        this.pos.x = x;
        this.pos.y = y;
    }
    
    public void rencontre(Humain h){}

    public void mort() {
        this.vivant = false;
    }
}
