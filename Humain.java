import java.util.ArrayList;

public abstract class Humain {
    private Coord pos = new Coord();
    private ArrayList<String> messages = new ArrayList<String>();
    private Coord futurePos = new Coord();

    public boolean vivant;

    public Humain(int x, int y) {
        this.pos.x = x;
        this.pos.y = y;
        this.vivant = true;
    }

    public ArrayList<String> getMessages() {
        return this.messages;
    }

    public Coord getPos() {
        return this.pos;
    }

    public Coord getFuturePos() {
        return this.futurePos;
    }

    public void setPos(int x, int y) {
        this.pos.x = x;
        this.pos.y = y;
    }

    public void setFuturePos(int x, int y) {
        this.pos.x = x;
        this.pos.y = y;
    }

    public void ajouterMessage(String m) {
        this.messages.add(m);
    }
    
    public void rencontre(Humain h){}

    public void mort() {
        this.vivant = false;
    }
}
