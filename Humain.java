import java.util.ArrayList;
import java.security.SecureRandom;

public abstract class Humain {
    private Coord pos = new Coord();
    private ArrayList<String> messages = new ArrayList<String>();
    private Coord futurePos = new Coord();
    private boolean vivant;

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

    public void setMessages(ArrayList<String> m) {
        this.messages = m;
    }

    public void mort() {
        this.vivant = false;
    }

    public Boolean estVivant() {
        return this.vivant;
    }

    public void partagerMessages(Humain h) {
        ArrayList<String> additionMessages = h.getMessages();
            
        for (String message : this.getMessages()) {
            if(!additionMessages.contains(message)) {
                additionMessages.add(message);
            }
        }

        h.setMessages(additionMessages);
        this.setMessages(additionMessages);
    }
    
    public int lancerDeDes() {
        SecureRandom rand = new SecureRandom();
        return rand.nextInt(6);
    }

    public abstract String getEquipe();
    public abstract void rencontre(Humain h);
}
