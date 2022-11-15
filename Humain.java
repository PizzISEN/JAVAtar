import java.util.ArrayList;
import java.security.SecureRandom;

public abstract class Humain {
    private Coord pos = new Coord();
    private ArrayList<String> messages = new ArrayList<String>();
    private boolean vivant;

    public Humain() {
        this.pos.setCoord(0, 0);
        this.vivant = true;
    }

    public Humain(int x, int y) {
        this.pos.setCoord(x, y);
        this.vivant = true;
    }

    public ArrayList<String> getMessages() {
        return this.messages;
    }

    public Coord getPos() {
        return this.pos;
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

    public void seDeplacer(Carte c) {
        int directionX, directionY;
        Boolean newPosFound = false;
        ArrayList<Coord> casesDispo = c.caseDispo(this.getPos());

        int x = this.pos.getX();
        int y = this.pos.getY();

        System.out.println("\n" + c + "\n");

        while (!newPosFound) {

            directionX = new SecureRandom().nextInt(3) - 1;
            directionY = new SecureRandom().nextInt(3) - 1;

            System.out.println(directionX + " " + directionY);

            if (casesDispo.contains(new Coord(directionX, directionY))) {
                x += directionX;
                y += directionY;
                System.out.println("Moved to coords: " + directionX + " " + directionY);
                newPosFound = true;
            }    
        }
        
        this.pos.setCoord(x, y);
    }

    public abstract String getEquipe();
    public abstract void rencontre(Humain h);
}
